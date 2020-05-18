package com.rewind.dev.staffranks;

import org.apache.commons.lang.enums.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.xml.stream.util.StreamReaderDelegate;
import java.util.*;


public enum ranks implements rankRequirement {


    GUEST(ChatColor.GRAY, "GUEST"),
    DEVELOPER(ChatColor.YELLOW, "DEVELOPER"),
    HELPER(ChatColor.BLUE, "HELPER"),
    MOD(ChatColor.DARK_GREEN, "MOD"),
    ADMIN(ChatColor.RED, "ADMIN"),
    OWNER(ChatColor.DARK_RED, "OWNER");

    private String name;
    private ChatColor color;



    ranks(ChatColor color, String name) {
        this.name = name;
        this.color = color;



    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }





}
