package de.funboyy.challenge.command;

import de.funboyy.challenge.Message;
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
            sender.sendMessage(Message.getInstance().getNoPermission());
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Message.getInstance().getChallengeHelp());
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

        sender.sendMessage(Message.getInstance().getChallengeHelp());
        return true;
    }

    private void start(final CommandSender sender) {
        if (RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            sender.sendMessage(Message.getInstance().getChallengeAlreadyFinished());
            return;
        }

        if (RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            sender.sendMessage(Message.getInstance().getChallengeAlreadyRunning());
            return;
        }

        RandomDropsPlugin.getInstance().getTimer().start();
        sender.sendMessage(Message.getInstance().getChallengeStart());
    }

    private void stop(final CommandSender sender) {
        if (RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            sender.sendMessage(Message.getInstance().getChallengeAlreadyFinished());
            return;
        }

        if (!RandomDropsPlugin.getInstance().getTimer().isRunning()) {
            sender.sendMessage(Message.getInstance().getChallengeAlreadyPaused());
            return;
        }

        RandomDropsPlugin.getInstance().getTimer().stop();
        sender.sendMessage(Message.getInstance().getChallengePause());
    }

    private void toggleFail(final CommandSender sender) {
        if (RandomDropsPlugin.getInstance().getTimer().isFinished()) {
            sender.sendMessage(Message.getInstance().getChallengeAlreadyFinished());
            return;
        }

        final boolean fail = !RandomDropsPlugin.getInstance().failOnDeath();
        RandomDropsPlugin.getInstance().failOnDeath(fail);

        if (fail) {
            sender.sendMessage(Message.getInstance().getChallengeToggleEnable());
            return;
        }

        sender.sendMessage(Message.getInstance().getChallengeToggleDisable());
    }

}
