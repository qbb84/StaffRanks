package com.rewind.dev.staffranks;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.omg.CORBA.WrongTransactionHolder;
import sun.awt.windows.WPrinterJob;

import java.io.File;
import java.util.UUID;

public class FileManagerr  {

    private YamlConfiguration configuration;
    private File file;

    public FileManagerr(StaffRanks staffRanks){
        file = new File(staffRanks.getDataFolder(), "data.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void setRank(Player player, ranks rank, CommandSender sender){
        if(!configuration.contains(player.getUniqueId().toString())){
            configuration.set(player.getUniqueId().toString(), rank.name());
            saveFile();


            if(!getRank(player).equals(ranks.GUEST)) {
                player.setPlayerListName(rank.getColor() + "" + ChatColor.BOLD + "" + rank.getName() + rank.getColor() + " " + player.getName());
            }else{ player.setPlayerListName(rank.getColor() + player.getName()); }
        }else{
            //configuration.set(player.getUniqueId().toString(), null);
            configuration.set(player.getUniqueId().toString(), rank.name());
            saveFile();

            if(!getRank(player).equals(ranks.GUEST)) {
                player.setPlayerListName(rank.getColor() + "" + ChatColor.BOLD + "" + rank.getName() + rank.getColor() + " " + player.getName());
            }else{ player.setPlayerListName(rank.getColor() + player.getName()); }
        }


    }

    public void setRank(UUID uuid, ranks rank, Player whoClicked){
        Player player = Bukkit.getPlayer(uuid);
        if(!configuration.contains(player.getUniqueId().toString())){
            configuration.set(player.getUniqueId().toString(), rank.name());
            saveFile();


            if(!getRank(player).equals(ranks.GUEST)) {
                player.setPlayerListName(rank.getColor() + "" + ChatColor.BOLD + "" + rank.getName() + rank.getColor() + " " + player.getName());
            }else{ player.setPlayerListName(rank.getColor() + player.getName()); }
        }else{
            //configuration.set(player.getUniqueId().toString(), null);
            configuration.set(player.getUniqueId().toString(), rank.name());
            saveFile();

            if(!getRank(player).equals(ranks.GUEST)) {
                player.setPlayerListName(rank.getColor() + "" + ChatColor.BOLD + "" + rank.getName() + rank.getColor() + " " + player.getName());
            }else{ player.setPlayerListName(rank.getColor() + player.getName()); }
        }

        player.sendMessage("You have been given rank " + getRank(player));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1,1);
        whoClicked.sendMessage("You gave rank" + getRank(player) + " to" + player.getName());
        whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1,1);




    }

    public void setRank(UUID uuid, ranks rank){
        configuration.set(uuid.toString(), rank.name());
        saveFile();
    }

    public ranks getRank(Player player){
        return ranks.valueOf(configuration.getString(player.getUniqueId().toString()));


    }

    public String guiRankString(ranks rak, Player player){
        return rak.getColor() + rak.getName() + " " + player.getName();

    }

    public boolean isStaff(Player player){
        return !getRank(player).equals(ranks.GUEST);
    }


    public void saveFile(){
        try{
            configuration.save(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public YamlConfiguration getConfiguration() {
        return configuration;

    }
}