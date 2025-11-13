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

    //Todo This is only getting one instance of prospectMap. Not Good.
    private String prospectMapData;

    @Override
    public boolean load(String moduleKey, JsonElement data) throws Exception {
        prospectMapData = data.getAsString();
        return true;
    }

    public String getProspectMapData() {
        return prospectMapData;
    }

    public static String getProspectMap() {
        if (property != null && property.prospectMapData != null) {
            return property.getProspectMapData();
        } else {
            throw new IllegalStateException("Failed to retrieve prospect map :(.");
        }
    }
}