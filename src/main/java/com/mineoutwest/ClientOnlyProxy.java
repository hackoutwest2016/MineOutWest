package com.mineoutwest;

import com.mineoutwest.block.Artist;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


/**
 * ClientProxy is used to set up the mod and start it running on normal minecraft.  It contains all the code that should run on the
 *   client side only.
 *   For more background information see here http://greyminecraftcoder.blogspot.com/2013/11/how-forge-starts-up-your-code.html
 */
public class ClientOnlyProxy extends CommonProxy {

  /**
   * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
   */
  public void preInit() {
    super.preInit();


    RenderingRegistry.registerEntityRenderingHandler(Artist.class,
            new MyRenderFactory(RenderVillager.class));

  }

  /**
   * Do your mod setup. Build whatever data structures you care about. Register recipes,
   * send FMLInterModComms messages to other mods.
   */
  public void init() {
    super.init();




  }

  /**
   * Handle interaction with other mods, complete your setup based on this.
   */
  public void postInit() {
    super.postInit();

    MinecraftForge.EVENT_BUS.register(new RenderGuiHandler());
  }
}
