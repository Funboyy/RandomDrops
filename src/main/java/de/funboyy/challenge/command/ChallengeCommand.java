package de.funboyy.challenge.command;

import de.funboyy.challenge.Configuration;
import de.funboyy.challenge.RandomDropsPlugin;
import de.funboyy.challenge.utils.Timer;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

@AllArgsConstructor
public class ChallengeCommand implements CommandExecutor, TabCompleter {

    private final RandomDropsPlugin plugin;

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command,
                             @Nonnull final String label, @Nonnull final String[] args) {

        if (!sender.hasPermission("randomdrops.command.challenge")) {
            sender.sendMessage(this.plugin.getConfiguration().getNoPermission());
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(this.plugin.getConfiguration().getChallengeHelp());
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            this.start(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("stop")) {
            this.stop(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("toggleFail")) {
            this.toggleFail(sender);
            return true;
        }

        sender.sendMessage(this.plugin.getConfiguration().getChallengeHelp());
        return true;
    }

    private void start(final CommandSender sender) {
        if (!sender.hasPermission("randomdrops.command.challenge.start")) {
            sender.sendMessage(this.plugin.getConfiguration().getNoPermission());
            return;
        }

        final Timer timer = this.plugin.getTimer();

        if (timer.isFinished()) {
            sender.sendMessage(this.plugin.getConfiguration().getChallengeAlreadyFinished());
            return;
        }

        if (timer.isRunning()) {
            sender.sendMessage(this.plugin.getConfiguration().getChallengeAlreadyRunning());
            return;
        }

        timer.start();
        sender.sendMessage(this.plugin.getConfiguration().getChallengeStart());
    }

    private void stop(final CommandSender sender) {
        if (!sender.hasPermission("randomdrops.command.challenge.stop")) {
            sender.sendMessage(this.plugin.getConfiguration().getNoPermission());
            return;
        }

        final Timer timer = this.plugin.getTimer();

        if (timer.isFinished()) {
            sender.sendMessage(this.plugin.getConfiguration().getChallengeAlreadyFinished());
            return;
        }

        if (!timer.isRunning()) {
            sender.sendMessage(this.plugin.getConfiguration().getChallengeAlreadyPaused());
            return;
        }

        timer.stop();
        sender.sendMessage(this.plugin.getConfiguration().getChallengePause());
    }

    private void toggleFail(final CommandSender sender) {
        if (!sender.hasPermission("randomdrops.command.challenge.togglefail")) {
            sender.sendMessage(this.plugin.getConfiguration().getNoPermission());
            return;
        }

        if (this.plugin.getTimer().isFinished()) {
            sender.sendMessage(this.plugin.getConfiguration().getChallengeAlreadyFinished());
            return;
        }

        final Configuration configuration = this.plugin.getConfiguration();
        final boolean fail = !configuration.failOnDeath();
        configuration.setFailOnDeath(fail);

        if (fail) {
            sender.sendMessage(configuration.getChallengeToggleEnable());
            return;
        }

        sender.sendMessage(configuration.getChallengeToggleDisable());
    }

    @Override
    public List<String> onTabComplete(@Nonnull final CommandSender sender, @Nonnull final Command command,
                                      @Nonnull final String label, @Nonnull final String[] args) {

        if (!sender.hasPermission("randomdrops.command.challenge")) {
            return List.of();
        }

        if (args.length == 1) {
            return Stream.of("start", "stop", "toggleFail")
                    .map(String::toLowerCase)
                    .filter(argument -> argument.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        return List.of();
    }

}
