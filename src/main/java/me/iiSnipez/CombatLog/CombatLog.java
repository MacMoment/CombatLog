/*
 *  CombatLog is a plugin for the popular game Minecraft that strives to
 *  make PvP combat in the game more fair
 *  
 *  Copyright (C) 2018 Jarod Saxberg (iiSnipez)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.iiSnipez.CombatLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.iiSnipez.CombatLog.Events.PlayerUntagEvent;
import me.iiSnipez.CombatLog.Events.PlayerUntagEvent.UntagCause;
import me.iiSnipez.CombatLog.Listeners.EntityDamageByEntityListener;
import me.iiSnipez.CombatLog.Listeners.PlayerCommandPreprocessListener;
import me.iiSnipez.CombatLog.Listeners.PlayerDeathListener;
import me.iiSnipez.CombatLog.Listeners.PlayerInteractListener;
import me.iiSnipez.CombatLog.Listeners.PlayerJoinListener;
import me.iiSnipez.CombatLog.Listeners.PlayerKickListener;
import me.iiSnipez.CombatLog.Listeners.PlayerMoveListener;
import me.iiSnipez.CombatLog.Listeners.PlayerQuitListener;
import me.iiSnipez.CombatLog.Listeners.PlayerTagListener;
import me.iiSnipez.CombatLog.Listeners.PlayerTeleportListener;
import me.iiSnipez.CombatLog.Listeners.PlayerToggleFlightListener;
import me.iiSnipez.CombatLog.Listeners.PlayerUntagListener;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CombatLog extends JavaPlugin {

	public Logger log = Logger.getLogger("Minecraft");
	public PluginFile clConfig;
	public CommandExec commandExec;
	public Variables vars;
	public ActionBar aBar;
	public WorldGuardPlugin wg;
	public boolean usesWorldGuard = false;
	public boolean usesPlaceholderAPI = false;
	public Updater updater;
	public boolean updateCheckEnabled = false;
	public boolean updateAvailable = false;
	public boolean MOTDEnabled = false;
	public boolean broadcastEnabled = false;
	public int tagDuration = 10;
	public boolean useActionBar = false;
	public boolean useBossBar = false;
	public boolean removeFlyEnabled = false;
	public boolean removeTagOnKick = false;
	public boolean removeTagOnLagout = false;
	public boolean removeTagInPvPDisabledArea = false;
	public boolean removeInvisPotion = false;
	public boolean blockCommandsEnabled = false;
	public List<String> commandNames = new ArrayList<>();
	public boolean whitelistModeEnabled = false;
	public boolean executeCommandsEnabled = false;
	public List<String> executeCommandList = new ArrayList<>();
	public boolean blockTeleportationEnabled = false;
	public List<String> disableWorldNames = new ArrayList<>();
	public boolean killEnabled = false;
	public String updateCheckMessage = "";
	public boolean updateCheckMessageEnabled = false;
	public String MOTDMessage = "";
	public boolean MOTDMessageEnabled = false;
	public String broadcastMessage = "";
	public boolean broadcastMessageEnabled = false;
	public String taggerMessage = "";
	public boolean taggerMessageEnabled = false;
	public String taggedMessage = "";
	public boolean taggedMessageEnabled = false;
	public String untagMessage = "";
	public boolean untagMessageEnabled = false;
	public String tagTimeMessage = "";
	public boolean tagTimeMessageEnabled = false;
	public String notInCombatMessage = "";
	public boolean notInCombatMessageEnabled = false;
	public String actionBarInCombatMessage = "";
	public String actionBarUntagMessage = "";
	public String removeModesMessage = "";
	public boolean removeModesMessageEnabled = false;
	public String removeInvisMessage = "";
	public boolean removeInvisMessageEnabled = false;
	public String blockCommandsMessage = "";
	public boolean blockCommandsMessageEnabled = false;
	public String blockTeleportationMessage = "";
	public boolean blockTeleportationMessageEnabled = false;
	public String killMessage = "";
	public boolean killMessageEnabled = false;
	public HashMap<String, Long> taggedPlayers = new HashMap<>();
	public ArrayList<String> killPlayers = new ArrayList<>();
	
	public int combatlogs;

	private static final int BSTATS_PLUGIN_ID = 24870;

	@Override
	public void onEnable() {
		checkForPlugins();
		initiateVars();
		loadSettings();
		updateCheck();
		initiateListeners();
		initiateCmds();
		LogHandler();
		enableTimer();
		if (clConfig.getCLConfig().getBoolean("Metrics")) {
			startMetrics();
			metricsTimer();
		}
		registerPlaceholders();
		getLogger().info("CombatLog v" + getPluginMeta().getVersion() + " has been enabled.");
	}

	@Override
	public void onDisable() {
		taggedPlayers.clear();
		getLogger().info("CombatLog v" + getPluginMeta().getVersion() + " has been disabled.");
	}

	public void updateCheck() {
		if (updateCheckEnabled) {
			updater.updateCheck();
		}
	}

	public void checkForPlugins() {
		if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {
			usesWorldGuard = false;
		} else {
			usesWorldGuard = true;
			getLogger().info("WorldGuard plugin found! PvP regions are now detected.");
			wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		}
		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
			usesPlaceholderAPI = false;
		} else {
			usesPlaceholderAPI = true;
			getLogger().info("PlaceholderAPI plugin found! Placeholders will be registered.");
		}
	}

	private void registerPlaceholders() {
		if (usesPlaceholderAPI) {
			new CombatLogExpansion(this).register();
			getLogger().info("PlaceholderAPI placeholders registered!");
		}
	}

	public void startMetrics() {
		Metrics metrics = new Metrics(this, BSTATS_PLUGIN_ID);
		metrics.addCustomChart(new SingleLineChart("combatlogs", () -> combatlogs));
	}

	public void enableTimer() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			Iterator<Map.Entry<String, Long>> iter = taggedPlayers.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Long> c = iter.next();
				Player player = getServer().getPlayer(c.getKey());
				if (player != null && useActionBar) {
					aBar.sendActionBar(player, actionBarInCombatMessage.replace("<time>", tagTimeRemaining(player.getName())));
				}
				if (getCurrentTime() - c.getValue() >= tagDuration) {
					iter.remove();
					if (player != null) {
						PlayerUntagEvent event = new PlayerUntagEvent(player, UntagCause.TIME_EXPIRE);
						getServer().getPluginManager().callEvent(event);
					}
				}
			}
		}, 0L, 20L);
	}

	private void metricsTimer() {
		final Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				combatlogs = 0;
			}
		}, 1000L * 60 * 5, 1000L * 60 * 30);
	}

	public void LogHandler() {
		log.addHandler(new Handler() {
			@Override
			public void publish(LogRecord logRecord) {
				String s = logRecord.getMessage();
				if (s != null && s.contains(" lost connection: ")) {
					String[] a = s.split(" ");
					if (a.length > 3) {
						String disconnectMsg = a[3];
						PlayerQuitListener.setDisconnectMsg(disconnectMsg);
					}
				}
			}

			@Override
			public void flush() {
			}

			@Override
			public void close() throws SecurityException {
			}
		});
	}

	public void initiateCmds() {
		var combatlogCmd = getCommand("combatlog");
		var tagCmd = getCommand("tag");
		if (combatlogCmd != null) {
			combatlogCmd.setExecutor(commandExec);
		}
		if (tagCmd != null) {
			tagCmd.setExecutor(commandExec);
		}
	}

	public void initiateListeners() {
		getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerKickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerTagListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerToggleFlightListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerUntagListener(this), this);
	}

	public void loadSettings() {
		clConfig.getCLConfig().options().copyDefaults(true);
		clConfig.saveDefault();
		clConfig.reloadCLConfig();

		vars.getValues();
	}

	public void initiateVars() {
		clConfig = new PluginFile(this);
		updater = new Updater(this);
		commandExec = new CommandExec(this);
		vars = new Variables(this);
		aBar = new ActionBar();
	}

	public void removeFly(Player player) {
		if (player.isFlying() && removeFlyEnabled) {
			player.setFlying(false);
			if (removeModesMessageEnabled) {
				player.sendMessage(translateText(removeModesMessage.replace("<mode>", "flight")));
			}
		}
	}

	public String tagTimeRemaining(String id) {
		Long tagTime = taggedPlayers.get(id);
		if (tagTime == null) {
			return "0";
		}
		return String.valueOf(tagDuration - (getCurrentTime() - tagTime));
	}

	public long tagTime(String id) {
		Long tagTime = taggedPlayers.get(id);
		if (tagTime == null) {
			return 0;
		}
		return tagDuration - (getCurrentTime() - tagTime);
	}

	public long getCurrentTime() {
		return System.currentTimeMillis() / 1000L;
	}

	public void broadcastMsg(String string) {
		getServer().broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(string));
	}

	public String translateText(String string) {
		return LegacyComponentSerializer.legacyAmpersand().serialize(
			LegacyComponentSerializer.legacyAmpersand().deserialize(string)
		);
	}
}