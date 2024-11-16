package dev.skydynamic.waystonesaddition.network;

import dev.skydynamic.waystonesaddition.Waystonesaddition;
import dev.skydynamic.waystonesaddition.network.message.SelectPlayerWaystoneMessage;
import net.blay09.mods.balm.api.network.BalmNetworking;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ModNetworking {
    public static void initialize(BalmNetworking networking) {
        networking.registerServerboundPacket(
            id("select_player_waystone"),
            SelectPlayerWaystoneMessage.class,
            SelectPlayerWaystoneMessage::encode,
            SelectPlayerWaystoneMessage::decode,
            SelectPlayerWaystoneMessage::handle
        );
    }

    @NotNull
    private static ResourceLocation id(String name) {
        return new ResourceLocation(Waystonesaddition.MODID, name);
    }
}
