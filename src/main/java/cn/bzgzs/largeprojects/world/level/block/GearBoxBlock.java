package cn.bzgzs.largeprojects.world.level.block;

import cn.bzgzs.largeprojects.world.level.block.entity.GearBoxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GearBoxBlock extends BaseEntityBlock {
	protected GearBoxBlock() {
		super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
	}

	@Override
	@SuppressWarnings("deprecation")
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new GearBoxBlockEntity(pos, state);
	}
}
