package com.spotify.mineoutwest.block;

import com.spotify.mineoutwest.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Speaker extends Block {

  public static Speaker blockSimple;  // this holds the unique instance of your block

  public static void preInitCommon() {
    // each instance of your block should have a name that is unique within your mod.  use lower case.
    blockSimple = (Speaker)(new Speaker().setUnlocalizedName("mbe01_block_simple"));
    GameRegistry.registerBlock(blockSimple, "mbe01_block_simple");
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

  public Speaker() {
    super(Material.ROCK);
    this.setCreativeTab(CreativeTabs.MISC);   // the block will appear on the Blocks tab in creative
  }

  @Override
  public IBlockState onBlockPlaced(World world,
                                   BlockPos pos,
                                   EnumFacing p_onBlockPlaced_3_,
                                   float p_onBlockPlaced_4_,
                                   float p_onBlockPlaced_5_,
                                   float p_onBlockPlaced_6_,
                                   int p_onBlockPlaced_7_,
                                   EntityLivingBase elb) {
    //System.out.println("Block placed " + pos + " - " + elb + " - " + world + " - " + p_onBlockPlaced_4_ + " - " + p_onBlockPlaced_7_);
    //EntityPlayer epsp = (EntityPlayer)elb;

    //world.playSound(epsp, pos, Sounds.EXAMPLE, SoundCategory.MASTER, 1, 1);

    return super.onBlockPlaced(world, pos, p_onBlockPlaced_3_, p_onBlockPlaced_4_, p_onBlockPlaced_5_, p_onBlockPlaced_6_, p_onBlockPlaced_7_, elb);
  }

  // the block will render in the SOLID layer.  See http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html for more information.
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.SOLID;
  }
}
