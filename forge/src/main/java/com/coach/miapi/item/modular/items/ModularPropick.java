package com.coach.miapi.item.modular.items;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.items.PropickItem;
import net.dries007.tfc.util.Helpers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import smartin.miapi.config.MiapiConfig;
import smartin.miapi.item.modular.ModularItem;
import smartin.miapi.item.modular.PlatformModularItemMethods;
import smartin.miapi.item.modular.items.ModularSetableToolMaterial;
import smartin.miapi.item.modular.items.ModularToolMaterial;
import smartin.miapi.modules.abilities.util.ItemAbilityManager;
import smartin.miapi.modules.properties.*;
import smartin.miapi.modules.properties.mining.MiningLevelProperty;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModularPropick extends PropickItem implements PlatformModularItemMethods, ModularItem, ModularSetableToolMaterial {
    public static final Map<Block, Block> REPRESENTATIVE_BLOCKS = new IdentityHashMap<>();
    public Tier currentFakeToolmaterial = ModularToolMaterial.toolMaterial;

    public ModularPropick() {
        super(new ModularToolMaterial(), 5, 5, new Properties().stacksTo(1).durability(500).rarity(Rarity.COMMON));
    }

    public static synchronized void registerRepresentative(Block representative, Block... blocks) {
        for (Block block : blocks) {
            REPRESENTATIVE_BLOCKS.put(block, representative);
        }

    }

    public static Block getRepresentative(Block block) {
        return REPRESENTATIVE_BLOCKS.getOrDefault(block, block);
    }

    @SuppressWarnings("unused")
    /* This isn't used anywhere, but it is part of the original TFC propickItem class. It could be useful to someone so it's staying.
    Since modular prospecting relies on a block tag, this method probably won't see much use. */
    public static void registerDefaultRepresentativeBlocks() {
        TFCBlocks.GRADED_ORES.forEach((rock, ores) -> ores.forEach((ore, blocks) -> registerRepresentative((Block) ((RegistryObject<?>) blocks.get(Ore.Grade.NORMAL)).get(), (Block) ((RegistryObject<?>) blocks.get(Ore.Grade.RICH)).get(), (Block) ((RegistryObject<?>) blocks.get(Ore.Grade.POOR)).get())));
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

    public Tier getTier() {
        if (MiapiConfig.INSTANCE.server.other.looseToolMaterial) {
            return currentFakeToolmaterial;
        }
        return super.getTier();
    }

    @Override
    public void lastItemStack(Tier toolMaterial) {
        this.currentFakeToolmaterial = toolMaterial;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float) stack.getDamageValue() * 13.0F / ModularItem.getDurability(stack));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float f = Math.max(0.0F, ((float) ModularItem.getDurability(stack) - (float) stack.getDamageValue()) / ModularItem.getDurability(stack));
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return RepairPriority.getRepairValue(stack, ingredient) > 0;
    }

    @Override
    public Component getName(ItemStack stack) {
        return DisplayNameProperty.getDisplayText(stack);
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (ToolOrWeaponProperty.isWeapon(stack)) {
            stack.hurtAndBreak(1, attacker, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        } else {
            stack.hurtAndBreak(2, attacker, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return ArrayListMultimap.create();
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
            if (ToolOrWeaponProperty.isWeapon(stack)) {
                stack.hurtAndBreak(2, miner, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            } else {
                stack.hurtAndBreak(1, miner, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player miner) {
        return MiningLevelProperty.canMine(state, world, pos, miner);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return MiningLevelProperty.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return ItemAbilityManager.getUseAction(stack, () -> super.getUseAnimation(stack));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return ItemAbilityManager.getMaxUseTime(stack, () -> super.getUseDuration(stack));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        return ItemAbilityManager.use(world, user, hand, () -> super.use(world, user, hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        ItemAbilityManager.onStoppedUsing(stack, world, user, remainingUseTicks, () -> super.releaseUsing(stack, world, user, remainingUseTicks));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        return ItemAbilityManager.finishUsing(stack, world, user, () -> super.finishUsingItem(stack, world, user));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return RarityProperty.getRarity(stack);
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        ItemAbilityManager.usageTick(world, user, stack, remainingUseTicks, () -> super.onUseTick(world, user, stack, remainingUseTicks));
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
        return ItemAbilityManager.useOnEntity(stack, user, entity, hand, () -> super.interactLivingEntity(stack, user, entity, hand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return ItemAbilityManager.useOnBlock(context, () -> super.useOn(context));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        LoreProperty.appendLoreTop(stack, world, tooltip, context);
    }
}


