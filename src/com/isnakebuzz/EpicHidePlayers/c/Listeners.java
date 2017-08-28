package com.isnakebuzz.EpicHidePlayers.c;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Listeners implements Listener{
    
    public static File f = new File("plugins//EpicHidePlayers//settings.yml");
    public static YamlConfiguration settings = YamlConfiguration.loadConfiguration(f);
    
    public static File f2 = new File("plugins//EpicHidePlayers//PlayerData.yml");
    public static YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(f2);
    
    private com.isnakebuzz.EpicHidePlayers.a.Main a;
    
    public Listeners(com.isnakebuzz.EpicHidePlayers.a.Main a){
        this.a = a;
    }
    
    @EventHandler
    public void a(final EntityDamageByEntityEvent e) {
        if (a.aa == false){
            return;
        }
        if (!(e.getEntity() instanceof Player)){
            return;
        }
        Player p = (Player) e.getDamager();
        final Player r = (Player)e.getEntity();
        if(!settings.getStringList("EnableWorlds").contains(p.getWorld().getName()) || !settings.getStringList("EnableWorlds").contains(r.getWorld().getName())){
            return;
        }
        if (r instanceof Player) {
            e.setCancelled(true);
            p.hidePlayer(r);
            p.playSound(r.getLocation(), Sound.valueOf(settings.getString("SubSettings.Sound")), settings.getInt("SubSettings.Vol"), settings.getInt("SubSettings.Pitch"));
            p.sendMessage(a(settings.getString("Message")));
            if (a.ab == true){
                a.versionHandler.sendParticle(p, r);
            }
        }
    }    
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (a.ad == false){
            return;
        }
        a.getAPI().setItems(p);
    }
    
    private ArrayList<Player> cooldown = new ArrayList<>();
    
    @EventHandler
    public void ChangeWorld(PlayerChangedWorldEvent e){
        Player p = e.getPlayer();
        if (a.ad == false){
            return;
        }
        if(!settings.getStringList("EnableWorlds").contains(p.getWorld().getName())){
            return;
        }
        a.getAPI().setItems(p);
    }
    
    @EventHandler
    public void Interact(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (a.ad == false){
            return;
        }
        if (e.getItem() == null){
            return;
        }
        if (e.getItem().getItemMeta() == null){
            return;
        }
        if (cooldown.contains(p)){
            p.sendMessage(c(settings.getString("Messages.Cooldown")));
            return;
        }
        if(!settings.getStringList("EnableWorlds").contains(p.getWorld().getName())){
            return;
        }
        if (settings.getString("ItemConfig.Enable.Name").replaceAll("&", "§").equals(e.getItem().getItemMeta().getDisplayName()) || settings.getString("ItemConfig.Disable.Name").replaceAll("&", "§").equals(e.getItem().getItemMeta().getDisplayName())){
            e.setCancelled(true);
            if (settings.getString("ItemConfig.Enable.Name").replaceAll("&", "§").equals(e.getItem().getItemMeta().getDisplayName())){
                a.enable.add(p);
                a.getAPI().setState(p, "false");
                a.getAPI().setItems(p);
                p.sendMessage(c(settings.getString("Messages.Enable")));
                for (Player all : Bukkit.getOnlinePlayers()){
                    p.hidePlayer(all);
                }
            }
            if (settings.getString("ItemConfig.Disable.Name").replaceAll("&", "§").equals(e.getItem().getItemMeta().getDisplayName())){
                a.enable.remove(p);
                p.sendMessage(c(settings.getString("Messages.Disable")));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player all : Bukkit.getOnlinePlayers()){
                            p.showPlayer(all);
                        }
                    }
                }.runTaskLater(a, 11);
                a.getAPI().setState(p, "true");
                a.getAPI().setItems(p);
            }
        }
    }
    
    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (a.ad == false){
            return;
        }
        if (a.enable.contains(p)){
            a.enable.remove(p);
        }
    }
    
    private String c(String c){
        return ChatColor.translateAlternateColorCodes('&', c);
    }
    
    @EventHandler
    public void Join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        
    }
    
    private String a(String a){
        return ChatColor.translateAlternateColorCodes('&', a);
    }
    
    public void a(){
        a.getServer().getPluginManager().registerEvents(this, this.a);
    }
}
