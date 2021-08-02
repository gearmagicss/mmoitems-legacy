// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.plugin.messaging;

public class ReservedChannelException extends RuntimeException
{
    public ReservedChannelException() {
        this("Attempted to register for a reserved channel name.");
    }
    
    public ReservedChannelException(final String name) {
        super("Attempted to register for a reserved channel name ('" + name + "')");
    }
}
