// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.InternalStat;

public class ItemLevel extends InternalStat
{
    public ItemLevel() {
        super("ITEM_LEVEL", VersionMaterial.EXPERIENCE_BOTTLE.toMaterial(), "Item Level", new String[] { "The item level" }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)((DoubleData)statData).getValue()));
        return list;
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.DOUBLE));
        }
        final StatData loadedNBT = this.getLoadedNBT(list);
        if (loadedNBT != null) {
            readMMOItem.setData(this, loadedNBT);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            return new DoubleData((double)tagAtPath.getValue());
        }
        return null;
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new DoubleData(0.0);
    }
}
