package fr.ph1823.GloryRP.event;


import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.ph1823.GloryRP.GloryRP1;
import fr.ph1823.GloryRP.utils.ItemCheck;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.Random;

public class BlockListener implements Listener {
    public boolean tree = false;
    GloryRP1 plugin;

    public BlockListener(GloryRP1 main) {
        this.plugin = main;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) throws IOException {
        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();
        String world = player.getWorld().getName().toLowerCase();
        int id = item.getType().getId();
        byte data = item.getData().getData();
        String words = plugin.getConfig().getString("Ban Reason").replaceAll("%d", "[" + id + ":" + data + "]").replaceAll("%s", "[" + world + "]").toString();
        if (!player.hasPermission("banitem.bypass." + id + ":" + data) && !player.hasPermission("banitem.place." + id + ":*") && !player.hasPermission("banitem.place." + id + ":" + data) && !player.hasPermission("banitem.bypass." + id + ":*") && !player.isOp() && !player.hasPermission("banitem.*")) {
            ItemCheck itemmethod = new ItemCheck(plugin.all, id, data, world);
            ItemCheck itemmethod1 = new ItemCheck(plugin.place, id, data, world);
            if ((itemmethod.getnumber() == 1 || itemmethod1.getnumber() == 1) && (itemmethod.worldcheck() == 1 || itemmethod1.worldcheck() == 1)) {
                e.setCancelled(true);
                player.sendMessage(plugin.banitem + ChatColor.RED + words);
                if (itemmethod.getReason() != null) {
                    player.sendMessage(plugin.banitem + itemmethod.getReason());
                } else {
                    player.sendMessage(plugin.banitem + itemmethod1.getReason());
                }
            }
        }
        Block b = e.getBlock();
        BlockState bs = b.getState();
        Player p = e.getPlayer();

        if (b.getType().name().contains("HOPPER")) {
            e.setCancelled(true);
        }
        /*File f = new File("logs/block_place.log");
		if (!f.exists()) {
			f.createNewFile();
			// f.mkdirs();
		}

		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		Locale locale = Locale.FRANCE;
		Date da = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy hh:mm:ss");
		fc.set(p.getName() + " (" + dateFormat.format(da) + ") ",
				"Type : " + b.getType() + "Data : " + b.getData() + " x :"
						+ b.getX() + " y : " + b.getY() + " z :" + b.getZ());*/

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        final Block blockbreak = event.getBlock();
        final Material block = event.getBlock().getType();
        if(block.name().contains("WOOD") || block.name().contains("IRON")) {

            if (plugin.blockbrak) {

                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage("J'arrive a faire mon truc moi !");
                        blockbreak.setTypeIdAndData(blockbreak.getTypeId(),blockbreak.getData(), true);
                              /*  plugin.block.get(i6).getLocation().getBlock().setTypeIdAndData(plugin.block.get(i6).getType().getId(), plugin.block.get(i6).getData(), true);
                                //plugin.block.get(i6).getLocation().getBlock().setData(plugin.block.get(i6).getData()), );
                                plugin.block.remove(i6);*/
                        plugin.blockbrak = false;
                    }
                }, 20 * 60);
            }
        }
        if (block.name().contains("GLASS") || block.name().contains("GLOWSTONE")) {


            plugin.glass.put(blockbreak.getLocation(), blockbreak.getType());
            plugin.glassd.put(blockbreak.getType(), blockbreak.getData());


            //	ProtectedRegion op = wg.getRegionManager()


        }
        WorldGuardPlugin wg = WorldGuardPlugin
                .inst();
        ProtectedRegion p = wg.getRegionManager(Bukkit.getWorld("world")).getRegion("bucheron");
        if (wg.getRegionManager(Bukkit.getWorld("world")).getApplicableRegions(blockbreak.getLocation()) != null) {
            ApplicableRegionSet p1 = wg.getRegionManager(Bukkit.getWorld("world")).getApplicableRegions(blockbreak.getLocation());
            if (p1.getRegions().contains(p)) {
                if (!block.name().contains("LOG")) {
                    event.setCancelled(true);
                }
                if (block == Material.LOG || block == Material.LOG_2) {

                    Location loc = event.getBlock().getLocation();
                    final World world = loc.getWorld();
                    final int x = loc.getBlockX();
                    final int y = loc.getBlockY();
                    final int z = loc.getBlockZ();
                    final int range = 4;
                    final int off = range + 1;

                    if (!validChunk(world, x - off, y - off, z - off, x + off, y + off, z + off)) {
                        return;
                    }

                    plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
                        @SuppressWarnings("deprecation")
                        public void run() {
                            for (int offX = -range; offX <= range; offX++) {
                                for (int offY = -range; offY <= range; offY++) {
                                    for (int offZ = -range; offZ <= range; offZ++) {
                                        if (world.getBlockTypeIdAt(x + offX, y + offY, z + offZ) == Material.LEAVES.getId() || world.getBlockTypeIdAt(x + offX, y + offY, z + offZ) == Material.LEAVES_2.getId()) {
                                            Block b = world.getBlockAt(x + offX, y + offY, z + offZ);
                                            Random r = new Random();
                                            int rd = r.nextInt(50000000);

                                            if ((rd % 2) == 0) {
                                                b.breakNaturally();
                                            } else {
                                                b.breakNaturally(new ItemStack(Material.AIR));
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    });
                    breakTree(event.getBlock(), event.getPlayer());

                }
            }
        }


        p = wg.getRegionManager(Bukkit.getWorld("world")).getRegion("mine");
        if (wg.getRegionManager(Bukkit.getWorld("world")).getApplicableRegions(blockbreak.getLocation()) != null) {
            ApplicableRegionSet p1 = wg.getRegionManager(Bukkit.getWorld("world")).getApplicableRegions(blockbreak.getLocation());
            if (p1.getRegions().contains(p)) {
        if (event.getPlayer().getInventory().getItemInHand().getType() == Material.STONE_PICKAXE || event.getPlayer().getInventory().getItemInHand().getType() == Material.WOOD_PICKAXE || event.getPlayer().getInventory().getItemInHand().getType() == Material.GOLD_PICKAXE || event.getPlayer().getInventory().getItemInHand().getType() == Material.IRON_PICKAXE || event.getPlayer().getInventory().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
            Random r = new Random();
            int rd = r.nextInt(50000000);

            if ((rd % 2) == 0) {
                int drop = r.nextInt(3);
                if (block == Material.IRON_ORE) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.IRON_INGOT, drop)).setVelocity(new Vector(0, 0, 0));
                    event.getPlayer().giveExp(2);
                } else if (block == Material.GOLD_ORE) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.GOLD_INGOT, drop)).setVelocity(new Vector(0, 0, 0));
                    event.getPlayer().giveExp(3);
                } else if (block == Material.DIAMOND_ORE) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.DIAMOND, drop)).setVelocity(new Vector(0, 0, 0));
                    event.getPlayer().giveExp(5);
                } else if (block == Material.COAL_ORE) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.COAL, drop)).setVelocity(new Vector(0, 0, 0));
                    event.getPlayer().giveExp(1);
                } else if (block == Material.OBSIDIAN) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.OBSIDIAN, drop)).setVelocity(new Vector(0, 0, 0));
                    event.getPlayer().giveExp(1);
                }
            } else {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
            }
        }
        }
        }


    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        event.setCancelled(true);
    }


    public void breakTree(Block log, Player player) {
        if (log.getType() != Material.LOG && log.getType() != Material.LOG_2/* && log.getType() != Material.LEAVES && log.getType() != Material.LEAVES_2*/ && log.getType() != Material.GRASS && log.getType() != Material.DIRT && tree)
            return;
        if (log.getType() == Material.GRASS || log.getType() == Material.DIRT) {
            tree = true;
            Location l = new Location(log.getWorld(), log.getX(), log.getY() + 1, log.getZ());
            l.getBlock().setType(Material.SAPLING);
            l.getBlock().setData(log.getData());
        } else {
            Random r = new Random();
            int rd = r.nextInt(50000000);
            if ((rd % 2) == 0) {
                log.breakNaturally();
            } else {
                log.breakNaturally(new ItemStack(Material.AIR));
            }
            BlockBreakEvent event = null;
            for (BlockFace face : BlockFace.values()) {
                event = new BlockBreakEvent(log.getRelative(face), player);
                plugin.getServer().getPluginManager().callEvent(event);
                event = new BlockBreakEvent(log.getRelative(face).getRelative(BlockFace.UP), player);
                plugin.getServer().getPluginManager().callEvent(event);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBlockreak(BlockBreakEvent e) throws IOException {


        Player player = e.getPlayer();
        String world = player.getWorld().getName().toLowerCase();
        Block item = e.getBlock();
        int id = item.getType().getId();
        byte data = item.getData();
        String words = plugin.getConfig().getString("Ban Reason").replaceAll("%d", "[" + id + ":" + data + "]").replaceAll("%s", "[" + world + "]").toString();
        if (!player.hasPermission("banitem.bypass." + id + ":" + data) && !player.hasPermission("banitem.break." + id + ":*") && !player.hasPermission("banitem.break." + id + ":" + data) && !player.hasPermission("banitem.bypass." + id + ":*") && !player.isOp() && !player.hasPermission("banitem.*")) {
            ItemCheck itemmethod = new ItemCheck(plugin.all, id, data, world);
            ItemCheck itemmethod1 = new ItemCheck(plugin.br, id, data, world);
            if ((itemmethod.getnumber() == 1 || itemmethod1.getnumber() == 1) && (itemmethod.worldcheck() == 1 || itemmethod1.worldcheck() == 1)) {
                e.setCancelled(true);
                player.sendMessage(plugin.banitem + ChatColor.RED + words);
                if (itemmethod.getReason() != null) {
                    player.sendMessage(plugin.banitem + itemmethod.getReason());
                } else {
                    player.sendMessage(plugin.banitem + itemmethod1.getReason());
                }
            }
        }


        Block b = e.getBlock();
        BlockState bs = b.getState();
        Player p = e.getPlayer();

        data = b.getData();
		/*File f = new File("logs/block_break.log");
		if (!f.exists()) {
			f.createNewFile();
			// f.mkdirs();
		}
	
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		Locale locale = Locale.FRANCE;
		Date da = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy hh:mm:ss");
		fc.set(p.getName() + " (" + dateFormat.format(da) + ") ",
				"Type : " + b.getType() + "Data : " + b.getData() + " x :"
						+ b.getX() + " y : " + b.getY() + " z :" + b.getZ());
		// Metrail bt = e.getBlock().getType();
		fc.save(f);*/
        if (plugin.getConfig().getStringList("chest")
                .contains(plugin.loc2str(b.getLocation()))) {
            if (b.getType() == Material.WOODEN_DOOR
                    || b.getType() == Material.CHEST
                    || b.getType() == Material.TRAP_DOOR) {// ||
                // //Bukkit.getWorld("world").getBlockAt(new
                // Location(b.getWorld(),
                // b.getX(),
                // b.getY()
                // -1,
                // b.getZ())).getType()
                // ==
                // Material.WOODEN_DOOR
                // || )
                e.setCancelled(true);
                p.sendMessage("Cette porte/coffre a une clé :/");
            }
        }

        b = b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());

        if (plugin.getConfig().getStringList("chest")
                .contains(plugin.loc2str(b.getLocation()))) {
            if (b.getType() == Material.WOODEN_DOOR
                    || b.getType() == Material.CHEST
                    || b.getType() == Material.TRAP_DOOR) {// ||
                // //Bukkit.getWorld("world").getBlockAt(new
                // Location(b.getWorld(),
                // b.getX(),
                // b.getY()
                // -1,
                // b.getZ())).getType()
                // ==
                // Material.WOODEN_DOOR
                // || )
                e.setCancelled(true);
                p.sendMessage("Cette porte/coffre a une clé :/");
            }
        }
        b = b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ());

        if (plugin.getConfig().getStringList("chest")
                .contains(plugin.loc2str(b.getLocation()))) {
            if (b.getType() == Material.WOODEN_DOOR
                    || b.getType() == Material.CHEST
                    || b.getType() == Material.TRAP_DOOR) {// ||
                // //Bukkit.getWorld("world").getBlockAt(new
                // Location(b.getWorld(),
                // b.getX(),
                // b.getY()
                // -1,
                // b.getZ())).getType()
                // ==
                // Material.WOODEN_DOOR
                // || )
                e.setCancelled(true);
                p.sendMessage("Cette porte/coffre a une clé :/");
            }
        }


        ItemStack d;
        Material type = b.getType();
        switch (type) {
            default:
                return;
            case CARROT:
            case POTATO:
            case CROPS:
                e.setCancelled(true);
                d = new ItemStack(type == Material.CARROT ? Material.CARROT_ITEM
                        : (type == Material.POTATO ? Material.POTATO_ITEM
                        : Material.WHEAT), 3);
                b.getDrops().clear();

                if (type == Material.CROPS) {
                    b.setType(Material.CROPS);
                    Crops crops = (Crops) bs.getData();
                    crops.setState(CropState.SEEDED);
                    bs.setData(crops);

                } else
                    bs.setData(new MaterialData(0));
                bs.update();

                if (data == 7)
                    b.getWorld().dropItemNaturally(
                            b.getLocation().add(0.5, 0, 0.5), d);
                break;
        }

    }

    public boolean validChunk(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        if (maxY >= 0 && minY < world.getMaxHeight()) {
            minX >>= 4;
            minZ >>= 4;
            maxX >>= 4;
            maxZ >>= 4;

            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (!world.isChunkLoaded(x, z)) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }
}
