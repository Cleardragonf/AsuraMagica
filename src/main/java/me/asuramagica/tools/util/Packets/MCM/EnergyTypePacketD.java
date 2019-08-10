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



public final class EnergyTypePacketD {



    public static void encode(EnergyTypePacketD msg, PacketBuffer buf) {

    	buf.writeBlockPos(msg.controllerPos);
    	buf.writeResourceLocation(msg.dimension.getRegistryName());
    	buf.writeInt(msg.slotD);
        //IOHelper.writeBlockPoses(msg.linkedInventories, buf);

    }



    public static EnergyTypePacketD decode(PacketBuffer buf) {

    	BlockPos pos = buf.readBlockPos();
    	DimensionType dimension = DimensionType.byName(buf.readResourceLocation());
    	int slotD = buf.readInt();
    	return new EnergyTypePacketD(pos, dimension, slotD);

    }



    public static void handle(EnergyTypePacketD msg, Supplier<NetworkEvent.Context> ctx) {

    	ctx.get().enqueueWork(() -> {
    		ServerWorld world = ServerLifecycleHooks.getCurrentServer().getWorld(msg.dimension);
    		TileEntity entity = world.getTileEntity(msg.controllerPos);
    		if(entity instanceof MCM_Tile) {
    			MCM_Tile tile = (MCM_Tile) entity;
    		    tile.slotDType = msg.slotD;
    		}
    		
    		});

    }



    private BlockPos controllerPos;

    private DimensionType dimension;

    private int slotD;


    public EnergyTypePacketD(BlockPos controllerPos,DimensionType dimension, int slotD) {

        this.controllerPos = controllerPos;

        this.dimension = dimension;
        
        this.slotD = slotD;

    }
	






}
