package de.funboyy.challenge;

import de.funboyy.challenge.command.ChallengeCommand;
import de.funboyy.challenge.listener.ChallengeListener;
import de.funboyy.challenge.listener.ProtectionListener;
import de.funboyy.challenge.listener.RandomDropListener;
import de.funboyy.challenge.tabcompleter.ChallengeTabCompleter;
import de.funboyy.challenge.utils.Timer;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomDropsPlugin extends JavaPlugin {

    public static RandomDropsPlugin getInstance() {
        return getPlugin(RandomDropsPlugin.class);
    }

    @Getter private Timer timer;

    @Override
    public void onEnable() {
        loadConfig();

        this.timer = new Timer();
        timer();

        Bukkit.getPluginManager().registerEvents(new ChallengeListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProtectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new RandomDropListener(), this);

        final PluginCommand command = getCommand("challenge");

        if (command != null) {
            command.setExecutor(new ChallengeCommand());
            command.setTabCompleter(new ChallengeTabCompleter());
        }
    }

    private void loadConfig() {
        this.saveDefaultConfig();

        this.getConfig().options().copyDefaults(true);
        this.reloadConfig();
    }

    private void timer() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            final ComponentBuilder builder = new ComponentBuilder("")
                    .append(this.timer.toString()).color(ChatColor.GOLD).bold(true);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, builder.create());
        }), 0, 10);
    }

}
