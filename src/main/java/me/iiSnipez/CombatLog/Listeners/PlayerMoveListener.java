package me.iiSnipez.CombatLog.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import me.iiSnipez.CombatLog.CombatLog;
import me.iiSnipez.CombatLog.Events.PlayerUntagEvent;
import me.iiSnipez.CombatLog.Events.PlayerUntagEvent.UntagCause;

public class PlayerMoveListener implements Listener {

	CombatLog plugin;

	public PlayerMoveListener(CombatLog plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
				
		if (plugin.taggedPlayers.containsKey(player.getName())) {
			if (plugin.usesWorldGuard && plugin.removeTagInPvPDisabledArea) {
				try {
					RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
					ApplicableRegionSet regions = query.getApplicableRegions(BukkitAdapter.adapt(player.getLocation()));
					if (regions.queryState(null, Flags.PVP) == StateFlag.State.DENY) {
						PlayerUntagEvent event1 = new PlayerUntagEvent(player, UntagCause.SAFE_AREA);
						plugin.getServer().getPluginManager().callEvent(event1);
					}
				} catch (Exception e) {
					// WorldGuard API might not be available or region not found
				}
			}
			if (plugin.removeFlyEnabled) {
				plugin.removeFly(player);
			}
		}
	}
}