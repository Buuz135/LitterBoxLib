package com.buuz135.litterboxlib.api;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IRegisterable<T extends IForgeRegistryEntry<T>> {

    void registerObject(IForgeRegistry<T> registry);

    @SideOnly(Side.CLIENT)
    void registerRender();
}
