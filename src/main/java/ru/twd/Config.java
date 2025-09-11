package ru.twd;
import eu.midnightdust.lib.config.MidnightConfig;
public class Config extends MidnightConfig {
    public static final String MAIN_COST = "main_cost";
    public static final String SECONDARY_COST = "secondary_cost";
    public static final String REPAIRMENTS = "repairment_attributes";

    @Entry(category = MAIN_COST, name = "XP Cost [ % ]:") public static int xp_cost = 150;
    @Entry(category = MAIN_COST, name = "LV Requirement:", min = 2, max = 50, isSlider=true) public static int level_requirement = 14;

    //todo
    @Entry(category = SECONDARY_COST, name = "Item:") public static String secondary_cost_type = "minecraft:lapis_lazuli";
    @Entry(category = SECONDARY_COST, name = "Amount:", min = 0, max = 64, isSlider = true) public static int secondary_cost = 12;

    @Entry(category = REPAIRMENTS, name = "Repair Amount [ % ]:", min = 1, max = 100, isSlider=true) public static int repair_amount = 10;
    @Entry(category = REPAIRMENTS, name = "Repair Limit [ % ]:", min = 1, max = 100, isSlider=true) public static int repair_limit = 25;
}
