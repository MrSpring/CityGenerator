package dk.mrspring.city.gen;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.List;
import java.util.Random;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public class MapGenCity extends MapGenStructure
{
    @Override
    public String getStructureName()
    {
        return "City";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return null;
    }

    public static class Start extends StructureStart
    {
        public Start()
        {
        }

        public Start(World world, Random random, int x, int y, int par5)
        {
            List<StructureCityPieces.PieceWeight> weights = StructureCityPieces.makeWeightList(random, par5);
        }
    }
}
