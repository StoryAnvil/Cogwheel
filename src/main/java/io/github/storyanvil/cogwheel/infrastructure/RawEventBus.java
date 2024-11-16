package io.github.storyanvil.cogwheel.infrastructure;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.storyanvil.cogwheel.Cogwheel;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

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
                .then(Commands.literal("msg")
                        .then(Commands.literal("dialog")
                                .then(Commands.argument("delay", IntegerArgumentType.integer(1))
                                        .then(Commands.argument("sender", StringArgumentType.string())
                                                .then(Commands.argument("message", StringArgumentType.string())
                                                        .executes(arguments -> {
                                                            Level level = arguments.getSource().getUnsidedLevel();
                                                            String sender = StringArgumentType.getString(arguments, "sender");
                                                            String message = StringArgumentType.getString(arguments, "message");
                                                            Cogwheel.queueServerWork(IntegerArgumentType.getInteger(arguments, "delay"), () -> {
                                                                level.players().forEach(player -> {
                                                                    player.sendSystemMessage(
                                                                            Component.translatable(sender)
                                                                                    .withStyle(style -> style.withColor(0x007594))
                                                                                    .append(
                                                                                            Component.translatable(message).withStyle(style -> style.withColor(0xFFFFFF))
                                                                                    ));
                                                                });
                                                            });
                                                            return 0;
                                                        })
                                                )
                                        )
                                )
                                .then(Commands.literal("choice")
                                        .then(Commands.argument("user_message", StringArgumentType.string())
                                                .then(Commands.argument("callback", StringArgumentType.string())
                                                        .executes(arguments -> {
                                                            if (Cogwheel.chatMsgCallbacks == null)
                                                                Cogwheel.chatMsgCallbacks = new HashMap<>();
                                                            Cogwheel.chatMsgCallbacks.put(
                                                                    StringArgumentType.getString(arguments, "user_message"),
                                                                    StringArgumentType.getString(arguments, "callback")
                                                            );
                                                            return 0;
                                                        })
                                                )
                                        )
                                )
                        )
                )
                .then(Commands.literal("multi")
                        .then(Commands.argument("cmd", StringArgumentType.string())
                                .executes(arguments -> {
                                    String[] cmd = StringArgumentType.getString(arguments, "cmd").split("\\|\\|");

                                    for (String c : cmd) {
                                        ServerLevel _level = arguments.getSource().getLevel();
                                        _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(0, 0, 0), Vec2.ZERO, (ServerLevel) _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                                                c);
                                    }
                                    return 0;
                                })
                        )
                )
        );
    }

    @SubscribeEvent
    public static void chatMessage(ServerChatEvent event) {
        try {
            if (Cogwheel.chatMsgCallbacks != null) {
                String msg = event.getRawText();
                for (String key : Cogwheel.chatMsgCallbacks.keySet()) {
                    if (msg.equals(Component.translatable(key).getString())) {
                        Level _level = event.getPlayer().level();
                        _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(0, 0, 0), Vec2.ZERO, (ServerLevel) _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                                "/function " + Cogwheel.chatMsgCallbacks.get(key));
                    }
                }
                Cogwheel.chatMsgCallbacks = null;
            }
        } catch (Exception e) {
            Cogwheel.LOGGER.error(e.toString());
        }
    }
}
