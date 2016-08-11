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
        public String time;
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
            String time = o.get("time").getAsString();
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
                sa.time = time;
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

            int left = 2;

            String day = "Saturday";
            if (worldTime / 24000 == 0) {
                day = "Thursday";
            } else if (worldTime / 24000 == 1) {
                day = "Friday";
            }
            String text = String.format("MoW Time: %d, Day: %s", worldTime, day);
            drawString(mc.fontRendererObj, text, left, (int) (0.05 * height), 0xffaa00);

            drawString(mc.fontRendererObj, "Flamingo", left, (int) (0.15 * height), 0xff0050);
            drawString(mc.fontRendererObj, "Azalea", left, (int) (0.35 * height), 0xff0050);
            drawString(mc.fontRendererObj, "Linné", left, (int) (0.55 * height), 0xff0050);
            drawString(mc.fontRendererObj, "Dungen", left, (int) (0.75 * height), 0xff0050);

            for (ScheduledArtist a : m_schedule) {
                if (worldTime >= a.start && worldTime < a.end) {
                    float y = 0.0f;
                    if (a.stage.equals("Flamingo")) {
                        y = 0.20f;
                    } else if (a.stage.equals("Azalea")) {
                        y = 0.40f;
                    } else if (a.stage.equals("Linné")) {
                        y = 0.60f;
                    } else if (a.stage.equals("Dungen")) {
                        y = 0.80f;
                    }
                    drawString(mc.fontRendererObj, a.artist, left, (int) (y * height), 0xaaff00);
                    drawString(mc.fontRendererObj, String.format("Start: %s", a.time), left, (int) ((y + 0.05) * height), 0xaaff00);
                    int right = left + (int) (0.2 * width);
                    int top = (int) ((y + 0.1) * height);
                    int bottom = top + 4;
                    drawRect(left, top, right, bottom, 0xa0ffffff);
                    if (a.start - a.end != 0) {
                        float progress = (float) (worldTime - a.start) / (a.end - a.start);
                        drawRect(left + 1, top + 1, left + 1 + (int) (((right - 1) - (left + 1)) * progress), bottom - 1, 0xa0205040);
                    }
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

