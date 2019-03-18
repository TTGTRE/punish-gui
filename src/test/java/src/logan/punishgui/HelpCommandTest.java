/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package logan.punishgui;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import logan.punishgui.HelpCommand;
import logan.punishgui.PunishPlugin;

public class HelpCommandTest {
    
    HelpCommand helpCommand = new HelpCommand();

    @Test
    public void onCommand() {

        CommandSender sender = mock(CommandSender.class);
        when(sender.hasPermission((String) (argThat(string -> string.equals(PunishPlugin.HELP_PERM))))).thenReturn(true);

        Command command = mock(Command.class);

        assertTrue(helpCommand.onCommand(sender, command, "punishgui", new String[0]));
        verify(sender, atMost(5)).sendMessage(anyString());

        assertFalse(helpCommand.onCommand(sender, command, "bogus", new String[0]));
        verify(sender, atMost(5)).sendMessage(anyString());

        assertFalse(helpCommand.onCommand(sender, command, "punishgui", new String[1]));
        verify(sender, atMost(5)).sendMessage(anyString());
    }
}