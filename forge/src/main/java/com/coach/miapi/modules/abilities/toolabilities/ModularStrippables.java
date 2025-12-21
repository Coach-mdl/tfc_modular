package com.coach.miapi.modules.abilities.toolabilities;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.dries007.tfc.common.blocks.wood.Wood;

import java.util.Arrays;
import java.util.List;

/**
 * Adds TFC logs to the STRIPPABLES map. Praise the Architectury.
 * Use addWoods to add more strippables. Or don't.
 */
public class ModularStrippables {
    public static void addWoods() {
        List<Wood> TFC = Arrays.stream(Wood.VALUES).toList();

        for (Wood wood : TFC) {
            AxeItemHooks.addStrippable(wood.getBlock(Wood.BlockType.LOG).get(), wood.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(wood.getBlock(Wood.BlockType.WOOD).get(), wood.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
        }
    }
}

