package com.isnakebuzz.EpicHidePlayers.a;

import static com.isnakebuzz.EpicHidePlayers.c.Listeners.*;
import com.isnakebuzz.EpicHidePlayers.d.*;
import com.isnakebuzz.EpicHidePlayers.e.*;
import com.isnakebuzz.EpicHidePlayers.f.*;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
    
    public Main(){
        this.a = new com.isnakebuzz.EpicHidePlayers.c.Listeners(this);
        this.b = new com.isnakebuzz.EpicHidePlayers.f.API(this);
    }
   
    public VersionHandler versionHandler;
    private com.isnakebuzz.EpicHidePlayers.c.Listeners a;
    private com.isnakebuzz.EpicHidePlayers.f.API b;
    public boolean aa;
    public boolean ab;
    public boolean ac;
    public boolean ad;
    public static MySQL mysql;
    public static Main INSTANCE;
    
    public ArrayList<Player> enable = new ArrayList<Player>();
    
    @Override
    public void onEnable(){
        com.isnakebuzz.EpicHidePlayers.b.Settings.get().a(this);
        this.ad = settings.getBoolean("EnableByItem");
        if (ad == true){
            this.ac = settings.getBoolean("MySQL.enable");
            for (Player all : Bukkit.getOnlinePlayers()){
                getAPI().setItems(all);
            }
            if (ac == false){
                new Task1(this).runTaskTimer(this, 01L, 01L);
                getLogger().log(Level.INFO, "*EpicHidePlayers: PlayerData = yml*");
                com.isnakebuzz.EpicHidePlayers.b.PlayerData.get().a(this);
            }else{
                this.mysqlinit();
                Main.mysql = new MySQL();
                MySQL.connect();
                MySQL.update("CREATE TABLE IF NOT EXISTS EpicHidePlayer (Name VARCHAR(100), Enable text)");
                getLogger().log(Level.INFO, "*EpicHidePlayers: PlayerData = MySQL*");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!MySQL.isConnected()){
                            MySQL.connect();
                        }
                    }
                }.runTaskTimer(this, 240 * 20, 240 * 20);
                new Task1(this).runTaskTimer(this, 10L, 10L);
            }
        }
        aa = settings.getBoolean("EnableTouchHide");
        ab = settings.getBoolean("Particle");
        getServerVersion();
        this.a.a();
        getLogger().log(Level.INFO, "*EpicHidePlayers: Has been loaded");
    }

    @Override
    public void onDisable() {
        if (ad == true){
            for (Player all : Bukkit.getOnlinePlayers()){
                getAPI().removeItems(all);
            }
        }
    }
    
    public String c(String c){
        return ChatColor.translateAlternateColorCodes('&', c);
    }
    
    public com.isnakebuzz.EpicHidePlayers.f.API getAPI(){
        return b;
    }
    
    public void mysqlinit() {
        final File file = new File(this.getDataFolder().getPath(), "settings.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        MySQL.host = config.getString("MySQL.HostName");
        MySQL.port = config.getString("MySQL.Port");
        MySQL.database = config.getString("MySQL.Database");
        MySQL.username = config.getString("MySQL.Username");
        MySQL.password = config.getString("MySQL.Password");
    }
    
    private void getServerVersion(){
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        boolean isVaild = true;
        switch (version){
            case "v1_7_R1":
                versionHandler = new NMS_1_7_R1();
                getLogger().log(Level.INFO, "EpicHidePlayers loaded in {0}", version);
                break;
            case "v1_7_R2":
                versionHandler = new NMS_1_7_R2();
                getLogger().log(Level.INFO, "EpicHidePlayers loaded in {0}", version);
                break;
            case "v1_7_R3":
                versionHandler = new NMS_1_7_R3();
                getLogger().log(Level.INFO, "EpicHidePlayers loaded in {0}", version);
                break;
            case "v1_8_R3":
                versionHandler = new NMS_1_8_R3();
                getLogger().log(Level.INFO, "EpicHidePlayers loaded in {0}", version);
                break;
            default:
                isVaild = false;
                break;
        }
        if (!isVaild){
            getLogger().log(Level.SEVERE, "EpicHidePlayers not enabled with version, please contact me contacto@isnakebuzz.com");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
