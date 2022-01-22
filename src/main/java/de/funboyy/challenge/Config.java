package de.funboyy.challenge;

import javax.annotation.Nullable;
import net.md_5.bungee.api.ChatColor;

public class Config {

    private static Config instance;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }


    private String replace(@Nullable final String message) {
        if (message == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }

        return ChatColor.translateAlternateColorCodes('&', message.replace("%prefix%", getPrefix()));
    }

    public boolean failOnDeath() {
        return RandomDropsPlugin.getInstance().getConfig().getBoolean("failOnDeath");
    }

    public void setFailOnDeath(final boolean failOnDeath) {
        RandomDropsPlugin.getInstance().getConfig().set("failOnDeath", failOnDeath);
        RandomDropsPlugin.getInstance().saveConfig();
    }


    private String getPrefix() {
        return RandomDropsPlugin.getInstance().getConfig().getString("prefix");
    }

    public String getNoPermission() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.no-permission"));
    }


    public String getChallengeHelp() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.challenge.usage"));
    }

    public String getChallengeAlreadyRunning() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.challenge.already.running"));
    }

    public String getChallengeAlreadyPaused() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.challenge.already.paused"));
    }

    public String getChallengeAlreadyFinished() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.challenge.already.finished"));
    }

    public String getChallengeStart() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.challenge.start"));
    }

    public String getChallengePause() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.challenge.pause"));
    }

    public String getChallengeToggleEnable() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.challenge.toggleFail.enabled"));
    }

    public String getChallengeToggleDisable() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.command.challenge.toggleFail.disabled"));
    }


    public String getDeathMessage() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.failed.reason"));
    }

    public String getDeathFinished() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.failed.message"));
    }

    public String getDeathTime() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.failed.duration"));
    }


    public String getKillMessage() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.completed.reason"));
    }

    public String getKillFinished() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.completed.message"));
    }

    public String getKillTime() {
        return replace(RandomDropsPlugin.getInstance().getConfig().getString("message.completed.duration"));
    }

}
