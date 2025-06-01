package com.example.globalhealth;

import net.minecraftforge.common.ForgeConfigSpec;
import java.util.Arrays;
import java.util.List;

public class Config {
    public static final ForgeConfigSpec SPEC;
    
    public static ForgeConfigSpec.DoubleValue HEALTH_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> WHITELIST;
    public static ForgeConfigSpec.BooleanValue USE_WHITELIST_MODE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        builder.push("Global Health Settings");
        
        HEALTH_MULTIPLIER = builder
            .comment("Health multiplier for entities (e.g. 2.0 for double health)")
            .defineInRange("health_multiplier", 2.0, 0.1, 100.0);
        
        USE_WHITELIST_MODE = builder
            .comment("Set to true to use whitelist mode (only modify listed entities)")
            .define("use_whitelist_mode", false);
        
        BLACKLIST = builder
            .comment("Entities to exclude from health modification",
                     "Format: modid:entity_id (e.g. minecraft:zombie)")
            .defineList("blacklist", Arrays.asList(
                "minecraft:ender_dragon",
                "minecraft:wither"
            ), o -> o instanceof String);
        
        WHITELIST = builder
            .comment("Entities to include in health modification (only if whitelist mode is enabled)",
                     "Format: modid:entity_id (e.g. minecraft:creeper)")
            .defineList("whitelist", Arrays.asList(
                "minecraft:zombie",
                "minecraft:skeleton"
            ), o -> o instanceof String);
        
        builder.pop();
        SPEC = builder.build();
    }
}