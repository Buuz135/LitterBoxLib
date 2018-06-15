package com.buuz135.litterboxlib.proxy.common.tile.container.capability.work;

import com.buuz135.litterboxlib.proxy.common.client.gui.IGuiAddonProvider;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.IGuiAddon;
import com.buuz135.litterboxlib.proxy.common.client.gui.addon.capability.WorkBarGuiAddon;
import lombok.Getter;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiWorkBarHandler implements IGuiAddonProvider {

    @Getter
    private List<PosWorkBar> posWorkBars;

    public MultiWorkBarHandler() {
        this.posWorkBars = new ArrayList<>();
    }

    public void addBar(PosWorkBar workBar) {
        this.posWorkBars.add(workBar);
    }

    public void update(World world) {
        for (PosWorkBar posWorkBar : posWorkBars) {
            if (posWorkBar.isShouldTickIncrease()) {
                posWorkBar.increase(world);
            }
        }
    }

    @Override
    public List<? extends IGuiAddon> getGuiAddons() {
        return posWorkBars.stream().map(WorkBarGuiAddon::new).collect(Collectors.toList());
    }
}
