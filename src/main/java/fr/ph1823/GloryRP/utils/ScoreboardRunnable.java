package fr.ph1823.GloryRP.utils;

/**
 * Created by ph1823 - Minecraft on 11/06/2016.
 */

import fr.ph1823.GloryRP.GloryRP1;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map.Entry;

public class ScoreboardRunnable extends BukkitRunnable {

    static int time = 0;

    @Override
    public void run() {
        for (Entry<Player, CustomScoreboardManager> scoreboard : GloryRP1.instance.sb.entrySet()) {
            CustomScoreboardManager board = scoreboard.getValue();
            board.refresh();
            time++;
        }

    }

}
