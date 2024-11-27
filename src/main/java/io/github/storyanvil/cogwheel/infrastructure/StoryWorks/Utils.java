package io.github.storyanvil.cogwheel.infrastructure.StoryWorks;

import io.github.storyanvil.cogwheel.Cogwheel;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class Utils {
    public static void setEntityData(Entity entity, String key, String value) {
        entity.getPersistentData().putString(key, value);
    }
    public static String getEntityData(Entity entity, String key) {
        Cogwheel.queueServerWork(1, () -> Cogwheel.LOGGER.error(entity.getPersistentData().getString(key)));
        return entity.getPersistentData().getString(key);
    }
    public static void runCommand(ServerLevel _level, String command) {
        _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(0, 0, 0), Vec2.ZERO, (ServerLevel) _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                command);
    }
    public static <T> T requireNonNull(T obj) {
        if (obj == null) {
            Cogwheel.LOGGER.error("Disallowed null detected!");
            throw new NullPointerException();
        }
        return obj;
    }
}
