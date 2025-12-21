package com.coach.forge;

import com.coach.miapi.modules.abilities.toolabilities.ModularStrippables;
import com.coach.miapi.modules.abilities.toolabilities.*;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.LoggerFactory;
import smartin.miapi.item.modular.items.ModularAxe;
import smartin.miapi.item.modular.items.ModularPickaxe;
import smartin.miapi.item.modular.items.ModularShovel;
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

        TFC_Modular.LOGGER.info("TFC Modular initialisation...");

        RegistryInventory.register(RegistryInventory.modularItems, "modular_mace", ModularAxe::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_propick", ModularPickaxe::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_saw", ModularAxe::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_chisel", ModularShovel::new);

        registerMiapi(useAbilityRegistry, PropickAbility.KEY, new PropickAbility());

        registerMiapi(moduleProperties, TreeFellingProperty.KEY, new TreeFellingProperty());
        registerMiapi(moduleProperties, AccuracyProperty.KEY, new AccuracyProperty());
        registerMiapi(moduleProperties, ProspectRadiusProperty.KEY, new ProspectRadiusProperty());
        registerMiapi(moduleProperties, ProspectTagProperty.KEY, new ProspectTagProperty());

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TFC_Modular.LOGGER.info("TFC Modular common setup...");

        ModularStrippables.addWoods();

    }

}
