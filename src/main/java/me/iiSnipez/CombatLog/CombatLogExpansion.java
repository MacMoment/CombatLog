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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

/**
 * PlaceholderAPI expansion for CombatLog.
 * 
 * Available placeholders:
 * - %combatlog_in_combat% - Returns "true" if player is in combat, "false" otherwise
 * - %combatlog_time_remaining% - Returns seconds remaining in combat tag, or "0" if not in combat
 * - %combatlog_tag_duration% - Returns the configured tag duration in seconds
 */
public class CombatLogExpansion extends PlaceholderExpansion {

    private final CombatLog plugin;

    public CombatLogExpansion(CombatLog plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "combatlog";
    }

    @Override
    public @NotNull String getAuthor() {
        return "iiSnipez";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        switch (params.toLowerCase()) {
            case "in_combat":
                return String.valueOf(plugin.taggedPlayers.containsKey(player.getName()));
                
            case "time_remaining":
                if (plugin.taggedPlayers.containsKey(player.getName())) {
                    long remaining = plugin.tagTime(player.getName());
                    return String.valueOf(Math.max(0, remaining));
                }
                return "0";
                
            case "tag_duration":
                return String.valueOf(plugin.tagDuration);
                
            default:
                return null;
        }
    }
}
