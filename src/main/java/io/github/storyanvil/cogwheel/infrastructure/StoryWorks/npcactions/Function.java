package io.github.storyanvil.cogwheel.infrastructure.StoryWorks.npcactions;

import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.NPCAction;
import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.Utils;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;

public class Function extends NPCAction {
    public String function;
    @Override
    public void execute(NPC myself) {
        Utils.runCommand(Utils.requireNonNull(myself.level().getServer()).overworld(), "function " + function);
    }
}
