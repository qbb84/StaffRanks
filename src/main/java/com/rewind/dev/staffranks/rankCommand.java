package com.rewind.dev.staffranks;


import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class rankCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("setrank")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length != 0) {
                    if (args.length == 2) {
                        Player target = Bukkit.getServer().getPlayer((args[0]));
                        if (target == null) return false;
                        if (EnumUtils.isValidEnum(ranks.class, args[1].toUpperCase())) {

                            StaffRanks.getFileManager().setRank(target, ranks.valueOf(args[1].toUpperCase()), p);
                            sendMessage(p, target, ranks.valueOf(args[1].toUpperCase()));
                        }

                    } else return false;

                } else {
                    Inventory inventory = Bukkit.getServer().createInventory(p, 54, "All players online");

                    for (Player online : Bukkit.getOnlinePlayers()) {

                        if(StaffRanks.getFileManager().isStaff(online) && !sender.isOp()){
                            continue;
                        }
                            ItemStack hOfP = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
                            SkullMeta meta = (SkullMeta) hOfP.getItemMeta();
                            meta.setOwningPlayer(online.getPlayer());
                            meta.setDisplayName(ChatColor.DARK_AQUA + "Player: " + StaffRanks.getFileManager().guiRankString(StaffRanks.getFileManager().getRank(online), online));
                            meta.setLore(new ArrayList<>(Arrays.asList(ChatColor.AQUA + "" + ChatColor.AQUA + "Left click to edit rank.")));
                            hOfP.setItemMeta(meta);
                            inventory.addItem(hOfP);


                    }
                    p.openInventory(inventory);
                }
            }


        }
        return true;
    }
    private void sendMessage(Player player, CommandSender sender, ranks rank) {
        player.sendMessage(ChatColor.RED + "You have been given rank " + rank.getColor() + " " + rank.getName());
        sender.sendMessage(ChatColor.RED + "You have given rank " + rank.getColor() + " " + rank.getName() + " to player " + player.getName());

    }



}

