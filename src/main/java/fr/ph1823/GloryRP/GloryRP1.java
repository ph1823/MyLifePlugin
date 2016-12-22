package fr.ph1823.GloryRP;


import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import fr.ph1823.GloryRP.Command.*;
import fr.ph1823.GloryRP.event.BlockListener;
import fr.ph1823.GloryRP.event.PlayerListener;
import fr.ph1823.GloryRP.utils.CustomScoreboardManager;
import fr.ph1823.GloryRP.utils.ScoreboardRunnable;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;


public class GloryRP1 extends JavaPlugin {

   /* public static final TS3Config config = new TS3Config();
    public static final TS3Query query = new TS3Query(config);
    public static final TS3Api api = query.getApi();*/
    public static GloryRP1 instance;
    public String error = "Inconnue";
    public int sec = 0;
    public int min = 0;
    public int tick;
    public HashMap<Player, Scoreboard> board = new HashMap<>();
    public Scoreboard sc;
    public HashMap<Player, Double> arg = new HashMap<>();
    public HashMap<Player, Integer> online = new HashMap<>();
    public HashMap<Player, Long> hour = new HashMap<>();
    public HashMap<Player, String> gr = new HashMap<>();
    public ArrayList<Player> SC = new ArrayList<>();
    public HashMap<Player, ItemStack> hasCard = new HashMap<>();
    public HashMap<Player, ItemStack> hasIden = new HashMap<>();
    public HashMap<Player, Score> con = new HashMap<>();
    public HashMap<Player, Score> grade = new HashMap<>();
    public HashMap<Player, Score> mo = new HashMap<>();
    public HashMap<Player, Score> h = new HashMap<>();
    public ArrayList<String> name = new ArrayList<>(getConfig().getStringList("name"));
    public ArrayList<String> surname = new ArrayList<>(getConfig().getStringList("surname"));
    public ItemStack card;
    public Hashtable<Location, Material> glass = new Hashtable<>();
    public Hashtable<Material, Byte> glassd = new Hashtable<>();
    public HashMap<Player, CustomScoreboardManager> sb = new HashMap<>();
final    public HashMap<Block,Integer> block = new HashMap<>();
    //final public HashMap<Player> loc = new ArrayList<>();
    public ArrayList<String> all = new ArrayList<>();
    public ArrayList<String> place = new ArrayList<>();
    public ArrayList<String> pickup = new ArrayList<>();
    public ArrayList<Integer> filter = new ArrayList<>();
    public ArrayList<String> interact = new ArrayList<>();
    public PluginDescriptionFile pdfFile = this.getDescription();
    public ArrayList<String> click = new ArrayList<>();
    public ArrayList<String> br = new ArrayList<>();
    public ArrayList<String> worlds = new ArrayList<>();
    public String banitem;

    public boolean blockbrak = false;

    @Override
    public void onLoad() {


    }

    @Override
    public void onEnable() {
        this.banitem = ChatColor.RED + "[" + ChatColor.GRAY + "BanItem" + ChatColor.RED + "] " + ChatColor.AQUA;


        this.all = (ArrayList) this.getConfig().getStringList("Blacklist");
        this.place = (ArrayList) this.getConfig().getStringList("Blacklist Placement");
        this.pickup = (ArrayList) this.getConfig().getStringList("Blacklist Pickup");
        this.interact = (ArrayList) this.getConfig().getStringList("Blacklist Interaction");
        this.filter = (ArrayList) this.getConfig().getIntegerList("Filter");
        this.click = (ArrayList) this.getConfig().getStringList("Blacklist Click");
        this.worlds = (ArrayList) this.getConfig().getStringList("Blacklist World");
        this.br = (ArrayList) this.getConfig().getStringList("Blacklist Break");
    /*    config.setHost("ts.glorycraft.fr");
//config.setQueryPort(5000);

        config.setDebugLevel(Level.ALL);
        query.connect();
        api.login("TSBot", /*"1LOwzahy""DRxz54pK");
     //   api.selectVirtualServerById(1);
       // api.setNickname("GloryBot");*/

        instance = this;
        initConfig();
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);

        getCommand("lag").setExecutor(new lag(this));
        getCommand("clearchat").setExecutor(new clearchat(this));
        getCommand("police").setExecutor(new police(this));
        getCommand("staffco").setExecutor(new staffco(this));
        getCommand("adminbc").setExecutor(new adminbc(this));
        getCommand("me").setExecutor(new me(this));
        ItemStack i = new ItemStack(Material.getMaterial("GLORYOFRP_TALKIE"));

