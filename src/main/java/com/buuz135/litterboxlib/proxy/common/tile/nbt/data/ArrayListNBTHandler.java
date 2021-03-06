package com.buuz135.litterboxlib.proxy.common.tile.nbt.data;

import com.buuz135.litterboxlib.proxy.common.tile.nbt.INBTHandler;
import com.buuz135.litterboxlib.proxy.common.tile.nbt.NBTManager;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ArrayListNBTHandler implements INBTHandler<ArrayList> {

    public static final String CLASS_NAME = "CLASS_NAME";

    @Override
    public boolean isClassValid(Class<?> aClass) {
        return ArrayList.class.isAssignableFrom(aClass) || List.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean storeToNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, @Nonnull ArrayList object) {
        if (object.size() == 0) return true;
        for (INBTHandler handler : NBTManager.getInstance().getHandlerList()) {
            if (handler.isClassValid(object.get(0).getClass())) {
                NBTTagCompound listCompound = new NBTTagCompound();
                listCompound.setString(CLASS_NAME, object.get(0).getClass().getName());
                int i = 0;
                for (Object item : object) {
                    handler.storeToNBT(listCompound, i + "", item);
                    ++i;
                }
                compound.setTag(name, listCompound);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList readFromNBT(@Nonnull NBTTagCompound compound, @Nonnull String name, ArrayList current) {
//        if (compound.getCompoundTag(name).hasKey(CLASS_NAME)) {
//            try {
//                Class cl = Class.forName(compound.getCompoundTag(name).getString(CLASS_NAME));
//                for (INBTHandler capability : NBTManager.getInstance().getHandlerList()) {
//                    ArrayList list = new ArrayList();
//                    if (capability.isClassValid(cl)) {
//                        for (String key : compound.getCompoundTag(name).getKeySet()) {
//                            if (!key.equals(CLASS_NAME)) {
//                                list.add(capability.readFromNBT(compound.getCompoundTag(name), key));
//                            }
//                        }
//                    }
//                    return list;
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
        return null;
    }
}
