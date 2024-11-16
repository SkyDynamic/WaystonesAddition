package dev.skydynamic.waystonesaddition.utils;

import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;

public class ModTeleportManager {
    public static void teleport(Player source, Player target) {
        source.teleportTo(
            (ServerLevel) target.level(), target.getX(), target.getY(),
            target.getZ(), RelativeMovement.ALL, target.getYRot(), target.getXRot()
        );

        WaystoneSyncManager.sendWaystoneCooldowns(source);
    }
}
