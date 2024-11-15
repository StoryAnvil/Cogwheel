package io.github.storyanvil.cogwheel.infrastructure;

import io.github.storyanvil.cogwheel.Cogwheel;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Cogwheel.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    //public static final ForgeConfigSpec.ConfigValue<String> TOKEN = BUILDER
    //        .comment("DO NOT USE THIS FIELD. IT DOES STUFF WITH SOME HARDCODED VALUES!")
    //        .define("token", "1");

    static final ForgeConfigSpec SPEC = BUILDER.build();
    //public static String token;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        //token = TOKEN.get();
    }
}
