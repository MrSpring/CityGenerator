package dk.mrspring.city;

import java.util.List;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public interface IGenerator
{
    void remove(List<CityBlock> blocks, int... indexes);

    int[] getAdjacent(CityBlock center, List<CityBlock> blocks);
}
