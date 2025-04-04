package de.funboyy.challenge.listener;

import de.funboyy.challenge.RandomDropsPlugin;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.craftbukkit.v1_21_R4.entity.CraftChestBoat;
import org.bukkit.craftbukkit.v1_21_R4.entity.CraftMinecartChest;
import org.bukkit.craftbukkit.v1_21_R4.entity.CraftMinecartHopper;
import org.bukkit.entity.*;
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
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.Lootable;

@AllArgsConstructor
public class RandomDropListener implements Listener {

    private final RandomDropsPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void handleItemSpawn(final ItemSpawnEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        final Item item = event.getEntity();
        final ItemStack itemStack = item.getItemStack();

        if (this.plugin.getManager().hasFlag(itemStack)) {
            item.setItemStack(this.plugin.getManager().removeFlag(itemStack));
            return;
        }

        final ItemStack drop = this.plugin.getRandom().getDrop(item);
        item.setItemStack(drop);
    }

    @EventHandler(ignoreCancelled = true)
    public void handlePlayerItemDrop(final PlayerDropItemEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        final Item item = event.getItemDrop();
        final ItemStack itemStack = item.getItemStack();

        item.setItemStack(this.plugin.getManager().addFlag(itemStack));
    }

    @EventHandler(ignoreCancelled = true)
    public void handleBlockDispense(final BlockDispenseEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        event.setItem(this.plugin.getManager().addFlag(event.getItem()));
    }

    @EventHandler(ignoreCancelled = true)
    public void handleItemDamage(final EntityDamageEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        if (!(event.getEntity() instanceof ItemFrame frame)) {
            return;
        }

        frame.setItem(this.plugin.getManager().addFlag(frame.getItem()));
    }

    @EventHandler(ignoreCancelled = true)
    public void handleHangingBreak(final HangingBreakEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        if (!(event.getEntity() instanceof ItemFrame frame)) {
            return;
        }

        final ItemStack item = frame.getItem();

        if (item.getType() == Material.AIR) {
            return;
        }

        frame.setItem(this.plugin.getManager().addFlag(frame.getItem()));
    }

    @EventHandler(ignoreCancelled = true)
    public void handleBlockBreak(final BlockBreakEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        this.dropLoot(event.getBlock().getState());
    }

    @EventHandler(ignoreCancelled = true)
    public void handleEntityExplode(final EntityExplodeEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        event.blockList().forEach(block -> this.dropLoot(block.getState()));
    }

    @EventHandler(ignoreCancelled = true)
    public void handleBlockExplode(final BlockExplodeEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        event.blockList().forEach(block -> this.dropLoot(block.getState()));
    }

    private void dropLoot(final BlockState state) {
        if (!(state instanceof Container container)) {
            return;
        }

        ItemStack[] items = container.getInventory().getContents();
        final Location location = state.getLocation().add(0.5, 0, 0.5);

        if (location.getWorld() == null) {
            return;
        }

        if (state instanceof Lootable loot && loot.getLootTable() != null) {
            final LootContext.Builder lootContextBuilder = new LootContext.Builder(state.getLocation());
            items = loot.getLootTable().populateLoot(new Random(), lootContextBuilder.build()).toArray(new ItemStack[0]);
        }

        container.getInventory().clear();
        Arrays.stream(items).filter(Objects::nonNull).forEach(item ->
                location.getWorld().dropItemNaturally(location, this.plugin.getManager().addFlag(item)));
    }

    @EventHandler(ignoreCancelled = true)
    public void handlePlayerDeath(final PlayerDeathEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        final Location location = event.getEntity().getLocation();

        if (location.getWorld() == null) {
            return;
        }

        event.getDrops().forEach(item ->
                location.getWorld().dropItemNaturally(location, this.plugin.getManager().addFlag(item)));
        event.getDrops().clear();
    }

    @EventHandler(ignoreCancelled = true)
    public void handleVehicleDestroy(final VehicleDestroyEvent event) {
        if (!this.plugin.getTimer().isRunning()) {
            return;
        }

        if (!(event.getVehicle() instanceof Minecart) && !(event.getVehicle() instanceof ChestBoat)) {
            return;
        }

        this.dropLootEntity(event.getVehicle());
    }

    private void dropLootEntity(final Entity entity) {
        if (!(entity instanceof Lootable lootable)) {
            return;
        }

        final Inventory inventory;

        switch (entity) {
            case CraftChestBoat chest ->
                    inventory = chest.getInventory();
            case CraftMinecartChest chest ->
                    inventory = chest.getInventory();
            case CraftMinecartHopper hopper ->
                    inventory = hopper.getInventory();
            default -> {
                return;
            }
        }

        ItemStack[] items = inventory.getContents();
        final Location location = entity.getLocation();

        if (location.getWorld() == null) {
            return;
        }

        if (lootable.getLootTable() != null) {
            final LootContext.Builder builder = new LootContext.Builder(entity.getLocation());
            items = lootable.getLootTable().populateLoot(new Random(), builder.build()).toArray(new ItemStack[0]);
        }

        inventory.clear();
        Arrays.stream(items).filter(Objects::nonNull).forEach(item ->
                location.getWorld().dropItemNaturally(location, this.plugin.getManager().addFlag(item)));
    }

}
