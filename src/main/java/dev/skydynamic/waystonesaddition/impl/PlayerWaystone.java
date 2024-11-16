package dev.skydynamic.waystonesaddition.impl;

import dev.skydynamic.waystonesaddition.WaystoneAdditionTypes;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.api.WaystoneOrigin;
import net.blay09.mods.waystones.core.WaystoneTypes;
import net.blay09.mods.waystones.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerWaystone implements IWaystone {
    private final UUID uuid;
    private final String name;
    private final ResourceKey<Level> dimension;
    private final BlockPos pos;

    public PlayerWaystone(Player player) {
        this.uuid = player.getUUID();
        this.name = player.getName().getString();
        this.dimension = player.level().dimension();
        this.pos = player.getOnPos().above();
    }

    @Override
    public UUID getWaystoneUid() {
        return this.uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ResourceKey<Level> getDimension() {
        return this.dimension;
    }

    @Override
    public WaystoneOrigin getOrigin() {
        return WaystoneOrigin.PLAYER;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public boolean isOwner(Player player) {
        return false;
    }

    @Override
    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public @Nullable UUID getOwnerUid() {
        return this.uuid;
    }

    @Override
    public ResourceLocation getWaystoneType() {
        return WaystoneAdditionTypes.PLAYER;
    }

    @Override
    public boolean isValidInLevel(ServerLevel level) {
        return true;
    }
}
