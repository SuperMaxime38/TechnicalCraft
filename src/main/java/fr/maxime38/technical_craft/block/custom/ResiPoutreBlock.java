package fr.maxime38.technical_craft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ResiPoutreBlock extends Block {
    public ResiPoutreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        if(entity instanceof LivingEntity lEntity) {
            lEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 1));
        }

        super.stepOn(level, pos, state, entity);
    }
}
