package io.github.storyanvil.cogwheel.infrastructure.datagen;

import io.github.storyanvil.cogwheel.infrastructure.registryObjects.ITEMS;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModel extends ItemModelProvider {
    public ItemModel(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // simpleItem(<item>)

        simpleItem(ITEMS.pointer);
        simpleItem(ITEMS.NPC_FOLLOW);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0",
                new ResourceLocation(modid, "item/" + item.getId().getPath()));
    }
}
