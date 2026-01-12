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

import org.bukkit.entity.Player;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * Modern ActionBar implementation using Paper's Adventure API.
 * No reflection needed for 1.21+ Paper servers.
 */
public class ActionBar {

	/**
	 * Send an action bar message to a player using the modern Adventure API.
	 * 
	 * @param player The player to send the action bar to
	 * @param message The message to display (supports legacy color codes with &)
	 */
	public void sendActionBar(Player player, String message) {
		if (player == null || !player.isOnline()) {
			return;
		}
		
		player.sendActionBar(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
	}
}