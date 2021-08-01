// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import javax.annotation.Nullable;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ConfigFile;
import net.Indyuce.mmoitems.comp.mythicmobs.crafting.MythicMobsSkillTrigger;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.MMOItemPlayerIngredient;
import net.Indyuce.mmoitems.api.crafting.ingredient.MMOItemIngredient;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.PlayerIngredient;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.VanillaPlayerIngredient;
import net.Indyuce.mmoitems.api.crafting.ingredient.VanillaIngredient;
import net.Indyuce.mmoitems.api.crafting.trigger.MMOItemTrigger;
import net.Indyuce.mmoitems.api.crafting.trigger.VanillaTrigger;
import net.Indyuce.mmoitems.api.crafting.trigger.SoundTrigger;
import net.Indyuce.mmoitems.api.crafting.trigger.MessageTrigger;
import net.Indyuce.mmoitems.api.crafting.trigger.CommandTrigger;
import net.Indyuce.mmoitems.api.crafting.condition.ClassCondition;
import net.Indyuce.mmoitems.api.crafting.condition.FoodCondition;
import net.Indyuce.mmoitems.api.crafting.condition.StaminaCondition;
import net.Indyuce.mmoitems.api.crafting.condition.ManaCondition;
import net.Indyuce.mmoitems.api.crafting.condition.PlaceholderCondition;
import net.Indyuce.mmoitems.api.crafting.condition.PermissionCondition;
import io.lumine.mythic.lib.api.MMOLineConfig;
import java.util.function.Function;
import net.Indyuce.mmoitems.api.crafting.ConditionalDisplay;
import net.Indyuce.mmoitems.api.crafting.condition.LevelCondition;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.crafting.CraftingStation;
import java.util.Map;
import net.Indyuce.mmoitems.api.crafting.trigger.Trigger;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;
import net.Indyuce.mmoitems.api.crafting.LoadedCraftingObject;
import java.util.Set;
import net.Indyuce.mmoitems.api.crafting.ingredient.IngredientType;
import java.util.List;

public class CraftingManager implements Reloadable
{
    private final List<net.Indyuce.mmoitems.api.crafting.ingredient.IngredientType> ingredients;
    private final Set<LoadedCraftingObject<Condition>> conditions;
    private final Set<LoadedCraftingObject<Trigger>> triggers;
    private final Map<String, CraftingStation> stations;
    
