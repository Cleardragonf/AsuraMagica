package me.asuramagica.tools.util.Packets.ManaStone;



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
import me.asuramagica.blocks.tileentities.Mana_StoneTile;



public final class ManaEnergyPacket {



    public static void encode(ManaEnergyPacket msg, PacketBuffer buf) {

    	buf.writeBlockPos(msg.controllerPos);
    	buf.writeResourceLocation(msg.dimension.getRegistryName());
    	buf.writeInt(msg.waterEnergy);
        //IOHelper.writeBlockPoses(msg.linkedInventories, buf);

    }



    public static ManaEnergyPacket decode(PacketBuffer buf) {

    	BlockPos pos = buf.readBlockPos();
    	DimensionType dimension = DimensionType.byName(buf.readResourceLocation());
    	int slotA = buf.readInt();
    	return new ManaEnergyPacket(pos, dimension, slotA);

    }



    public static void handle(ManaEnergyPacket msg, Supplier<NetworkEvent.Context> ctx) {

    	ctx.get().enqueueWork(() -> {
    		ServerWorld world = ServerLifecycleHooks.getCurrentServer().getWorld(msg.dimension);
    		TileEntity entity = world.getTileEntity(msg.controllerPos);
    		if(entity instanceof Mana_StoneTile) {
    			Mana_StoneTile tile = (Mana_StoneTile) entity;
    		    tile.waterEnergy.setEnergy(msg.waterEnergy);
    		}
    		
    		});

    }



    private BlockPos controllerPos;

    private DimensionType dimension;

    private int waterEnergy;


    public ManaEnergyPacket(BlockPos controllerPos,DimensionType dimension, int waterEnergy) {

        this.controllerPos = controllerPos;

        this.dimension = dimension;
        
        this.waterEnergy = waterEnergy;

    }
	






}
