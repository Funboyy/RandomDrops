package de.funboyy.challenge.listener;

import de.funboyy.challenge.RandomDropsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ProtectionListener implements Listener {

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning() &&
                !RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning() &&
                !RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning() &&
                !RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning() &&
                !RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(final EntityPickupItemEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning() &&
                !RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(final FoodLevelChangeEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning() &&
                !RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(final BlockExplodeEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning() &&
                !RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            final Player player = event.getPlayer();

            if (event.getTo() == null) {
                return;
            }

            if (event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                    event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
                return;
            }

            player.teleport(event.getFrom());
        }
    }

}
