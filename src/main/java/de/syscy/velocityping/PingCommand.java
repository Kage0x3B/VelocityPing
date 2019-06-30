package de.syscy.velocityping;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PingCommand implements Command {
	private final ProxyServer proxyServer;

	public PingCommand(ProxyServer proxyServer) {
		this.proxyServer = proxyServer;
	}

	@Override
	public void execute(CommandSource source, @NonNull String[] args) {
		if(args.length == 0) {
			if(!(source instanceof Player)) {
				source.sendMessage(TextComponent.of("Only players can check their own ping!", TextColor.RED));

				return;
			}

			if(source.getPermissionValue("velocityping.self") == Tristate.FALSE) {
				source.sendMessage(TextComponent.of("You do not have permission!", TextColor.RED));

				return;
			}

			source.sendMessage(TextComponent.of("Your ping is " + ((Player) source).getPing() + "ms.", TextColor.GREEN));
		} else if(args.length == 1) {
			if(source.getPermissionValue("velocityping.others") == Tristate.FALSE) {
				source.sendMessage(TextComponent.of("You do not have permission!", TextColor.RED));

				return;
			}

			Optional<Player> target = proxyServer.getPlayer(args[0]);

			if(!target.isPresent()) {
				source.sendMessage(TextComponent.of("The player is not online!", TextColor.RED));

				return;
			}

			source.sendMessage(TextComponent.of(target.get().getUsername() + "'s ping is " + target.get().getPing() + "ms.", TextColor.GREEN));
		} else {
			source.sendMessage(TextComponent.of("Usage: /ping [player]", TextColor.RED));
		}
	}

	@Override
	public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
		if (currentArgs.length == 0) {
			return proxyServer.getAllPlayers().stream().map(Player::getUsername).collect(Collectors.toList());
		} else if (currentArgs.length == 1) {
			return proxyServer.getAllPlayers().stream().map(Player::getUsername).filter(name -> name.regionMatches(true, 0, currentArgs[0], 0, currentArgs[0].length())).collect(Collectors.toList());
		} else {
			return ImmutableList.of();
		}
	}
}