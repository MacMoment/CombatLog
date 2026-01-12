# CombatLog

A Minecraft Paper plugin to prevent players from logging out to escape combat.

Code was developed with another person. They wrote the original version, and I performed a complete rewrite of the project to bring it up-to-date with the newest Minecraft versions and remove unnecessary features. Currently has over 300,000 downloads.

## Requirements

- Paper 1.21+ (or compatible forks)
- Java 21+

## Features

- Combat tagging system to prevent players from escaping PvP
- ActionBar and BossBar display for combat status
- Configurable tag duration
- Block commands/teleportation while in combat
- Remove fly mode and disguises during combat
- Support for WorldGuard PvP regions
- Support for Factions SafeZone
- PlaceholderAPI integration

## Installation

1. Download the latest release from the releases page
2. Place the JAR file in your server's `plugins` folder
3. Restart your server
4. Configure the plugin in `plugins/CombatLog/config.yml`

## Commands

| Command | Aliases | Description | Permission |
|---------|---------|-------------|------------|
| `/combatlog` | `/cl` | Main plugin command | - |
| `/combatlog help` | `/cl help` | Show help | - |
| `/combatlog reload` | `/cl reload` | Reload configuration | `combatlog.reload` |
| `/tag` | `/ct` | Check remaining combat time | - |

## Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `combatlog.reload` | Allows reloading the configuration | OP |
| `combatlog.update` | Allows seeing update notifications | OP |
| `combatlog.bypass` | Bypass all combat log prevention | false |

## PlaceholderAPI Placeholders

If PlaceholderAPI is installed, the following placeholders are available:

| Placeholder | Description |
|-------------|-------------|
| `%combatlog_in_combat%` | Returns `true` if the player is in combat, `false` otherwise |
| `%combatlog_time_remaining%` | Returns the seconds remaining in combat tag, or `0` if not in combat |
| `%combatlog_tag_duration%` | Returns the configured tag duration in seconds |

## Soft Dependencies

- **PlaceholderAPI** - For placeholder support
- **WorldGuard** - For PvP region detection
- **Factions** - For SafeZone detection
- **LibsDisguises** / **iDisguise** - For disguise removal during combat

## Building

```bash
./gradlew build
```

The built JAR will be located in `build/libs/`.

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.txt](LICENSE.txt) file for details.

