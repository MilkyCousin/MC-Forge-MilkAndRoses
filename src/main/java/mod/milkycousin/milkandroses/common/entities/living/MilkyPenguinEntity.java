package mod.milkycousin.milkandroses.common.entities.living;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MilkyPenguinEntity extends AnimalEntity
{
    final protected double MovementSpeed = 0.4D;
    final protected double EntityMaxHealth = 8.0D;
    final protected float EntityLookAtDistance = 8.0F;

    public MilkyPenguinEntity(EntityType<? extends AnimalEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public MilkyPenguinEntity createChild(AgeableEntity ageable)
    {
        MilkyPenguinEntity penguinEntity = ModEventSubscriber.ModEntities.PENGUIN.get().create(this.world);
        return penguinEntity;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target)
    {
        return new ItemStack(ModEventSubscriber.ModItems.PENGUIN_SPAWN_EGG.get());
    }

    @Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.SALMON;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn)
    {
        return this.isChild() ? 0.25F : 0.6F;
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PolarBearEntity.class, 8.0F, MovementSpeed, MovementSpeed * 1.5D));
        this.goalSelector.addGoal(1, new PanicGoal(this, MovementSpeed * 1.5D));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, MovementSpeed));
        this.goalSelector.addGoal(2, new BreedGoal(this, MovementSpeed));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, MovementSpeed));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, MovementSpeed, 50));
        this.goalSelector.addGoal(2, new TemptGoal(this, MovementSpeed, Ingredient.fromItems(Items.SALMON), false));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, EntityLookAtDistance));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(EntityMaxHealth);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MovementSpeed);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return this.onGround && !this.inWater ? ModEventSubscriber.Sounds.PENGUIN_AMBIENT_SOUND.get() : super.getAmbientSound();
    }
}
