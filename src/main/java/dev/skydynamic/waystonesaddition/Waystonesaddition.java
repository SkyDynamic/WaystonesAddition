package dev.skydynamic.waystonesaddition;

import com.mojang.logging.LogUtils;
import dev.skydynamic.waystonesaddition.impl.PlayerWaystone;
import dev.skydynamic.waystonesaddition.network.ModNetworking;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.block.ModBlocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(Waystonesaddition.MODID)
public class Waystonesaddition {
    public static final String MODID = "waystonesaddition";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Waystonesaddition() {
        ModNetworking.initialize(Balm.getNetworking());

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static List<IWaystone> getPlayerWayStone(Player player, List<IWaystone> originValue, MinecraftServer server) {
        List<IWaystone> newValue = new ArrayList<>(originValue);
        if (server != null) {
            server.getPlayerList().getPlayers().stream()
                .filter(p -> p.getInventory().contains(ModBlocks.waystone.asItem().getDefaultInstance()))
                .forEach(it -> {
                    if (player.getUUID().equals(it.getUUID())) {
                        return;
                    }
                    newValue.add(new PlayerWaystone(it));
                });
        }
        return newValue;
    }
}
