// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import net.Indyuce.mmoitems.api.TypeSet;
import net.Indyuce.mmoitems.api.interaction.weapon.Weapon;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.LivingEntity;
import com.evill4mer.RealDualWield.Api.PlayerDamageEntityWithOffhandEvent;
import org.bukkit.event.Listener;

public class RealDualWieldHook implements Listener
{
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void a(final PlayerDamageEntityWithOffhandEvent playerDamageEntityWithOffhandEvent) {
        if (playerDamageEntityWithOffhandEvent.getDamage() == 0.0 || !(playerDamageEntityWithOffhandEvent.getEntity() instanceof LivingEntity) || playerDamageEntityWithOffhandEvent.getEntity().hasMetadata("NPC")) {
            return;
        }
        final LivingEntity livingEntity = (LivingEntity)playerDamageEntityWithOffhandEvent.getEntity();
        if (MythicLib.plugin.getDamage().findInfo((Entity)livingEntity) != null) {
            return;
        }
        final Player player = playerDamageEntityWithOffhandEvent.getPlayer();
        final PlayerData value = PlayerData.get((OfflinePlayer)player);
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(player.getInventory().getItemInOffHand());
        final ItemAttackResult itemAttackResult = new ItemAttackResult(playerDamageEntityWithOffhandEvent.getDamage(), new DamageType[] { DamageType.WEAPON, DamageType.PHYSICAL });
        if (nbtItem.hasType()) {
            final Weapon weapon = new Weapon(value, nbtItem);
            if (weapon.getMMOItem().getType().getItemSet() == TypeSet.RANGE) {
                playerDamageEntityWithOffhandEvent.setCancelled(true);
                return;
            }
            if (!weapon.checkItemRequirements()) {
                playerDamageEntityWithOffhandEvent.setCancelled(true);
                return;
            }
        }
        itemAttackResult.applyEffects(value.getStats().newTemporary(EquipmentSlot.OFF_HAND), nbtItem, livingEntity);
        playerDamageEntityWithOffhandEvent.setDamage(itemAttackResult.getDamage());
    }
}