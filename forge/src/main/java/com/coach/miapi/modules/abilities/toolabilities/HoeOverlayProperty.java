package com.coach.miapi.modules.abilities.toolabilities;

import com.coach.TFC_Modular;
import net.dries007.tfc.client.HoeOverlays;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.fml.common.Mod;

/**
 * This property allows tools with HoeAbility to display the gui for TFC farmland.
 * This property automatically applies to any module with HoeAbility.
 */

@Mod.EventBusSubscriber(modid = TFC_Modular.MOD_ID, value = Dist.CLIENT)
public class HoeOverlayProperty {

    @SuppressWarnings("unused")
    public static void RenderTFCHoeOverlay(RenderGuiOverlayEvent.Post event) {
        GuiGraphics stack = event.getGuiGraphics();
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player != null) {
            boolean hasHoeAbility = ModularEnums.canTill(player.getMainHandItem()) || ModularEnums.canTill(player.getOffhandItem());
            if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type() && minecraft.screen == null && hasHoeAbility &&
                    (!(Boolean) TFCConfig.CLIENT.showHoeOverlaysOnlyWhenShifting.get() || player.isShiftKeyDown())) {
                HoeOverlays.render(minecraft, event.getWindow(), stack);
            }
        }
    }
}
