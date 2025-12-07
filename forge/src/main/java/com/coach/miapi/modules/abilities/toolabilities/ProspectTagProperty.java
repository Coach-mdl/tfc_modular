package com.coach.miapi.modules.abilities.toolabilities;

import com.coach.forge.TFC_Modular;
import com.google.gson.JsonElement;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import smartin.miapi.modules.ItemModule;
import smartin.miapi.modules.cache.ModularItemCache;
import smartin.miapi.modules.properties.util.MergeType;
import smartin.miapi.modules.properties.util.ModuleProperty;

/**
 * This property allows PropickAbility to prospect based on block tags.
 * IF THIS VALUE IS NULL, THE GAME WILL CRASH!
 * TFC Modular will attempt to default the value to tfc:prospectable but this will spam the log until you set a value for the module.
 * tfc:prospectable is what tfc propicks use. tfc:minerals is added by modular and prospects for minerals and gems.
 * tfc:fluxstones is added by modular and prospects for raw, hardened and cobble variants of fluxstones.
 * Despite the name, it does not use a map. Not really sure why I named it that.
 */

public class ProspectTagProperty implements ModuleProperty {

    public static final String KEY = "prospectTag";
    public static ProspectTagProperty property;

    public ProspectTagProperty() {
        property = this;

        ModularItemCache.setSupplier(KEY, ProspectTagProperty::resolveProspectMapData);
    }

    public static String getProspectMapData(ItemStack stack) {
        return ModularItemCache.getVisualOnlyCache(stack, KEY, "tfc:prospectable");
    }

    private static String resolveProspectMapData(ItemStack itemStack) throws Error {

        ItemModule.ModuleInstance root = ItemModule.getModules(itemStack);
        for (ItemModule.ModuleInstance moduleInstance : root.allSubModules()) {

            JsonElement data = moduleInstance.getProperties().get(property);
            if (data != null) {
                return data.getAsString();
            }
        }

        TFC_Modular.LOGGER.warn("Module prospectMap returned null, fix it! Defaulting to tfc:prospectable.");
        return "tfc:prospectable";
    }

    @Override
    public boolean load(@NotNull String moduleKey, JsonElement data) {
        data.getAsString();
        return true;
    }

    @Override
    public JsonElement merge(JsonElement old, JsonElement toMerge, MergeType type) {
        switch (type) {
            case EXTEND -> {
                return old;
            }
            case SMART, OVERWRITE -> {
                return toMerge;
            }
        }
        return old;
    }
}