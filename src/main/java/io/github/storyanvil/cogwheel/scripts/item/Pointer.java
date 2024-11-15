package io.github.storyanvil.cogwheel.scripts.item;

import io.github.storyanvil.cogwheel.Cogwheel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Pointer extends Item {
    public Pointer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            BlockPos pos = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            BlockState blockState = pContext.getLevel().getBlockState(pos);
            Level level = pContext.getLevel();

            level.destroyBlockProgress(-1848, pos, 8);

            Cogwheel.queueServerWork(10, () -> {
                level.destroyBlockProgress(-1848, pos, -1);
            });
        }

        return InteractionResult.SUCCESS;
    }
}
