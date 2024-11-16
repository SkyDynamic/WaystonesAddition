package dev.skydynamic.waystonesaddition.network.message;

import dev.skydynamic.waystonesaddition.impl.PlayerWaystone;
import dev.skydynamic.waystonesaddition.utils.ModTeleportManager;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WarpMode;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SelectPlayerWaystoneMessage {
    private final UUID playerUid;

    public SelectPlayerWaystoneMessage(UUID playerUid) {
        this.playerUid = playerUid;
    }

    public static void encode(SelectPlayerWaystoneMessage message, FriendlyByteBuf buf) {
        buf.writeUUID(message.playerUid);
    }

    public static SelectPlayerWaystoneMessage decode(FriendlyByteBuf buf) {
        UUID waystoneUid = buf.readUUID();
        return new SelectPlayerWaystoneMessage(waystoneUid);
    }

    public static void handle(ServerPlayer player, SelectPlayerWaystoneMessage message) {
        if (player.containerMenu instanceof WaystoneSelectionMenu) {
            Player target = player.getServer().getPlayerList().getPlayer(message.playerUid);

            int cooldown = PlayerWaystoneManager.getCooldownPeriod(WarpMode.WARP_STONE, new PlayerWaystone(target));

            PlayerWaystoneManager.getPlayerWaystoneData(player.level()).
                setWarpStoneCooldownUntil(player, System.currentTimeMillis() + (long)cooldown * 1000L);
            WaystoneSyncManager.sendWaystoneCooldowns(player);

            int xpCost = getExpCost(player, target);

            if (player.experienceLevel >= xpCost) {
                ModTeleportManager.teleport(player, target);
                player.setExperienceLevels(-xpCost);
            } else {
                player.displayClientMessage(Component.literal("经验不足, 传送失败"), false);
            }
            player.closeContainer();
        }
    }

    private static int getExpCost(Player source, Player target) {
        double minimumXpCost = WaystonesConfig.getActive().xpCost.minimumBaseXpCost;
        double maximumXpCost = WaystonesConfig.getActive().xpCost.maximumBaseXpCost;

        double xpLevelCost;
        if (source.level() != target.level()) {
            int dimensionalWarpXpCost = WaystonesConfig.getActive().xpCost.dimensionalWarpXpCost;
            xpLevelCost = Mth.clamp(dimensionalWarpXpCost, minimumXpCost, dimensionalWarpXpCost);
        } else if (WaystonesConfig.getActive().xpCost.blocksPerXpLevel > 0) {
            double distance = Math.sqrt(source.distanceToSqr(target));
            xpLevelCost = Mth.clamp(Math.floor(distance / (double)((float)WaystonesConfig.getActive().xpCost.blocksPerXpLevel)), minimumXpCost, maximumXpCost);
            if (WaystonesConfig.getActive().xpCost.inverseXpCost) {
                xpLevelCost = maximumXpCost - xpLevelCost;
            }
        } else {
            xpLevelCost = minimumXpCost;
        }
        return !source.isCreative() ? (int) xpLevelCost : 0;
    }
}
