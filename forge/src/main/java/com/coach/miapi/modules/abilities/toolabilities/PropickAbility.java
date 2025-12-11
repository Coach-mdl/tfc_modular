package com.coach.miapi.modules.abilities.toolabilities;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.dries007.tfc.client.TFCSounds;
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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;
import smartin.miapi.modules.abilities.ToolAbilities;
import smartin.miapi.modules.properties.AbilityMangerProperty;
import smartin.miapi.modules.properties.LoreProperty;

import java.util.*;

/**
 * When this ability is applied to a module, that module will be capable of prospecting for ore. This ability
 * is a modified version of the propickItem designed to support modularity. The modular parts are split
 * up into their own properties. Tools with AxeAbility cannot prospect do to some limitation within miapi.
 */
public class PropickAbility extends ToolAbilities {

    public static final Map<Block, Block> REPRESENTATIVE_BLOCKS = new IdentityHashMap<>();
    public static final String KEY = "propick_ability";

    public PropickAbility() {
        LoreProperty.bottomLoreSuppliers.add(itemStack -> {
            List<Component> texts = new ArrayList<>();
            if (canProspect(itemStack)) {

                float falseNegativeChance = AccuracyProperty.getFalseNegativeChance(itemStack);
                int prospectRadius = ProspectRadiusProperty.getRadius(itemStack);
                String prospectTag = ProspectTagProperty.getProspectMapData(itemStack);

                Component raw = Component.translatable("tfc.tooltip.propick.accuracy",
                        (int) (100.0F * (1.0F - falseNegativeChance))).withStyle(ChatFormatting.LIGHT_PURPLE);
                texts.add(raw);

                Component radiusText = Component.translatable("miapi.tooltip.propick.radius", prospectRadius)
                        .withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.AQUA);
                texts.add(radiusText);

                Component prospectMap = Component.translatable("miapi.tooltip.propick.prospectMap", prospectTag)
                        .withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.YELLOW);
                texts.add(prospectMap);
            }
            return texts;
        });
    }

    public static Block getRepresentative(Block block) {
        return REPRESENTATIVE_BLOCKS.getOrDefault(block, block);
    }

    public static Object2IntMap<Block> scanAreaFor(Level level, BlockPos center, int radius, String tagString) {
        Object2IntMap<Block> results = new Object2IntOpenHashMap<>();
        @SuppressWarnings("removal") TagKey<Block> tag = TagKey.create(Registries.BLOCK, new ResourceLocation(tagString));

        for (BlockPos cursor : BlockPos.betweenClosed(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center.getX() + radius, center.getY() + radius, center.getZ() + radius)) {
            Block block = getRepresentative(level.getBlockState(cursor).getBlock());
            if (Helpers.isBlock(block, tag)) {
                results.mergeInt(block, 1, Integer::sum);
            }
        }
        return results;
    }

    //I read somewhere that regex may not the greatest for performance so this method may change.
    public static boolean canProspect(ItemStack itemStack) {
        String regex = ".*PropickAbility.*";
        return AbilityMangerProperty.get(itemStack).toString().matches(regex);
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
        float falseNegativeChance = AccuracyProperty.getFalseNegativeChance(itemStack);
        int prospectRadius = ProspectRadiusProperty.getRadius(itemStack);
        String prospectTag = ProspectTagProperty.getProspectMapData(itemStack);
        @SuppressWarnings("removal") TagKey<Block> tag = TagKey.create(Registries.BLOCK, new ResourceLocation(prospectTag));

        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (player instanceof ServerPlayer serverPlayer) {
            level.playSound(null, pos, TFCSounds.KNAP_STONE.get(), SoundSource.BLOCKS, 1F, 1F);
            context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
            player.getCooldowns().addCooldown(itemStack.getItem(), 10);
            Block found = state.getBlock();
            Random random = new Random();
            random.setSeed(Helpers.hash(19827384739241223L, pos));
            ProspectResult result;
            if (Helpers.isBlock(state, tag)) {
                result = ProspectResult.FOUND;
            } else if (random.nextFloat() < falseNegativeChance) {
                result = ProspectResult.NOTHING;
            } else {
                Object2IntMap<Block> states = scanAreaFor(level, pos, prospectRadius, prospectTag);
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