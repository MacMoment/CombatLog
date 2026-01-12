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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Updater {

	private final CombatLog plugin;

	public Updater(CombatLog plugin) {
		this.plugin = plugin;
	}

	private static final String KEY = "key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=";
	private static final String ID = "36192";

	public void updateCheck() {
		try {
			HttpURLConnection connection = (HttpURLConnection) URI.create("http://www.spigotmc.org/api/general.php")
					.toURL().openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.getOutputStream().write((KEY + ID).getBytes("UTF-8"));
			String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
			if (version != null && !version.equals(plugin.getPluginMeta().getVersion())) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + "[CombatLog] Update available! Current: " + ChatColor.GOLD
								+ plugin.getPluginMeta().getVersion() + ChatColor.GREEN + " New: " + ChatColor.GOLD
								+ version);
				plugin.updateAvailable = true;
			}
			connection.disconnect();
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage("[CombatLog] Failed to connect to spigotmc.org! Update check did not work.");
		}
	}
}
