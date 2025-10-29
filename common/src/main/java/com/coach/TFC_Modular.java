package com.coach;

import smartin.miapi.item.modular.items.ModularWeapon;
import smartin.miapi.registries.RegistryInventory;

public final class TFC_Modular {
    public static final String MOD_ID = "tfc_modular";

    public static void init() {

        RegistryInventory.register(RegistryInventory.modularItems, "modular_mace", ModularWeapon::new);

    }
}

//TODO
// Turn existing overlay modules into synergies.
// Apply Miapi stun to maces and smithing hammers.
// Add Prospector's picks.
// Add Chisels.
// Add Saws.
// add halberd and warhammer modules reminiscent of an old 1.7.10 TFC addon.
// add Claymore module for fun. Balance it between a longsword and a zweihander.
// Figure out why maces have wrong durability.
// Allow Miapi tools to be placed on item racks.
// Add TFC damage types to tools.
// Epic fight and better combat movesets.
// Modular workbench recipe.
// New modular workbench model and textures because I want to.
// Maybe change texture shading to fit TFC.
// TFC Gems and effects.
// Look into material generation. Potentially recategorise some of the wood types.
// 1.21 port.
