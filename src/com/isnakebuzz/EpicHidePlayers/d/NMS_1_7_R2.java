package com.isnakebuzz.EpicHidePlayers.d;

import com.isnakebuzz.EpicHidePlayers.a.VersionHandler;
import net.minecraft.server.v1_7_R2.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS_1_7_R2 implements VersionHandler{

    @Override
    public void sendParticle(Player a, Player b) {
        Location loc = b.getLocation();
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                "largesmoke", 
                ((float) (loc.getX())),
                ((float) (loc.getY())),
                ((float) (loc.getZ())),
                0,
                0,
                0, 
                300, 
                0
        );
        ((CraftPlayer) a.getPlayer()).getHandle().playerConnection.sendPacket(packet);
    }

    
}
