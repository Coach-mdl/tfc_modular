package com.coach.forge;

import com.coach.TFC_Modular;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import smartin.miapi.Miapi;

import java.util.Map;

/**
 * This class exists solely to replace the modular workbench model with something "TFC appropriate".
 * ModelEvents took me 2 and a half days to understand.
 */
@Mod.EventBusSubscriber(modid = TFC_Modular.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelReplacementHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
        event.register(ResourceLocation.fromNamespaceAndPath(TFC_Modular.MOD_ID, "modular_workbench_north"));
        event.register(ResourceLocation.fromNamespaceAndPath(TFC_Modular.MOD_ID, "modular_workbench_south"));
        event.register(ResourceLocation.fromNamespaceAndPath(TFC_Modular.MOD_ID, "modular_workbench_east"));
        event.register(ResourceLocation.fromNamespaceAndPath(TFC_Modular.MOD_ID, "modular_workbench_west"));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onModelBaking(ModelEvent.ModifyBakingResult event) {

        System.out.println("Modular DEBUG: Model replacement handler called!");

        Map<ResourceLocation, BakedModel> onModelRegistry = event.getModels();

        ResourceLocation miapiKey = ResourceLocation.fromNamespaceAndPath(Miapi.MOD_ID, "modular_work_bench");

        ResourceLocation customNorth = ResourceLocation.fromNamespaceAndPath(TFC_Modular.MOD_ID, "modular_workbench_north");
        ResourceLocation customSouth = ResourceLocation.fromNamespaceAndPath(TFC_Modular.MOD_ID, "modular_workbench_south");
        ResourceLocation customEast = ResourceLocation.fromNamespaceAndPath(TFC_Modular.MOD_ID, "modular_workbench_east");
        ResourceLocation customWest = ResourceLocation.fromNamespaceAndPath(TFC_Modular.MOD_ID, "modular_workbench_west");

        BakedModel customModelNorth = onModelRegistry.get(customNorth);
        BakedModel customModelSouth = onModelRegistry.get(customSouth);
        BakedModel customModelEast = onModelRegistry.get(customEast);
        BakedModel customModelWest = onModelRegistry.get(customWest);

        ModelResourceLocation northVariant = new ModelResourceLocation(miapiKey, "facing=north");
        ModelResourceLocation southVariant = new ModelResourceLocation(miapiKey, "facing=south");
        ModelResourceLocation eastVariant = new ModelResourceLocation(miapiKey, "facing=east");
        ModelResourceLocation westVariant = new ModelResourceLocation(miapiKey, "facing=west");
        ModelResourceLocation inventoryVariant = new ModelResourceLocation(miapiKey, "inventory");

        onModelRegistry.put(northVariant, customModelNorth);
        onModelRegistry.put(southVariant, customModelSouth);
        onModelRegistry.put(eastVariant, customModelEast);
        onModelRegistry.put(westVariant, customModelWest);
        onModelRegistry.put(inventoryVariant, customModelNorth);

        System.out.println("Modular DEBUG: Model replacement handler finished!");
    }
}
