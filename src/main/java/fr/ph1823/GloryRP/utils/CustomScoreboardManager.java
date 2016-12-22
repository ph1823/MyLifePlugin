package fr.ph1823.GloryRP.utils;

/**
 * Created by ph1823 - Minecraft on 11/06/2016.
 */

import fr.ph1823.GloryRP.GloryRP1;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Random;

public class CustomScoreboardManager implements ScoreboardManager {

    public Player player;
    public Scoreboard scoreboard;
    public Objective objective;

    private String name;

    public CustomScoreboardManager(Player p) {

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.player = p;

        if (GloryRP1.instance.sb.containsKey(p)) return;

        GloryRP1.instance.sb.put(p, this);

        this.name = "sb." + new Random().nextInt(999999);

        objective = scoreboard.registerNewObjective(name, "dummy");
        objective = scoreboard.getObjective(name);
        objective.setDisplayName(ChatColor.AQUA + "GloryOfRP" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Alpha 0.7#5");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

    public void refresh() {
        String arg = "";
        Economy economy;
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            String argent = economy.getBalance(player) + "";
            arg = argent.length() > 16 ? argent.substring(0, 14) : argent;

        }
        Permission perm = null;
        RegisteredServiceProvider<Permission> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (chatProvider != null) {

            perm = chatProvider.getProvider();
        }
        long time = player.getPlayerTimeOffset() % 20 % 60;
        String time1 = time + "";
        for (String ligne : scoreboard.getEntries()) {
            //Bukkit.broadcastMessage(ligne);

            if (ligne.contains("$")) {
                scoreboard.resetScores(ligne);

                // String lastligne = ligne.split(":")[0];
                //   String newligne = lastligne + ":" + ScoreboardRunnable.time;

                objective.getScore(arg + "$").setScore(-3);
            }

            if (ligne.contains("Joueur")) {
                scoreboard.resetScores(ligne);

                // String lastligne = ligne.split(":")[0];
                //   String newligne = lastligne + ":" + ScoreboardRunnable.time;
                GloryRP1.instance.SC.clear();
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    GloryRP1.instance.SC.add(p);
                }
                objective.getScore(ChatColor.AQUA + "" + GloryRP1.instance.SC.size() + " Joueur").setScore(-9);
            }



            if (ligne.contains("min")) {
                scoreboard.resetScores(ligne);

                // String lastligne = ligne.split(":")[0];
                //   String newligne = lastligne + ":" + ScoreboardRunnable.time;

                objective.getScore(ChatColor.RED + time1 + " min(s)").setScore(-11);
            }

        }
    }

    public void sendLine() {
        String arg = "";
        Economy economy;
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            String argent = economy.getBalance(player) + "";
            arg = argent.length() > 16 ? argent.substring(0, 14) : argent;

        }
        Permission perm = null;
        RegisteredServiceProvider<Permission> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (chatProvider != null) {

            perm = chatProvider.getProvider();
        }
        long time = player.getPlayerTimeOffset() % 20 % 60;
    //    long time2 =
        String time1 = time + "";
        objective.getScore("§e").setScore(-1);
        objective.getScore(ChatColor.BOLD + "" + ChatColor.GOLD + "Argent :").setScore(-2);
        objective.getScore(arg + "$").setScore(-3);
        objective.getScore(ChatColor.BOLD + "" + ChatColor.GREEN + "Métier :").setScore(-4);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getPrefix().length() > 16 ?PermissionsEx.getUser(player).getPrefix().substring(0,16) : PermissionsEx.getUser(player).getPrefix())).setScore(-5);

        if( PermissionsEx.getUser(player).getPrefix().length() > 32) {
            objective.getScore(ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getPrefix().length() > 32 ? PermissionsEx.getUser(player).getPrefix().substring(16, 32) : PermissionsEx.getUser(player).getPrefix().substring(16, PermissionsEx.getUser(player).getPrefix().length()))).setScore(-6);
            if (PermissionsEx.getUser(player).getPrefix().length() > 48) {
               // objective.getScore(ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getPrefix().length() > 48 ? PermissionsEx.getUser(player).getPrefix().substring(32, 48) : PermissionsEx.getUser(player).getPrefix()).substring(32, PermissionsEx.getUser(player).getPrefix().length())).setScore(-7);
            }
        }
            objective.getScore(ChatColor.AQUA + "Connecté :").setScore(-8);
        GloryRP1.instance.SC.clear();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            GloryRP1.instance.SC.add(p);
        }
        objective.getScore(ChatColor.AQUA + "" + GloryRP1.instance.SC.size() + " Joueur").setScore(-9);
        objective.getScore(ChatColor.YELLOW + "Temps de jeu :").setScore(-10);
        objective.getScore(ChatColor.RED + time1 + " min(s)").setScore(-11);



    }

    @Override
    public Scoreboard getMainScoreboard() {
        return scoreboard;
    }

    @Override
    public Scoreboard getNewScoreboard() {
        return null;
    }

    public void set() {
        player.setScoreboard(getMainScoreboard());
    }

}