package com.spotify.mineoutwest;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod {
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

    //    Block b = new Block(Material.CACTUS);
//    b.setCreativeTab(CreativeTabs.MISC);
//    b.setUnlocalizedName("Hello world!");
//    b.setRegistryName("helloworldblock");

//    Item item = new Item();
//    item.setCreativeTab(CreativeTabs.MISC);
//    item.setRegistryName("helloworlditem");
//
//    GameRegistry.register(item);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    System.out.println("#############################################");
    System.out.println("#            post init                      #");
    System.out.println("#############################################");

    proxy.postInit();
  }



}
