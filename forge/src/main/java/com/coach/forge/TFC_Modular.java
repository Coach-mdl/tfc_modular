package com.coach.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(com.coach.TFC_Modular.MOD_ID)
public final class TFC_Modular {
    public TFC_Modular() {
        // Intellisense complains about FMLJavaModLoadingContext.get() being deprecated since 1.21 but this is 1.20.1 so we ignore this.
        //noinspection removal
        EventBuses.registerModEventBus(com.coach.TFC_Modular.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        com.coach.TFC_Modular.init();
    }
}
