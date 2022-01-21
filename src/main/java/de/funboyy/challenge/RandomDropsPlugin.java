package de.funboyy.challenge;

import de.funboyy.challenge.command.ChallengeCommand;
import de.funboyy.challenge.listener.ChallengeListener;
import de.funboyy.challenge.listener.ProtectionListener;
import de.funboyy.challenge.listener.RandomDropListener;
import de.funboyy.challenge.utils.Timer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomDropsPlugin extends JavaPlugin {

    public static RandomDropsPlugin getInstance() {
        return getPlugin(RandomDropsPlugin.class);
    }

    @Getter private Timer timer;
    @Accessors(fluent = true) @Getter @Setter private boolean failOnDeath = true;

    @Override
    public void onEnable() {
        this.timer = new Timer();
        timer();

        Bukkit.getPluginManager().registerEvents(new ChallengeListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProtectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new RandomDropListener(), this);

        final PluginCommand command = getCommand("challenge");
        if (command != null) {
            command.setExecutor(new ChallengeCommand());
        }
    }

    private void timer() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            final TextComponent text = new TextComponent(this.timer.toString());
            text.setColor(ChatColor.GOLD);
            text.setBold(true);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
        }), 0, 10);
    }
}
