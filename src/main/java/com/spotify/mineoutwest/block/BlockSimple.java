package com.spotify.mineoutwest.block;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * User: The Grey Ghost
 * Date: 24/12/2014
 *
 * BlockSimple is a ordinary solid cube with the six faces numbered from 0 - 5.
 * For background information on blocks see here http://greyminecraftcoder.blogspot.com.au/2014/12/blocks-18.html
 */
public class BlockSimple extends Block {

  public static BlockSimple blockSimple;  // this holds the unique instance of your block

  public static void preInitCommon() {
    // each instance of your block should have a name that is unique within your mod.  use lower case.
    blockSimple = (BlockSimple)(new BlockSimple().setUnlocalizedName("mbe01_block_simple"));
    GameRegistry.registerBlock(blockSimple, "mbe01_block_simple");

    // you don't need to register an item corresponding to the block, GameRegistry.registerBlock does this automatically.
  }

  public static void preInitClientOnly() {
    // This is currently necessary in order to make your block render properly when it is an item (i.e. in the inventory
    //   or in your hand or thrown on the ground).
    // Minecraft knows to look for the item model based on the GameRegistry.registerBlock.  However the registration of
    //  the model for each item is normally done by RenderItem.registerItems(), and this is not currently aware
    //   of any extra items you have created.  Hence you have to do it manually.
    // It must be done on client only, and must be done after the block has been created in Common.preinit().
    Item itemBlockSimple = GameRegistry.findItem("mineoutwest", "mbe01_block_simple");
    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("mineoutwest:mbe01_block_simple", "inventory");
    final int DEFAULT_ITEM_SUBTYPE = 0;

    ModelLoader.setCustomModelResourceLocation(itemBlockSimple, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
  }



  public BlockSimple()
  {
    super(Material.ROCK);
    this.setCreativeTab(CreativeTabs.MISC);   // the block will appear on the Blocks tab in creative
    //this.setRegistryName("mbe01_block_simple");
  }

  // the block will render in the SOLID layer.  See http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html for more information.
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.SOLID;
  }
}
