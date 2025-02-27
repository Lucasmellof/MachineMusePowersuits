/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.powersuits.entity;

import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.basemod.MPSObjects;
import com.github.lehjr.powersuits.block.LuxCapacitorBlock;
import com.github.lehjr.powersuits.tile_entity.LuxCapacitorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class LuxCapacitorEntity extends ThrowableEntity implements IEntityAdditionalSpawnData {
    public Colour color;

    public LuxCapacitorEntity(EntityType<? extends LuxCapacitorEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        if (color == null) {
            color = LuxCapacitorBlock.defaultColor;
        }
    }

    public LuxCapacitorEntity(World world, LivingEntity shootingEntity, Colour color) {
        super(MPSObjects.LUX_CAPACITOR_ENTITY_TYPE.get(), shootingEntity, world);
        this.setNoGravity(true);
        this.color = color != null ? color : LuxCapacitorBlock.defaultColor;
        Vector3d direction = shootingEntity.getLookAngle().normalize();
        double speed = 1.0;
        this.setDeltaMovement(
                direction.x * speed,
                direction.y * speed,
                direction.z * speed
        );

        double r = 0.4375;
        double xoffset = 0.1;
        double yoffset = 0;
        double zoffset = 0;
        double horzScale = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        double horzx = direction.x / horzScale;
        double horzz = direction.z / horzScale;
        this.setPos(
                (shootingEntity.getX() + direction.x * xoffset - direction.y * horzx * yoffset - horzz * zoffset),
                (shootingEntity.getY() + shootingEntity.getEyeHeight() + direction.y * xoffset + (1 - Math.abs(direction.y)) * yoffset),
                (shootingEntity.getZ() + direction.z * xoffset - direction.y * horzz * yoffset + horzx * zoffset));
        this.setBoundingBox(new AxisAlignedBB(getX() - r, getY() - 0.0625, getZ() - r, getX() + r, getY() + 0.0625, getZ() + r));
    }

    BlockState getBlockState(BlockPos pos, Direction facing) {
        FluidState ifluidstate = level.getFluidState(pos);
        return MPSObjects.LUX_CAPACITOR_BLOCK.get().defaultBlockState().setValue(DirectionalBlock.FACING, facing)
                .setValue(LuxCapacitorBlock.WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8));
    }

    @Override
    protected void onHit(RayTraceResult hitResult) {
        if (color == null) {
            color = LuxCapacitorBlock.defaultColor;
        }

        if (this.isAlive() && hitResult.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockRayTrace = (BlockRayTraceResult)hitResult;
            Direction dir = blockRayTrace.getDirection().getOpposite();
            int x = blockRayTrace.getBlockPos().getX() - dir.getStepX();
            int y = blockRayTrace.getBlockPos().getY() - dir.getStepY();
            int z = blockRayTrace.getBlockPos().getZ() - dir.getStepZ();
            if (y > 0) {
                BlockPos blockPos = new BlockPos(x, y, z);
                if (MPSObjects.LUX_CAPACITOR_BLOCK.get().defaultBlockState().canSurvive(level, blockPos)) {
                    BlockState blockState = getBlockState(blockPos, blockRayTrace.getDirection());
                    level.setBlockAndUpdate(blockPos, blockState);
                    level.setBlockEntity(blockPos, new LuxCapacitorTileEntity(color));
                } else {
                    for (Direction facing : Direction.values()) {
                        if (MPSObjects.LUX_CAPACITOR_BLOCK.get().defaultBlockState().setValue(LuxCapacitorBlock.FACING, facing).canSurvive(level, blockPos)) {
                            BlockState blockState = getBlockState(blockPos, facing);
                            level.setBlockAndUpdate(blockPos, blockState);
                            level.setBlockEntity(blockPos, new LuxCapacitorTileEntity(color));
                            break;
                        }
                    }
                }
                this.remove();
            }
        }
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
    protected float getGravity() {
        return 0;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 400) {
            this.remove();
        }
    }

    /**
     * Called by the server when constructing the spawn packet.
     * Data should be added to the provided stream.
     *
     * @param buffer The packet data stream
     */
    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(this.color.getInt());
    }

    /**
     * Called by the client when it receives a Entity spawn packet.
     * Data should be read out of the stream in the same way as it was written.
     *
     * @param additionalData The packet data stream
     */
    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.color = new Colour(additionalData.readInt());
    }
}