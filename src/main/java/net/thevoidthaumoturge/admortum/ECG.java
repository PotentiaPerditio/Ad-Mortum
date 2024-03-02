package net.thevoidthaumoturge.admortum;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.HolderSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryOps;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.RandomState;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.structure.StructureSet;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ECG extends ChunkGenerator {

	private final Registry<Biome> biomeRegistry;

	public static final Codec<ECG> CODEC = RecordCodecBuilder.create((instance) -> {
		return m_asjkihmg(instance).and(RegistryOps.getRegistry(Registry.BIOME_KEY).forGetter((ecg -> {
			return ecg.biomeRegistry;
		}))).apply(instance, instance.stable(ECG::new));
	});

	public ECG(Registry<StructureSet> registry, Registry<Biome> registry2) {
		super(registry, Optional.empty(), new FixedBiomeSource(registry2.getOrCreateHolderOrThrow(AdMortum.EXILE_BIOME_KEY)));
		this.biomeRegistry = registry2;
	}

	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return null;
	}

	@Override
	public void carve(ChunkRegion chunkRegion, long seed, RandomState randomState, BiomeAccess biomeAccess, StructureManager structureManager, Chunk chunk, GenerationStep.Carver generationStep) {

	}

	@Override
	public void buildSurface(ChunkRegion region, StructureManager structureManager, RandomState randomState, Chunk chunk) {

	}

	@Override
	public void populateEntities(ChunkRegion region) {

	}

	@Override
	public int getWorldHeight() {
		return 0;
	}

	@Override
	public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, RandomState randomState, StructureManager structureManager, Chunk chunk) {
		return null;
	}

	@Override
	public int getSeaLevel() {
		return 0;
	}

	@Override
	public int getMinimumY() {
		return 0;
	}

	@Override
	public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, RandomState randomState) {
		return 0;
	}

	@Override
	public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, RandomState randomState) {
		return null;
	}

	@Override
	public void m_hfetlfug(List<String> list, RandomState randomState, BlockPos pos) {

	}
}
