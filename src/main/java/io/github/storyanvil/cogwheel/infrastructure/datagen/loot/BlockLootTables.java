package io.github.storyanvil.cogwheel.infrastructure.datagen.loot;

import io.github.storyanvil.cogwheel.infrastructure.registryObjects.BLOCKS;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    // Ores: https://youtu.be/enzKJWq0vNI?list=PLKGarocXCE1H9Y21-pxjt5Pt8bW14twa-&t=710

    public BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        //this.dropSelf(BLOCKS.SAPPHIRE_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BLOCKS.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
