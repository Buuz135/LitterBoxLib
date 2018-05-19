package com.buuz135.litterboxlib._test;

import com.buuz135.litterboxlib.Litterboxlib;
import com.buuz135.litterboxlib.annotation.RegisteringBlock;
import com.buuz135.litterboxlib.proxy.common.block.BlockTileHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

@RegisteringBlock
public class BlockTest extends BlockTileHorizontal<TileEntityTest> {

    public BlockTest() {
        super(new ResourceLocation(Litterboxlib.MOD_ID, "blocktest"), TileEntityTest.class, Material.ROCK);
    }
}
