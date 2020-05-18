package com.rewind.dev.staffranks;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.UUID;

public interface rankRequirement {

    String name = "";
    ChatColor color = null;
    UUID uuid = null;
    LinkedHashMap<UUID, Enum> rankMap = null;



}
