package com.isnakebuzz.EpicHidePlayers.f;

import com.isnakebuzz.EpicHidePlayers.a.Main;
import java.sql.*;

public class HideMyqslAPI{
    
    public static boolean playerExists(final String uuid) {
        try {
            final ResultSet rs = MySQL.query("SELECT * FROM EpicHidePlayer WHERE Name='" + uuid + "'");
            return rs.next() && rs.getString("Name") != null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void createPlayer(final String uuid) {
        if (!playerExists(uuid)) {
            MySQL.update("INSERT INTO Data_API (Name, Enable) VALUES ('" + uuid + "', 'true');");
        }
    }
    
    public static void setState(final String name, final String State) {
        if (playerExists(name)) {
            MySQL.update("UPDATE Data_API SET Enable='" + State + "' WHERE Name='" + name + "'");
        }
    }
    
    public static String getState(final String name) {
        String i = null;
        if (playerExists(name)) {
            try {
                final MySQL mysql = Main.mysql;
                final ResultSet rs = MySQL.query("SELECT * FROM Data_API WHERE Name='" + name + "'");
                if (rs.next()) {
                    rs.getString("Enable");
                }
                i = String.valueOf(rs.getString("Enable"));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
}
