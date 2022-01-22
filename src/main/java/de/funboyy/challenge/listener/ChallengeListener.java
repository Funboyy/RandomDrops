package de.funboyy.challenge.listener;

import de.funboyy.challenge.Config;
import de.funboyy.challenge.RandomDropsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ChallengeListener implements Listener {

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        if (!Config.getInstance().failOnDeath()) {
            return;
        }

        RandomDropsPlugin.getInstance().getTimer().stop();
        RandomDropsPlugin.getInstance().getTimer().setFinished(true);

        final String reason = event.getDeathMessage();
        Bukkit.getScheduler().runTaskLater(RandomDropsPlugin.getInstance(), () -> {
            event.getEntity().spigot().respawn();

            Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SPECTATOR));

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(Config.getInstance().getDeathMessage()
                    .replace("%deathMessage%", reason == null ? "error" : reason));
            Bukkit.broadcastMessage(Config.getInstance().getDeathFinished());
            Bukkit.broadcastMessage(Config.getInstance().getDeathTime()
                    .replace("%duration%", RandomDropsPlugin.getInstance().getTimer().toString()));
            Bukkit.broadcastMessage("");
        }, 2);

        event.setDeathMessage(null);
    }

    @EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        if (!(event.getEntity() instanceof EnderDragon)) {
            return;
        }

        RandomDropsPlugin.getInstance().getTimer().stop();
        RandomDropsPlugin.getInstance().getTimer().setFinished(true);

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(Config.getInstance().getKillMessage());
        Bukkit.broadcastMessage(Config.getInstance().getKillFinished());
        Bukkit.broadcastMessage(Config.getInstance().getKillTime()
                .replace("%duration%", RandomDropsPlugin.getInstance().getTimer().toString()));
        Bukkit.broadcastMessage("");
    }

}
