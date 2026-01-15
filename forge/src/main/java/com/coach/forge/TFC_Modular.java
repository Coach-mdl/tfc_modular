package com.coach.forge;

import com.coach.miapi.modules.abilities.toolabilities.*;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.LoggerFactory;
import smartin.miapi.item.modular.items.ModularAxe;
import smartin.miapi.item.modular.items.ModularPickaxe;
import smartin.miapi.item.modular.items.ModularShovel;
import smartin.miapi.item.modular.items.ModularWeapon;
import smartin.miapi.registries.RegistryInventory;

import java.util.List;

import static smartin.miapi.modules.abilities.util.ItemAbilityManager.useAbilityRegistry;
import static smartin.miapi.registries.RegistryInventory.moduleProperties;
import static smartin.miapi.registries.RegistryInventory.registerMiapi;

@Mod(com.coach.TFC_Modular.MOD_ID)
public final class TFC_Modular {

    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("tfc_modular");

    public TFC_Modular(FMLJavaModLoadingContext context) {

        IEventBus modEventBus = context.getModEventBus();

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        EventBuses.registerModEventBus(com.coach.TFC_Modular.MOD_ID, modEventBus);

        modEventBus.addListener(this::commonSetup);

        com.coach.TFC_Modular.init();

        TFC_Modular.LOGGER.info("TFC Modular initialisation...");

        forgeBus.addListener(TFC_Modular::onLivingHurt);
        forgeBus.addListener(TFC_Modular::onItemTooltip);

        RegistryInventory.register(RegistryInventory.modularItems, "modular_mace", ModularAxe::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_propick", ModularPickaxe::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_saw", ModularAxe::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_chisel", ModularShovel::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_halberd", ModularWeapon::new);
        RegistryInventory.register(RegistryInventory.modularItems, "modular_warhammer", ModularWeapon::new);
        RegistryInventory.register(RegistryInventory.modularItems, "saw_on_a_stick", ModularAxe::new);

        registerMiapi(useAbilityRegistry, PropickAbility.KEY, new PropickAbility());
        registerMiapi(useAbilityRegistry, ChiselAbility.KEY, new ChiselAbility());

        registerMiapi(moduleProperties, TreeFellingProperty.KEY, new TreeFellingProperty());
        registerMiapi(moduleProperties, AccuracyProperty.KEY, new AccuracyProperty());
        registerMiapi(moduleProperties, ProspectRadiusProperty.KEY, new ProspectRadiusProperty());
        registerMiapi(moduleProperties, ProspectTagProperty.KEY, new ProspectTagProperty());
        registerMiapi(moduleProperties, GrassDamageProperty.KEY, new GrassDamageProperty());
        registerMiapi(moduleProperties, DamageTypeProperty.KEY, new DamageTypeProperty());

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TFC_Modular.LOGGER.info("TFC Modular common setup...");

        ModularEnums.addWoods();
        ModularEnums.addSoils();

    }

    public static void onLivingHurt(LivingHurtEvent event) {
        float amount = event.getAmount();
        amount *= DamageTypeProperty.calculateMultiplier(event.getSource(), event.getEntity());
        event.setAmount(amount);
    }

    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> text = event.getToolTip();

        DamageTypeProperty.addTooltipInfo(stack, text);
    }
}
