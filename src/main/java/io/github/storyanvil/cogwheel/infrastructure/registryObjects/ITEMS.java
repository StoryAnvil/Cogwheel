package io.github.storyanvil.cogwheel.infrastructure.registryObjects;

import io.github.storyanvil.cogwheel.Cogwheel;
import io.github.storyanvil.cogwheel.scripts.item.Pointer;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ITEMS {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Cogwheel.modId);

    public static final RegistryObject<Item> pointer = ITEMS.register("pointer",
            () -> new Pointer(new Item.Properties()));

    public static final RegistryObject<Item> NPC_FOLLOW = ITEMS.register("npc_follow_wand",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
