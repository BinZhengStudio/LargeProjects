package net.industrybase.api.util;

import net.industrybase.api.IndustryBaseApi;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.IntArrayTag;

import java.util.Arrays;
import java.util.Optional;

public class NbtHelper {
	public static Optional<BlockPos> readBlockPos(IntArrayTag tag) {
		int[] array = tag.getAsIntArray();
		if (array.length != 3) IndustryBaseApi.LOGGER.warn("Invalid BlockPos: {}", Arrays.toString(array));
		return array.length == 3 ? Optional.of(new BlockPos(array[0], array[1], array[2])) : Optional.empty();
	}
}
