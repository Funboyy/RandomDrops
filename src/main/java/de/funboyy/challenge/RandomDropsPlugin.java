package de.funboyy.challenge;

import de.funboyy.challenge.command.ChallengeCommand;
import de.funboyy.challenge.listener.ChallengeListener;
import de.funboyy.challenge.listener.ProtectionListener;
import de.funboyy.challenge.listener.RandomDropListener;
import de.funboyy.challenge.utils.ComponentManager;
import de.funboyy.challenge.utils.RandomDrop;
import de.funboyy.challenge.utils.Timer;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RandomDropsPlugin extends JavaPlugin {

    private Timer timer;
    private RandomDrop random;
    private ComponentManager manager;
    private Configuration configuration;

    @Override
    public void onEnable() {
        this.loadConfig();

        this.timer = new Timer(this);
        this.random = new RandomDrop();
        this.manager = new ComponentManager();
        this.configuration = new Configuration(this);

        final PluginManager manager = super.getServer().getPluginManager();
        manager.registerEvents(new ChallengeListener(this), this);
        manager.registerEvents(new ProtectionListener(this), this);
        manager.registerEvents(new RandomDropListener(this), this);

        final PluginCommand command = getCommand("challenge");

        if (command != null) {
            final ChallengeCommand challenge = new ChallengeCommand(this);

            command.setExecutor(challenge);
            command.setTabCompleter(challenge);
        }
    }

    private void loadConfig() {
        super.saveDefaultConfig();

        super.getConfig().options().copyDefaults(true);
        super.reloadConfig();
    }

}
