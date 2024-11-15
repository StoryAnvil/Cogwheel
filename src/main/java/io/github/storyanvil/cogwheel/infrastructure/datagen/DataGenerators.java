package io.github.storyanvil.cogwheel.infrastructure.datagen;

import io.github.storyanvil.cogwheel.Cogwheel;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Cogwheel.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new RecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), LootTableGenerator.create(packOutput));

        generator.addProvider(event.includeClient(), new BlockState(packOutput, Cogwheel.modId, existingFileHelper));
        generator.addProvider(event.includeClient(), new ItemModel(packOutput, Cogwheel.modId, existingFileHelper));

        BlockTag blockTag = generator.addProvider(event.includeServer(), new BlockTag(packOutput, lookupProvider, Cogwheel.modId, existingFileHelper));
        generator.addProvider(event.includeServer(), new ItemTagGenerator(packOutput, lookupProvider, blockTag.contentsGetter(), Cogwheel.modId, existingFileHelper));
    }
}
