package mod.milkycousin.milkandroses.common.entities.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BulletEntity extends SnowballEntity
{
    private float RANGED_ATTACK;
    private EffectInstance POTION_EFFECT;
    private int EFFECT_AMPLIFIER;
    private float EFFECT_DURATION;

    public BulletEntity(World worldIn, LivingEntity throwerIn, float onImpactDamageIn, EffectInstance effectInstaanceIn)
    {
        super(worldIn, throwerIn);
        this.RANGED_ATTACK = onImpactDamageIn;
        this.POTION_EFFECT = effectInstaanceIn;
    }

    public BulletEntity(EntityType<BulletEntity> bulletEntityEntityType, World world)
    {
        super(bulletEntityEntityType, world);
    }

    protected void onImpact(RayTraceResult result)
    {
        if (result.getType() == RayTraceResult.Type.ENTITY)
        {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), RANGED_ATTACK);
            if(entity instanceof LivingEntity)
            {
                ((LivingEntity)entity).addPotionEffect(POTION_EFFECT);
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }

    }
}
