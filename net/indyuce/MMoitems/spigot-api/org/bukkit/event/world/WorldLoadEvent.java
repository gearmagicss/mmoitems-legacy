// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.world;

import org.bukkit.World;
import org.bukkit.event.HandlerList;

public class WorldLoadEvent extends WorldEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public WorldLoadEvent(final World world) {
        super(world);
    }
    
    @Override
    public HandlerList getHandlers() {
        return WorldLoadEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return WorldLoadEvent.handlers;
    }
}
