package com.coach.miapi.modules.abilities.toolabilities;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.dries007.tfc.common.items.ToolItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import smartin.miapi.modules.properties.LoreProperty;
import smartin.miapi.modules.properties.util.BooleanProperty;
import smartin.miapi.modules.properties.util.ModuleProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * This property ensures tools that can harvest straw/plants take durability damage upon doing so. Applying the tfc:knives
 * Make sure to add tfc:sharp_tools as a fake tag or the tool will not drop straw.
 */
public class GrassDamageProperty extends BooleanProperty implements ModuleProperty {
    public static final String KEY = "grassDamage";
    public static GrassDamageProperty property;

    public GrassDamageProperty() {
        super(KEY, false);
        property = this;

        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {
            ItemStack stack = player.getMainHandItem();

            if (canCut(stack) && ToolItem.willConsumeDurability(level, pos, state) && !level.isClientSide) {

                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            return EventResult.pass();
        });

        LoreProperty.bottomLoreSuppliers.add(itemStack -> {
            List<Component> texts = new ArrayList<>();
            if (canCut(itemStack)) {

                Component raw = Component.translatable("miapi.tooltip.cutgrass").withStyle(ChatFormatting.DARK_GREEN);
                texts.add(raw);
            }

            return texts;
        });
    }

    public static boolean canCut(ItemStack stack) {
        return property.isTrue(stack);
    }

}
