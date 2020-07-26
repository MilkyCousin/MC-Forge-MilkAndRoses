package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.common.entities.item.BulletEntity;
import mod.milkycousin.milkandroses.common.entities.item.ThrownOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class QueenRangedItem extends ItemBattleOrb
{
    private final float RANGED_ATTACK;
    private final ItemStack BULLET_ITEM;
    private final EffectInstance POTION_EFFECT;

    public QueenRangedItem(Properties properties, float rangedAttackValueIn, ItemStack itemIn, EffectInstance effectIn)
    {
        super(properties, rangedAttackValueIn);
        this.RANGED_ATTACK = rangedAttackValueIn;
        this.BULLET_ITEM = itemIn;
        this.POTION_EFFECT = effectIn;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack currentItem = playerIn.getHeldItem(handIn);

        worldIn.playSound(
                null,
                playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
                SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.PLAYERS,
                0.5F,
                0.4F / (random.nextFloat() * 0.4F + 0.8F)
        );
        if (!worldIn.isRemote) {
            SnowballEntity bulletEntity = new BulletEntity(worldIn, playerIn, this.RANGED_ATTACK, this.POTION_EFFECT);
            bulletEntity.setItem(this.BULLET_ITEM);
            bulletEntity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.0F, 0.75F);
            worldIn.addEntity(bulletEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            currentItem.damageItem(1, playerIn, (var) -> var.sendBreakAnimation(handIn));
        }

        playerIn.getCooldownTracker().setCooldown(currentItem.getItem(), 4);

        return ActionResult.resultSuccess(currentItem);
    }
}
