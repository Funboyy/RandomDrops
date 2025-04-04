package de.funboyy.challenge.utils;

import de.funboyy.challenge.RandomDropsPlugin;
import java.time.Instant;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;

public class Timer {

    private long start = 0;
    private long duration = 0;
    private boolean running = false;
    @Getter
    private boolean finished = false;

    public Timer(final RandomDropsPlugin plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () ->
                Bukkit.getOnlinePlayers().forEach(player -> {
                    final ComponentBuilder builder = new ComponentBuilder("")
                            .append(plugin.getTimer().getFormattedDuration()).color(ChatColor.GOLD).bold(true);

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, builder.build());
                }), 0, 10);
    }

    public void start() {
        if (this.running || this.finished) {
            return;
        }

        this.running = true;
        this.start = Instant.now().toEpochMilli();
    }

    public void stop() {
        if (!this.running) {
            return;
        }

        this.duration += (Instant.now().toEpochMilli() - this.start);
        this.start = 0;
        this.running = false;
    }

    public void finish() {
        this.finished = true;
    }

    public boolean isRunning() {
        return this.running && !this.finished;
    }

    public long getDuration() {
        if (this.start == 0) {
            return this.duration;
        }

        return (Instant.now().toEpochMilli() - this.start) + this.duration;
    }

    public String getFormattedDuration() {
        final long duration = getDuration();

        final long days = duration / 1000 / 60 / 60 / 24;
        final long hours = duration / 1000 / 60 / 60 % 24;
        final long minutes = duration / 1000 / 60 % 60;
        final long seconds = duration / 1000 % 60;

        final long[] times = new long[]{ days, hours, minutes, seconds };
        final String[] units = new String[]{ "d", "h", "m", "s" };

        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < times.length; i++) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }

            if (times[i] <= 0 && builder.isEmpty() && i != times.length - 1) {
                continue;
            }

            builder.append(times[i]).append(units[i]);
        }

        return builder.toString();
    }

}
