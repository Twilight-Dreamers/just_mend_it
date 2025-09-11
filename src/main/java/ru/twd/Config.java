package ru.twd;
import eu.midnightdust.lib.config.MidnightConfig;
public class Config extends MidnightConfig {
    public static final String MAIN_COST = "Main Cost";
    public static final String SECONDARY_COST = "Secondary Cost";
    public static final String REPAIRMENTS = "Repairments";


    @Entry(category = MAIN_COST, name = "XP Cost [%]:") public static int xp_cost = 150;
    @Entry(category = MAIN_COST, name = "LV Requirement [Quantity]:", min = 2, max = 50, isSlider=true) public static int level_requirement = 14;

    @Entry(category = SECONDARY_COST, name = "Item [Type]:") public static String secondary_cost_type = "minecraft:lapis_lazuli";
    @Entry(category = SECONDARY_COST, name = "Amount [%]:", min = 0, max = 100, isSlider = true) public static int secondary_cost = 12;

    @Entry(category = REPAIRMENTS, name = "Durability to Restore [%]:") public static int restored_durability = 10;
    @Entry(category = REPAIRMENTS, name = "Minimum Damage to Repair [%]:", min = 1, max = 99, isSlider=true) public static int minimum_damage = 75;
}
