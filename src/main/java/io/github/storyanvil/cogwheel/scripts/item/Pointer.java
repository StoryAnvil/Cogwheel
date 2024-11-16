package io.github.storyanvil.cogwheel.scripts.item;

import io.github.storyanvil.cogwheel.Cogwheel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

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

            level.destroyBlockProgress(-1545, pos, 8);

            Cogwheel.queueServerWork(10, () -> {
                level.destroyBlockProgress(-1545, pos, -1);
            });
        }

        return InteractionResult.SUCCESS;
    }


}
