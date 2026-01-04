package com.coach.miapi.modules.abilities.toolabilities;

import com.mojang.datafixers.util.Pair;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.hooks.item.tool.ShovelItemHooks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import smartin.miapi.mixin.HoeItemAccessor;
import smartin.miapi.modules.properties.AbilityMangerProperty;

import java.util.Arrays;
import java.util.List;

/**
 * Adds TFC values to various enums. Praise the Architectury.
 */
public class ModularEnums {
    public static void addWoods() {
        List<Wood> TFC = Arrays.stream(Wood.VALUES).toList();

        for (Wood wood : TFC) {
            AxeItemHooks.addStrippable(wood.getBlock(Wood.BlockType.LOG).get(), wood.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(wood.getBlock(Wood.BlockType.WOOD).get(), wood.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
        }
    }

    public static void addSoils() {
        List<SoilBlockType.Variant> TFC = Arrays.stream(SoilBlockType.Variant.values()).toList();

        for (SoilBlockType.Variant soilVariant : TFC) {
            HoeItemAccessor.getTILLING_ACTIONS().put(
                    soilVariant.getBlock(SoilBlockType.DIRT).get(),
                    new Pair<>(
                            context -> {
                                if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
                                    return false;
                                }
                                return canTill(context.getItemInHand());
                            },
                            context -> {
                                Level world = context.getLevel();
                                BlockPos pos = context.getClickedPos();
                                BlockState newState = soilVariant.getBlock(SoilBlockType.FARMLAND).get().defaultBlockState();
                                world.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
                            }
                    )
            );

            HoeItemAccessor.getTILLING_ACTIONS().put(soilVariant.getBlock(SoilBlockType.GRASS).get(), new Pair<>(
                            context -> {
                                if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
                                    return false;
                                }
                                return canTill(context.getItemInHand());
                            },
                            context -> {
                                Level world = context.getLevel();
                                BlockPos pos = context.getClickedPos();
                                BlockState newState = soilVariant.getBlock(SoilBlockType.FARMLAND).get().defaultBlockState();
                                world.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
                            }
                    )
            );

            HoeItemAccessor.getTILLING_ACTIONS().put(
                    soilVariant.getBlock(SoilBlockType.CLAY).get(),
                    new Pair<>(
                            context -> {
                                if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
                                    return false;
                                }
                                return canTill(context.getItemInHand());
                            },
                            context -> {
                                Level world = context.getLevel();
                                BlockPos pos = context.getClickedPos();
                                BlockState newState = soilVariant.getBlock(SoilBlockType.FARMLAND).get().defaultBlockState();
                                world.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
                            }
                    )
            );

            HoeItemAccessor.getTILLING_ACTIONS().put(
                    soilVariant.getBlock(SoilBlockType.CLAY_GRASS).get(),
                    new Pair<>(
                            context -> {
                                if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
                                    return false;
                                }
                                return canTill(context.getItemInHand());
                            },
                            context -> {
                                Level world = context.getLevel();
                                BlockPos pos = context.getClickedPos();
                                BlockState newState = soilVariant.getBlock(SoilBlockType.FARMLAND).get().defaultBlockState();
                                world.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
                            }
                    )
            );

            HoeItemAccessor.getTILLING_ACTIONS().put(
                    soilVariant.getBlock(SoilBlockType.ROOTED_DIRT).get(),
                    new Pair<>(
                            context -> {
                                if (!context.getLevel().isEmptyBlock(context.getClickedPos().above())) {
                                    return false;
                                }
                                return canTill(context.getItemInHand());
                            },
                            context -> {
                                Level world = context.getLevel();
                                BlockPos pos = context.getClickedPos();
                                BlockState newState = soilVariant.getBlock(SoilBlockType.DIRT).get().defaultBlockState();
                                world.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
                            }
                    )
            );
            ShovelItemHooks.addFlattenable(soilVariant.getBlock(SoilBlockType.DIRT).get(), soilVariant.getBlock(SoilBlockType.GRASS_PATH).get().defaultBlockState());
            ShovelItemHooks.addFlattenable(soilVariant.getBlock(SoilBlockType.GRASS).get(), soilVariant.getBlock(SoilBlockType.GRASS_PATH).get().defaultBlockState());
            ShovelItemHooks.addFlattenable(soilVariant.getBlock(SoilBlockType.CLAY).get(), soilVariant.getBlock(SoilBlockType.GRASS_PATH).get().defaultBlockState());
            ShovelItemHooks.addFlattenable(soilVariant.getBlock(SoilBlockType.CLAY_GRASS).get(), soilVariant.getBlock(SoilBlockType.GRASS_PATH).get().defaultBlockState());
        }
    }

    public static boolean canTill(ItemStack itemStack) {
        String regex = ".*HoeAbility.*";
        return AbilityMangerProperty.get(itemStack).toString().matches(regex);
    }
}



