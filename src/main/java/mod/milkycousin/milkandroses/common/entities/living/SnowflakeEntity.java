package mod.milkycousin.milkandroses.common.entities.living;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;

public class SnowflakeEntity extends MobEntity
{
    public SnowflakeEntity(EntityType<? extends MobEntity> type, World worldIn)
    {
        super(type, worldIn);
    }
}
