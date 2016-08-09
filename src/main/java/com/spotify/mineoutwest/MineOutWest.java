package com.spotify.mineoutwest;

import com.spotify.mineoutwest.block.ArtistCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = MineOutWest.MODID, version = MineOutWest.VERSION)
public class MineOutWest {
  public static final String MODID = "mineoutwest";
  public static final String VERSION = "1.0";


  // Says where the client and server 'proxy' code is loaded.
  @SidedProxy(clientSide="com.spotify.mineoutwest.ClientOnlyProxy", serverSide="com.spotify.mineoutwest.DedicatedServerProxy")
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
    event.registerServerCommand(new ArtistCommand());
  }

}
