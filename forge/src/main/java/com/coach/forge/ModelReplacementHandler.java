package com.coach.forge;

import com.coach.TFC_Modular;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = TFC_Modular.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelReplacementHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onModelBaking(ModelEvent.ModifyBakingResult event) {

        System.out.println("Modular DEBUG: Model replacement handler called!");

        Map<ResourceLocation, BakedModel> onModelRegistry = event.getModels();

        ResourceLocation customModelKey = ResourceLocation.fromNamespaceAndPath("tfc_modular", "models/block/modular_work_bench");
        BakedModel customModel = onModelRegistry.get(customModelKey);

        if (customModel == null) {
            System.out.println("Modular DEBUG: Custom model not found at:" + customModelKey);
        } else {

            System.out.println("Modular DEBUG: Found custom model!" + customModelKey);

            int replacedCount = 0;
            for (ResourceLocation key : onModelRegistry.keySet()) {
                if (key.getNamespace().equals("miapi") && key.getNamespace().startsWith("modular_work_bench")) {
                    System.out.println("Modular DEBUG: Replacing: " + key);
                    onModelRegistry.put(key, customModel);
                    replacedCount++;
                }
            }
            System.out.println("Modular DEBUG: Replaced " + replacedCount + " model variants");
        }

        System.out.println("Modular DEBUG: Model replacement handler finished!");
    }
}
