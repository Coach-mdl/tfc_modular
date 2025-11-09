package com.coach.miapi.modules.abilities.toolabilities;

import net.minecraft.world.item.ItemStack;
import smartin.miapi.modules.properties.util.DoubleProperty;
import smartin.miapi.modules.properties.util.ModuleProperty;

/**
 * This property controls the accuracy of prospect capable modules using PropickAbility.
 * The accuracy is determined by the value set in the "falseNegativeChance" key set in the module.json.
 * The key accepts a value from 1 to 5 and then converts the value to a percentage. Use [material.mining_level] for best results.
 * Be aware of the calculation in PropickAbility, if you want a specific number you'll want a calculator handy.
 */
public class AccuracyProperty extends DoubleProperty implements ModuleProperty {
    public static final String KEY = "falseNegativeChance";
    public static AccuracyProperty property;

    public AccuracyProperty() {
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
