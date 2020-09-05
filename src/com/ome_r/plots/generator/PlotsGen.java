package com.ome_r.plots.generator;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.*;

public class PlotsGen extends ChunkGenerator{

    private List<BlockPopulator> populators = new ArrayList<>();
    public Map<Material, Byte> blocksData = new HashMap<>();

    public PlotsGen(){
        populators.add(new BlocksData());
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 64, 0);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return populators;
    }

    @Override
    public short[][] generateExtBlockSections(World world, Random random, int cX, int cZ, BiomeGrid biomes) {
        short[][] result = new short[world.getMaxHeight() / 16][];

        for(int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++) {
                int blockX = (cX * 16) + x, blockZ = (cZ * 16) + z, blockXMod = blockX % 36, blockZMod = blockZ % 36;

                for (int y = 1; y <= 64; y++) {
                    if (y == 1)
                        setBlock(result, x, y , z, 7);
                    else if (y <= 63)
                        setBlock(result, x, y , z, 3);
                    else setBlock(result, x, y , z, 2);
                }

                if(((blockX + 3) % 36 == 0 || (blockX - 3) % 36 == 0) || ((blockZ + 3) % 36 == 0 || (blockZ - 3) % 36 == 0))
                    setBlock(result, x, 65, z, 44);

                if((blockXMod >= -2 && blockXMod <= 2) || Math.abs(blockXMod) == 35 || Math.abs(blockXMod) == 34 ||
                        (blockZMod >= -2 && blockZMod <= 2) || Math.abs(blockZMod) == 35 || Math.abs(blockZMod) == 34){
                    setBlock(result, x, 64 , z, 24);
                    setBlock(result, x, 65 , z, 0);
                }

                biomes.setBiome(x, z, Biome.PLAINS);
            }

        return result;
    }

    private void setBlock(short[][] result, int x, int y, int z, int blockID){
        if(result[y >> 4] == null)
            result[y >> 4] = new short[4096];

        result[y >> 4][((y&0xF) << 8) | (z << 4) | x] = (short) blockID;
    }

}
