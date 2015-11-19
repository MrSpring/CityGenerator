package dk.mrspring.city.maker;

import dk.mrspring.city.CityBlock;
import dk.mrspring.city.IGenerator;

import java.util.List;
import java.util.Random;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public interface CityBlockMaker extends Comparable<CityBlockMaker>
{
    CityBlock makeBlock(CityBlock block, List<CityBlock> blocks, int index, Random random, IGenerator generator);

    int getSize();
}
