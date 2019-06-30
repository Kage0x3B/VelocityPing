package de.syscy.velocityping;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(id = "velocityping", name = "VelocityPing", version = "1.0", description = "Adds a /ping command", authors = { "Kage0x3B" })
public class VelocityPingPlugin {
	@Inject
	public VelocityPingPlugin(ProxyServer proxyServer, CommandManager commandManager) {
		commandManager.register(new PingCommand(proxyServer), "velocityping", "vping");
	}
}