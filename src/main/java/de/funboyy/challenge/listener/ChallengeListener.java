package de.funboyy.challenge.listener;

import de.funboyy.challenge.Message;
import de.funboyy.challenge.RandomDropsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class ChallengeListener implements Listener {

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            return;
        }

        if (!RandomDropsPlugin.getInstance().failOnDeath()) {
            return;
        }

        RandomDropsPlugin.getInstance().getTimer().stop();
        RandomDropsPlugin.getInstance().getTimer().setFinished(true);

        final String reason = event.getDeathMessage();
        Bukkit.getScheduler().runTaskLater(RandomDropsPlugin.getInstance(), () -> {
            event.getEntity().spigot().respawn();

            Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SPECTATOR));

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(Message.getInstance().getDeathMessage()
                    .replace("%reason%", reason == null ? "N/A" : reason));
            Bukkit.broadcastMessage(Message.getInstance().getDeathFinished());
            Bukkit.broadcastMessage(Message.getInstance().getDeathTime()
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
        Bukkit.broadcastMessage(Message.getInstance().getKillMessage());
        Bukkit.broadcastMessage(Message.getInstance().getKillFinished());
        Bukkit.broadcastMessage(Message.getInstance().getKillTime()
                .replace("%duration%", RandomDropsPlugin.getInstance().getTimer().toString()));
        Bukkit.broadcastMessage("");
    }

}
