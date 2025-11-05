package com.coach.miapi.modules.abilities.toolabilities;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.items.ProspectResult;
import net.dries007.tfc.network.PacketHandler;
import net.dries007.tfc.network.ProspectedPacket;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.events.ProspectedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.RegistryObject;
import smartin.miapi.modules.abilities.ToolAbilities;

import java.util.*;

public class PropickAbility extends ToolAbilities {

    public static final String KEY = "propick_ability";

    public static int RADIUS;
    public static final int COOLDOWN = 10;
    private static final Map<Block, Block> REPRESENTATIVE_BLOCKS = new IdentityHashMap();
    private float falseNegativeChance;

    public static synchronized void registerRepresentative(Block representative, Block... blocks) {
        for (Block block : blocks) {
            REPRESENTATIVE_BLOCKS.put(block, representative);
        }

    }

    public static Object2IntMap<Block> scanAreaFor(Level level, BlockPos center, int radius, TagKey<Block> tag) {
        Object2IntMap<Block> results = new Object2IntOpenHashMap();

        for (BlockPos cursor : BlockPos.betweenClosed(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center.getX() + radius, center.getY() + radius, center.getZ() + radius)) {
            Block block = getRepresentative(level.getBlockState(cursor).getBlock());
            if (Helpers.isBlock(block, tag)) {
                results.mergeInt(block, 1, Integer::sum);
            }
        }

        return results;
    }

    public static Block getRepresentative(Block block) {
        return (Block) REPRESENTATIVE_BLOCKS.getOrDefault(block, block);
    }

    public static void registerDefaultRepresentativeBlocks() {
        TFCBlocks.GRADED_ORES.forEach((rock, ores) -> ores.forEach((ore, blocks) -> registerRepresentative((Block) ((RegistryObject) blocks.get(Ore.Grade.NORMAL)).get(), (Block) ((RegistryObject) blocks.get(Ore.Grade.RICH)).get(), (Block) ((RegistryObject) blocks.get(Ore.Grade.POOR)).get())));
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (player instanceof ServerPlayer serverPlayer) {
            SoundType sound = state.getSoundType();
            Random random = new Random();
            level.playSound(player, pos, sound.getHitSound(), SoundSource.PLAYERS, sound.getVolume(), sound.getPitch());
            context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
            Block found = state.getBlock();
            random.setSeed((long) Helpers.hash(19827384739241223L, pos));
            ProspectResult result;
            if (Helpers.isBlock(state, TFCTags.Blocks.PROSPECTABLE)) {
                result = ProspectResult.FOUND;
            } else if (random.nextFloat() < this.falseNegativeChance) {
                result = ProspectResult.NOTHING;
            } else {
                Object2IntMap<Block> states = scanAreaFor(level, pos, 12, TFCTags.Blocks.PROSPECTABLE);
                if (states.isEmpty()) {
                    result = ProspectResult.NOTHING;
                } else {
                    ArrayList<Block> stateKeys = new ArrayList(states.keySet());
                    found = (Block) stateKeys.get(random.nextInt(stateKeys.size()));
                    int amount = states.getOrDefault(found, 1);
                    if (amount < 10) {
                        result = ProspectResult.TRACES;
                    } else if (amount < 20) {
                        result = ProspectResult.SMALL;
                    } else if (amount < 40) {
                        result = ProspectResult.MEDIUM;
                    } else if (amount < 80) {
                        result = ProspectResult.LARGE;
                    } else {
                        result = ProspectResult.VERY_LARGE;
                    }
                }
            }

            MinecraftForge.EVENT_BUS.post(new ProspectedEvent(player, result, found));
            PacketHandler.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new ProspectedPacket(found, result));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public Optional<BlockState> getBlockState(BlockState blockState, UseOnContext context) {
        return Optional.empty();
    }


}
