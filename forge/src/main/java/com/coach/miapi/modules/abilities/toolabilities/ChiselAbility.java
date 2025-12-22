package com.coach.miapi.modules.abilities.toolabilities;

import com.mojang.datafixers.util.Either;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.player.PlayerData;
import net.dries007.tfc.common.recipes.ChiselRecipe;
import net.dries007.tfc.common.recipes.CollapseRecipe;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.advancements.TFCAdvancements;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;
import smartin.miapi.modules.abilities.ToolAbilities;
import smartin.miapi.modules.properties.AbilityMangerProperty;
import smartin.miapi.modules.properties.LoreProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ChiselAbility extends ToolAbilities {
    public static final String KEY = "chisel_ability";

    public ChiselAbility() {
        LoreProperty.bottomLoreSuppliers.add(itemStack -> {
            List<Component> texts = new ArrayList<>();
            if (canChisel(itemStack)) {

                Component chiselText = Component.translatable("miapi.tooltip.chisel.canchisel")
                        .withStyle(ChatFormatting.GREEN);
                texts.add(chiselText);
            }
            return texts;
        });
    }

    @Override
    public boolean useCooldown(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        return super.useCooldown(stack, world, user, remainingUseTicks);
    }

    public InteractionResult useOnBlock(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);
            Either<BlockState, InteractionResult> result = ChiselRecipe.computeResult(player, state, new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, context.isInside()), true);
            return result.map((resultState) -> {
                player.playSound(resultState.getSoundType().getHitSound(), 1.0F, 1.0F);
                ItemStack held = player.getMainHandItem();
                if (!level.isClientSide) {
                    if (TFCConfig.SERVER.enableChiselsStartCollapses.get() && Helpers.isBlock(state, TFCTags.Blocks.CAN_TRIGGER_COLLAPSE) && CollapseRecipe.tryTriggerCollapse(level, pos)) {
                        return InteractionResult.SUCCESS;
                    }

                    PlayerData cap = PlayerData.get(player);
                    ChiselRecipe recipeUsed = ChiselRecipe.getRecipe(state, held, cap.getChiselMode());
                    if (recipeUsed != null) {
                        ItemStack extraDrop = recipeUsed.getExtraDrop(held);
                        if (!extraDrop.isEmpty()) {
                            ItemHandlerHelper.giveItemToPlayer(player, extraDrop);
                        }
                    }
                }

                level.setBlockAndUpdate(pos, resultState);
                if (player instanceof ServerPlayer serverPlayer) {
                    TFCAdvancements.CHISELED.trigger(serverPlayer, resultState);
                }

                held.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                player.getCooldowns().addCooldown(held.getItem(), 10);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }, Function.identity());
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public Optional<BlockState> getBlockState(BlockState blockState, UseOnContext context) {
        return Optional.empty();
    }

    public static boolean canChisel(ItemStack itemStack) {
        String regex = ".*ChiselAbility.*";
        return AbilityMangerProperty.get(itemStack).toString().matches(regex);
    }
}
