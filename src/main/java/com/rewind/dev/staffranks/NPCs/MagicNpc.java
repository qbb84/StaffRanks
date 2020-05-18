package com.rewind.dev.staffranks.NPCs;

import com.rewind.dev.staffranks.StaffRanks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;

import javax.swing.*;
import java.util.Random;

public class MagicNpc implements Listener {

    private Villager villager;


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Material material = Material.OBSIDIAN;
        if(event.getBlock().getType().equals(material)){

            Player player = event.getPlayer();

             villager = player.getWorld().spawn(event.getBlock().getLocation(), Villager.class,
                    new Consumer<Villager>() {
                        @Override
                        public void accept(Villager villager) {
                            villager.setCanPickupItems(false);
                            villager.setAI(true);
                            villager.teleport(event.getBlock().getLocation());
                            villager.setTarget(player);

                            villager.setCustomName(player.getName());
                            villager.setCustomNameVisible(true);


                        }
                    });
             new BukkitRunnable(){
                 @Override
                 public void run() {
                     player.spawnParticle(Particle.NOTE, villager.getLocation(), 3, 1, 1, 1);
                 }
             }.runTaskTimerAsynchronously(StaffRanks.mainInstance, 0, 20);
        }
    }

    public void onClick(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();

        if(player.getEyeLocation().getWorld().getEntities().equals(villager)){
            Random random = new Random();

            player.teleport(new Location(player.getWorld(), random.nextInt(5000), 10, random.nextInt(5000)));

        }



    }
}
