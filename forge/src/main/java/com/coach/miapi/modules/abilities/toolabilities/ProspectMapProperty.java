package com.coach.miapi.modules.abilities.toolabilities;

import com.google.gson.JsonElement;
import smartin.miapi.modules.properties.util.ModuleProperty;

/**
 * This property allows PropickAbility to prospect based on block tags.
 */
public class ProspectMapProperty implements ModuleProperty {
    public static final String KEY = "prospectMap";
    public static ProspectMapProperty property;

    public ProspectMapProperty() {
        property = this;
    }

    @Override
    public boolean load(String moduleKey, JsonElement data) throws Exception {
        return false;
    }
}
