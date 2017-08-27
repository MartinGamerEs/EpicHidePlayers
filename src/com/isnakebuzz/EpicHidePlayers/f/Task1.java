package com.isnakebuzz.EpicHidePlayers.f;

import com.isnakebuzz.EpicHidePlayers.a.Main;
import static com.isnakebuzz.EpicHidePlayers.c.Listeners.playerdata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Task1 extends BukkitRunnable {

    private final Main plugin;

    public Task1(Main plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        
        for (Player p : plugin.enable){
            if (plugin.ac == false){
                if ("false".equals(playerdata.getString(p.getName()))){
                    for (Player all : Bukkit.getOnlinePlayers()){
                        p.hidePlayer(all);
                    }
                }
            }else{
                String name = p.getName();
                if ("false".equals(HideMyqslAPI.getState(name))){
                    for (Player all : Bukkit.getOnlinePlayers()){
                        p.hidePlayer(all);
                    }
                }
            }
        }
        
    }

}
