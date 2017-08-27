package com.isnakebuzz.EpicHidePlayers.e;

import com.isnakebuzz.EpicHidePlayers.a.VersionHandler;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS_1_8_R3 implements VersionHandler{

    @Override
    public void sendParticle(Player a, Player b) {
        Location loc = b.getLocation();
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
            EnumParticle.SMOKE_LARGE,    // particle type.
            true,                           // true
            b.getLocation().getBlockX(),     // x coordinate
            b.getLocation().getBlockY(),     // y coordinate
            b.getLocation().getBlockZ(),     // z coordinate
            0,                              // x offset
            0,                              // y offset
            0,                              // z offset
            1,                             // speed
            300,                         // number of particles
            null
        );
        ((CraftPlayer) a.getPlayer()).getHandle().playerConnection.sendPacket(packet);
    }
    
}
