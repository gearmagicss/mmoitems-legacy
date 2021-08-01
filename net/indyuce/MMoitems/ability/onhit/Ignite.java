// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Ignite extends Ability
{
    public Ignite() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT });
        this.addModifier("duration", 80.0);
        this.addModifier("max-ignite", 200.0);
        this.addModifier("radius", 5.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new LocationAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final Location target = ((LocationAbilityResult)abilityResult).getTarget();
        final int b = (int)(abilityResult.getModifier("max-ignite") * 20.0);
        final int n = (int)(abilityResult.getModifier("duration") * 20.0);
        final double pow = Math.pow(abilityResult.getModifier("radius"), 2.0);
        target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target.add(0.0, 0.1, 0.0), 0);
        target.getWorld().spawnParticle(Particle.LAVA, target, 12);
        target.getWorld().spawnParticle(Particle.FLAME, target, 48, 0.0, 0.0, 0.13);
        target.getWorld().playSound(target, VersionSound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST.toSound(), 2.0f, 1.0f);
        for (final Entity entity : MMOUtils.getNearbyChunkEntities(target)) {
            if (entity.getLocation().distanceSquared(target) < pow && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                entity.setFireTicks(Math.min(entity.getFireTicks() + n, b));
            }
        }
    }
}