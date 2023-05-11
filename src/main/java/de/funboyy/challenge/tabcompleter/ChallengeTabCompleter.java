package de.funboyy.challenge.tabcompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ChallengeTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@Nonnull final CommandSender sender, @Nonnull final Command command,
                                      @Nonnull final String label, @Nonnull final String[] args) {

        final List<String> suggestions = new ArrayList<>();

        if (!sender.hasPermission("randomdrops.command.challenge")) {
            return suggestions;
        }

        if (args.length == 1) {
            suggestions.addAll(Stream.of("start", "stop", "toggleFail").map(String::toLowerCase)
                    .filter(subCommand -> subCommand.startsWith(args[0].toLowerCase())).toList());
        }

        return suggestions;
    }

}
