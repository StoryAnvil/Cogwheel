package io.github.storyanvil.cogwheel.infrastructure.StoryWorks;

import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.npcactions.Chat;
import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.npcactions.Function;
import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.npcactions.GoTo;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public abstract class NPCAction {
    public abstract void execute(NPC myself);
    public int postDelay = 0;
    public static NPCAction convert(Tag _nbt) throws NPCActionConversionFailedException {
        if (_nbt instanceof CompoundTag nbt) {
            _throw(nbt, "type");

            NPCAction action = null;
            switch (nbt.getString("type")) {
                case "CHAT": {
                    _throw(nbt, "msg");
                    Chat chat = new Chat();
                    chat.message = Component.translatable(nbt.getString("msg"));
                    action = chat;
                    break;
                }
                case "GO": {
                    _throw(nbt, "x");
                    _throw(nbt, "y");
                    _throw(nbt, "z");
                    GoTo goTo = new GoTo();
                    goTo.x = nbt.getDouble("x");
                    goTo.y = nbt.getDouble("y");
                    goTo.z = nbt.getDouble("z");
                    action = goTo;
                    break;
                }
                case "FUN": {
                    _throw(nbt, "fname");
                    Function f = new Function();
                    f.function = nbt.getString("fname");
                    action = f;
                    break;
                }
            }
            if (action != null) {
                if (nbt.contains("delay", Tag.TAG_INT)) {
                    action.postDelay = nbt.getInt("delay");
                }
            }
            return action;
        } else {
            throw new NPCActionConversionFailedException(Component.literal("Action required to be compound tag"));
        }
    }
    private static void _throw(CompoundTag tag, String key) throws NPCActionConversionFailedException {
        if (!tag.contains(key)) {
            throw new NPCActionConversionFailedException(Component.literal("Action required to have \"" + key + "\" tag"));
        }
    }
    public boolean amIFreeToGo(NPC myself) {
        return true;
    }
}
