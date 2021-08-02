// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.util;

import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.util.Collection;

public class StringUtil
{
    public static <T extends Collection<? super String>> T copyPartialMatches(final String token, final Iterable<String> originals, final T collection) throws UnsupportedOperationException, IllegalArgumentException {
        Validate.notNull(token, "Search token cannot be null");
        Validate.notNull(collection, "Collection cannot be null");
        Validate.notNull(originals, "Originals cannot be null");
        for (final String string : originals) {
            if (startsWithIgnoreCase(string, token)) {
                ((Collection<String>)collection).add(string);
            }
        }
        return collection;
    }
    
    public static boolean startsWithIgnoreCase(final String string, final String prefix) throws IllegalArgumentException, NullPointerException {
        Validate.notNull(string, "Cannot check a null string for a match");
        return string.length() >= prefix.length() && string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}
