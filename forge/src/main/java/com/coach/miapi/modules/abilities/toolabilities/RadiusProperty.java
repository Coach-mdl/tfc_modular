package com.coach.miapi.modules.abilities.toolabilities;

import net.minecraft.world.item.ItemStack;
import smartin.miapi.modules.properties.util.DoubleProperty;
import smartin.miapi.modules.properties.util.ModuleProperty;

/**
 * This property controls the radius of prospect capable modules using PropickAbility.
 * Try to keep the value a multiple of 2 to prevent anything crazy happening.
 * Default for TerraFirmaCraft is 12.
 */
public class RadiusProperty extends DoubleProperty implements ModuleProperty {
    public static final String KEY = "radius";
    public static RadiusProperty property;

    public RadiusProperty() {
        super(KEY);
        property = this;
    }

    @Override
    public Double getValue(ItemStack stack) {
        return this.getValueRaw(stack);
    }

    @Override
    public double getValueSafe(ItemStack stack) {
        return this.getValueSafeRaw(stack);
    }
}
