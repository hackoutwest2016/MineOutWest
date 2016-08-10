package com.mineoutwest;

import com.mineoutwest.block.Artist;
import com.mineoutwest.block.ArtistsCommand;
import com.mineoutwest.speaker.MusicCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = MineOutWest.MODID, version = MineOutWest.VERSION)
public class MineOutWest {
  public static final String MODID = "mineoutwest";
  public static final String VERSION = "1.0";


  // Says where the client and server 'proxy' code is loaded.
  @SidedProxy(clientSide="com.mineoutwest.ClientOnlyProxy", serverSide="com.mineoutwest.DedicatedServerProxy")
  public static CommonProxy proxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    System.out.println("#############################################");
    System.out.println("#                 pre init                  #");
    System.out.println("#############################################");

    proxy.preInit();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    System.out.println("#############################################");
    System.out.println("#                 init                      #");
    System.out.println("#############################################");

    int id =1;
    EntityRegistry.registerModEntity(Artist.class, "Artist", id, this, 80, 1, true);//id is an internal mob id, you can start at 0 and continue adding them up.
    id++;

    proxy.init();
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    System.out.println("#############################################");
    System.out.println("#            post init                      #");
    System.out.println("#############################################");

    proxy.postInit();
  }

  @EventHandler
  public void serverLoad(FMLServerStartingEvent event)
  {
    event.registerServerCommand(new ArtistsCommand());
    event.registerServerCommand(new MusicCommand());
  }

}
