package com.mineoutwest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mineoutwest.Stages;
import com.mineoutwest.speaker.Sounds;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


public class WowCommand implements ICommand {

    private List aliases;
    public List<Sounds.ScheduledSound> concerts;

    public WowCommand() {

      // copy paste
      JsonParser jsonParser = new JsonParser();
      JsonElement element = null;
      try {
        element = jsonParser.parse(new InputStreamReader(this.getClass()
                                                             .getClassLoader()
                                                             .getResource("events.json")
                                                             .openStream()));
      } catch (IOException e) {
        throw new RuntimeException("Error loading event config", e);
      }

      JsonArray array = element.getAsJsonArray();

      List<Sounds.ScheduledSound> out = Lists.newArrayList();
      for (JsonElement e : array) {
        JsonObject o = (JsonObject) e;

        long tick = o.get("tick").getAsLong();
        String soundId = o.get("id").getAsString();
        String stage = o.get("stage").getAsString();
        String artist = o.get("artist").getAsString();

        BlockPos pos;
        if (stage.startsWith("Linn")) {
          pos = Stages.LINNE.crowd.getMidPoint();
        } else if ("Azalea".equals(stage)) {
          pos = Stages.AZALEA.crowd.getMidPoint();
        } else if ("Flamingo".equals(stage)) {
          pos = Stages.FLAMINGO.crowd.getMidPoint();
        } else if ("Dungen".equals(stage)) {
          pos = Stages.DUNGEN.crowd.getMidPoint();
        } else {
          throw new RuntimeException("Unknown stage: " + stage);
        }

        ResourceLocation location = new ResourceLocation("mineoutwest", soundId);
        SoundEvent event = new SoundEvent(location);
        //GameRegistry.register(event, location);

        out.add(new Sounds.ScheduledSound(event, tick, pos, stage, artist));
      }
      this.concerts = out;

      this.aliases = new ArrayList();
      this.aliases.add("wow");
    }

    @Override
    public String getCommandName() {
        return "wow";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "wow [event <id>  | open]" ;
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {

        World world = iCommandSender.getEntityWorld();

        String lower = strings[0].toLowerCase();
        if (lower.equals("open")) {
          iCommandSender.addChatMessage(new TextComponentString("WOW opened!"));
          world.setWorldTime(0);
        } else if(lower.equals("event")) {
          if (strings.length == 1) {
            // give all concerts
            for (Sounds.ScheduledSound concert: this.concerts) {
              iCommandSender.addChatMessage(new TextComponentString(concert.artist + " " + concert.stage + " " + concert.getTime()));
            }
          } else {
            // find concert
            System.out.println("Trying to get info about concert");
            String id_concert = strings[1].toLowerCase();
            Sounds.ScheduledSound this_concert = null;
            for (Sounds.ScheduledSound concert: this.concerts) {

              if (concert.artist.toLowerCase().indexOf(id_concert) > -1) {
                this_concert = concert;
                break;
              }

            }
            if (this_concert != null) {
              System.out.println("Found concert");
              // this is the concert

              //iCommandSender.addChatMessage(new TextComponentString(id + " " + this_concert.artist + " " + this_concert.stage + " " + this_concert.getTime()));
              EntityPlayer player0 = world.getPlayerEntityByName(iCommandSender.getName());
              if (player0 != null) {
                BlockPos pos = this_concert.getPos();
                System.out.println("Moving player");
                player0.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
                System.out.println("Updating world time ");
                world.setWorldTime(this_concert.getTime()-60);
              } else {

                System.out.println("Could not find player");
              }

              // jump to concert

            } else {
              iCommandSender.addChatMessage(new TextComponentString("Could not find id "));

            }


          }

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

