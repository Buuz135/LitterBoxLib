package com.buuz135.litterboxlib.proxy.common.network;

import com.buuz135.litterboxlib.proxy.common.tile.TileEntitySided;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ButtonClickedMessage implements IMessage {

    public BlockPos pos;
    public int id;

    public ButtonClickedMessage(BlockPos pos, int id) {
        this.pos = pos;
        this.id = id;
    }

    public ButtonClickedMessage() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        this.pos = packetBuffer.readBlockPos();
        this.id = packetBuffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeBlockPos(pos);
        packetBuffer.writeInt(id);
    }


    public static class Handler implements IMessageHandler<ButtonClickedMessage, IMessage> {

        @Override
        public IMessage onMessage(ButtonClickedMessage message, MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                TileEntity entity = ctx.getServerHandler().player.getServerWorld().getTileEntity(message.pos);
                if (entity instanceof TileEntitySided) {
                    ((TileEntitySided) entity).handleButton(message.id, new NBTTagCompound());
                }
            });
            return null;
        }
    }
}
