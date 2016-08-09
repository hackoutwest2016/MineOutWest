package com.spotify.mineoutwest.block;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 2016-08-09.
 */
public class ArtistCommand implements ICommand {

    private List aliases;

    public ArtistCommand() {
        this.aliases = new ArrayList();
        this.aliases.add("spawnArtists");
        this.aliases.add("spA");
    }

    @Override
    public String getCommandName() {
        return "spawnArtists";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "spawnArtists";
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length != 0) {
            iCommandSender.addChatMessage(new TextComponentString("Invalid arguments"));
            return;
        }

        World world = iCommandSender.getEntityWorld();
        BlockPos worldSpawnPoint = new BlockPos(126, 68, 112);

        EntityVillager villager = new EntityVillager(world);
        villager.setLocationAndAngles(worldSpawnPoint.getX(), worldSpawnPoint.getY(), worldSpawnPoint.getZ(), 30, 30);

        world.spawnEntityInWorld(villager);

        iCommandSender.addChatMessage(new TextComponentString("Artist spawned at " + (worldSpawnPoint.getX() - 2) + ", " + worldSpawnPoint.getY() + ", " + worldSpawnPoint.getZ()));
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

