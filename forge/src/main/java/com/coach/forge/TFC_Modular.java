package com.coach.forge;

import com.coach.miapi.item.modular.items.ModularPropick;
import com.coach.miapi.modules.abilities.toolabilities.AccuracyProperty;
import com.coach.miapi.modules.abilities.toolabilities.PropickAbility;
import com.coach.miapi.modules.abilities.toolabilities.ProspectMapProperty;
import com.coach.miapi.modules.abilities.toolabilities.RadiusProperty;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.LoggerFactory;
import smartin.miapi.item.modular.items.ModularWeapon;
import smartin.miapi.registries.RegistryInventory;

import static smartin.miapi.modules.abilities.util.ItemAbilityManager.useAbilityRegistry;
import static smartin.miapi.registries.RegistryInventory.moduleProperties;
import static smartin.miapi.registries.RegistryInventory.registerMiapi;

@Mod(com.coach.TFC_Modular.MOD_ID)
public final class TFC_Modular {

    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("tfc_modular");

    public TFC_Modular(FMLJavaModLoadingContext context) {

        IEventBus modEventBus = context.getModEventBus();

        EventBuses.registerModEventBus(com.coach.TFC_Modular.MOD_ID, modEventBus);

        modEventBus.addListener(this::commonSetup);
        com.coach.TFC_Modular.init();

        RegistryInventory.register(RegistryInventory.modularItems, "modular_mace", ModularWeapon::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_propick", ModularPropick::new);

        registerMiapi(useAbilityRegistry, PropickAbility.KEY, new PropickAbility());
        registerMiapi(moduleProperties, AccuracyProperty.KEY, new AccuracyProperty());
        registerMiapi(moduleProperties, RadiusProperty.KEY, new RadiusProperty());
        registerMiapi(moduleProperties, ProspectMapProperty.KEY, new ProspectMapProperty());

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

}
