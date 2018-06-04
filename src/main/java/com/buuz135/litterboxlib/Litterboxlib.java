package com.buuz135.litterboxlib;

import com.buuz135.litterboxlib.proxy.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(
        modid = Litterboxlib.MOD_ID,
        name = Litterboxlib.MOD_NAME,
        version = Litterboxlib.VERSION
)
public class Litterboxlib {

    public static final String MOD_ID = "litterboxlib";
    public static final String MOD_NAME = "Litterboxlib";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    @Mod.Instance(MOD_ID)
    public static Litterboxlib INSTANCE;

    @SidedProxy(serverSide = "com.buuz135.litterboxlib.proxy.common.CommonProxy", clientSide = "com.buuz135.litterboxlib.proxy.common.client.ClientProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.onPreInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.onInit(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        proxy.onPostInit(event);
    }


}
