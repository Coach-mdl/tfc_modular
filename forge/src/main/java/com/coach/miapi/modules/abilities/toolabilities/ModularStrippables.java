package com.coach.miapi.modules.abilities.toolabilities;

import com.coach.forge.TFC_Modular;
import com.therighthon.afc.AFC;
import com.therighthon.afc.common.blocks.AFCWood;
import com.therighthon.afc.common.blocks.AncientLogs;
import com.therighthon.afc.common.blocks.UniqueLogs;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;
import java.util.List;

/**
 * Adds TFC and AFC logs to the STRIPPABLES map. Praise the Architectury.
 * Use addWoods to add more strippables. Or don't.
 */
public class ModularStrippables {
    public static void addWoods() {
        List<Wood> TFC = Arrays.stream(Wood.VALUES).toList();

        for (Wood wood : TFC) {
            AxeItemHooks.addStrippable(wood.getBlock(Wood.BlockType.LOG).get(), wood.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(wood.getBlock(Wood.BlockType.WOOD).get(), wood.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
        }//Please add other strippables below this and not AFC.

        if (ModList.get().isLoaded(AFC.MOD_ID)) {
            TFC_Modular.LOGGER.info("Adding AFC logs to STRIPPABLES map.");
            List<AFCWood> AFC = Arrays.stream(AFCWood.VALUES).toList();

            for (AFCWood wood : AFC) {
                AxeItemHooks.addStrippable(wood.getBlock(Wood.BlockType.LOG).get(), wood.getBlock(Wood.BlockType.STRIPPED_LOG).get());
                AxeItemHooks.addStrippable(wood.getBlock(Wood.BlockType.WOOD).get(), wood.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            }

            AxeItemHooks.addStrippable(UniqueLogs.BLACK_OAK.getBlock(UniqueLogs.BlockType.LOG).get(), Wood.OAK.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(UniqueLogs.BLACK_OAK.getBlock(UniqueLogs.BlockType.WOOD).get(), Wood.OAK.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(UniqueLogs.POPLAR.getBlock(UniqueLogs.BlockType.LOG).get(), Wood.ASPEN.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(UniqueLogs.POPLAR.getBlock(UniqueLogs.BlockType.WOOD).get(), Wood.ASPEN.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(UniqueLogs.GUM_ARABIC.getBlock(UniqueLogs.BlockType.LOG).get(), Wood.ACACIA.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(UniqueLogs.GUM_ARABIC.getBlock(UniqueLogs.BlockType.WOOD).get(), Wood.ACACIA.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(UniqueLogs.RAINBOW_EUCALYPTUS.getBlock(UniqueLogs.BlockType.LOG).get(), AFCWood.EUCALYPTUS.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(UniqueLogs.RAINBOW_EUCALYPTUS.getBlock(UniqueLogs.BlockType.WOOD).get(), AFCWood.EUCALYPTUS.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(UniqueLogs.REDCEDAR.getBlock(UniqueLogs.BlockType.LOG).get(), AFCWood.CYPRESS.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(UniqueLogs.REDCEDAR.getBlock(UniqueLogs.BlockType.WOOD).get(), AFCWood.CYPRESS.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(UniqueLogs.RUBBER_FIG.getBlock(UniqueLogs.BlockType.LOG).get(), AFCWood.FIG.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(UniqueLogs.RUBBER_FIG.getBlock(UniqueLogs.BlockType.WOOD).get(), AFCWood.FIG.getBlock(Wood.BlockType.STRIPPED_WOOD).get());

            //Below is the point of no return. Ancient logs don't drop themselves, but I spent too much time doing this.
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_ACACIA.getBlock(AncientLogs.BlockType.LOG).get(), Wood.ACACIA.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_ACACIA.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.ACACIA.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_ASH.getBlock(AncientLogs.BlockType.LOG).get(), Wood.ASH.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_ASH.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.ASH.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_ASPEN.getBlock(AncientLogs.BlockType.LOG).get(), Wood.ASPEN.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_ASPEN.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.ASPEN.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_BAOBAB.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.BAOBAB.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_BAOBAB.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.BAOBAB.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_BIRCH.getBlock(AncientLogs.BlockType.LOG).get(), Wood.BIRCH.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_BIRCH.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.BIRCH.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_BLACK_OAK.getBlock(AncientLogs.BlockType.LOG).get(), Wood.OAK.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_BLACK_OAK.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.OAK.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_BLACKWOOD.getBlock(AncientLogs.BlockType.LOG).get(), Wood.BLACKWOOD.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_BLACKWOOD.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.BLACKWOOD.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_CHESTNUT.getBlock(AncientLogs.BlockType.LOG).get(), Wood.CHESTNUT.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_CHESTNUT.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.CHESTNUT.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_EUCALYPTUS.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.EUCALYPTUS.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_EUCALYPTUS.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.EUCALYPTUS.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_MAHOGANY.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.MAHOGANY.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_MAHOGANY.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.MAHOGANY.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_HEVEA.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.HEVEA.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_HEVEA.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.HEVEA.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_TEAK.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.TEAK.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_TEAK.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.TEAK.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_TUALANG.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.TUALANG.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_TUALANG.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.TUALANG.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_CYPRESS.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.CYPRESS.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_CYPRESS.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.CYPRESS.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_FIG.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.FIG.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_FIG.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.FIG.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_IRONWOOD.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.IRONWOOD.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_IRONWOOD.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.IRONWOOD.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_IPE.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.IPE.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_IPE.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.IPE.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_DOUGLAS_FIR.getBlock(AncientLogs.BlockType.LOG).get(), Wood.DOUGLAS_FIR.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_DOUGLAS_FIR.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.DOUGLAS_FIR.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_HICKORY.getBlock(AncientLogs.BlockType.LOG).get(), Wood.HICKORY.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_HICKORY.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.HICKORY.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_KAPOK.getBlock(AncientLogs.BlockType.LOG).get(), Wood.KAPOK.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_KAPOK.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.KAPOK.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_MAPLE.getBlock(AncientLogs.BlockType.LOG).get(), Wood.MAPLE.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_MAPLE.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.MAPLE.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_OAK.getBlock(AncientLogs.BlockType.LOG).get(), Wood.OAK.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_OAK.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.OAK.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_PALM.getBlock(AncientLogs.BlockType.LOG).get(), Wood.PALM.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_PALM.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.PALM.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_PINE.getBlock(AncientLogs.BlockType.LOG).get(), Wood.PINE.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_PINE.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.PINE.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_ROSEWOOD.getBlock(AncientLogs.BlockType.LOG).get(), Wood.ROSEWOOD.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_ROSEWOOD.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.ROSEWOOD.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_SEQUOIA.getBlock(AncientLogs.BlockType.LOG).get(), Wood.SEQUOIA.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_SEQUOIA.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.SEQUOIA.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_SPRUCE.getBlock(AncientLogs.BlockType.LOG).get(), Wood.SPRUCE.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_SPRUCE.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.SPRUCE.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_SYCAMORE.getBlock(AncientLogs.BlockType.LOG).get(), Wood.SYCAMORE.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_SYCAMORE.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.SYCAMORE.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_WHITE_CEDAR.getBlock(AncientLogs.BlockType.LOG).get(), Wood.WHITE_CEDAR.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_WHITE_CEDAR.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.WHITE_CEDAR.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_WILLOW.getBlock(AncientLogs.BlockType.LOG).get(), Wood.WILLOW.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_WILLOW.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.WILLOW.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_RAINBOW_EUCALYPTUS.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.EUCALYPTUS.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_RAINBOW_EUCALYPTUS.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.EUCALYPTUS.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_GUM_ARABIC.getBlock(AncientLogs.BlockType.LOG).get(), Wood.ACACIA.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_GUM_ARABIC.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.ACACIA.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_REDCEDAR.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.CYPRESS.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_REDCEDAR.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.CYPRESS.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_RUBBER_FIG.getBlock(AncientLogs.BlockType.LOG).get(), AFCWood.FIG.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_RUBBER_FIG.getBlock(AncientLogs.BlockType.WOOD).get(), AFCWood.FIG.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_POPLAR.getBlock(AncientLogs.BlockType.LOG).get(), Wood.ASPEN.getBlock(Wood.BlockType.STRIPPED_LOG).get());
            AxeItemHooks.addStrippable(AncientLogs.ANCIENT_POPLAR.getBlock(AncientLogs.BlockType.WOOD).get(), Wood.ASPEN.getBlock(Wood.BlockType.STRIPPED_WOOD).get());
        }
    }
}

