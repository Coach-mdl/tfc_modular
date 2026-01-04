package com.coach.miapi.modules.abilities.toolabilities;

import net.dries007.tfc.client.HoeOverlays;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import smartin.miapi.modules.properties.util.BooleanProperty;
import smartin.miapi.modules.properties.util.ModuleProperty;

/**
 * This property allows tools with HoeAbility to display the gui for TFC farmland.
 * This property automatically applies to any module with HoeAbility.
 */
public class HoeOverlayProperty extends BooleanProperty implements ModuleProperty {
    public static final String KEY = "hoeOverlay";
    public static HoeOverlayProperty property;


    public HoeOverlayProperty() {
        super(KEY, false);
        property = this;

    }

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
