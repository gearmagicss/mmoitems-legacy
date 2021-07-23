// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.event.HandlerList;
import net.Indyuce.mmoitems.api.event.PlayerDataEvent;

public class BreakSoulboundEvent extends PlayerDataEvent
{
    private static final HandlerList handlers;
    private final VolatileMMOItem consumable;
    private final NBTItem target;
    
    public BreakSoulboundEvent(final PlayerData playerData, final VolatileMMOItem consumable, final NBTItem target) {
        super(playerData);
        this.consumable = consumable;
        this.target = target;
    }
    
    public VolatileMMOItem getConsumable() {
        return this.consumable;
    }
    
    public NBTItem getTargetItem() {
        return this.target;
    }
    
    public HandlerList getHandlers() {
        return BreakSoulboundEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BreakSoulboundEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}