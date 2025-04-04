package de.funboyy.challenge.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.bukkit.craftbukkit.v1_21_R4.inventory.CraftItemStack;

public class ComponentManager {

    public org.bukkit.inventory.ItemStack addFlag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        final NBTTagCompound compound = new NBTTagCompound();
        compound.a("CustomDrops", true);

        item.b(DataComponents.b, CustomData.a(compound));

        return CraftItemStack.asBukkitCopy(item);
    }

    public org.bukkit.inventory.ItemStack removeFlag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        item.e(DataComponents.b);

        return CraftItemStack.asBukkitCopy(item);
    }

    public boolean hasFlag(final org.bukkit.inventory.ItemStack itemStack) {
        final ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        final CustomData data = item.a(DataComponents.b);

        if (data == null) {
            return false;
        }

        final NBTTagCompound compound = data.d();

        return compound.b("CustomDrops");
    }

}
