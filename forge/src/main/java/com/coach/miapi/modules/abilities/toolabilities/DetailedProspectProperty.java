package com.coach.miapi.modules.abilities.toolabilities;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import smartin.miapi.modules.properties.util.ComplexBooleanProperty;

/**
 * This property controls what prospecting mode the tool uses. False will use regular modular prospecting.
 * True will use advanced prospecting which gets the number of all tag respecting blocks and give you a percentage.
 * Default is false.
 * This property is WIP and planned for a later update. Propicks are in a good spot currently.
 */

public class DetailedProspectProperty extends ComplexBooleanProperty {
    public static final String KEY = "detailedProspecting";
    public static DetailedProspectProperty property;

    public DetailedProspectProperty() {
        super(KEY, false);
        property = this;
    }

    public static boolean isDetailed(ItemStack stack) {
        return property.isTrue(stack);
    }
}
