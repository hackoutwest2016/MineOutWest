package com.mineoutwest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mineoutwest.speaker.Sounds;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EventConfigParser {

  public List<Sounds.ScheduledSound> loadFromConfig() {
    JsonParser jsonParser = new JsonParser();
    JsonElement element = null;
    try {
      element = jsonParser.parse(new InputStreamReader(this.getClass().getClassLoader().getResource("events.json").openStream()));
    } catch (IOException e) {
      throw new RuntimeException("Error loading event config", e);
    }

    JsonArray array = element.getAsJsonArray();

    List<Sounds.ScheduledSound> out = Lists.newArrayList();
    for (JsonElement e : array) {
      JsonObject o = (JsonObject)e;

      long tick = o.get("tick").getAsLong();
      String soundId = o.get("id").getAsString();
      String stage = o.get("stage").getAsString();

      BlockPos pos;
      if (stage.startsWith("Linn")) {
        pos = Stages.LINNE.getCenter();
      } else if ("Azalea".equals(stage)) {
        pos = Stages.AZALEA.getCenter();
      } else if ("Flamingo".equals(stage)) {
        pos = Stages.FLAMINGO.getCenter();
      } else if ("Dungen".equals(stage)) {
        pos = Stages.DUNGEN.getCenter();
      } else {
        throw new RuntimeException("Unknown stage: " + stage);
      }

      ResourceLocation location = new ResourceLocation("mineoutwest", soundId);
      SoundEvent event = new SoundEvent(location);
      GameRegistry.register(event, location);

      out.add(new Sounds.ScheduledSound(event, tick, pos));
    }

    return out;
  }
}
