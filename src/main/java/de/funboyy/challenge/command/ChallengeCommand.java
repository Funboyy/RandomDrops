package de.funboyy.challenge.command;

import de.funboyy.challenge.Config;
import de.funboyy.challenge.RandomDropsPlugin;
import javax.annotation.Nonnull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChallengeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command,
                             @Nonnull final String label, @Nonnull final String[] args) {

        if (!sender.hasPermission("randomdrops.command.challenge")) {
            sender.sendMessage(Config.getInstance().getNoPermission());
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Config.getInstance().getChallengeHelp());
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            start(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("stop")) {
            stop(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("toggleFail")) {
            toggleFail(sender);
            return true;
        }

        sender.sendMessage(Config.getInstance().getChallengeHelp());
        return true;
    }

    private void start(final CommandSender sender) {
        if (!sender.hasPermission("randomdrops.command.challenge.start")) {
            sender.sendMessage(Config.getInstance().getNoPermission());
            return;
        }

        if (RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            sender.sendMessage(Config.getInstance().getChallengeAlreadyFinished());
            return;
        }

        if (RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            sender.sendMessage(Config.getInstance().getChallengeAlreadyRunning());
            return;
        }

        RandomDropsPlugin.getInstance().getTimer().start();
        sender.sendMessage(Config.getInstance().getChallengeStart());
    }

    private void stop(final CommandSender sender) {
        if (!sender.hasPermission("randomdrops.command.challenge.stop")) {
            sender.sendMessage(Config.getInstance().getNoPermission());
            return;
        }

        if (RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            sender.sendMessage(Config.getInstance().getChallengeAlreadyFinished());
            return;
        }

        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            sender.sendMessage(Config.getInstance().getChallengeAlreadyPaused());
            return;
        }

        RandomDropsPlugin.getInstance().getTimer().stop();
        sender.sendMessage(Config.getInstance().getChallengePause());
    }

    private void toggleFail(final CommandSender sender) {
        if (!sender.hasPermission("randomdrops.command.challenge.togglefail")) {
            sender.sendMessage(Config.getInstance().getNoPermission());
            return;
        }

        if (RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            sender.sendMessage(Config.getInstance().getChallengeAlreadyFinished());
            return;
        }

        final boolean fail = !Config.getInstance().failOnDeath();
        Config.getInstance().setFailOnDeath(fail);

        if (fail) {
            sender.sendMessage(Config.getInstance().getChallengeToggleEnable());
            return;
        }

        sender.sendMessage(Config.getInstance().getChallengeToggleDisable());
    }

}
