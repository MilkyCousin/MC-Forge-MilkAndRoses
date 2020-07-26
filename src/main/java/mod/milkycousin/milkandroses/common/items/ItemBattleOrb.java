package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.common.entities.item.ThrownOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemBattleOrb extends Item
{
    private final float RANGED_ATTACK;

    public ItemBattleOrb(Properties properties, float rangedAttackValueIn)
    {
        super(properties);
        this.RANGED_ATTACK = rangedAttackValueIn;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack currentItem = playerIn.getHeldItem(handIn);

        worldIn.playSound(
                null,
                playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
                SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS,
                0.5F,
                0.4F / (random.nextFloat() * 0.4F + 0.8F)
        );
        if (!worldIn.isRemote) {
            SnowballEntity thrownOrbEntity = new ThrownOrbEntity(worldIn, playerIn, this.RANGED_ATTACK);
            thrownOrbEntity.setItem(currentItem);
            thrownOrbEntity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(thrownOrbEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            currentItem.damageItem(1, playerIn, (var) -> var.sendBreakAnimation(handIn));
        }

        playerIn.getCooldownTracker().setCooldown(currentItem.getItem(), 16);

        return ActionResult.resultSuccess(currentItem);
    }
}
