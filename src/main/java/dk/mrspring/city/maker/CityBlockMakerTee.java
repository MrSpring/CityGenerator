package dk.mrspring.city.maker;

import dk.mrspring.city.CityBlock;
import dk.mrspring.city.IGenerator;

import java.util.List;
import java.util.Random;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public class CityBlockMakerTee implements CityBlockMaker
{
    @Override
    public CityBlock makeBlock(CityBlock block, List<CityBlock> blocks, int index, Random random, IGenerator generator)
    {
        int[] adjacent = generator.getAdjacent(block, blocks);

    }

    @Override
    public int getSize()
    {
        return 4;
    }

    @Override
    public int compareTo(CityBlockMaker that)
    {
        return Integer.compare(this.getSize(), that.getSize());
    }
}
