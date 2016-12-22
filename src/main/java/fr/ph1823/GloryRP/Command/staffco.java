package fr.ph1823.GloryRP.Command;

import fr.ph1823.GloryRP.GloryRP1;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by ph1823 - Minecraft on 26/03/2016.
 */
public class staffco implements CommandExecutor

{

    GloryRP1 plugin;

    public staffco(GloryRP1 instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            ArrayList<String> online = new ArrayList<>();
            Player p = (Player) sender;
            for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                if (staff.hasPermission("is.staff")) {
                    online.add(staff.getDisplayName());
                }
            }
            if (online.size() > 1) {
                p.sendMessage("§cStaff connecte(e) : §a" + online);
            } else if (online.size() == 1) {
                p.sendMessage("§cStaff connecte(e) : §a" + online);
            } else {
                p.sendMessage("§cAucune personne du Staff n'est connecte.");
            }
        } else {

            return false;
        }
        return true;
    }

}
