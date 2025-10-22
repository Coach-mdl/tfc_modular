package com.coach;

import smartin.miapi.item.modular.items.ModularWeapon;
import smartin.miapi.registries.RegistryInventory;

public final class TFC_Modular {
    public static final String MOD_ID = "tfc_modular";

    public static void init() {

        RegistryInventory.register(RegistryInventory.modularItems, "modular_mace", ModularWeapon::new);

    }
}

//TODO Turn existing overlay modules into synergies.
//TODO Fix mace and hammer attack speeds.
//TODO Add Miapi stun to maces.
//TODO Allow Miapi tools to be placed on item racks.
//TODO Add TFC damage types to tools.
//TODO Add Propicks.
//TODO Add Chisels.
//TODO Add Saws.
//TODO Maybe change texture shading to fit TFC.