    public CraftingManager() {
        this.ingredients = new ArrayList<net.Indyuce.mmoitems.api.crafting.ingredient.IngredientType>();
        this.conditions = new HashSet<LoadedCraftingObject<Condition>>();
        this.triggers = new HashSet<LoadedCraftingObject<Trigger>>();
        this.stations = new HashMap<String, CraftingStation>();
        this.registerCondition("level", (Function<MMOLineConfig, Condition>)LevelCondition::new, new ConditionalDisplay("&a\u2714 Requires Level #level#", "&c\u2716 Requires Level #level#"));
        this.registerCondition("permission", (Function<MMOLineConfig, Condition>)PermissionCondition::new, null);
        this.registerCondition("placeholder", (Function<MMOLineConfig, Condition>)PlaceholderCondition::new, null);
        this.registerCondition("mana", (Function<MMOLineConfig, Condition>)ManaCondition::new, new ConditionalDisplay("&a\u2714 Requires #mana# Mana", "&c\u2716 Requires #mana# Mana"));
        this.registerCondition("stamina", (Function<MMOLineConfig, Condition>)StaminaCondition::new, new ConditionalDisplay("&a\u2714 Requires #stamina# Stamina", "&c\u2716 Requires #stamina# Stamina"));
        this.registerCondition("food", (Function<MMOLineConfig, Condition>)FoodCondition::new, new ConditionalDisplay("&a\u2714 Requires #food# Food", "&c\u2716 Requires #food# Food"));
        this.registerCondition("class", (Function<MMOLineConfig, Condition>)ClassCondition::new, new ConditionalDisplay("&a\u2714 Required Class: #class#", "&c\u2716 Required Class: #class#"));
        this.registerTrigger("command", (Function<MMOLineConfig, Trigger>)CommandTrigger::new);
        this.registerTrigger("message", (Function<MMOLineConfig, Trigger>)MessageTrigger::new);
        this.registerTrigger("sound", (Function<MMOLineConfig, Trigger>)SoundTrigger::new);
        this.registerTrigger("vanilla", (Function<MMOLineConfig, Trigger>)VanillaTrigger::new);
        this.registerTrigger("mmoitem", (Function<MMOLineConfig, Trigger>)MMOItemTrigger::new);
        this.registerIngredient("vanilla", (Function<MMOLineConfig, Ingredient>)VanillaIngredient::new, new ConditionalDisplay("&8\u2714 &7#amount# #item#", "&c\u2716 &7#amount# #item#"), p0 -> true, (Function<NBTItem, PlayerIngredient>)VanillaPlayerIngredient::new);
        this.registerIngredient("mmoitem", (Function<MMOLineConfig, Ingredient>)MMOItemIngredient::new, new ConditionalDisplay("&8\u2714 &7#amount# #level##item#", "&c\u2716 &7#amount# #level##item#"), NBTItem::hasType, (Function<NBTItem, PlayerIngredient>)MMOItemPlayerIngredient::new);
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            this.registerTrigger("mmskill", (Function<MMOLineConfig, Trigger>)MythicMobsSkillTrigger::new);
        }
    }
    
    @Override
    public void reload() {
        this.stations.clear();
        final ConfigFile configFile = new ConfigFile("/language", "crafting-stations");
        for (final LoadedCraftingObject<Condition> loadedCraftingObject : this.getConditions()) {
            if (loadedCraftingObject.hasDisplay()) {
                final String string = "condition." + loadedCraftingObject.getId();
                if (!configFile.getConfig().contains(string)) {
                    configFile.getConfig().createSection(string);
                    loadedCraftingObject.getDisplay().setup(configFile.getConfig().getConfigurationSection(string));
                }
                loadedCraftingObject.setDisplay(new ConditionalDisplay(configFile.getConfig().getConfigurationSection(string)));
            }
        }
        for (final net.Indyuce.mmoitems.api.crafting.ingredient.IngredientType ingredientType : this.getIngredients()) {
            final String string2 = "ingredient." + ingredientType.getId();
            if (!configFile.getConfig().contains(string2)) {
                configFile.getConfig().createSection(string2);
                ingredientType.getDisplay().setup(configFile.getConfig().getConfigurationSection(string2));
            }
            ingredientType.setDisplay(new ConditionalDisplay(configFile.getConfig().getConfigurationSection(string2)));
        }
        configFile.save();
        for (final File file : new File(MMOItems.plugin.getDataFolder() + "/crafting-stations").listFiles()) {
            try {
                final CraftingStation craftingStation = new CraftingStation(file.getName().substring(0, file.getName().length() - 4), (FileConfiguration)YamlConfiguration.loadConfiguration(file));
                this.stations.put(craftingStation.getId(), craftingStation);
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load station '" + file.getName() + "': " + ex.getMessage());
            }
        }
        for (final CraftingStation craftingStation2 : this.stations.values()) {
            try {
                craftingStation2.postLoad();
            }
            catch (IllegalArgumentException ex2) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not post-load station '" + craftingStation2.getId() + "': " + ex2.getMessage());
            }
        }
    }
    
    public int countRecipes() {
        int n = 0;
        final Iterator<CraftingStation> iterator = this.stations.values().iterator();
        while (iterator.hasNext()) {
            n += iterator.next().getRecipes().size();
        }
        return n;
    }
    
    public boolean hasStation(final String s) {
        return this.stations.containsKey(s);
    }
    
    public Collection<CraftingStation> getStations() {
        return this.stations.values();
    }
    
    public CraftingStation getStation(final String s) {
        return this.stations.get(s);
    }
    
    @NotNull
    public Ingredient getIngredient(final MMOLineConfig mmoLineConfig) {
        final String key = mmoLineConfig.getKey();
        for (final net.Indyuce.mmoitems.api.crafting.ingredient.IngredientType ingredientType : this.ingredients) {
            if (ingredientType.getId().equals(key)) {
                return ingredientType.load(mmoLineConfig);
            }
        }
        throw new IllegalArgumentException("Could not match ingredient");
    }
    
    @NotNull
    public Condition getCondition(final MMOLineConfig mmoLineConfig) {
        final String key = mmoLineConfig.getKey();
        for (final LoadedCraftingObject<Condition> loadedCraftingObject : this.conditions) {
            if (loadedCraftingObject.getId().equalsIgnoreCase(key)) {
                return loadedCraftingObject.load(mmoLineConfig);
            }
        }
        throw new IllegalArgumentException("Could not match condition");
    }
    
    @NotNull
    public Trigger getTrigger(final MMOLineConfig mmoLineConfig) {
        final String key = mmoLineConfig.getKey();
        for (final LoadedCraftingObject<Trigger> loadedCraftingObject : this.triggers) {
            if (loadedCraftingObject.getId().equalsIgnoreCase(key)) {
                return loadedCraftingObject.load(mmoLineConfig);
            }
        }
        throw new IllegalArgumentException("Could not match trigger");
    }
    
    public List<net.Indyuce.mmoitems.api.crafting.ingredient.IngredientType> getIngredients() {
        return this.ingredients;
    }
    
    public Set<LoadedCraftingObject<Condition>> getConditions() {
        return this.conditions;
    }
    
    public Set<LoadedCraftingObject<Trigger>> getTriggers() {
        return this.triggers;
    }
    
    public void registerIngredient(final String s, final Function<MMOLineConfig, Ingredient> function, final ConditionalDisplay conditionalDisplay, final Predicate<NBTItem> predicate, final Function<NBTItem, PlayerIngredient> function2) {
        this.ingredients.add(0, new net.Indyuce.mmoitems.api.crafting.ingredient.IngredientType(s, function, conditionalDisplay, predicate, function2));
    }
    
    public void registerCondition(final String s, final Function<MMOLineConfig, Condition> function, @Nullable final ConditionalDisplay conditionalDisplay) {
        this.conditions.add(new LoadedCraftingObject<Condition>(s, function, conditionalDisplay));
    }
    
    public void registerTrigger(final String s, final Function<MMOLineConfig, Trigger> function) {
        this.triggers.add(new LoadedCraftingObject<Trigger>(s, function, null));
    }
    
    public Collection<CraftingStation> getAll() {
        return this.stations.values();
    }
    
    public static class LoadedObject<C>
    {
        private final String id;
        private final Function<MMOLineConfig, C> function;
        private ConditionalDisplay display;
        
        public LoadedObject(final String id, final Function<MMOLineConfig, C> function, final ConditionalDisplay display) {
            this.id = id;
            this.function = function;
            this.display = display;
        }
        
        public String getId() {
            return this.id;
        }
        
        public void setDisplay(final ConditionalDisplay display) {
            this.display = display;
        }
        
        public boolean hasDisplay() {
            return this.display != null;
        }
        
        public ConditionalDisplay getDisplay() {
            return this.display;
        }
        
        public C load(final MMOLineConfig mmoLineConfig) {
            return this.function.apply(mmoLineConfig);
        }
    }
    
    public static class IngredientType extends LoadedObject<Ingredient>
    {
        private final Predicate<NBTItem> check;
        private final Function<NBTItem, String> read;
        
        public IngredientType(final String s, final Function<MMOLineConfig, Ingredient> function, final ConditionalDisplay conditionalDisplay, final Predicate<NBTItem> check, final Function<NBTItem, String> read) {
            super(s, function, conditionalDisplay);
            this.check = check;
            this.read = read;
        }
        
        public boolean check(final NBTItem nbtItem) {
            return this.check.test(nbtItem);
        }
        
        public String readKey(final NBTItem nbtItem) {
            return this.read.apply(nbtItem);
        }
    }
}