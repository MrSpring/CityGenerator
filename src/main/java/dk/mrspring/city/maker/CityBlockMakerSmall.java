package dk.mrspring.city.maker;

import dk.mrspring.city.CityBlock;
import dk.mrspring.city.IGenerator;

import java.util.List;
import java.util.Random;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public class CityBlockMakerSmall implements CityBlockMaker
{
    @Override
    public CityBlock makeBlock(CityBlock block, List<CityBlock> blocks, int index, Random random, IGenerator generator)
    {
        generator.remove(blocks, index);
        return new CityBlock(block.x, block.z, 8, 8);
    }

    @Override
    public int getSize()
    {
        return 1;
    }

    @Override
    public int compareTo(CityBlockMaker that)
    {
        return Integer.compare(this.getSize(), that.getSize());
    }
}
