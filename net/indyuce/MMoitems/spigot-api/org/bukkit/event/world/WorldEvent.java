// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.world;

import org.bukkit.World;
import org.bukkit.event.Event;

public abstract class WorldEvent extends Event
{
    private final World world;
    
    public WorldEvent(final World world) {
        this.world = world;
    }
    
    public World getWorld() {
        return this.world;
    }
}
