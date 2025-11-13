package com.coach.forge;

import com.coach.miapi.item.modular.items.ModularPropick;
import com.coach.miapi.modules.abilities.toolabilities.AccuracyProperty;
import com.coach.miapi.modules.abilities.toolabilities.PropickAbility;
import com.coach.miapi.modules.abilities.toolabilities.ProspectMapProperty;
import com.coach.miapi.modules.abilities.toolabilities.RadiusProperty;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import smartin.miapi.item.modular.items.ModularWeapon;
import smartin.miapi.registries.RegistryInventory;

import static smartin.miapi.modules.abilities.util.ItemAbilityManager.useAbilityRegistry;
import static smartin.miapi.registries.RegistryInventory.moduleProperties;
import static smartin.miapi.registries.RegistryInventory.registerMiapi;

@Mod(com.coach.TFC_Modular.MOD_ID)
public final class TFC_Modular {
    public TFC_Modular() {
        //noinspection removal
        EventBuses.registerModEventBus(com.coach.TFC_Modular.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        com.coach.TFC_Modular.init();

        RegistryInventory.register(RegistryInventory.modularItems, "modular_mace", ModularWeapon::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_propick", ModularPropick::new);

        registerMiapi(useAbilityRegistry, PropickAbility.KEY, new PropickAbility());
        registerMiapi(moduleProperties, AccuracyProperty.KEY, new AccuracyProperty());
        registerMiapi(moduleProperties, RadiusProperty.KEY, new RadiusProperty());
        registerMiapi(moduleProperties, ProspectMapProperty.KEY, new ProspectMapProperty());

    }
}
