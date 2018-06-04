package com.buuz135.litterboxlib.proxy.common;

import com.buuz135.litterboxlib.Litterboxlib;
import com.buuz135.litterboxlib.annotation.RegisteringBlock;
import com.buuz135.litterboxlib.annotation.RegisteringItem;
import com.buuz135.litterboxlib.api.IRegisterable;
import com.buuz135.litterboxlib.proxy.common.network.ButtonClickedMessage;
import com.buuz135.litterboxlib.util.AnnotationUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

public class CommonProxy {

    public static final List<IRegisterable<Block>> BLOCKS = new ArrayList<>();
    public static final List<IRegisterable<Item>> ITEMS = new ArrayList<>();

    public void onPreInit(FMLPreInitializationEvent event) {
        ASMDataTable dataTable = event.getAsmData();
        searchForBlocks(dataTable);
        searchForItems(dataTable);

        MinecraftForge.EVENT_BUS.register(this);
        NetworkRegistry.INSTANCE.registerGuiHandler(Litterboxlib.INSTANCE, new GuiHandler());
        int id = 0;
        Litterboxlib.NETWORK.registerMessage(ButtonClickedMessage.Handler.class, ButtonClickedMessage.class, ++id, Side.SERVER);
    }

    public void onInit(FMLInitializationEvent event) {

    }

    public void onPostInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> register) {
        BLOCKS.forEach(blockIRegisterable -> blockIRegisterable.registerObject(register.getRegistry()));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> register) {
        ITEMS.forEach(blockIRegisterable -> {
            blockIRegisterable.registerObject(register.getRegistry());
            if (FMLCommonHandler.instance().getSide() == Side.CLIENT){
                blockIRegisterable.registerRender();
            }
        });
        BLOCKS.forEach(blockIRegisterable -> {
            register.getRegistry().register(new ItemBlock((Block) blockIRegisterable).setRegistryName(((Block) blockIRegisterable).getRegistryName()));
            if (FMLCommonHandler.instance().getSide() == Side.CLIENT){
                blockIRegisterable.registerRender();
            }
        });

    }

    private void searchForBlocks(ASMDataTable table) {
        for (Class aClass : AnnotationUtils.getAnnotatedClasses(table, RegisteringBlock.class)) {
            if (IRegisterable.class.isAssignableFrom(aClass)) {
                try {
                    BLOCKS.add((IRegisterable) aClass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void searchForItems(ASMDataTable table) {
        for (Class aClass : AnnotationUtils.getAnnotatedClasses(table, RegisteringItem.class)) {
            if (IRegisterable.class.isAssignableFrom(aClass)) {
                try {
                    ITEMS.add((IRegisterable) aClass.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
