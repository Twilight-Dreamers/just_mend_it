package ru.twd.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.twd.Config;

import java.util.Map;


@Mixin(net.minecraft.entity.ExperienceOrbEntity.class)
public abstract class VanillaMendingOverrider {


    private float get_repairment(int amount)
    {
        float mpl, amt;
        mpl=Config.passive_repair_amount_multiplier; amt=(float)amount;
        return mpl*amt;
    }
    private int repair(ItemStack item, int amount)
    {
        float dmg,fix;
        dmg=item.getDamage();
        fix=Math.min(dmg,get_repairment(amount));
        item.setDamage((int)(dmg-fix));
        return (int)( (((float)amount)/ Config.passive_repair_cost_multiplier) - (fix*Config.passive_repair_cost_multiplier) );
    }



    @Inject(at = @At("HEAD"), method = "repairPlayerGears", cancellable = true)
    private void repairPlayerGears(PlayerEntity player, int amount, CallbackInfoReturnable info) {
        info.cancel();
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.MENDING, player, ItemStack::isDamaged);
        if (player.experienceLevel < Config.passive_repair_level || entry == null) info.setReturnValue(amount);
        else info.setReturnValue(repair(entry.getValue(), amount));
    }
}