        final ShapedRecipe talkie = new ShapedRecipe(i);
        talkie.shape(
                "RT ",
                "  ",
                "  ");
        talkie.setIngredient('R', Material.REDSTONE);
        talkie.setIngredient('T', Material.getMaterial("GLORYOFRP_TALKIE"));
        Bukkit.addRecipe(talkie);
        Bukkit.broadcastMessage("MESSAGE DE ITEM ADD");
        final ShapedRecipe talkiem = new ShapedRecipe(i);
        talkiem.shape(
                "   ",
                "RT ",
                "   ");
        talkiem.setIngredient('R', Material.REDSTONE);
        talkiem.setIngredient('T', Material.getMaterial("GLORYOFRP_TALKIE"));
        Bukkit.addRecipe(talkiem);
        final ShapedRecipe talkieb = new ShapedRecipe(i);
        talkieb.shape(
                "   ",
                "   ",
                "RT ");
        talkieb.setIngredient('R', Material.REDSTONE);
        talkieb.setIngredient('T', Material.getMaterial("GLORYOFRP_TALKIE"));
        Bukkit.addRecipe(talkieb);

                  /*  final ShapedRecipe talkiehd = new ShapedRecipe(i);
                    talkiehd.shape(
                            " RT",
                            "",
                            "");
                    talkiehd.setIngredient('R', Material.REDSTONE);
                    talkiehd.setIngredient('T', Material.getMaterial("GLORYOFRP_TALKIE"));
                    Bukkit.addRecipe(talkiehd);

                    final ShapedRecipe talkiemd = new ShapedRecipe(i);
                    talkiehd.shape(
                            "",
                            "RT ",
                            "");
                    talkiemd.setIngredient('R', Material.REDSTONE);
                    talkiemd.setIngredient('T', Material.getMaterial("GLORYOFRP_TALKIE"));
                    Bukkit.addRecipe(talkiemd);
                    final ShapedRecipe talkiebd = new ShapedRecipe(i);
                    talkiebd.shape(
                            "",
                            "",
                            " RT");
                    talkiebd.setIngredient('R', Material.REDSTONE);
                    talkiebd.setIngredient('T', Material.getMaterial("GLORYOFRP_TALKIE"));
                    Bukkit.addRecipe(talkiebd);*/


        final World w = Bukkit.getWorld("world");

        w.setGameRuleValue("doDayLightCycle", "false");
        w.setFullTime(w.getFullTime() - 24000L);

        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

            @Override
            public void run() {


             //   for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                  //  checkHas(p);

                  /*  if (sec == 30) {

                        if (hasCard.get(p) == null) {
                            hasCard.put(p, card);

                            if (sec == 31) {
                                p.getInventory().addItem(card);
                                p.sendMessage("§cVous n'avez plus de carte de crédit, nous vous en donnons donc une nouvelle.");
                            }

                        }


                        card = new ItemStack(Material.getMaterial("GLORYOFRP_IDENTITY"));
                        if (hasIden.get(p) == null) {

                            net.minecraft.server.v1_7_R4.ItemStack stack = CraftItemStack.asNMSCopy(card);

                            NBTTagCompound tag = stack.getTag();

                            if (tag == null) {
                                tag = new NBTTagCompound();
                            }
                            Random r = new Random();
                            int age = r.nextInt(90);
                            while (age < 18) {
                                age = r.nextInt(90);
                            }
                            tag.setInt("age", age);
                            tag.setString("name", name.get(r.nextInt(name.size() - 1)));
                            tag.setString("surname", surname.get(r.nextInt(surname.size() - 1)));
                            stack.save(tag);
                            stack.setTag(tag);
                            ItemStack put = CraftItemStack.asBukkitCopy(stack);
                            hasIden.put(p, put);

                            p.getInventory().addItem(put);


                        }
                    }
                }


                //sec++;
                tick++;
                if (tick == 20) {
                    sec++;
                    tick = 0;
                }
                if (sec == 60) {
                    sec = 0;
                    min++;

                }
                if (min == 0) {
                    if (!glass.isEmpty()) {
                        Bukkit.broadcastMessage("§4§l[§c§lVitrié§4§l]§a§lToutes les vitres places sont remise !");
                        // Iterator it = glass.entrySet().iterator();

                        for (Map.Entry<Location, Material> entry : glass.entrySet()) {
                            Location loc = entry.getKey();
                            Material b = entry.getValue();
                            Bukkit.broadcastMessage("" + b);
                            loc.getBlock().setType(b);
                            loc.getBlock().setData(glassd.get(b), true);

                            glass.remove(loc, b);


                        }
                    }

                    min = 0;
                }

              /* if(min == 10) {

                    min = 0;
                    for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                        Permission perm = null;
                        RegisteredServiceProvider<Permission> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
                        if (chatProvider != null) {
                            perm = chatProvider.getProvider();


                            String groupe = perm.getPrimaryGroup(p);
                            File f = new File("/home/mylife/WiiPay/config.yml");
                            FileConfiguration fc = YamlConfiguration.loadConfiguration(f);

                            double sal = fc.get("salary.group." + groupe) != null ? fc.getDouble("salary.group." + groupe) : 0;

                            p.sendMessage("§4§l----------------------");
                            p.sendMessage("§eVotre salaire viens d'arrivé !");
                            p.sendMessage("§eMétier : " + groupe);
                            p.sendMessage("§eIl est de : " + sal);
                            p.sendMessage("§4§l----------------------");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "donne " + p.getName() + " " + sal);
                        }
                    }
                }*/



                TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                TimeZone tz = TimeZone.getTimeZone("Europe/Paris");
                long time = System.currentTimeMillis();
                time += tz.getOffset(time);

                w.setTime((time % 86400000L) * 24000L / 86400000L + 18000L);

                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    File f = new File("./plugins/GloryRP1/users/" + p.getName() + ".yml");
                    if (!f.exists()) {

                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            error = "Impossible de crée le fichier merci de contacté un Développeur";
                            p.sendMessage(ChatColor.GOLD + "********************");
                            p.sendMessage(ChatColor.AQUA + "[MyLife] Version :  " + getDescription().getVersion());
                            p.sendMessage(ChatColor.DARK_RED + "[Bug]" + ChatColor.AQUA + error);
                            p.sendMessage(ChatColor.GOLD + "********************");
                        }
                    }
                    FileConfiguration fg = YamlConfiguration.loadConfiguration(f);
                    double m = fg.getDouble("money");

