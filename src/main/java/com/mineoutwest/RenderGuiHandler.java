package com.mineoutwest;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class RenderGuiHandler
{
    public static class ScheduledArtist {
        public long start;
        public long end;
        public String artist;
        public String stage;
    }

    List<ScheduledArtist> m_schedule;

    public RenderGuiHandler() {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = null;
        try {
            element = jsonParser.parse(new InputStreamReader(this.getClass().getClassLoader().getResource("events.json").openStream()));
        } catch (IOException e) {
            throw new RuntimeException("Error loading event config", e);
        }

        m_schedule = Lists.newArrayList();
        JsonArray array = element.getAsJsonArray();
        for (JsonElement e : array) {
            JsonObject o = (JsonObject) e;

            long tick = o.get("tick").getAsLong();
            String stage = o.get("stage").getAsString();
            String artist = o.get("artist").getAsString();
            boolean found = false;
            for(ScheduledArtist sa : m_schedule) {
                if(sa != null && sa.artist.equals(artist)) {
                    sa.start = Math.min(tick, sa.start);
                    sa.end = Math.max(tick + 600, sa.end);
                    found = true;
                }
            }
            if (!found) {
                ScheduledArtist sa = new ScheduledArtist();
                sa.stage = stage;
                sa.artist = artist;
                sa.start = tick;
                sa.end = tick + 600;
                m_schedule.add(sa);
            }
        }
        m_schedule.sort(new Comparator<ScheduledArtist>() {
            @Override
            public int compare(ScheduledArtist o1, ScheduledArtist o2) {
                int res = o1.stage.compareTo(o2.stage);
                if (res == 0) {
                    res = o1.artist.compareTo(o2.artist);
                }
                return res;
            }
        });
    }

    class GuiStages extends Gui
    {
        public GuiStages(Minecraft mc)
        {
            ScaledResolution scaled = new ScaledResolution(mc);
            int width = scaled.getScaledWidth();
            int height = scaled.getScaledHeight();

            long worldTime = mc.theWorld.getWorldTime() % 72000;

            String text = String.format("WoW Time: %d", worldTime);
            drawString(mc.fontRendererObj, text, (int) (0.8 * width), (int) (0.1 * height), Integer.parseInt("FFAA00", 16));

            drawString(mc.fontRendererObj, "Flamingo", (int) (0.8 * width), (int) (0.2 * height), Integer.parseInt("FF0050", 16));
            drawString(mc.fontRendererObj, "Azalea", (int) (0.8 * width), (int) (0.4 * height), Integer.parseInt("FF0050", 16));
            drawString(mc.fontRendererObj, "Linné", (int) (0.8 * width), (int) (0.6 * height), Integer.parseInt("FF0050", 16));
            drawString(mc.fontRendererObj, "Dungen", (int) (0.8 * width), (int) (0.8 * height), Integer.parseInt("FF0050", 16));

            for (ScheduledArtist a : m_schedule) {
                if (worldTime >= a.start && worldTime < a.end) {
                    float y = 0.0f;
                    if (a.stage.equals("Flamingo")) {
                        y = 0.25f;
                    } else if (a.stage.equals("Azalea")) {
                        y = 0.45f;
                    } else if (a.stage.equals("Linné")) {
                        y = 0.65f;
                    } else if (a.stage.equals("Dungen")) {
                        y = 0.85f;
                    }
                    drawString(mc.fontRendererObj, a.artist, (int) (0.8 * width), (int) (y * height), Integer.parseInt("AAFF00", 16));
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
        new GuiStages(Minecraft.getMinecraft());
    }
}

