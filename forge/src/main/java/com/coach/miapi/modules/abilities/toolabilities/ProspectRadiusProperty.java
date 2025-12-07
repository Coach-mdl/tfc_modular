package com.coach.miapi.modules.abilities.toolabilities;

import net.minecraft.world.item.ItemStack;
import smartin.miapi.modules.properties.util.DoubleProperty;
import smartin.miapi.modules.properties.util.ModuleProperty;

/**
 * This property controls the radius of prospect capable modules using PropickAbility.
 * Try to keep the value a multiple of 2 to prevent anything crazy happening.
 * Default for TerraFirmaCraft propicks is 12.
 */
public class ProspectRadiusProperty extends DoubleProperty implements ModuleProperty {
    public static final String KEY = "prospectRadius";
    public static ProspectRadiusProperty property;

    public ProspectRadiusProperty() {
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

    public static int getRadius(ItemStack itemStack) {
        return (int) property.getValueSafe(itemStack);
    }
}
