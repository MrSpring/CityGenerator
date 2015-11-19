package dk.mrspring.city.maker;

import dk.mrspring.city.CityBlock;
import dk.mrspring.city.IGenerator;

import java.util.List;
import java.util.Random;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public class CityBlockMakerMedium implements CityBlockMaker
{
    private static final int POSITIVE_Z = 0, POSITIVE_X = 1, NEGATIVE_Z = 2, NEGATIVE_X = 3;

    @Override
    public CityBlock makeBlock(CityBlock block, List<CityBlock> blocks, int index, Random random, IGenerator generator)
    {
        int[] adjacent = generator.getAdjacent(block, blocks);
        if (random.nextInt(2) == 0)
        {
            CityBlock result = midWideBlock(block, blocks, index, adjacent, random, generator);
            if (result == null) result = midTallBlock(block, blocks, index, adjacent, random, generator);
            return result;
        } else
        {
            CityBlock result = midTallBlock(block, blocks, index, adjacent, random, generator);
            if (result == null) result = midWideBlock(block, blocks, index, adjacent, random, generator);
            return result;
        }
    }

    private CityBlock midTallBlock(CityBlock block, List<CityBlock> blocks, int index, int[] adjacent, Random rand, IGenerator generator)
    {
        if (adjacent[POSITIVE_Z] >= 0)
        {
            generator.remove(blocks, index, adjacent[POSITIVE_Z]);
            return new CityBlock(block.x, block.z, 8, 16);
        } else if (adjacent[NEGATIVE_Z] >= 0)
        {
            generator.remove(blocks, index, adjacent[NEGATIVE_Z]);
            return new CityBlock(block.x, block.z - 8, 8, 16);
        } else return null;
    }

    private CityBlock midWideBlock(CityBlock block, List<CityBlock> blocks, int index, int[] adjacent, Random rand, IGenerator generator)
    {
        if (adjacent[POSITIVE_X] >= 0)
        {
            generator.remove(blocks, index, adjacent[POSITIVE_X]);
            return new CityBlock(block.x, block.z, 16, 8);
        } else if (adjacent[NEGATIVE_X] >= 0)
        {
            generator.remove(blocks, index, adjacent[NEGATIVE_X]);
            return new CityBlock(block.x - 8, block.z, 16, 8);
        } else return null;
    }

    @Override
    public int getSize()
    {
        return 2;
    }

    @Override
    public int compareTo(CityBlockMaker that)
    {
        return Integer.compare(this.getSize(), that.getSize());
    }
    /*private CityBlock midBlock(CityBlock block, List<CityBlock> blocks, int index, Random rand)
    {
        int[] adjacent = getAdjacent(block, blocks);
        if (rand.nextInt(2) == 0)
        {
            CityBlock result = midWideBlock(block, blocks, index, adjacent, rand);
            if (result == null) result = midTallBlock(block, blocks, index, adjacent, rand);
            return result;
        } else
        {
            CityBlock result = midTallBlock(block, blocks, index, adjacent, rand);
            if (result == null) result = midWideBlock(block, blocks, index, adjacent, rand);
            return result;
        }
    }

    private CityBlock midTallBlock(CityBlock block, List<CityBlock> blocks, int index, int[] adjacent, Random rand)
    {
        if (adjacent[POSITIVE_Z] >= 0)
        {
            remove(blocks, index, adjacent[POSITIVE_Z]);
            return new CityBlock(block.x, block.z, 8, 16);
        } else if (adjacent[NEGATIVE_Z] >= 0)
        {
            remove(blocks, index, adjacent[NEGATIVE_Z]);
            return new CityBlock(block.x, block.z - 8, 8, 16);
        } else return null;
    }

    private CityBlock midWideBlock(CityBlock block, List<CityBlock> blocks, int index, int[] adjacent, Random rand)
    {
        if (adjacent[POSITIVE_X] >= 0)
        {
            remove(blocks, index, adjacent[POSITIVE_X]);
            return new CityBlock(block.x, block.z, 16, 8);
        } else if (adjacent[NEGATIVE_X] >= 0)
        {
            remove(blocks, index, adjacent[NEGATIVE_X]);
            return new CityBlock(block.x - 8, block.z, 16, 8);
        } else return null;
    }*/
}
