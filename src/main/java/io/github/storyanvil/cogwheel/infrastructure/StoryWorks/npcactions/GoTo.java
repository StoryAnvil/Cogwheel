package io.github.storyanvil.cogwheel.infrastructure.StoryWorks.npcactions;

import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.NPCAction;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;

public class GoTo extends NPCAction {
    public double x;
    public double y;
    public double z;
    @Override
    public void execute(NPC myself) {
        PathNavigation navigation = myself.getNavigation();
        Path path = navigation.createPath(x, y, z, 0);
        myself.getNavigation().moveTo(path, 2);
        myself.setAllowNextAction(false);
    }

    @Override
    public boolean amIFreeToGo(NPC myself) {
        return myself.getNavigation().isDone();
    }
}
