package com.coach.miapi.modules.abilities.toolabilities;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.dries007.tfc.common.items.ProspectResult;
import net.dries007.tfc.network.PacketHandler;
import net.dries007.tfc.network.ProspectedPacket;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.events.ProspectedEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;
import smartin.miapi.modules.abilities.ToolAbilities;
import smartin.miapi.modules.properties.AbilityMangerProperty;
import smartin.miapi.modules.properties.LoreProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.coach.miapi.item.modular.items.ModularPropick.scanAreaFor;

/**
 * When this ability is applied to a module, that module will be capable of prospecting for ore. This ability
 * comes with a few properties that should be understood before this ability.
 */
public class PropickAbility extends ToolAbilities {

    public static final String KEY = "propick_ability";
    public float falseNegativeChance;
    public int radius;
    public String prospectMap;

    public PropickAbility() {
        LoreProperty.bottomLoreSuppliers.add(itemStack -> {
            List<Component> texts = new ArrayList<>();
            if (AbilityMangerProperty.isPrimaryAbility(this, itemStack)) {

                updateValues(itemStack);

                Component raw = Component.translatable("tfc.tooltip.propick.accuracy",
                        (int) (100.0F * (1.0F - this.falseNegativeChance))).withStyle(ChatFormatting.LIGHT_PURPLE);
                texts.add(raw);

                Component radiusText = Component.translatable("miapi.tooltip.propick.radius", this.radius)
                        .withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.AQUA);
                texts.add(radiusText);

                Component prospectMap = Component.translatable("miapi.tooltip.propick.prospectMap", this.prospectMap)
                        .withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.YELLOW);
                texts.add(prospectMap);
            }
            return texts;
        });
    }

    public void updateValues(ItemStack itemStack) {

        double accuracyValue = AccuracyProperty.property.getValueSafe(itemStack);
        this.falseNegativeChance = (float) calculate(accuracyValue);

        this.radius = (int) RadiusProperty.property.getValueSafe(itemStack);

        this.prospectMap = ProspectMapProperty.getProspectMap();
    }

    //Todo Remember to move these two into their corresponding properties.
    public static int getRadius(ItemStack itemStack) {
        return (int) RadiusProperty.property.getValueSafe(itemStack);
    }

    public static float getFalseNegativeChance(ItemStack itemStack) {
        double accuracyValue = AccuracyProperty.property.getValueSafe(itemStack);
        return (float) calculate(accuracyValue);
    }

    //This calculation is run on the value of AccuracyValue which is then converted to falseNegativeChance.
    public static double calculate(double value) {
        return 0.3F - Mth.clamp(value, 1, 5) * 0.060000002F;
    }

    @Override
    public boolean useCooldown(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        return super.useCooldown(stack, world, user, remainingUseTicks);
    }

    @Override
    public Optional<BlockState> getBlockState(BlockState blockState, UseOnContext context) {
        return Optional.empty();
    }

    public InteractionResult useOnBlock(UseOnContext context) {

        ItemStack itemStack = context.getItemInHand();
        int radius = getRadius(itemStack);
        float falseNegativeChance = getFalseNegativeChance(itemStack);
        String tagString = this.prospectMap;
        @SuppressWarnings("removal") TagKey<Block> tag = TagKey.create(Registries.BLOCK, new ResourceLocation(tagString));

        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (player instanceof ServerPlayer serverPlayer) {
            SoundType sound = state.getSoundType(level, pos, player);
            Random random = new Random();
            level.playSound(player, pos, sound.getHitSound(), SoundSource.PLAYERS, sound.getVolume(), sound.getPitch());
            context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
            player.getCooldowns().addCooldown(itemStack.getItem(), 10);
            Block found = state.getBlock();
            random.setSeed(Helpers.hash(19827384739241223L, pos));
            ProspectResult result;
            if (Helpers.isBlock(state, tag)) {
                result = ProspectResult.FOUND;
            } else if (random.nextFloat() < falseNegativeChance) {
                result = ProspectResult.NOTHING;
            } else {
                Object2IntMap<Block> states = scanAreaFor(level, pos, radius, this.prospectMap);
                if (states.isEmpty()) {
                    result = ProspectResult.NOTHING;
                } else {
                    ArrayList<Block> stateKeys = new ArrayList<>(states.keySet());
                    found = stateKeys.get(random.nextInt(stateKeys.size()));
                    int amount = states.getOrDefault(found, 1);
                    if (amount < 10) {
                        result = ProspectResult.TRACES;
                    } else if (amount < 20) {
                        result = ProspectResult.SMALL;
                    } else if (amount < 40) {
                        result = ProspectResult.MEDIUM;
                    } else if (amount < 80) {
                        result = ProspectResult.LARGE;
                    } else {
                        result = ProspectResult.VERY_LARGE;
                    }
                }
            }
            MinecraftForge.EVENT_BUS.post(new ProspectedEvent(player, result, found));
            PacketHandler.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new ProspectedPacket(found, result));
        }

        return InteractionResult.SUCCESS;
    }
}