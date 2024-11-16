package dev.skydynamic.waystonesaddition.mixin;

import dev.skydynamic.waystonesaddition.WaystoneAdditionTypes;
import dev.skydynamic.waystonesaddition.impl.PlayerWaystone;
import dev.skydynamic.waystonesaddition.network.message.SelectPlayerWaystoneMessage;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.client.gui.screen.WaystoneSelectionScreenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaystoneSelectionScreenBase.class)
public class MixinWaystoneSelectionScreenBase {
    @Inject(
        method = "onWaystoneSelected",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectWaystoneButton(IWaystone waystone, CallbackInfo ci) {
        if (waystone instanceof PlayerWaystone || waystone.getWaystoneType().equals(WaystoneAdditionTypes.PLAYER)) {
            Balm.getNetworking().sendToServer(new SelectPlayerWaystoneMessage(waystone.getWaystoneUid()));
            ci.cancel();
        }
    }
}