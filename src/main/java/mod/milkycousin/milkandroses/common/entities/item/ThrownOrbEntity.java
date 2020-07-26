package mod.milkycousin.milkandroses.common.entities.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ThrownOrbEntity extends SnowballEntity
{
    private float RANGED_ATTACK;

    public ThrownOrbEntity(World worldIn, LivingEntity throwerIn, float onImpactDamageIn)
    {
        super(worldIn, throwerIn);
        this.RANGED_ATTACK = onImpactDamageIn;
    }

    public ThrownOrbEntity(EntityType<ThrownOrbEntity> thrownOrbEntityEntityType, World world)
    {
        super(thrownOrbEntityEntityType, world);
    }

    // rewrite later
    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            entity.attackEntityFrom(DamageSource.causeThrownDamage(
                    this,
                    this.getThrower()),
                    this.RANGED_ATTACK
            );
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }

    }
}
