package dk.mrspring.city.maker;

import dk.mrspring.city.CityBlock;
import dk.mrspring.city.IGenerator;

import java.util.List;
import java.util.Random;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public class CityBlockMakerLarge implements CityBlockMaker
{
    private static final int POSITIVE_Z = 0, POSITIVE_X = 1, NEGATIVE_Z = 2, NEGATIVE_X = 3;

    @Override
    public CityBlock makeBlock(CityBlock block, List<CityBlock> blocks, int index, Random random, IGenerator generator)
    {
        int[] adjacent = generator.getAdjacent(block, blocks);
        if (adjacent[POSITIVE_Z] >= 0)
        {
            int[] northAdjacent = generator.getAdjacent(new CityBlock(block.x, block.z + 8, 8, 8), blocks);
            if (adjacent[POSITIVE_X] >= 0 && northAdjacent[POSITIVE_X] >= 0)
            {
                generator.remove(blocks, adjacent[POSITIVE_Z], adjacent[POSITIVE_X], northAdjacent[POSITIVE_X], index);
                return new CityBlock(block.x, block.z, 16, 16);
            } else if (adjacent[NEGATIVE_X] >= 0 && northAdjacent[NEGATIVE_X] >= 0)
            {
                generator.remove(blocks, adjacent[POSITIVE_Z], adjacent[NEGATIVE_X], northAdjacent[NEGATIVE_X], index);
                return new CityBlock(block.x - 8, block.z, 16, 16);
            }
        } else if (adjacent[NEGATIVE_Z] >= 0)
        {
            int[] southAdjacent = generator.getAdjacent(new CityBlock(block.x, block.z - 8, 8, 8), blocks);
            if (adjacent[POSITIVE_X] >= 0 && southAdjacent[POSITIVE_X] >= 0)
            {
                generator.remove(blocks, adjacent[NEGATIVE_Z], adjacent[POSITIVE_X], southAdjacent[POSITIVE_X], index);
                return new CityBlock(block.x, block.z - 8, 16, 16);
            } else if (adjacent[NEGATIVE_X] >= 0 && southAdjacent[NEGATIVE_X] >= 0)
            {
                generator.remove(blocks, adjacent[NEGATIVE_Z], adjacent[NEGATIVE_X], southAdjacent[NEGATIVE_X], index);
                return new CityBlock(block.x - 8, block.z - 8, 16, 16);
            }
        }
        return null;
    }

    @Override
    public int compareTo(CityBlockMaker that)
    {
        return Integer.compare(this.getSize(), that.getSize());
    }

    @Override
    public int getSize()
    {
        return 4;
    }
}
