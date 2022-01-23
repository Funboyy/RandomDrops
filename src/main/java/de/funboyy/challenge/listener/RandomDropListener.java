package de.funboyy.challenge.listener;

import de.funboyy.challenge.RandomDropsPlugin;
import de.funboyy.challenge.utils.NBTUtils;
import de.funboyy.challenge.utils.RandomDrop;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftMinecartChest;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftMinecartContainer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftMinecartHopper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.Lootable;

public class RandomDropListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onItemSpawn(final ItemSpawnEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        final Item item = event.getEntity();
        final ItemStack itemStack = item.getItemStack();

        if (NBTUtils.hasTag(itemStack)) {
            item.setItemStack(NBTUtils.removeTag(itemStack));
            return;
        }

        final ItemStack drop = RandomDrop.getInstance().getDrop(item);
        item.setItemStack(drop);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrop(final PlayerDropItemEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        final Item item = event.getItemDrop();
        final ItemStack itemStack = item.getItemStack();

        item.setItemStack(NBTUtils.addTag(itemStack));
    }

    @EventHandler(ignoreCancelled = true)
    public void onDispense(final BlockDispenseEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        event.setItem(NBTUtils.addTag(event.getItem()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(final EntityDamageEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        if (!(event.getEntity() instanceof ItemFrame)) {
            return;
        }

        final ItemFrame frame = (ItemFrame) event.getEntity();
        frame.setItem(NBTUtils.addTag(frame.getItem()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onHangingBreak(final HangingBreakEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        if (!(event.getEntity() instanceof ItemFrame)) {
            return;
        }

        final ItemFrame frame = (ItemFrame) event.getEntity();
        final ItemStack item = frame.getItem();

        if (item.getType() == Material.AIR) {
            return;
        }

        frame.setItem(NBTUtils.addTag(frame.getItem()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        dropLoot(event.getBlock().getState());
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        event.blockList().forEach(block -> dropLoot(block.getState()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockExplode(final BlockExplodeEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        event.blockList().forEach(block -> dropLoot(block.getState()));
    }

    private void dropLoot(final BlockState state) {
        if (!(state instanceof Container)) {
            return;
        }

        final Container container = (Container) state;
        ItemStack[] items = container.getInventory().getContents();
        final Location location = state.getLocation().add(0.5, 0, 0.5);

        if (location.getWorld() == null) {
            return;
        }

        if (state instanceof Lootable) {
            final Lootable loot = (Lootable) state;

            if (loot.getLootTable() != null) {
                final LootContext.Builder lootContextBuilder = new LootContext.Builder(state.getLocation());
                items = loot.getLootTable().populateLoot(new Random(), lootContextBuilder.build()).toArray(new ItemStack[0]);
            }
        }

        container.getInventory().clear();
        Arrays.stream(items).filter(Objects::nonNull).forEach(item ->
                location.getWorld().dropItemNaturally(location, NBTUtils.addTag(item)));
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeath(final PlayerDeathEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        final Location location = event.getEntity().getLocation();

        if (location.getWorld() == null) {
            return;
        }

        event.getDrops().forEach(item ->
                location.getWorld().dropItemNaturally(location, NBTUtils.addTag(item)));
        event.getDrops().clear();
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleDestroy(final VehicleDestroyEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        if (!(event.getVehicle() instanceof Minecart)) {
            return;
        }

        dropLootEntity(event.getVehicle());
    }

    private void dropLootEntity(final Entity entity) {
        if (!(entity instanceof CraftMinecartContainer)) {
            return;
        }

        final Inventory inventory;

        if (entity instanceof CraftMinecartChest) {
            inventory = ((CraftMinecartChest) entity).getInventory();
        } else {
            inventory = ((CraftMinecartHopper) entity).getInventory();
        }

        ItemStack[] items = inventory.getContents();
        final Location location = entity.getLocation();

        if (location.getWorld() == null) {
            return;
        }

        final Lootable loot = (Lootable) entity;

        if (loot.getLootTable() != null) {
            final LootContext.Builder lootContextBuilder = new LootContext.Builder(entity.getLocation());
            items = loot.getLootTable().populateLoot(new Random(), lootContextBuilder.build()).toArray(new ItemStack[0]);
        }

        inventory.clear();
        Arrays.stream(items).filter(Objects::nonNull).forEach(item ->
                location.getWorld().dropItemNaturally(location, NBTUtils.addTag(item)));
    }

}
