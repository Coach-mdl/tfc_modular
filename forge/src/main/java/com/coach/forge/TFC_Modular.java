package com.coach.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.coach.tfc_modular;

@Mod(tfc_modular.MOD_ID)
public final class TFC_Modular {
    public TFC_Modular() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(tfc_modular.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        tfc_modular.init();
    }
}
