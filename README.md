# PunishGUI
A Minecraft plugin that allows you to customize a GUI of punishments to make issuing mutes, kicks, or bans more linear, ultimately reducing the number of errors administrators make when issuing punishments.

## Commands & Permissions

|Command|Description|Permission|
|-------|:-----------:|----------|
|`punishreload`|Reloads the configuration file|`punishgui.reload`|
|`punishgui <player>`|Opens the general punish menu|`punishgui.punish`|
|`mutegui <player>`|Opens the mute menu for muting a player|`punishgui.mute`|
|`kickgui <player>`|Opens the kick menu for kicking a player|`punishgui.kick`|
|`bangui <player>`|Opens the ban menu for banning a player|`punishgui.ban`|

## To-do
- Usage text for mutegui, kickgui, and bangui are printed in white instead of red.
- Refactor other parts of the plugin to make it easier to maintain.
- ~~Fix `MenuItemBuilder` using same instance of the item stack it's modifying  
preventing the developer from using the same `MenuItemBuilder` for different  
menu items.~~

