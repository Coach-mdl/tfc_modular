package com.coach.miapi.modules.abilities.toolabilities;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.wood.BranchDirection;
import net.dries007.tfc.util.AxeLoggingHelper;
import net.dries007.tfc.util.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import smartin.miapi.modules.properties.LoreProperty;
import smartin.miapi.modules.properties.util.BooleanProperty;
import smartin.miapi.modules.properties.util.ModuleProperty;

import java.util.*;

/**
 * This property when applied to a module, allows it to fell a tree. This uses TFC's system rather
 * than a vein mine mining shape property for proper integration with TFC tree. This skip the #tfc:axes_that_log tag,
 * so there's no need to add it. Ensure your loggable blocks have #tfc:logs_that_logs.
 */
public class TreeFellingProperty extends BooleanProperty implements ModuleProperty {
    public static final String KEY = "canLog";
    public static TreeFellingProperty property;

    public static boolean canLog(ItemStack stack) {
        return property.isTrue(stack);
    }

    private static final EnumProperty<BranchDirection> BRANCH_DIRECTION;

    public TreeFellingProperty() {
        super(KEY, false);
        property = this;
        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {
            ItemStack stack = player.getMainHandItem();
            if (shouldLog(level, pos, state) && canLog(stack)) {
                doLogging(level, pos, player, stack);

            }

            return EventResult.pass();
        });

        LoreProperty.bottomLoreSuppliers.add(itemStack -> {
            List<Component> texts = new ArrayList<>();
            if (canLog(itemStack)) {

                Component raw = Component.translatable("miapi.tooltip.axe.canlog").withStyle(ChatFormatting.GREEN);
                texts.add(raw);
            }

            return texts;
        });
    }

    public static boolean shouldLog(LevelAccessor level, BlockPos pos, BlockState state) {
        return AxeLoggingHelper.isLoggingBlock(state) && !AxeLoggingHelper.isPartOfLargerTrunk(level, pos, state);
    }

    public static void doLogging(LevelAccessor level, BlockPos pos, Player player, ItemStack stack) {
        boolean inefficient = Helpers.isItem(stack, TFCTags.Items.INEFFICIENT_LOGGING_AXES);

        for (BlockPos log : findLogs(level, pos)) {
            level.destroyBlock(log, !inefficient || level.getRandom().nextFloat() < 0.6F, player);
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            if (stack.isEmpty()) {
                return;
            }
        }
    }

    public static List<BlockPos> findLogs(LevelAccessor level, BlockPos pos) {
        Set<BlockPos> seen = new HashSet<>(64);
        List<BlockPos> logs = new ArrayList<>(16);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        logs.add(pos);

        for (int i = 0; i < logs.size(); ++i) {
            BlockPos log = logs.get(i);

            for (int dx = -1; dx <= 1; ++dx) {
                for (int dy = -1; dy <= 1; ++dy) {
                    for (int dz = -1; dz <= 1; ++dz) {
                        cursor.setWithOffset(log, dx, dy, dz);
                        if (!seen.contains(cursor)) {
                            BlockPos cursorPos = cursor.immutable();
                            BlockState cursorState = level.getBlockState(cursorPos);
                            if (AxeLoggingHelper.isLoggingBlock(cursorState)) {
                                if (isConnected(log, cursorPos, cursorState)) {
                                    logs.add(cursorPos);
                                    seen.add(cursorPos);
                                }
                            } else {
                                seen.add(cursorPos);
                            }
                        }
                    }
                }
            }
        }

        Collections.reverse(logs);
        return logs;
    }

    private static boolean isConnected(BlockPos rootPos, BlockPos branchPos, BlockState branchState) {
        if (branchState.hasProperty(BRANCH_DIRECTION)) {
            BranchDirection branch = branchState.getValue(BRANCH_DIRECTION);
            return branch.connected(rootPos, branchPos);
        } else {
            return false;
        }
    }

    static {
        BRANCH_DIRECTION = TFCBlockStateProperties.BRANCH_DIRECTION;
    }
}
