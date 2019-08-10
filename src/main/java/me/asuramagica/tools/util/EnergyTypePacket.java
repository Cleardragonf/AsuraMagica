package me.asuramagica.tools.util;



import net.minecraft.client.Minecraft;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;

import java.util.function.Supplier;

import me.asuramagica.blocks.tileentities.MCM_Tile;



public final class EnergyTypePacket {



    public static void encode(EnergyTypePacket msg, PacketBuffer buf) {

    	buf.writeBlockPos(msg.controllerPos);
    	buf.writeResourceLocation(msg.dimension.getRegistryName());
    	buf.writeInt(msg.slotA);
        //IOHelper.writeBlockPoses(msg.linkedInventories, buf);

    }



    public static EnergyTypePacket decode(PacketBuffer buf) {

    	BlockPos pos = buf.readBlockPos();
    	DimensionType dimension = DimensionType.byName(buf.readResourceLocation());
    	int slotA = buf.readInt();
    	return new EnergyTypePacket(pos, dimension, slotA);

    }



    public static void handle(EnergyTypePacket msg, Supplier<NetworkEvent.Context> ctx) {

    	ctx.get().enqueueWork(() -> {
    		ServerWorld world = ServerLifecycleHooks.getCurrentServer().getWorld(msg.dimension);
    		TileEntity entity = world.getTileEntity(msg.controllerPos);
    		if(entity instanceof MCM_Tile) {
    			MCM_Tile tile = (MCM_Tile) entity;
    		    tile.slotAType = msg.slotA;
    		}
    		
    		});

    }



    private BlockPos controllerPos;

    private DimensionType dimension;

    private int slotA;


    public EnergyTypePacket(BlockPos controllerPos,DimensionType dimension, int slotA) {

        this.controllerPos = controllerPos;

        this.dimension = dimension;
        
        this.slotA = slotA;

    }
	






}
