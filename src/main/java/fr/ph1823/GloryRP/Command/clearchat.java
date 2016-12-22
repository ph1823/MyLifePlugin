package fr.ph1823.GloryRP.Command;

import fr.ph1823.GloryRP.GloryRP1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by ph1823 - Minecraft on 26/03/2016.
 */
public class clearchat implements CommandExecutor

{

    GloryRP1 plugin;

    public clearchat(GloryRP1 instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        for (int i = 0; i <= 100; i++) {
            Bukkit.broadcastMessage("  ");
        }

        if (args.length >= 1) {
            StringBuilder sb = new StringBuilder();
            for (int i0 = 0; i0 < args.length; i0++) {
                sb.append(args[i0]).append(" ");
            }
            String message = ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
            Bukkit.broadcastMessage(ChatColor.RED + "[ClearChat] Le chat a était nettoyé par " + sender.getName() + " pour : " + message + ".");
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "[ClearChat] Le chat a était nettoyé par " + sender.getName() + " sans aucune rasion.");
            return true;
        }

        return true;
    }

}
