package com.ricardoredstone.mod.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.google.common.base.Optional;
import net.minecraft.block.properties.IProperty;

public class StringArrayProperty implements IProperty<Integer> {

	private final String name;
	private final String[] data;

	public StringArrayProperty(String name,String[] newdata){
		this.name=name;
		data=newdata.clone();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<Integer> getAllowedValues() {
		return IntStream.rangeClosed(0, data.length-1).boxed().collect(Collectors.toList());
	}

	@Override
	public Class<Integer> getValueClass() {
		return Integer.class;
	}

	@Override
	public Optional<Integer> parseValue(String value) {
		for(int i=0;i<data.length;i++){
			if(data[i]==value) {
				return Optional.of(i);
			}
		}
		return Optional.absent();
	}

	@Override
	public String getName(Integer value) {
		return data[value];
	}

	
}
