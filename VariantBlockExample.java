package com.ricardoredstone.mod.objects;

import com.ricardoredstone.mod.util.StringArrayProperty;
import net.minecraft.block.material.Material;

public class VariantBlockExample extends VariantBlock{
	static {
		/*
		You Must Set The 'variants' Variable Here( Preferably To A 'StringArrayProperty' ), Or Else You'll Get A NullPointerExeption
		
		StringArrayProperty Constructor usage: StringArrayProperty(<Variant Name>,<Array Of States>)
		*/
		variants=new StringArrayProperty("variant",new String[] {"variant1","variant2","variant3"});
	}
	public VariantBlockExample() {
		super("varianttest",0,0,Material.CLOTH);
		/*
		
		Put Your Code Here
		
		The Variable 'item' is the 'ItemBlock', You Must Use It Or Call 'VariantBlock.GetItemBlock()' Instead Of Creating A New 'ItemBlock'
		
		*/
	}
}
