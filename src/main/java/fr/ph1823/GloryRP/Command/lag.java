package fr.ph1823.GloryRP.Command;

import fr.ph1823.GloryRP.GloryRP1;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class lag implements CommandExecutor {

    GloryRP1 plugin;

    public lag(GloryRP1 instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        MinecraftServer s = ((org.bukkit.craftbukkit.v1_7_R4.CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();

        if (sender instanceof Player) {
            Player se = (Player) sender;
            CraftPlayer p = (CraftPlayer) se;
            int ping = p.getHandle().ping;
            int pingsrv = s.ay().c().b();
            double[] tps = s.recentTps;
            ChatColor c = tps[0] >= 20 ? ChatColor.GREEN : ChatColor.RED;
            ChatColor c1 = tps[0] >= 20 ? ChatColor.GREEN : ChatColor.RED;
            ChatColor c2 = tps[0] >= 20 ? ChatColor.GREEN : ChatColor.RED;
            p.sendMessage("Votre ping : " + ping + "\n Ping du serveur : " + pingsrv + "\nTPS du serveur:" + c + tps[0] + " (1m)\n" + ChatColor.RESET + ChatColor.BOLD + c1 + tps[1] + " (5m) \n " + ChatColor.RESET + ChatColor.BOLD + c2 + tps[2] + " (15m)");
            p.sendMessage("Attention : ceci est un système en bêta, les chiffres peuvent être très exta ou non.");
            return true;
        }
        return true;
    }


}

