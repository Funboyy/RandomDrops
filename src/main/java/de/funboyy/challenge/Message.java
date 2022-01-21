package de.funboyy.challenge;

import net.md_5.bungee.api.ChatColor;

public class Message {

    private static Message instance;

    public static Message getInstance() {
        if (instance == null) {
            instance = new Message();
        }
        return instance;
    }


    private String replace(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("%prefix%", getPrefix()));
    }


    private String getPrefix() {
        return "&6RandomDrops &8|";
    }

    public String getNoPermission() {
        return replace("%prefix% &cUnzureichende Rechte.");
    }


    public String getChallengeHelp() {
        return replace("%prefix% &7Nutze &e/challenge <start/stop/toggleFail>");
    }

    public String getChallengeAlreadyRunning() {
        return replace("%prefix% &7Die Challenge wurde &ebereits &7gestartet.");
    }

    public String getChallengeAlreadyPaused() {
        return replace("%prefix% &7Die Challenge wurde &ebereits &7pausiert.");
    }

    public String getChallengeAlreadyFinished() {
        return replace("%prefix% &7Die Challenge wurde &ebereits &7beendet.");
    }

    public String getChallengeStart() {
        return replace("%prefix% &7Die Challenge wurde &egestartet&7.");
    }

    public String getChallengePause() {
        return replace("%prefix% &7Die Challenge wurde &epausiert&7.");
    }

    public String getChallengeToggleEnable() {
        return replace("%prefix% &7Die Challenge wird &ebeendet&7, wenn ein Spieler &estirbt&7.");
    }

    public String getChallengeToggleDisable() {
        return replace("%prefix% &7Die Challenge wird &enicht &7mehr &ebeendet&7, wenn ein Spieler &estirbt&7.");
    }


    public String getDeathMessage() {
        return replace("%prefix% &7%reason%");
    }

    public String getDeathFinished() {
        return replace("%prefix% &cDie Challenge ist somit beendet!");
    }

    public String getDeathTime() {
        return replace("%prefix% &7Benötigte Zeit: &e%duration%");
    }


    public String getKillMessage() {
        return replace("%prefix% &7Der Enderdrache wurde getötet");
    }

    public String getKillFinished() {
        return replace("%prefix% &aDie Challenge ist somit geschafft!");
    }

    public String getKillTime() {
        return replace("%prefix% &7Benötigte Zeit: &e%duration%");
    }

}
