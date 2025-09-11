package ru.twd;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Common {
    public static final String MOD_ID = "just_mend_it";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ItemStack get_item(PlayerEntity player) { return player.getActiveItem(); }
    public static int get_damage(ItemStack item)
    {
        return item.getDamage();
    }
    public static float get_durability(ItemStack item)
    {
        return item.getItem().getMaxDamage();
    }
    public static float get_repair_amount(ItemStack item)
    {
        return ((0.01f* Config.repair_amount)*get_durability(item));
    }
    public static void repair(ItemStack item)
    {
        float dmg,rpr, diff; dmg = get_damage(item); rpr = get_repair_amount(item); diff = Math.max(dmg - rpr, 0);
        item.setDamage((int) diff);
    }



    public static float get_single_level_cost(float level)
    {
        level = Math.max(level, 0);
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else {
            return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
        }
    }
    public static float get_level_cost(float level)
    {
        float cost=0;
        for (float c=level; c>=0; c--) cost += get_single_level_cost(c);
        return cost;
    }
    public static float get_levels(float cost)
    {
        float level=0;
        while (cost > get_level_cost(level)) level++;
        return level-1;
    }
    public static float get_remainder_absolute(float cost, float level)
    {
        return cost - get_level_cost(level);
    }
    public static float get_remainder(float progress, float level)
    {
        return progress * get_single_level_cost(level);
    }
    public static float get_total(float progress, float level)
    {
        return get_level_cost(level) + get_remainder(progress, level);
    }
    public static float get_repair_cost(float level)
    {
        return (  (0.01f* Config.xp_cost) * get_single_level_cost(level)  );
    }
    public static float pay(PlayerEntity player)
    {
        ItemStack offhand = player.getOffHandStack();
        if(0 < Config.secondary_cost && Config.secondary_cost <= offhand.getCount()) offhand.decrement(Config.secondary_cost);

        float level,progress,remainder,base,balance,cost,new_balance,new_level,new_remainder,new_single_level_cost,new_progress;
        level = player.experienceLevel;
        progress = player.experienceProgress;

        remainder = get_remainder(progress, level);
        base = get_level_cost(level);

        balance = get_total(progress,level);
        cost = get_repair_cost(level);

        new_balance = balance - cost;
        new_level = get_levels(new_balance);
        new_remainder = get_remainder_absolute(new_balance, new_level);
        if (0 > new_remainder)
        {
            new_remainder = remainder;
            new_level = level;
        }

        new_single_level_cost = get_single_level_cost(new_level);
        new_progress = new_remainder/new_single_level_cost;

        player.experienceLevel = (int) new_level;
        player.experienceProgress = new_progress;

        return new_balance;
    }
    /** CALCULATION ENTRY POINT **/
    public static boolean mend(PlayerEntity player)
    {
        if (player.isSneaking() && is_payable(player)) {
            ItemStack item = player.getMainHandStack();
            if (is_fixable(item)) {
                pay(player);
                repair(item);
                play_sfx(player);
                return true;
            }
        }
        return false;
    }
    public static boolean is_payable(PlayerEntity player)
    {
        return has_offhand(player) && Config.level_requirement<=player.experienceLevel;
    }
    private static boolean has_offhand(PlayerEntity player)
    {
        if (0 > Config.secondary_cost) Config.secondary_cost=0;

        if (0 == Config.secondary_cost) return true;
        else {

            ItemStack stack = player.getOffHandStack();
            Item item = stack.getItem();

            String offhand, target;
            offhand = item.getTranslationKey().replace('.', ':');
            target = "item:" + Config.secondary_cost_type;

            int cost, amount;
            cost = Config.secondary_cost;
            amount = stack.getCount();

            boolean match, enough;
            match = Objects.equals(target, offhand);
            enough = amount > Config.secondary_cost;

            return match && enough;
        }
    }
    public static boolean is_fixable(ItemStack item)
    {
        if (item.isDamaged()) {
            int durability = (int) (100 * (1 - (((float) get_damage(item)) / ((float) item.getMaxDamage()))));
            return (0 < EnchantmentHelper.getLevel(Enchantments.MENDING, item) && durability < Config.repair_limit);
        }
        return false;
    }
    public static void play_sfx(PlayerEntity player)
    {
        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,0.75f,1f);
        player.playSound(SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH, 0.35f,1f);
    }
}
