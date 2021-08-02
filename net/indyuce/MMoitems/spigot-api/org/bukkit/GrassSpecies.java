// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum GrassSpecies
{
    DEAD("DEAD", 0, 0), 
    NORMAL("NORMAL", 1, 1), 
    FERN_LIKE("FERN_LIKE", 2, 2);
    
    private final byte data;
    private static final Map<Byte, GrassSpecies> BY_DATA;
    
    static {
        BY_DATA = Maps.newHashMap();
        GrassSpecies[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final GrassSpecies grassSpecies = values[i];
            GrassSpecies.BY_DATA.put(grassSpecies.getData(), grassSpecies);
        }
    }
    
    private GrassSpecies(final String name, final int ordinal, final int data) {
        this.data = (byte)data;
    }
    
    @Deprecated
    public byte getData() {
        return this.data;
    }
    
    @Deprecated
    public static GrassSpecies getByData(final byte data) {
        return GrassSpecies.BY_DATA.get(data);
    }
}