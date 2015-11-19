package dk.mrspring.city.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public class StructureCityPieces
{
    public static List<PieceWeight> makeWeightList(Random random, int par5)
    {
        List<PieceWeight> weights = new ArrayList<PieceWeight>();
    }

    public static class PieceWeight
    {
        public Class pieceClass;
        public final int pieceWeight;
        public int piecesSpawned;
        public int piecesLimit;

        public PieceWeight(Class pieceClass, int weight, int limit)
        {
            this.pieceClass = pieceClass;
            this.pieceWeight = weight;
            this.piecesLimit = limit;
        }
    }
}
