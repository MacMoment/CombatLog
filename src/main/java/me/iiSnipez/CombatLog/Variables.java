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

public class Variables {

	CombatLog plugin;

	public Variables(CombatLog plugin) {
		this.plugin = plugin;
	}
	
	public void getValues() {
		plugin.getLogger().info("Loading config.yml");
		// configuration
		plugin.updateCheckEnabled = plugin.clConfig.getCLConfig().getBoolean("UpdateCheck");
		plugin.MOTDEnabled = plugin.clConfig.getCLConfig().getBoolean("MOTD");
		plugin.broadcastEnabled = plugin.clConfig.getCLConfig().getBoolean("Broadcast");
		plugin.tagDuration = plugin.clConfig.getCLConfig().getInt("Tag-Duration");
		if (plugin.clConfig.getCLConfig().getStringList("Remove-Modes").contains("fly")) {
			plugin.removeFlyEnabled = true;
		}
		plugin.removeTagOnKick = plugin.clConfig.getCLConfig().getBoolean("Remove-Tag.onKick");
		plugin.removeTagOnLagout = plugin.clConfig.getCLConfig().getBoolean("Remove-Tag.onLagout");
		plugin.removeTagInPvPDisabledArea = plugin.clConfig.getCLConfig().getBoolean("Remove-Tag.inPvPDisabledArea");
		plugin.removeInvisPotion = plugin.clConfig.getCLConfig().getBoolean("Remove-Invis-Potion");
		plugin.useActionBar = plugin.clConfig.getCLConfig().getBoolean("ActionBar");
		plugin.useBossBar = plugin.clConfig.getCLConfig().getBoolean("BossBar");
		plugin.blockCommandsEnabled = plugin.clConfig.getCLConfig().getBoolean("Block-Commands");
		plugin.whitelistModeEnabled = plugin.clConfig.getCLConfig().getBoolean("Whitelist-Mode");
		plugin.commandNames = plugin.clConfig.getCLConfig().getStringList("Commands");
		plugin.executeCommandsEnabled = plugin.clConfig.getCLConfig().getBoolean("Execute-Commands");
		plugin.executeCommandList = plugin.clConfig.getCLConfig().getStringList("ExecutedCommands");
		plugin.blockTeleportationEnabled = plugin.clConfig.getCLConfig().getBoolean("Block-Teleportation");
		plugin.disableWorldNames = plugin.clConfig.getCLConfig().getStringList("Disabled-Worlds");
		plugin.killEnabled = plugin.clConfig.getCLConfig().getBoolean("Kill");
		// messages
		plugin.updateCheckMessage = plugin.clConfig.getCLConfig().getString("UpdateCheckMessage");
		plugin.updateCheckMessageEnabled = !plugin.updateCheckMessage.equalsIgnoreCase("false");
		
		plugin.MOTDMessage = plugin.clConfig.getCLConfig().getString("MOTDMessage");
		plugin.MOTDMessageEnabled = !plugin.MOTDMessage.equalsIgnoreCase("false");
		
		plugin.broadcastMessage = plugin.clConfig.getCLConfig().getString("BroadcastMessage");
		plugin.broadcastMessageEnabled = !plugin.broadcastMessage.equalsIgnoreCase("false");
		
		plugin.taggerMessage = plugin.clConfig.getCLConfig().getString("TaggerMessage");
		plugin.taggerMessageEnabled = !plugin.taggerMessage.equalsIgnoreCase("false");
		
		plugin.taggedMessage = plugin.clConfig.getCLConfig().getString("TaggedMessage");
		plugin.taggedMessageEnabled = !plugin.taggedMessage.equalsIgnoreCase("false");
		
		plugin.untagMessage = plugin.clConfig.getCLConfig().getString("UntagMessage");
		plugin.untagMessageEnabled = !plugin.untagMessage.equalsIgnoreCase("false");
		
		plugin.tagTimeMessage = plugin.clConfig.getCLConfig().getString("InCombatMessage");
		plugin.tagTimeMessageEnabled = !plugin.tagTimeMessage.equalsIgnoreCase("false");
		
		plugin.notInCombatMessage = plugin.clConfig.getCLConfig().getString("NotInCombatMessage");
		plugin.notInCombatMessageEnabled = !plugin.notInCombatMessage.equalsIgnoreCase("false");
		
		plugin.actionBarInCombatMessage = plugin.clConfig.getCLConfig().getString("ActionBarInCombatMessage");
		plugin.actionBarUntagMessage = plugin.clConfig.getCLConfig().getString("ActionBarUntagMessage");
		plugin.removeModesMessage = plugin.clConfig.getCLConfig().getString("RemoveModesMessage");
		plugin.removeModesMessageEnabled = !plugin.removeModesMessage.equalsIgnoreCase("false");
		
		plugin.removeInvisMessage = plugin.clConfig.getCLConfig().getString("RemoveInvisMessage");
		plugin.removeInvisMessageEnabled = !plugin.removeInvisMessage.equalsIgnoreCase("false");
		
		plugin.blockCommandsMessage = plugin.clConfig.getCLConfig().getString("BlockCommandsMessage");
		plugin.blockCommandsMessageEnabled = !plugin.blockCommandsMessage.equalsIgnoreCase("false");
		
		plugin.blockTeleportationMessage = plugin.clConfig.getCLConfig().getString("BlockTeleportationMessage");
		plugin.blockTeleportationMessageEnabled = !plugin.blockTeleportationMessage.equalsIgnoreCase("false");
		
		plugin.killMessage = plugin.clConfig.getCLConfig().getString("KillMessage");
		plugin.killMessageEnabled = !plugin.killMessage.equalsIgnoreCase("false");
	}
}