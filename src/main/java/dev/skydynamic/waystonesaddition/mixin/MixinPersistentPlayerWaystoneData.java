package dev.skydynamic.waystonesaddition.mixin;

import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.core.PersistentPlayerWaystoneData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static dev.skydynamic.waystonesaddition.Waystonesaddition.getPlayerWayStone;

@Mixin(PersistentPlayerWaystoneData.class)
public class MixinPersistentPlayerWaystoneData {
    @Inject(
        method = "getWaystones",
        at = @At("RETURN"),
        cancellable = true,
        remap = false
    )
    private void getWaystones(Player player, CallbackInfoReturnable<List<IWaystone>> cir) {
        List<IWaystone> value = cir.getReturnValue();
        MinecraftServer server = player.getServer();

        cir.setReturnValue(getPlayerWayStone(player, value, server));
    }
}