                    try {
                        com.earth2me.essentials.api.Economy.setMoney(p.getName(), BigDecimal.valueOf(m));
                    } catch (NoLoanPermittedException e) {
                        error = "Erreur avec NoLoanPermittedException";
                        p.sendMessage(ChatColor.GOLD + "********************");
                        p.sendMessage(ChatColor.AQUA + "[MyLife] Version :  " + getDescription().getVersion());
                        p.sendMessage(ChatColor.DARK_RED + "[Bug]" + ChatColor.AQUA + error);
                        p.sendMessage(ChatColor.GOLD + "********************");
                    } catch (UserDoesNotExistException e) {
                        // TODO Auto-generated catch block
                        error = "L'utiisateur n'existe pas.";
                        if (p.isOp() || p.hasPermission("essentials.tp")) {
                            p.sendMessage(ChatColor.GOLD + "********************");
                            p.sendMessage(ChatColor.AQUA + "[MyLife] Version :  " + getDescription().getVersion());
                            p.sendMessage(ChatColor.DARK_RED + "[Bug]" + ChatColor.AQUA + error);
                            p.sendMessage(ChatColor.GOLD + "********************");
                        }
                    }
                }
              /*  for(Entity e : w.getEntities()) {



                    if(!e.getType().name().contains("FLENIXCITIES")) {

                        if(!e.getType().name().contains("FLAN")) {

                            if(e.getType() != EntityType.PLAYER) {
                                if(e instanceof Animals) {
                                    e.remove();
                                }
                            }
                        }

                    }
                }*/


            }
        }, 20, 0);
        new ScoreboardRunnable().runTaskTimer(this, 0L, 20L);

    }

    private void initConfig() {
        FileConfiguration config = this.getConfig();

        // Les configurations generales
        if (config.get("isMaintenance") == null)
            config.addDefault("isMaintenance", false);
        config.addDefault("general.maintenance", "§Le serveur est actuellement en maintenance, merci de revenir plus tard");


        config.options().copyDefaults(true);
        this.saveConfig();
    }


    @Override
    public void onDisable() {

    }

    public Location str2loc(String str) {
        String str2loc[] = str.split(":");
        Location loc = new Location(getServer().getWorld(str2loc[0]), 0, 0, 0);
        loc.setX(Double.parseDouble(str2loc[1]));
        loc.setY(Double.parseDouble(str2loc[2]));
        loc.setZ(Double.parseDouble(str2loc[3]));
        return loc;
    }

    public String loc2str(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }

    public void openMenu(String p, ItemStack[] i) {

        Player p1 = Bukkit.getPlayer(p);
        Inventory i1 = Bukkit.createInventory(null, i.length > 9 ? (i.length > 18 ? (i.length > 27 ? (i.length > 36 ? 45 : 36) : 27) : 18) : 9, "Fréquense radio");
        i1.setContents(i);
        p1.openInventory(i1);

    }

    public void checkHas(Player p) {
        HashMap<String, Boolean> l = new HashMap<>();
        Inventory i = p.getInventory();
        for (ItemStack i1 : i.getContents()) {
            if (i1 != null) {
                if (i1.getType() != Material.AIR) {
                    if (hasIden.get(p) == null) {
                        if (i1.getType().name().contains("IDENTITY")) {
                            hasIden.put(p, i1);
                            l.put("IDENTITY", true);
                        }
                    } else {

                        if (i1.getType().name().contains("IDENTITY")) {
                            l.put("IDENTITY", true);
                        }

                    }
                    if (hasCard.get(p) == null) {
                        if (i1.getType().name().contains("DEBITCARD")) {
                            hasCard.put(p, i1);
                            l.put("DEBITCARD", true);
                        }
                    } else {

                        if (i1.getType().name().contains("DEBITCARD")) {
                            l.put("DEBITCARD", true);
                        }

                    }
                }
            }
        }
        if (l.get("DEBITCARD") == null) {
            hasCard.remove(p);

        } else if (l.get("IDENTITY") == null) {
            hasIden.remove(p);
        }
    }


}
