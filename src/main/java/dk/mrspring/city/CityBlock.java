package dk.mrspring.city;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public class CityBlock
{
    public int x, z;
    public int width, depth;

    public CityBlock(int x, int z, int width, int depth)
    {
        this.x = x;
        this.z = z;
        this.width = width;
        this.depth = depth;
    }

    public CityBlock copy()
    {
        return new CityBlock(x, z, width, depth);
    }
}
