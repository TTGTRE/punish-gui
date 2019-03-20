v1.1
- Support for 1.13.2
- Only create menu items for players with permissions to issue punishment  
(E.g. A player shouldn't have the ban menu item if they don't have permission to ban).
- Refactor other parts of the plugin to make it easier to maintain.
- Implement right-click punishing functionality.
- Fix punishments sometimes punishing the wrong player depending on who the last  
person punished was (Caused by listeners not being added for new menu).
- Fix menus duplicating (Caused by adding listeners more than once).
- Fix usage text for mutegui, kickgui, and bangui are printed in white instead of red.
- Fix `MenuItemBuilder` using same instance of the item stack it's modifying  
preventing the developer from using the same `MenuItemBuilder` for different  
menu items.
- Fix bug where if the punisher tries to mute a player twice the clicked  
menu item is dropped.
