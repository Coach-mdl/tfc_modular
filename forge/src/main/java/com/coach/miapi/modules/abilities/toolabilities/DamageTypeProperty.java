package com.coach.miapi.modules.abilities.toolabilities;

import com.google.gson.JsonElement;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.util.EntityDamageResistance;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.PhysicalDamageType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import smartin.miapi.modules.ItemModule;
import smartin.miapi.modules.properties.util.MergeType;
import smartin.miapi.modules.properties.util.ModuleProperty;

import java.util.List;

/**
 * This property allows modular tools to accept TFC damage types. When a damage type is applied, the corresponding
 * tooltips will be automatically applied. Certain modular tools already have damage types applied without using this property.
 */
public class DamageTypeProperty implements ModuleProperty {
    public static String KEY = "damageType";
    public static DamageTypeProperty property;

    public DamageTypeProperty() {
        property = this;
    }

    private static String getData(ItemStack itemStack) {

        ItemModule.ModuleInstance root = ItemModule.getModules(itemStack);
        for (ItemModule.ModuleInstance moduleInstance : root.allSubModules()) {

            JsonElement data = moduleInstance.getProperties().get(property);
            if (data != null) {
                return data.getAsString();
            }
        }
        return "";
    }

    public static float calculateMultiplier(DamageSource source, Entity entityUnderAttack) {
        PhysicalDamageType type = getTypeForSource(source);
        float resistance = 0.0F;
        if (type != null) {
            PhysicalDamageType.Multiplier naturalMultiplier = EntityDamageResistance.get(entityUnderAttack);
            if (naturalMultiplier != null) {
                resistance += naturalMultiplier.value(type, source);
            }
        }

        for (ItemStack stack : entityUnderAttack.getArmorSlots()) {
            PhysicalDamageType.Multiplier armorMultiplier = PhysicalDamageType.getResistanceForItem(stack);
            if (armorMultiplier != null) {
                resistance += armorMultiplier.value(type, source);
            }
        }

        return (float) Math.pow(Math.E, -0.01 * (double) resistance);
    }

    public static @Nullable PhysicalDamageType getTypeForSource(DamageSource source) {
        if (source.is(PhysicalDamageType.BYPASSES_DAMAGE_RESISTANCES)) {
            return null;
        } else if (source.is(PhysicalDamageType.IS_PIERCING)) {
            return PhysicalDamageType.PIERCING;
        } else if (source.is(PhysicalDamageType.IS_CRUSHING)) {
            return PhysicalDamageType.CRUSHING;
        } else if (source.is(PhysicalDamageType.IS_SLASHING)) {
            return PhysicalDamageType.CRUSHING;
        } else {
            Entity entity = source.getEntity();
            if (entity != null) {
                if (entity instanceof LivingEntity livingEntity) {
                    ItemStack heldItem = livingEntity.getMainHandItem();
                    if (!heldItem.isEmpty()) {
                        PhysicalDamageType weaponDamageType = getTypeForItem(heldItem);
                        if (weaponDamageType != null) {
                            return weaponDamageType;
                        }
                    }
                }

                if (Helpers.isEntity(entity, TFCTags.Entities.DEALS_PIERCING_DAMAGE)) {
                    return PhysicalDamageType.PIERCING;
                }

                if (Helpers.isEntity(entity, TFCTags.Entities.DEALS_SLASHING_DAMAGE)) {
                    return PhysicalDamageType.SLASHING;
                }

                if (Helpers.isEntity(entity, TFCTags.Entities.DEALS_CRUSHING_DAMAGE)) {
                    return PhysicalDamageType.CRUSHING;
                }
            }

            return null;
        }
    }

    public static void addTooltipInfo(ItemStack stack, List<Component> tooltips) {
        PhysicalDamageType damageType = getTypeForItem(stack);
        if (damageType != null) {
            tooltips.add(Component.translatable("tfc.tooltip.deals_damage." + damageType.getSerializedName()));
        }
    }

    public static @Nullable PhysicalDamageType getTypeForItem(ItemStack stack) {
        if (getData(stack).contains("piercing")) {
            return PhysicalDamageType.PIERCING;
        } else if (getData(stack).contains("slashing")) {
            return PhysicalDamageType.SLASHING;
        } else {
            return getData(stack).contains("crushing") ? PhysicalDamageType.CRUSHING : null;
        }
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



