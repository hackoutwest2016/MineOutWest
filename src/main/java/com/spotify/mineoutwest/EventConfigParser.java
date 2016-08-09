package com.spotify.mineoutwest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
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

      int x, y, z;
      if ("linn_".equals(stage)) {
        x = -172;
        y = 72;
        z = 241;
      } else if ("azalea".equals(stage)) {
        x = -150;
        y = 72;
        z = 241;
      } else if ("flamingo".equals(stage)) {
        x = -160;
        y = 72;
        z = 201;
      } else if ("dungen".equals(stage)) {
        x = -130;
        y = 72;
        z = 241;
      } else if ("h_jden".equals(stage)) {
        x = -2000;
        y = 72;
        z = 241;
      } else {
        throw new RuntimeException("Unknwon stage: " + stage);
      }

      ResourceLocation location = new ResourceLocation("mineoutwest", soundId);
      SoundEvent event = new SoundEvent(location);
      GameRegistry.register(event, location);

      out.add(new Sounds.ScheduledSound(event, tick, x, y, z));
    }

    return out;
  }
}
