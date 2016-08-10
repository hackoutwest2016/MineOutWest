package com.mineoutwest.speaker;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.mineoutwest.Stages;
import com.mineoutwest.block.Actions;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class MusicCommand implements ICommand {

    private List aliases;

    public MusicCommand() {
        this.aliases = new ArrayList();
        this.aliases.add("music");
    }

    @Override
    public String getCommandName() {
        return "music";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "music id stage";
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length != 2) {
            iCommandSender.addChatMessage(new TextComponentString("Invalid arguments"));
            return;
        }

        World world = iCommandSender.getEntityWorld();
        String id = strings[0].toLowerCase();
        String stage = strings[1].toLowerCase();

        BlockPos pos = null;
        if ("linne".equals(stage)) {
            pos = Stages.LINNE.getCenter();
        } else if ("azalea".equals(stage)) {
            pos = Stages.AZALEA.getCenter();
        } else if ("flamingo".equals(stage)) {
            pos = Stages.FLAMINGO.getCenter();
        } else if ("dungen".equals(stage)) {
            pos = Stages.DUNGEN.getCenter();
        } else {
            iCommandSender.addChatMessage(new TextComponentString("Unknown stage: " + stage));
        }

        Sounds.ScheduledSound soundToPlay = null;
        for (Sounds.ScheduledSound sound : Sounds.SCHEDULE) {
            System.out.println(id + " - " + sound.getEvent().getRegistryName() + " - " + id.equals(sound.getEvent().getRegistryName().toString()));
            if (id.equals(sound.getEvent().getRegistryName().toString())) {
                soundToPlay = sound;
            }
        }
        if (soundToPlay == null) {
            iCommandSender.addChatMessage(new TextComponentString("Unknown music ID: " + id));
        }
        if (pos != null && soundToPlay != null) {
            world.playSound(null, pos, soundToPlay.getEvent(), SoundCategory.MASTER, 5, 1);
            iCommandSender.addChatMessage(new TextComponentString("Played sound."));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer minecraftServer, ICommandSender iCommandSender) {
        return true;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings, @Nullable BlockPos blockPos) {
        return null;
    }


    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}

