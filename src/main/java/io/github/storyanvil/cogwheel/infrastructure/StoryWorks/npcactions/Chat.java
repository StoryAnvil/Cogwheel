package io.github.storyanvil.cogwheel.infrastructure.StoryWorks.npcactions;

import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.NPCAction;
import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.Utils;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;
import net.minecraft.network.chat.Component;

public class Chat extends NPCAction {
    public Component message;
    @Override
    public void execute(NPC myself) {
        Utils.requireNonNull(myself.level().getServer()).getPlayerList().broadcastSystemMessage(message, false);
    }
}
