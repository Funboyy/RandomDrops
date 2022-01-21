package de.funboyy.challenge.utils;

import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;

public class NBTUtils {

    public static org.bukkit.inventory.ItemStack addTag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        final NBTTagCompound compound = item.getOrCreateTag();

        compound.setBoolean("CustomDrops", true);
        item.setTag(compound);

        return CraftItemStack.asBukkitCopy(item);
    }

    public static org.bukkit.inventory.ItemStack removeTag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        item.removeTag("CustomDrops");

        return CraftItemStack.asBukkitCopy(item);
    }

    public static boolean hasTag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);

        if (!item.hasTag()) {
            return false;
        }

        final NBTTagCompound compound = item.getTag();

        if (compound == null) {
            return false;
        }

        return compound.hasKey("CustomDrops");
    }

}
