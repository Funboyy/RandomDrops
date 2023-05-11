package de.funboyy.challenge.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;

public class NBTUtils {

    public static org.bukkit.inventory.ItemStack addTag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        final NBTTagCompound compound = item.v();

        compound.a("CustomDrops", true);
        item.c(compound);

        return CraftItemStack.asBukkitCopy(item);
    }

    public static org.bukkit.inventory.ItemStack removeTag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        item.c("CustomDrops");

        return CraftItemStack.asBukkitCopy(item);
    }

    public static boolean hasTag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);

        if (!item.t()) {
            return false;
        }

        final NBTTagCompound compound = item.u();

        if (compound == null) {
            return false;
        }

        return compound.e("CustomDrops");
    }

}
