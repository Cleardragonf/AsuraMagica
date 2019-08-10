package me.asuramagica.tools.util.Packets.MCM;



import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;


import java.util.function.Supplier;

import me.asuramagica.blocks.tileentities.MCM_Tile;



public final class EnergyTypePacketB {



    public static void encode(EnergyTypePacketB msg, PacketBuffer buf) {

    	buf.writeBlockPos(msg.controllerPos);
    	buf.writeResourceLocation(msg.dimension.getRegistryName());
    	buf.writeInt(msg.slotB);
        //IOHelper.writeBlockPoses(msg.linkedInventories, buf);

    }



    public static EnergyTypePacketB decode(PacketBuffer buf) {

    	BlockPos pos = buf.readBlockPos();
    	DimensionType dimension = DimensionType.byName(buf.readResourceLocation());
    	int slotB = buf.readInt();
    	return new EnergyTypePacketB(pos, dimension, slotB);

    }



    public static void handle(EnergyTypePacketB msg, Supplier<NetworkEvent.Context> ctx) {

    	ctx.get().enqueueWork(() -> {
    		ServerWorld world = ServerLifecycleHooks.getCurrentServer().getWorld(msg.dimension);
    		TileEntity entity = world.getTileEntity(msg.controllerPos);
    		if(entity instanceof MCM_Tile) {
    			MCM_Tile tile = (MCM_Tile) entity;
    		    tile.slotBType = msg.slotB;
    		}
    		
    		});

    }



    private BlockPos controllerPos;

    private DimensionType dimension;

    private int slotB;


    public EnergyTypePacketB(BlockPos controllerPos,DimensionType dimension, int slotB) {

        this.controllerPos = controllerPos;

        this.dimension = dimension;
        
        this.slotB = slotB;

    }
	






}
