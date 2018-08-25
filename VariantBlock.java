package com.ricardoredstone.mod.objects;

import com.ricardoredstone.mod.proxy.CommonProxy;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class VariantBlock extends Block {
	protected final String name;
	protected final ItemBlock item;
	protected static IProperty<Integer> variants=null;

	public VariantBlock(String name, float hardness, float resistance, Material blockMaterialIn) {
		this(name,hardness,resistance,0,null,CreativeTabs.BUILDING_BLOCKS,blockMaterialIn);
	}

	public VariantBlock(String name, float hardness, float resistance, CreativeTabs tab, Material blockMaterialIn) {
		this(name,hardness,resistance,0,null,tab,blockMaterialIn);
	}

	public VariantBlock(String name, float hardness, float resistance, int harvestLevel, String toolClass, Material blockMaterialIn) {
		this(name,hardness,resistance,harvestLevel,toolClass,CreativeTabs.BUILDING_BLOCKS,blockMaterialIn);
	}

	public VariantBlock(String sname, float hardness, float resistance, int harvestLevel, String toolClass, CreativeTabs tab,Material blockMaterialIn) {
		super(blockMaterialIn);
		name=sname;
		setDefaultState(this.blockState.getBaseState().withProperty(variants, 0));
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setHardness(hardness);
		setResistance(resistance);
		if(toolClass!=null)setHarvestLevel(toolClass,harvestLevel);
		(item=new ItemBlock(this) {
			{
				setHasSubtypes(true);
				setMaxDamage(0);
			}
			@Override
			public int getMetadata(int damage) {
				return damage;
			}
			@Override
			public String getUnlocalizedName(ItemStack stack) {
				return super.getUnlocalizedName()+"_"+variants.getName(stack.getItemDamage());
			}
		}).setRegistryName(name).setUnlocalizedName(name);
	}

	public void registerModels(CommonProxy proxy) {
		Collection<Integer> values=variants.getAllowedValues();
		for(int i:values){
			proxy.registerVariantRenderer(Item.getItemFromBlock(this),i,name+"_"+variants.getName(i),"inventory");
		}
	}

	public ItemBlock getItemBlock() {
		return item;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return ((Integer)state.getValue(variants));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer)state.getValue(variants));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(variants, meta);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		Collection<Integer> values=variants.getAllowedValues();
		for(int i:values){
			items.add(new ItemStack(this,1,i));
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {variants});
	}
}
