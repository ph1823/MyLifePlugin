package fr.ph1823.GloryRP.Command;

import fr.ph1823.GloryRP.GloryRP1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by ph1823 - Minecraft on 26/03/2016.
 */
public class adminbc implements CommandExecutor

{

    GloryRP1 plugin;

    public adminbc(GloryRP1 instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("broadcast.admin")) {
                if (args.length >= 1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i0 = 0; i0 < args.length; i0++) {
                        sb.append(args[i0]).append(" ");
                    }
                    String message = ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
                    Bukkit.broadcastMessage(ChatColor.RED + "§7§l[§9§lGloryOfRP§f-§cInformations§7§l] §7§l" + sender.getName() + " §r:§9§c§l " + message + ".");
                } else {
                    p.sendMessage("§4[Mylife-Erreur] Un message vide ne sert strictement a rien.");
                    return true;
                }
            } else {

            }

        }

        return true;
    }

}
