package com.buuz135.litterboxlib.proxy.common.network;

import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySaving;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

public class TileUpdateFromClientMessage implements IMessage {

    private String id;
    private BlockPos pos;
    private NBTTagCompound information;

    public TileUpdateFromClientMessage(String id, BlockPos pos, NBTTagCompound information) {
        this.id = id;
        this.pos = pos;
        this.information = information;
    }

    public TileUpdateFromClientMessage() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        pos = packetBuffer.readBlockPos();
        id = packetBuffer.readString(100);
        try {
            information = packetBuffer.readCompoundTag();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeBlockPos(pos);
        packetBuffer.writeString(id);
        packetBuffer.writeCompoundTag(information);
    }

    public static class Handler implements IMessageHandler<TileUpdateFromClientMessage, IMessage> {

        @Override
        public IMessage onMessage(TileUpdateFromClientMessage message, MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                TileEntity entity = ctx.getServerHandler().player.getServerWorld().getTileEntity(message.pos);
                if (entity instanceof TileEntitySaving) {
                    ((TileEntitySaving) entity).handleClientPacket(message.id, message.information);
                }
            });
            return null;
        }
    }
}
