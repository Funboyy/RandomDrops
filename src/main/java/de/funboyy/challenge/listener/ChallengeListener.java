package de.funboyy.challenge.listener;

import de.funboyy.challenge.Configuration;
import de.funboyy.challenge.RandomDropsPlugin;
import de.funboyy.challenge.utils.Timer;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class ChallengeListener implements Listener {

    private final RandomDropsPlugin plugin;

    @EventHandler
    public void handlePlayerDeath(final PlayerDeathEvent event) {
        final Timer timer = this.plugin.getTimer();

        if (!timer.isRunning()) {
            return;
        }

        final Configuration configuration = this.plugin.getConfiguration();

        if (!configuration.failOnDeath()) {
            return;
        }

        timer.stop();
        timer.finish();

        final String reason = event.getDeathMessage();

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            event.getEntity().spigot().respawn();

            Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SPECTATOR));

            final ComponentBuilder builder = new ComponentBuilder("")
                    .append("\n")
                    .append(configuration.getDeathMessage().replace("%deathMessage%", reason == null ? "error" : reason))
                    .append("\n")
                    .append(configuration.getDeathFinished())
                    .append("\n")
                    .append(configuration.getDeathTime().replace("%duration%", timer.getFormattedDuration()))
                    .append("\n");

            Bukkit.spigot().broadcast(builder.build());
        }, 2);

        event.setDeathMessage(null);
    }

    @EventHandler
    public void handleEntityDeath(final EntityDeathEvent event) {
        final Timer timer = this.plugin.getTimer();

        if (!timer.isRunning()) {
            return;
        }

        if (!(event.getEntity() instanceof EnderDragon)) {
            return;
        }

        timer.stop();
        timer.finish();

        final Configuration configuration = this.plugin.getConfiguration();
        final ComponentBuilder builder = new ComponentBuilder("")
                .append("\n")
                .append(configuration.getKillMessage())
                .append("\n")
                .append(configuration.getKillFinished())
                .append("\n")
                .append(configuration.getKillTime().replace("%duration%", timer.getFormattedDuration()))
                .append("\n");

        Bukkit.spigot().broadcast(builder.build());
    }

}
