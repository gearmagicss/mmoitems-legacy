// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityShootBowEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final ItemStack bow;
    private Entity projectile;
    private final float force;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public EntityShootBowEvent(final LivingEntity shooter, final ItemStack bow, final Projectile projectile, final float force) {
        super(shooter);
        this.bow = bow;
        this.projectile = projectile;
        this.force = force;
    }
    
    @Override
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }
    
    public ItemStack getBow() {
        return this.bow;
    }
    
    public Entity getProjectile() {
        return this.projectile;
    }
    
    public void setProjectile(final Entity projectile) {
        this.projectile = projectile;
    }
    
    public float getForce() {
        return this.force;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntityShootBowEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityShootBowEvent.handlers;
    }
}
