package me.asuramagica.tools.util;



import net.minecraft.client.Minecraft;

import net.minecraft.network.PacketBuffer;

import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;

import net.minecraftforge.fml.network.NetworkEvent;



import java.util.*;

import java.util.function.Supplier;



public final class EnergyTypePacket {



    public static void encode(EnergyTypePacket msg, PacketBuffer buf) {

        buf.writeBlockPos(msg.controllerPos);

        //IOHelper.writeBlockPoses(msg.linkedInventories, buf);

    }



    public static EnergyTypePacket decode(PacketBuffer buf) {

        BlockPos controllerPos = buf.readBlockPos();

        //List<BlockPos> linkedInventories = IOHelper.readBlockPosesSized(buf, ArrayList::new);
        List<BlockPos> linkedInventories = new LinkedList<BlockPos>();

        return new EnergyTypePacket(controllerPos, linkedInventories);

    }



    public static void handle(EnergyTypePacket msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            World world = Minecraft.getInstance().world;

            //INetworkController controller = Objects.requireNonNull((INetworkController) world.getTileEntity(msg.controllerPos));

            //controller.removeAllLinks();

            //controller.addLinks(msg.linkedInventories);

        });

    }



    private BlockPos controllerPos;

    private Collection<BlockPos> linkedInventories;



    public EnergyTypePacket(BlockPos controllerPos, Collection<BlockPos> poses) {

        this.controllerPos = controllerPos;

        this.linkedInventories = poses;

    }

}
