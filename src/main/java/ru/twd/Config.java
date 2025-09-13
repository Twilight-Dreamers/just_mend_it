package ru.twd;
import eu.midnightdust.lib.config.MidnightConfig;
public class Config extends MidnightConfig {
    public static final String MAIN_COST = "main_cost";
    public static final String SECONDARY_COST = "secondary_cost";
    public static final String REPAIRMENTS = "repairment_attributes";
    public static final String VANILLA = "vanilla_behaviour";

    @Entry(category = MAIN_COST, name = "Cost [ % ]:") public static int xp_cost = 150;
    @Entry(category = MAIN_COST, name = "Unlock Level:", min = 2, max = 50, isSlider=true) public static int level_requirement = 14;

    //todo
    @Entry(category = SECONDARY_COST, name = "Item:") public static String secondary_cost_type = "minecraft:lapis_lazuli";
    @Entry(category = SECONDARY_COST, name = "Amount:", min = 0, max = 64, isSlider = true) public static int secondary_cost = 12;

    @Entry(category = REPAIRMENTS, name = "Amount [ % ]:", min = 1, max = 100, isSlider=true) public static int repair_amount = 10;
    @Entry(category = REPAIRMENTS, name = "Limit [ % ]:", min = 1, max = 100, isSlider=true) public static int repair_limit = 40;

    @Entry(category = VANILLA, name = "Unlock Level:", min = 0) public static int passive_repair_level = 25;
    @Entry(category = VANILLA, name = "Amount Multiplier:", min = 0.0f) public static float passive_repair_amount_multiplier = 0.0025f;
    @Entry(category = VANILLA, name = "Cost Multiplier:", min = 1.0f) public static float passive_repair_cost_multiplier = 1.0f;
    @Entry(category = VANILLA, name = "Repair Limit [ % ]:", min = 1, max = 100, isSlider = true) public static int passive_repair_limit = 40;
}
