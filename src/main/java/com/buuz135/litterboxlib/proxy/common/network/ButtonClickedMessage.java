package com.buuz135.litterboxlib.proxy.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ButtonClickedMessage implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }


    public static class Handler implements IMessageHandler<ButtonClickedMessage,IMessage>{

        @Override
        public IMessage onMessage(ButtonClickedMessage message, MessageContext ctx) {
            return null;
        }
    }
}
