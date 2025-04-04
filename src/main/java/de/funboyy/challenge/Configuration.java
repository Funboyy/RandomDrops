package de.funboyy.challenge;

import javax.annotation.Nullable;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    private final RandomDropsPlugin plugin;
    private final FileConfiguration config;
    
    public Configuration(final RandomDropsPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    private String replace(@Nullable final String message) {
        if (message == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }

        return ChatColor.translateAlternateColorCodes('&', message.replace("%prefix%", getPrefix()));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean failOnDeath() {
        return this.config.getBoolean("failOnDeath");
    }

    public void setFailOnDeath(final boolean failOnDeath) {
        this.config.set("failOnDeath", failOnDeath);
        this.plugin.saveConfig();
    }


    private String getPrefix() {
        return this.config.getString("prefix");
    }

    public String getNoPermission() {
        return this.replace(this.config.getString("message.command.no-permission"));
    }


    public String getChallengeHelp() {
        return this.replace(this.config.getString("message.command.challenge.usage"));
    }

    public String getChallengeAlreadyRunning() {
        return this.replace(this.config.getString("message.command.challenge.already.running"));
    }

    public String getChallengeAlreadyPaused() {
        return this.replace(this.config.getString("message.command.challenge.already.paused"));
    }

    public String getChallengeAlreadyFinished() {
        return this.replace(this.config.getString("message.command.challenge.already.finished"));
    }

    public String getChallengeStart() {
        return this.replace(this.config.getString("message.command.challenge.start"));
    }

    public String getChallengePause() {
        return this.replace(this.config.getString("message.command.challenge.pause"));
    }

    public String getChallengeToggleEnable() {
        return this.replace(this.config.getString("message.command.challenge.toggleFail.enabled"));
    }

    public String getChallengeToggleDisable() {
        return this.replace(this.config.getString("message.command.challenge.toggleFail.disabled"));
    }


    public String getDeathMessage() {
        return this.replace(this.config.getString("message.failed.reason"));
    }

    public String getDeathFinished() {
        return this.replace(this.config.getString("message.failed.message"));
    }

    public String getDeathTime() {
        return this.replace(this.config.getString("message.failed.duration"));
    }


    public String getKillMessage() {
        return this.replace(this.config.getString("message.completed.reason"));
    }

    public String getKillFinished() {
        return this.replace(this.config.getString("message.completed.message"));
    }

    public String getKillTime() {
        return this.replace(this.config.getString("message.completed.duration"));
    }

}
