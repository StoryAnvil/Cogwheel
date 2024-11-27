package io.github.storyanvil.cogwheel.infrastructure.StoryWorks;

import io.github.storyanvil.cogwheel.Cogwheel;
import net.minecraft.network.chat.Component;

public class NPCActionConversionFailedException extends Exception {
    public NPCActionConversionFailedException(Component component) {
        super(component.getString());
        Cogwheel.LOGGER.error("Failed to add npc action: " + component.getString());
    }
}
