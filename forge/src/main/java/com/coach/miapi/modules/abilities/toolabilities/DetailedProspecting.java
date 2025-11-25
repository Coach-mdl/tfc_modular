package com.coach.miapi.modules.abilities.toolabilities;

import net.minecraft.world.item.ItemStack;

/**
 * This class will contain the detailed prospecting method. The plan will be to move the prospecting method
 * in PropickAbility's InteractionResult into its own class and to change DetailedProspectProperty from a boolean to something
 * more akin to ProspectMapProperty. This will allow the prospect type to be modular and allow devs to add their own types.
 * This leaves propicks very open-ended and with sufficient java and miapi knowledge, propicks could be turned into anything.
 */
public class DetailedProspecting {

    public DetailedProspecting(ItemStack stack) {

    }
}
