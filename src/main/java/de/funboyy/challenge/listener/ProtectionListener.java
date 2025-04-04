package de.funboyy.challenge.listener;

import de.funboyy.challenge.RandomDropsPlugin;
import de.funboyy.challenge.utils.Timer;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
public class ProtectionListener implements Listener {
    
    private final RandomDropsPlugin plugin;

    @EventHandler
    public void handleInventoryClick(final InventoryClickEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning() && !timer.isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockBreak(final BlockBreakEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning() && !timer.isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockPlace(final BlockPlaceEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning() && !timer.isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerDropItem(final PlayerDropItemEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning() && !timer.isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleEntityPickupItem(final EntityPickupItemEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning() && !timer.isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleFoodLevelChange(final FoodLevelChangeEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning()) {
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void handleEntityDamage(final EntityDamageEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerInteract(final PlayerInteractEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning() && !timer.isFinished()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleEntityExplode(final EntityExplodeEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockExplode(final BlockExplodeEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerMove(final PlayerMoveEvent event) {
        final Timer timer = this.plugin.getTimer();
        
        if (!timer.isRunning() && !timer.isFinished()) {
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
