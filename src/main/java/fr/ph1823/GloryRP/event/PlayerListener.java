package fr.ph1823.GloryRP.event;

import fr.ph1823.GloryRP.GloryRP1;
import fr.ph1823.GloryRP.utils.CustomScoreboardManager;
import fr.ph1823.GloryRP.utils.ItemCheck;

import net.milkbowl.vault.chat.Chat;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerListener implements Listener {
    public static HashMap<String, Boolean> pris = new HashMap<>();
    public static HashMap<Player, Integer> vole = new HashMap<>();
    public static HashMap<Player, Integer> perse = new HashMap<>();
    public static ArrayList<Player> res = new ArrayList<>();
    public static Chat chat = null;
    boolean isV = false;
    GloryRP1 plugin;


    public PlayerListener(GloryRP1 main) {
        this.plugin = main;

//&7[&cMylife&f-&cInformations&7]&9&c&l
    }

    @EventHandler
    public void onPlayerChatEvent(org.bukkit.event.player.AsyncPlayerChatEvent e) {

        Player p1 = e.getPlayer();
        String msg = e.getMessage();
        ArrayList<String> tmp = new ArrayList<>();
        e.setCancelled(true);

        if (msg.substring(0, 1).equals("!")) {
            e.setCancelled(false);
            e.setFormat("§e§l[§b§lHRP§e§l] "

                    + p1.getDisplayName() + ChatColor.RESET + " : "
                    + ChatColor.translateAlternateColorCodes('&', msg.replaceFirst("!", "")));
        } else if (msg.substring(0, 1).equals("*")) {
            e.setCancelled(true);
            p1.sendMessage("§e§l[§b§lHRP-Local§e§l] "

                    + p1.getDisplayName() + ChatColor.RESET + " : "
                    + ChatColor.translateAlternateColorCodes('&', msg.replaceFirst("\\*", "")));

            for (Entity e1 : p1.getNearbyEntities(20, 20, 20)) {
                if (e1 instanceof Player) {
                    Player p = (Player) e1;
                    String name = p1.getDisplayName();
                    if (!tmp.contains(name)) {
                        p.sendMessage("§e§l[§b§lHRP-Local§e§l] "

                                + name + ChatColor.RESET + " : "
                                + ChatColor.translateAlternateColorCodes('&', msg.replaceFirst("\\*", "")));
                        tmp.add(name);
                    }

                }
            }

        } else {
            e.setCancelled(true);
            p1.sendMessage("§e§l[§b§lRP§e§l] "

                    + p1.getDisplayName() + ChatColor.RESET + " : "
                    + ChatColor.translateAlternateColorCodes('&', msg));

            for (Entity e1 : p1.getNearbyEntities(20, 20, 20)) {
                if (e1 instanceof Player) {
                    Player p = (Player) e1;
                    String name = p.getName();
                    if (!tmp.contains(name)) {
                        String chat = p1.getDisplayName();
                        ItemStack i = plugin.hasIden.get(p1) != null ? plugin.hasIden.get(p1) : null;
                        if (i != null) {
                            net.minecraft.server.v1_7_R4.ItemStack stack = CraftItemStack.asNMSCopy(i);
                            NBTTagCompound tag = stack.getTag();
                            chat = tag.getString("name") + " " + tag.getString("surname");

                        }
                        p.sendMessage("§e§l[§b§lRP§e§l] "

                                + chat + ChatColor.RESET + " : " + ChatColor.translateAlternateColorCodes('&', msg)
                        );
                        tmp.add(name);
                    }

                }
            }

        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        /*
		TODO FAIRE LE TRUC CASSER ect..

		TODO

		 */

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Random r = new Random();
            int r1 = r.nextInt(5);
            if (e.getFinalDamage() > 3.0D) {
                switch (r1) {
                    case 0:

                        p.sendMessage("Tu t'es cassé la Jambe :/ Systéme SOON !");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 15/4, 2, true), true);

                        break;
                    case 1:
                        p.sendMessage("Tu t'es cassé le Bras :/ Systéme SOON !");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 15/4, 5, true), true);
                        break;
                    case 2:
                        p.sendMessage("Tu t'es cassé la Tête :/ Systéme SOON !");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 60/4, 5, true), true);
                        break;
                    case 3:
                        p.sendMessage("Tu t'es cassé une côte ! :/ Systéme SOON !");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60/4, 2, true), true);
                        break;
                    case 4:
                        if (p.getFoodLevel() < 10 || p.getHealth() < 15) {
                            p.sendMessage("Tu t'es cassé la colonne vertébralle ! :/ Systéme SOON !");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 60 * 2, 255/4, true), true);
                        } else {
                            p.sendMessage("Tu as eu de la chance, tu n'as riend e cassé !!");
                        }
                        break;
                    case 5:
                        p.sendMessage("Tu as eu de la chance, tu n'as riend e cassé !!");
                        break;
                }
            } else {
                p.sendMessage("Tu as eu de la chance, tu n'as riend e cassé.");
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {


        Player p = event.getPlayer();


        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

        CustomScoreboardManager sc = new CustomScoreboardManager(p);
        sc.sendLine();
        sc.set();


        //ItemStack c = new ItemStack(Material.getMaterial("GLORYOFRP_IDENTITY"), 1);
		/*net.minecraft.server.v1_7_R4.ItemStack stack = CraftItemStack.asNMSCopy(c);
		for(ItemStack i : p.getInventory().getContents()) {
			if(i != null && i.getType() != null) {
				if(i.getType() != Material.AIR) {
					if (i.getType() == c.getType()) {
						if (plugin.hasIden.get(p) == null) {
							plugin.hasIden.put(p, i);
						}
					}
				}
			}
		}*/
       // plugin.checkHas(p);
/*		if(plugin.hasIden.get(p) != null) {


				c = plugin.hasIden.get(p);

					 stack = CraftItemStack.asNMSCopy(c);
			NBTTagCompound tag = stack.getTag();
				if(tag == null) {
					tag = new NBTTagCompound();
					Random r = new Random();
					int age = r.nextInt(90);
					while(age < 18) {
						age = r.nextInt(90);
					}
					tag.setInt("age", age);
					tag.setString("name", plugin.name.get(r.nextInt(plugin.name.size() - 1)));
					tag.setString("surname", plugin.surname.get(r.nextInt(plugin.surname.size() - 1)));
					stack.save(tag);
					stack.setTag(tag);
				}

			} else {

				NBTTagCompound tag = stack.getTag();

				if(tag == null) {
					tag = new NBTTagCompound();
				}
				Random r = new Random();
				int age = r.nextInt(90);
				while(age < 18) {
					age = r.nextInt(90);
				}
				tag.setInt("age", age);
				tag.setString("name", plugin.name.get(r.nextInt(plugin.name.size() - 1)));
				tag.setString("surname", plugin.surname.get(r.nextInt(plugin.surname.size() - 1)));
				stack.save(tag);
				stack.setTag(tag);
			p.getInventory().addItem(CraftItemStack.asBukkitCopy(stack));
			}*/


        if (plugin.getConfig().getBoolean("isMaintenance")) {
            if (!p.hasPermission("mylife.maintenance")) {


                p.kickPlayer(plugin.getConfig().getString("maintenance"));
            }
        }


        //prefix = prefix.length() > 16 ? ChatColor.translateAlternateColorCodes('&',chat.getPlayerPrefix(p).substring(0, 2)) : prefix;


        p.setPlayerListName(p.getDisplayName());


        if (pris.get(event.getPlayer().getName()) == null) {
            pris.put(event.getPlayer().getName(), false);
        }

        plugin.getServer().getScheduler().runTaskLater(plugin,
                new Runnable() {
                    @Override
                    public void run() {
           p.sendMessage("§e§l[GloryOfRP-MAJ-Interne]");
                        p.sendMessage("§a§l M.A.J Effectué : (sur le plugin)");
                        p.sendMessage("§a§l Mise en place du plugin V3.1");
                        p.sendMessage("§a§l   Autre maj :");

                        p.sendMessage("§a§l Refonte launcher & mods");
                    }
                },
        5);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        String world = player.getWorld().getName().toLowerCase();
        if (item != null) {
            int id = item.getType().getId();
            byte data = item.getData().getData();
            String words = plugin.getConfig().getString("Ban Reason").replaceAll("%d", "" + id + ":" + data + "").replaceAll("%s", "" + world + "");
            //if(!player.hasPermission("banitem.bypass." + id + ":" + data) && !player.hasPermission("banitem.click." + id + ":*") && !player.hasPermission("banitem.click." + id + ":" + data) && !player.hasPermission("banitem.bypass." + id + ":*") && !player.isOp() && !player.hasPermission("banitem.*")) {
            ItemCheck itemmethod = new ItemCheck(plugin.all, id, data, world);
            ItemCheck itemmethod1 = new ItemCheck(plugin.click, id, data, world);
            if ((itemmethod.getnumber() == 1 || itemmethod1.getnumber() == 1) && (itemmethod1.worldcheck() == 1 || itemmethod.worldcheck() == 1)) {
                if (plugin.getConfig().getBoolean("Confiscate")) {
                    e.setCurrentItem(new ItemStack(Material.AIR, 1));
                    e.setCancelled(true);
                    player.sendMessage(plugin.banitem + ChatColor.RED + words + itemmethod.getReason());

                } else {
                    e.setCancelled(true);
                    player.sendMessage(plugin.banitem + ChatColor.RED + words +  itemmethod.getReason());

                }
                //	}
            }

        }
        if (e.getCurrentItem() != null) {
            if (e.getCurrentItem().getItemMeta() != null) {

                if (!e.getInventory().getTitle().equals("")) {
                    if (e.getInventory().getTitle().contains("radio")) {
                        e.setCancelled(true);
                   //     Ts ts = new Ts();
                     //   ts.move(e.getWhoClicked().getName(), e.getCurrentItem().getItemMeta().getDisplayName());
                        e.getWhoClicked().closeInventory();
                    }
                }

            }
        }


    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (pris.get(e.getEntity().getName())) {
            pris.put(e.getEntity().getName(), false);
        }


	/*String[] g = {"Citoyen"};
			PermissionsEx.getUser(e.getEntity()).setGroups(g);*/

    }

    @EventHandler(
            priority = EventPriority.HIGH
    )
    private void onPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();
        int id = item.getType().getId();
        byte data = item.getData().getData();
        String world = player.getWorld().getName().toLowerCase();
        String words = plugin.getConfig().getString("Ban Reason").replaceAll("%d", "" + id + ":" + data + "").replaceAll("%s", "" + world + "");
        //if(!player.hasPermission("banitem.bypass." + id + ":" + data) && !player.hasPermission("banitem.pickup." + id + ":*") && !player.hasPermission("banitem.pickup." + id + ":" + data) && !player.hasPermission("banitem.bypass." + id + ":*") && !player.isOp() && !player.hasPermission("banitem.*")) {
        ItemCheck itemmethod = new ItemCheck(plugin.all, id, data, world);
        ItemCheck itemmethod1 = new ItemCheck(plugin.pickup, id, data, world);
        if ((itemmethod.getnumber() == 1 || itemmethod1.getnumber() == 1) && (itemmethod1.worldcheck() == 1 || itemmethod.worldcheck() == 1)) {
            e.setCancelled(true);
            player.sendMessage(plugin.banitem + ChatColor.RED + words +  itemmethod.getReason());

        }
        //	}

    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        //


        if (e.getRightClicked() instanceof Player) {
            Player target = (Player) e.getRightClicked();
            String targetName = target.getName();
            Player policier = e.getPlayer();

            if (policier.getInventory().getItemInHand().getType() == Material
                    .getMaterial("GLORYOFRP_KEY")) {

                if (policier.getInventory().getItemInHand().getItemMeta().getDisplayName().contains("Démenotte - " + targetName)) {
                    if (pris.get(targetName)) {
                        policier.getInventory().remove(policier.getInventory().getItemInHand());
                        target.sendMessage(ChatColor.GREEN + "Vous êtes libéré.");
                        policier.sendMessage(ChatColor.GREEN + "Vous l'avez libéré");
                        pris.remove(targetName);
                        pris.put(targetName, false);
                    }
                }

            } else if (policier.getInventory().getItemInHand().getType() == Material
                    .getMaterial("GLORYOFRP_MENOTTES")) {

                if (!pris.get(targetName)) {


                    ItemStack key = new ItemStack(Material
                            .getMaterial("GLORYOFRP_KEY"), 1);
                    ItemMeta km = key.getItemMeta();
                    km.setDisplayName("Démenotte - " + targetName);
                    km.setLore(Arrays.asList("§e§lSert a démenotté", "§c§l" + targetName, "§e§let seulement luis."));
                    key.setItemMeta(km);
                    policier.getInventory().remove(policier.getInventory().getItemInHand());

                    policier.getInventory().addItem(key);
                    target.sendMessage(ChatColor.RED
                            + "Vous avez était menotté.");
                    policier.sendMessage(ChatColor.RED
                            + "Vous l'avez menottés.");
                    pris.remove(targetName);
                    pris.put(targetName, true);
                }
            } else {
                isV = true;
                Inventory i = target.getInventory();


                int pour = 10;
                if (vole.get(policier) != null) {
                    pour = vole.get(policier) + 10;
                    vole.remove(policier);
                    vole.put(policier, pour);
                } else {
                    vole.put(policier, pour);
                }

                policier.sendMessage("Vous êtes entrain de le fouillez : " + vole.get(policier) + " %");

                isV = false;
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

                    @Override
                    public void run() {

                        if (vole.get(policier) != null && !isV) {
                            if (vole.get(policier) != 100) {

                                vole.remove(policier);
                            }

                        }
                    }
                }, 20 * 2);
                if (vole.get(policier) == 50) {
                    target.sendMessage("§cAttention : On tente de vous voler vos affaires");
                }
                if (vole.get(policier) == 100) {
                    target.sendMessage("§cAttention : On vous vole vos affaires");
                    policier.sendMessage("§a Vous volez les affaire ");
                    policier.openInventory(i);
                }
            }
        }

    }

    @EventHandler
    public void onJumonWheat(EntityChangeBlockEvent e) {
        Block b = e.getBlock();
        Material bt = b.getType();
        if (bt == Material.WHEAT || bt == Material.POTATO
                || bt == Material.CARROT) {
            e.setCancelled(true);
        }
    }

    @EventHandler

    public void onMove(org.bukkit.event.player.PlayerMoveEvent e) {
        if (pris.get(e.getPlayer().getName()) != null && pris.get(e.getPlayer().getName())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Vous êtes menotté.");
        }

    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent e) {
        ItemStack i = e.getRecipe().getResult();

        if (i.getType() == Material.getMaterial("GLORYOFRP_TALKIE") && ((String)i.getItemMeta().getLore().toArray()[1]) == null) {
            ItemMeta im = i.getItemMeta();
            im.setLore(Arrays.asList("100"));
            i.setItemMeta(im);
            e.getInventory().setResult(i);
        } else {
            ItemMeta im = i.getItemMeta();

            if (((String)i.getItemMeta().getLore().toArray()[1]).contains("200")) {
                im.setLore(Arrays.asList("§e§lRadio de 100Hz à 300Hz"));
            } else if (((String)i.getItemMeta().getLore().toArray()[1]).contains("300")) {
                im.setLore(Arrays.asList("§e§lRadio de 100Hz à 400Hz"));

            } else if (((String)i.getItemMeta().getLore().toArray()[1]).contains("400")) {
                im.setLore(Arrays.asList("§e§lRadio de 100Hz à 500Hz"));

            } else {
                im.setLore(Arrays.asList("§e§lRadio de 100Hz à 200Hz"));
            }
            i.setItemMeta(im);
            e.getInventory().setResult(i);
        }

    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack i = e.getRecipe().getResult();

        if (i.getType() == Material.getMaterial("GLORYOFRP_TALKIE") && ((String)i.getItemMeta().getLore().toArray()[1]) == null) {
            ItemMeta im = i.getItemMeta();
            im.setLore(Arrays.asList("§e§l Radio de 100Hz"));
            i.setItemMeta(im);
            e.getInventory().setResult(i);
            e.setCurrentItem(i);
        } else {
            ItemMeta im = i.getItemMeta();

            if (((String)i.getItemMeta().getLore().toArray()[1]).contains("200")) {
                im.setLore(Arrays.asList("§e§lRadio de 10Hz à 300Hz"));
            } else if (((String)i.getItemMeta().getLore().toArray()[1]).contains("300")) {
                im.setLore(Arrays.asList("§e§lRadio de 100Hz à 400Hz"));

            } else if (((String)i.getItemMeta().getLore().toArray()[1]).contains("400")) {
                im.setLore(Arrays.asList("§e§lRadio de 100Hz à 500Hz"));

            } else {
                im.setLore(Arrays.asList("§e§lRadio de 100Hz à 200Hz"));
            }
            i.setItemMeta(im);
            e.getInventory().setResult(i);
            e.setCurrentItem(i);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onOpenChest(org.bukkit.event.player.PlayerInteractEvent e) {


        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();
        int id = item.getType().getId();
        byte data = item.getData().getData();
        String world = player.getWorld().getName();
        String words = plugin.getConfig().getString("Ban Reason").replaceAll("%d", "" + id + ":" + data + "").replaceAll("%s", "" + world + "");
        //if(!player.hasPermission("banitem.bypass." + id + ":" + data) && !player.hasPermission("banitem.int." + id + ":*") && !player.hasPermission("banitem.int." + id + ":" + data) && !player.hasPermission("banitem.bypass." + id + ":*") && !player.isOp() && !player.hasPermission("banitem.*")) {
        ItemCheck itemmethod = new ItemCheck(plugin.all, id, data, world);
        ItemCheck itemmethod1 = new ItemCheck(plugin.interact, id, data, world);
        if (!(e instanceof Player) && (itemmethod.getnumber() == 1 || itemmethod1.getnumber() == 1) && (itemmethod1.worldcheck() == 1 || itemmethod.worldcheck() == 1)) {
            if (plugin.getConfig().getBoolean("Confiscate")) {
                e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 1));
                e.setCancelled(true);
                player.sendMessage(plugin.banitem + ChatColor.RED + words +  itemmethod.getReason());

            } else {
                int itemslot = e.getPlayer().getInventory().getHeldItemSlot();
                if (itemslot == 8) {
                    e.getPlayer().getInventory().setHeldItemSlot(itemslot - 1);
                } else {
                    e.getPlayer().getInventory().setHeldItemSlot(itemslot + 1);
                }

                e.setCancelled(true);
                player.sendMessage(plugin.banitem + ChatColor.RED + words +  itemmethod.getReason());

            }
        }


      /*  if (e.getPlayer().getItemInHand().getType() == Material.getMaterial("GLORYOFRP_TALKIE")) {
            Ts ts = new Ts();
            ItemStack[] i2 = ts.allChannelHz(e.getPlayer().getItemInHand());
            if (i2 != null)
                plugin.openMenu(e.getPlayer().getName(), i2);
        }*/
        if (pris.get(e.getPlayer().getName()) != null && pris.get(e.getPlayer().getName())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Vous êtes menotté.");
        }
        Block b = e.getClickedBlock();
        if (b != null) {


            ItemStack pers = new ItemStack(Material.getMaterial("BIBLIOCRAFT_ITEMBIBLIODRILL"));
            if (e.getPlayer().getItemInHand().isSimilar(pers)) {

                isV = true;


                if (e.getClickedBlock().getType().name().contains("WOOD") || e.getClickedBlock().getType().name().contains("IRON") || e.getClickedBlock().getType().name().contains("DOOR")) {

                    int pour = 5;
                    if (perse.get(e.getPlayer()) != null) {
                        pour = perse.get(e.getPlayer()) + 5;
                        perse.remove(e.getPlayer());
                        perse.put(e.getPlayer(), pour);
                    } else {
                        perse.put(e.getPlayer(), pour);
                    }

                    e.getPlayer().sendMessage("Perssage en cours .. ( " + perse.get(e.getPlayer()) + " %)");

                    isV = false;
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

                        @Override
                        public void run() {

                            if (perse.get(e.getPlayer()) != null && isV == false && perse.get(e.getPlayer()) != 100) {

                                perse.remove(e.getPlayer());

                            }
                        }
                    }, 20 * 3);

                    if (perse.get(e.getPlayer()) == 100) {
                        perse.remove(e.getPlayer());
                        int i6 = plugin.block.size() > 1 ? plugin.block.size() - 1 : 0;
                        final Block b11 = e.getClickedBlock();
                        final Location loc = e.getClickedBlock().getLocation();
                       // plugin.block.put(b11,0);



                        BlockBreakEvent bbe = new BlockBreakEvent(e.getClickedBlock(),e.getPlayer());
                    plugin.blockbrak = true;
                        Bukkit.getServer().getPluginManager().callEvent(bbe);
                        e.getClickedBlock().breakNaturally(new ItemStack(Material.AIR));
                      //  Bukkit.broadcastMessage("Block register :" +  plugin.block.get(i6));

                    }
                }

            }


            final Player p = e.getPlayer();
            ItemStack i = p.getInventory().getItemInHand();



            org.bukkit.event.block.Action a = e.getAction();

            if (b.getType().name().contains("DOOR") || b.getType().name().contains("CHEST")) {
                Block chest = null;
                if (b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ()).getType().name().contains("CHEST")) {
                    chest = b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ());
                } else if (b.getWorld().getBlockAt(b.getX() - 1, b.getY(), b.getZ()).getType().name().contains("CHEST")
                        ) {
                    chest = b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ());
                } else if (b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1).getType().name().contains("CHEST")) {
                    chest = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
                } else if (b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1).getType().name().contains("CHEST")
                        ) {
                    chest = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1);

                    }
                List<String> list = plugin.getConfig().getStringList("chest");

                if (list.contains(plugin.loc2str(b.getLocation())) || list.contains(chest)) {
                    if (i.getType() != Material.getMaterial("GLORYOFRP_KEY")) {
                        e.setCancelled(true);
                    }
                }
                    Block door = b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ()).getType().name().contains("DOOR")
                            ? b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ())
                            : b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
                   list = plugin.getConfig().getStringList("chest");

                    if (list.contains(plugin.loc2str(b.getLocation())) || list.contains(door)) {
                        if (i.getType() != Material.getMaterial("GLORYOFRP_KEY")) {
                            e.setCancelled(true);
                        }
                    }



                if (i.getType() == Material.getMaterial("GLORYOFRP_KEY")) {
                    if (a.equals(Action.LEFT_CLICK_BLOCK)) {
                        if (p.isSneaking()) {
                            e.setCancelled(true);


                            if (plugin.getConfig().getStringList("chest") == null) {

                                plugin.getConfig().set("chest", Arrays.asList(""));
                            }
                            if (b.getType().name().contains("DOOR")) {
                                 door = b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ()).getType().name().contains("DOOR")
                                        ? b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ())
                                        : b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
                              list = plugin.getConfig().getStringList("chest");

                                if (!list.contains(plugin.loc2str(b.getLocation()))) {
                                    list.add(plugin.loc2str(b.getLocation()));
                                    list.add(plugin.loc2str(door.getLocation()));
                                    ItemMeta m = i.getItemMeta();
                                    m.setLore(Arrays.asList("Clès de la porte:",
                                            plugin.loc2str(b.getLocation()) + " & " +
                                                    plugin.loc2str(door.getLocation())));

                                    i.setItemMeta(m);
                                    plugin.getConfig().set("chest", list);
                                    p.sendMessage("Clès mise");

                                } else {
                                    p.sendMessage("§4§l[GloryOfRP-Error]§c§lCette porte a déjà une clès asignez.");
                                }
                            } else {

                                if (b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ()).getType().name().contains("CHEST")) {
                                    chest = b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ());
                                } else if (b.getWorld().getBlockAt(b.getX() - 1, b.getY(), b.getZ()).getType().name().contains("CHEST")
                                        ) {
                                    chest = b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ());
                                } else if (b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1).getType().name().contains("CHEST")) {
                                    chest = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
                                } else if (b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1).getType().name().contains("CHEST")
                                        ) {
                                    chest = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1);

                                }
                           list = plugin.getConfig().getStringList("chest");
                                if (!list.contains(plugin.loc2str(b.getLocation()))) {
                                    list.add(plugin.loc2str(b.getLocation()));
                                    if (chest != null) {
                                        list.add(plugin.loc2str(chest.getLocation()));
                                    }
                                    ItemMeta m = i.getItemMeta();
                                    m.setLore(Arrays.asList("Clès du(des) coffres(s):",
                                            plugin.loc2str(b.getLocation()) + (chest != null ? " & " +
                                                    plugin.loc2str(chest.getLocation()) : "")));

                                    i.setItemMeta(m);
                                    plugin.getConfig().set("chest", list);
                                    p.sendMessage("Clès mise");
                                } else {
                                    p.sendMessage("§4§l[GloryOfRP-Errpr]§c§lCette porte a déjà une clès asignez.");
                                }

                            }

                        }

                    }
                    if (a.equals(Action.RIGHT_CLICK_BLOCK)) {

                        if (plugin.getConfig().getStringList("chest").contains(plugin.loc2str(b.getLocation()))) {


                            if (((String) i.getItemMeta().getLore().toArray()[1]) != null) {
                                if (b.getType().name().contains("DOOR")) {
                                     door = b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ()).getType().name().contains("DOOR")
                                            ? b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ())
                                            : b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
                                    if (!((String) i.getItemMeta().getLore().toArray()[1]).contains(plugin.loc2str(b.getLocation()))) {
                                        e.setCancelled(true);
                                        p.sendMessage("§4§c[GloryOfRP-Error]&!&c&!&lVotre clès n'est pas asignez a ce coffre.");
                                    } /*else if(!((String)i.getItemMeta().getLore().toArray()[1]).contains(plugin.loc2str(door.getLocation()))) {
					e.setCancelled(true);
					p.sendMessage("§4§c[GloryOfRP-Error]&!&c&!&lVotre clès n'est pas asignez a ce coffre.");
				}*/

                                } else {

                                    if (!((String) i.getItemMeta().getLore().toArray()[1]).contains(plugin.loc2str(b.getLocation()))) {
                                        e.setCancelled(true);
                                        p.sendMessage("§4§c[GloryOfRP-Error]&!&c&!&lVotre clès n'est pas asignez a ce coffre.");
                                    }
								/*	else if(chest != null) {
										if(!((String)i.getItemMeta().getLore().toArray()[1]).contains(plugin.loc2str(chest.getLocation()))) {
											e.setCancelled(true);
											p.sendMessage("§4§c[GloryOfRP-Error]&!&c&!&lVotre clès n'est pas asignez a ce coffre.");
										}
									}*/
                                }
                            } else {
                                e.setCancelled(true);
                                p.sendMessage("§4§l[GloryOfRP-Error]§c§llVotre clès n'est asignez a aucun porte n'y coffre.");
                            }
                        }

                    }

                }
            }
        }
    }

    //@EventHandler
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent e) {
        Player p = e.getEntity();
        Locale locale = Locale.FRANCE;
        Date da = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy hh:mm:ss");

        File f = new File("./plugins/GloryRP1/users/" + p.getName() + ".yml");
        FileConfiguration fs = YamlConfiguration.loadConfiguration(f);
        fs.getStringList("death").add(
                "Date : " + dateFormat.format(da) + " Cordonnée : x : "
                        + p.getLocation().getX() + " y: "
                        + p.getLocation().getY() + " z: "
                        + p.getLocation().getZ());

    }
}
