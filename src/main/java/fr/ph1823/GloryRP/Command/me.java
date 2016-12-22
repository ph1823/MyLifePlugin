package fr.ph1823.GloryRP.Command;

import fr.ph1823.GloryRP.GloryRP1;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created by ph1823 - Minecraft on 26/03/2016.
 */
public class me implements CommandExecutor

{

    GloryRP1 plugin;

    public me(GloryRP1 instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0
                     ; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                p.sendMessage(p.getName() + " " + sb.toString().trim());
                for (Entity e : p.getNearbyEntities(20, 20, 20)) {
                    if (e instanceof Player) {

                        Player p1 = (Player) e;

                        p1.sendMessage(p.getName() + " " + sb.toString().trim());

                    }
                }
            }
        } else {

            return false;
        }
        return true;
    }

}
