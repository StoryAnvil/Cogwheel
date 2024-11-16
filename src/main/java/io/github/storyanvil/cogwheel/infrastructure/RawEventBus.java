package io.github.storyanvil.cogwheel.infrastructure;

import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.StringRepresentableArgument;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class RawEventBus {
    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && event.getEntity() != null && event.getSource().getEntity() != null) {
            if (event.getSource().getEntity() instanceof Player p) {
                if (p.getMainHandItem() != null) {
                    if (ForgeRegistries.ITEMS.getKey(p.getMainHandItem().getItem()).toString().equals("storyanvil_cogwheel:pointer")) {
                        event.setCanceled(true);
                        event.getEntity().addEffect(new MobEffectInstance(MobEffects.GLOWING, 15, 1), event.getEntity());
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("cogwheel").requires(s -> s.hasPermission(3))
                .then(Commands.literal("npc")
                        .then(Commands.literal("set")
                                .then(Commands.literal("skin")
                                        .then(Commands.argument("_skin", StringArgumentType.string())
                                                .then(Commands.argument("uuid", EntityArgument.entity())
                                                        .executes(arguments -> {
                                                            Level level = arguments.getSource().getUnsidedLevel();
                                                            Entity entity = EntityArgument.getEntity(arguments, "uuid");
                                                            if (entity instanceof NPC npc) {
                                                                npc.setSkin(StringArgumentType.getString(arguments, "_skin"));
                                                            }
                                                            return 0;
                                                        })
                                                )
                                        )
                                )
                        )
                )
        );
    }
}
