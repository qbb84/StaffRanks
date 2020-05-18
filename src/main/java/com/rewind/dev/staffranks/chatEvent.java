package com.rewind.dev.staffranks;

import com.google.common.util.concurrent.Service;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class chatEvent implements Listener {

    // rankSet each player in the GUI has a unique array of ranks assisgned to their UUID.
    private LinkedHashMap<UUID, ArrayList<ranks>> ranksSet = new LinkedHashMap<>();
    //Used for getting a unique int value for each in the gui used for settins ranks in /setrank
    private LinkedHashMap<UUID, Integer> playerInt = new LinkedHashMap<>();
    //Map of a player viewing a certain inventory with that item name so only 1 person can view it at a time.
    private HashMap<String, String> viewers = new HashMap<>();
    //Used for creating a colorful pattern in the gui through a loop
    private int i = 0;
    //Inventory GUI
    private Inventory inventory;


    private Material[] coloredGlass = {
            Material.RED_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS_PANE,
            Material.AIR,
            Material.GREEN_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,


    };



    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {


        if (StaffRanks.getFileManager().getConfiguration().contains(e.getPlayer().getUniqueId().toString())) {
            ranks rank = StaffRanks.fileManagerr.getRank(e.getPlayer());
            if (!StaffRanks.getFileManager().getRank(e.getPlayer()).equals(ranks.GUEST)) {
                e.setFormat(rank.getColor() + "" + ChatColor.BOLD + "" + rank.getName() + rank.getColor() + " " + e.getPlayer().getName() + ChatColor.WHITE + ": " + e.getMessage());
            } else {
                e.setFormat(rank.getColor() + e.getPlayer().getName() + ChatColor.WHITE + ": " + e.getMessage());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        viewers.clear();
        Player player = e.getPlayer();

        if (StaffRanks.getFileManager().getConfiguration().contains(player.getUniqueId().toString())) {
            ranks rank = StaffRanks.fileManagerr.getRank(e.getPlayer());
            if (!StaffRanks.getFileManager().getRank(e.getPlayer()).equals(ranks.GUEST)) {
                player.setPlayerListName(rank.getColor() + "" + ChatColor.BOLD + "" + rank.getName() + rank.getColor() + " " + player.getName());
            } else {
                player.setPlayerListName(rank.getColor() + player.getName());
            }
        } else {
            StaffRanks.getFileManager().setRank(e.getPlayer().getUniqueId(), ranks.GUEST);
            ranks rank = StaffRanks.fileManagerr.getRank(e.getPlayer());
            player.setPlayerListName(rank.getColor() + player.getName());
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        ItemStack hOfP = new ItemStack(Material.BOOK, 1, (short) SkullType.PLAYER.ordinal());
        ItemMeta meta = hOfP.getItemMeta();
        LinkedList<String> lore = new LinkedList<>();// Make sure it's not null, get the item clicked names/attributes and store in a variable for the setrank in one item.
        Player p = (Player) e.getWhoClicked();
        String getPlayerOfItemClicked = "";
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Player")) {
                if (e.getClick().equals(ClickType.LEFT)) {


                    if (i > 8) i = 0;

                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().contains(player1.getName())) {
                            getPlayerOfItemClicked = player1.getName();
                        }
                    }

                    if (!playerInt.containsKey(Bukkit.getPlayer(getPlayerOfItemClicked).getUniqueId())) {
                        playerInt.put(Bukkit.getPlayer(getPlayerOfItemClicked).getUniqueId(), 0);
                    }


                    if(viewers.values().contains(getPlayerOfItemClicked)){
                        p.sendMessage(ChatColor.LIGHT_PURPLE + " You cannot view this as someone else is viewing it");
                        e.setCancelled(true);
                        return;
                    }
                    inventory = Bukkit.getServer().createInventory(p, 9, "Set rank");

                    // ItemStack hOfP = new ItemStack(Material.BOOK, 1, (short) SkullType.PLAYER.ordinal());
                    // ItemMeta meta = hOfP.getItemMeta();
//                    meta.setDisplayName(getPlayerOfItemClicked);
//
//                    lore.add(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "                                  ");
//                    lore.add(" ");
//                    lore.add(ChatColor.WHITE + "\u2022" + ChatColor.ITALIC + ChatColor.AQUA + " Current rank: " + ChatColor.GREEN + ChatColor.BOLD + StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).getName());
//                    lore.add(" ");
//                    lore.add(ChatColor.WHITE + "\u2022 " + ChatColor.ITALIC + ChatColor.AQUA + "" + "Available ranks: ");
//                    lore.add(" ");
//                    ranksSet.clear();
//                    ranksSet.put(Bukkit.getPlayer(getPlayerOfItemClicked).getUniqueId(), new ArrayList<>());
//                    for (Enum s : ranks.values()) {
//
//                        if (!StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).equals(s)) {
//                            ranksSet.get(Bukkit.getPlayer(getPlayerOfItemClicked).getUniqueId()).add(ranks.valueOf(s.name()));
//                            lore.add(ChatColor.WHITE + "\u2022 " + ranks.valueOf(s.name()).getColor() + s.name());
//
//
//                        }
//
//
//                    }
//                    lore.add(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "                                  ");
//                    lore.add(6+j, ChatColor.GREEN + "\u2022 " + StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).getColor() +
//                            StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).name());


                    updateLore(lore, getPlayerOfItemClicked, p, meta, hOfP);
                    Bukkit.broadcastMessage("--------------------");
                    for (UUID p1 : ranksSet.keySet()) {
                        Bukkit.broadcastMessage(getPlayerOfItemClicked + " " + p1 + ranksSet.get(p1));
                    }


                    meta.setLore(lore);
                    hOfP.setItemMeta(meta);

                    inventory.setItem(4, hOfP);

                    for (; i < 9; i++) {
                        if (i == 4) {
                            continue;
                        }
                        inventory.setItem(i, new ItemStack(coloredGlass[i]));
                    }
                    p.openInventory(inventory);

                    if(!viewers.values().contains(getPlayerOfItemClicked)){
                        viewers.put(p.getName(), getPlayerOfItemClicked);
                    }


                } else e.setCancelled(true);

            } else e.setCancelled(true);
        }
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getType() == Material.BOOK) {
            if (e.getClick().equals(ClickType.LEFT)) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                UUID pl = Bukkit.getPlayer(name).getUniqueId();

                if(!playerInt.containsKey(pl)){
                    playerInt.put(pl,  0);
                }

                int im = playerInt.get(pl);



                try {
                    if (im <= 4) {
                        im++;
                        playerInt.put(pl, im);
                        StaffRanks.getFileManager().setRank(Bukkit.getPlayer(name).getUniqueId(), ranksSet.get(Bukkit.getPlayer(name).getUniqueId()).get(im), p);


                    } else {
                        im = 0;
                        playerInt.put(pl, im);
                        StaffRanks.getFileManager().setRank(Bukkit.getPlayer(name).getUniqueId(), ranksSet.get(Bukkit.getPlayer(name).getUniqueId()).get(0), p);

                    }


                    updateLore(lore, name, ((Player) e.getWhoClicked()).getPlayer(), meta, hOfP);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    private void updateLore(LinkedList<String> lore, String getPlayerOfItemClicked, Player p, ItemMeta meta, ItemStack hOfP) {
        

        meta.setDisplayName(getPlayerOfItemClicked);
        lore.clear();
        lore.add(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "                                  ");
        lore.add(" ");
        lore.add(ChatColor.WHITE + "\u2022" + ChatColor.ITALIC + ChatColor.AQUA + " Current rank: " + ChatColor.GREEN + ChatColor.BOLD + StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).getName());
        lore.add(" ");
        lore.add(ChatColor.WHITE + "\u2022 " + ChatColor.ITALIC + ChatColor.AQUA + "" + "Available ranks: ");
        lore.add(" ");
        ranksSet.clear();
       // ranksSet.put(Bukkit.getPlayer(getPlayerOfItemClicked).getUniqueId(), new ArrayList<>());
        ArrayList<ranks> rank = new ArrayList<>();
        for (Enum s : ranks.values()) {
            if(!rank.contains(ranks.valueOf(s.name()))) {
                rank.add(ranks.valueOf(s.name()));
            }
            //ranksSet.get(Bukkit.getPlayer(getPlayerOfItemClicked).getUniqueId()).add(ranks.valueOf(s.name()));
            ranksSet.put(Bukkit.getPlayer(getPlayerOfItemClicked).getUniqueId(),  rank);
            if (StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).equals(ranks.valueOf(s.name()))) {
                lore.add(ChatColor.GREEN + "\u2022 " + StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).getColor() +
                        StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).name());
            } else lore.add(ChatColor.WHITE + "\u2022 " + ranks.valueOf(s.name()).getColor() + s.name());


        }

        lore.add(ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + "                                  ");
//                lore.add(6 + playerInt.get(Bukkit.getPlayer(getPlayerOfItemClicked).getUniqueId()), ChatColor.GREEN + "\u2022 " + StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).getColor() +
//                        StaffRanks.getFileManager().getRank(Bukkit.getPlayer(getPlayerOfItemClicked)).name());
        meta.setLore(lore);
        hOfP.setItemMeta(meta);
        inventory.setItem(4, hOfP);
        p.updateInventory();


    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        viewers.remove(event.getPlayer().getName());
    }


}
