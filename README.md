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
- Refactor other parts of the plugin to make it easier to maintain.
- ~~Only create menu items for players with permissions to issue punishment  
(E.g. A player shouldn't have the ban menu item if they don't have permission to ban).~~

## Done
- Usage text for mutegui, kickgui, and bangui are printed in white instead of red.
- Fix `MenuItemBuilder` using same instance of the item stack it's modifying  
preventing the developer from using the same `MenuItemBuilder` for different  
menu items.
- Fix bug where if the punisher tries to mute a player twice the clicked  
menu item is dropped.

