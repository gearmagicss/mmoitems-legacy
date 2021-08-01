// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.List;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeExplorer;
import net.Indyuce.mmoitems.api.ConfigFile;
import net.Indyuce.mmoitems.gui.edition.ItemEdition;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.Parameter;
import net.Indyuce.mmoitems.command.MMOItemsCommandTreeRoot;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class CopyCommandTreeNode extends CommandTreeNode
{
    public CopyCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "copy");
        this.addParameter(MMOItemsCommandTreeRoot.TYPE);
        this.addParameter(MMOItemsCommandTreeRoot.ID_2);
        this.addParameter(new Parameter("<new-id>", (p0, p1) -> {}));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        if (array.length < 4) {
            return CommandTreeNode.CommandResult.THROW_USAGE;
        }
        if (!Type.isValid(array[1])) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item type called " + array[1].toUpperCase().replace("-", "_") + ".");
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Type " + ChatColor.GREEN + "/mi list type " + ChatColor.GRAY + "to see all the available item types.");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final Type value = Type.get(array[1]);
        final ConfigFile configFile = value.getConfigFile();
        final String replace = array[2].toUpperCase().replace("-", "_");
        if (!configFile.getConfig().contains(replace)) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is no item called " + replace + ".");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        final String upperCase = array[3].toUpperCase();
        if (configFile.getConfig().contains(upperCase)) {
            commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "There is already an item called " + upperCase + "!");
            return CommandTreeNode.CommandResult.FAILURE;
        }
        configFile.getConfig().set(upperCase, (Object)configFile.getConfig().getConfigurationSection(replace));
        configFile.save();
        MMOItems.plugin.getTemplates().requestTemplateUpdate(value, upperCase);
        if (commandSender instanceof Player) {
            new ItemEdition((Player)commandSender, MMOItems.plugin.getTemplates().getTemplate(value, upperCase)).open();
        }
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GREEN + "You successfully copied " + replace + " to " + upperCase + "!");
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
