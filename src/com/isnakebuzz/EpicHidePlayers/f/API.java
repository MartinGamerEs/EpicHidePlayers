package com.isnakebuzz.EpicHidePlayers.f;

import static com.isnakebuzz.EpicHidePlayers.c.Listeners.playerdata;
import static com.isnakebuzz.EpicHidePlayers.c.Listeners.settings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class API {
    
    List<String> EnableLore = settings.getStringList("ItemConfig.Enable.Lore");
    List<String> DisableLore = settings.getStringList("ItemConfig.Disable.Lore");
    
    private com.isnakebuzz.EpicHidePlayers.a.Main a;
    
    public API(com.isnakebuzz.EpicHidePlayers.a.Main a){
        this.a = a;
    }
    
    public void setItems(Player p){
        String name = p.getName();
        if (a.ac == true){
            if (!HideMyqslAPI.playerExists(name)){
                HideMyqslAPI.createPlayer(name);
            }
            if ("true".equals(HideMyqslAPI.getState(name))){
                a.enable.remove(p);
                ItemStack Enable = new ItemStack(Material.getMaterial(settings.getInt("ItemConfig.Enable.ID")), settings.getInt("ItemConfig.Enable.AMOUNT"),(byte) settings.getInt("ItemConfig.Enable.DATA"));
                ItemMeta Enablem = Enable.getItemMeta();
                final List<String> lore1 = new ArrayList<>();
                Enablem.setDisplayName(c(settings.getString("ItemConfig.Enable.Name")));
                EnableLore.stream().map((s) -> c(s).replaceAll("%player%", p.getName())).forEach((ss) -> {
                    lore1.add(ss);
                });
                Enablem.setLore(lore1);
                p.getInventory().setItem(settings.getInt("ItemConfig.Slot"), Enable);
            }else if ("false".equals(HideMyqslAPI.getState(name))){
                a.enable.add(p);
                ItemStack Disable = new ItemStack(Material.getMaterial(settings.getInt("ItemConfig.Disable.ID")), settings.getInt("ItemConfig.Disable.AMOUNT"),(byte) settings.getInt("ItemConfig.Enable.DATA"));
                ItemMeta Disablem = Disable.getItemMeta();
                final List<String> lore2 = new ArrayList<>();
                Disablem.setDisplayName(c(settings.getString("ItemConfig.Disable.Name")));
                DisableLore.stream().map((s) -> c(s).replaceAll("%player%", p.getName())).forEach((ss) -> {
                    lore2.add(ss);
                });
                Disablem.setLore(lore2);
                p.getInventory().setItem(settings.getInt("ItemConfig.Slot"), Disable);
            }
        }else if (a.ac == false){
            if (!PlayerExist(p)){
                CreatePlayer(p);
            }
            if ("true".equals(playerdata.getString(name))){
                a.enable.remove(p);
                ItemStack Enable = new ItemStack(Material.getMaterial(settings.getInt("ItemConfig.Enable.ID")), settings.getInt("ItemConfig.Enable.AMOUNT"),(byte) settings.getInt("ItemConfig.Enable.DATA"));
                ItemMeta Enablem = Enable.getItemMeta();
                final List<String> lore1 = new ArrayList<>();
                Enablem.setDisplayName(c(settings.getString("ItemConfig.Enable.Name")));
                EnableLore.stream().map((s) -> c(s).replaceAll("%player%", p.getName())).forEach((ss) -> {
                    lore1.add(ss);
                });
                Enablem.setLore(lore1);
                Enable.setItemMeta(Enablem);
                p.getInventory().setItem(settings.getInt("ItemConfig.Slot"), Enable);
            }else if ("false".equals(playerdata.getString(name))){
                a.enable.add(p);
                ItemStack Disable = new ItemStack(Material.getMaterial(settings.getInt("ItemConfig.Disable.ID")), settings.getInt("ItemConfig.Disable.AMOUNT"),(byte) settings.getInt("ItemConfig.Enable.DATA"));
                ItemMeta Disablem = Disable.getItemMeta();
                final List<String> lore2 = new ArrayList<>();
                Disablem.setDisplayName(c(settings.getString("ItemConfig.Disable.Name")));
                DisableLore.stream().map((s) -> c(s).replaceAll("%player%", p.getName())).forEach((ss) -> {
                    lore2.add(ss);
                });
                Disablem.setLore(lore2);
                Disable.setItemMeta(Disablem);
                p.getInventory().setItem(settings.getInt("ItemConfig.Slot"), Disable);
            }
        }
    }
    
    public void removeItems(Player p){
        ItemStack air = new ItemStack(Material.AIR);
        p.getInventory().setItem(settings.getInt("ItemConfig.Slot"), air);
    }
    
    private File f2 = new File("plugins//EpicHidePlayers//PlayerData.yml");
    
    public void setState(Player p, String state){
        playerdata.set(p.getName(), state);
        try {
            playerdata.save(f2);
        } catch (IOException ex) {
            Logger.getLogger(API.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void CreatePlayer(Player p){
        if (!PlayerExist(p)){
            playerdata.set(p.getName(), "true");
            try {
                playerdata.save(f2);
            } catch (IOException ex) {
                Logger.getLogger(API.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean PlayerExist(Player p){
        try {
            return playerdata.getString(p.getName()) != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    private String c(String c){
        return ChatColor.translateAlternateColorCodes('&', c);
    }
    
}
