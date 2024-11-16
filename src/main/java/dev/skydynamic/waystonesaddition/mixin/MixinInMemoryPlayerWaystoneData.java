package dev.skydynamic.waystonesaddition.mixin;

import dev.skydynamic.waystonesaddition.impl.PlayerWaystone;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.block.ModBlocks;
import net.blay09.mods.waystones.core.InMemoryPlayerWaystoneData;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static dev.skydynamic.waystonesaddition.Waystonesaddition.getPlayerWayStone;

@Mixin(InMemoryPlayerWaystoneData.class )
public class MixinInMemoryPlayerWaystoneData {
    @Inject(
        method = "getWaystones",
        at = @At("RETURN"),
        cancellable = true,
        remap = false
    )
    private void getWaystones(Player player, CallbackInfoReturnable<List<IWaystone>> cir) {
        List<IWaystone> value = cir.getReturnValue();
        MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();

        cir.setReturnValue(getPlayerWayStone(player, value, server));
    }

    @Inject(
        method = "isWaystoneActivated",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectIsWaystoneActivated(Player player, IWaystone waystone, CallbackInfoReturnable<Boolean> cir) {
        if (waystone instanceof PlayerWaystone) {
            cir.setReturnValue(true);
        }
    }
}
