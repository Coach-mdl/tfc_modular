package com.coach;

import smartin.miapi.item.modular.items.ModularPickaxe;
import smartin.miapi.item.modular.items.ModularWeapon;
import smartin.miapi.registries.RegistryInventory;

public final class TFC_Modular {
    public static final String MOD_ID = "tfc_modular";

    public static void init() {

        RegistryInventory.register(RegistryInventory.modularItems, "modular_mace", ModularWeapon::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_propick", ModularPickaxe::new);

    }
}

//TODO
// Move Forge specific data in the forge Subproject.
// Add Prospector's picks.
// Add Chisels.
// Add Saws.
// add halberd and warhammer modules reminiscent of an old 1.7.10 TFC addon.
// add Claymore module for fun. Balance it between a longsword and a zweihander.
// Allow Miapi tools to be placed on tool racks.
// Add TFC damage types to tools.
// Epic fight and better combat movesets.
// Modular workbench recipe.
// New modular workbench model and textures because I want to.
// Finish en_us lang.
// TFC Gems and effects.
// Replace current material colours with grayscale maps (metals are done).
// Recategorise some of the wood types.
// 1.21 port.
