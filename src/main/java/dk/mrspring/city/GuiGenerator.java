package dk.mrspring.city;

import dk.mrspring.city.maker.CityBlockMaker;
import dk.mrspring.city.maker.CityBlockMakerLarge;
import dk.mrspring.city.maker.CityBlockMakerMedium;
import dk.mrspring.city.maker.CityBlockMakerSmall;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

import java.io.IOException;
import java.util.*;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
public class GuiGenerator extends GuiScreen implements IGenerator
{
    private static final int POSITIVE_Z = 0, POSITIVE_X = 1, NEGATIVE_Z = 2, NEGATIVE_X = 3;

    List<CityBlock> blocks = new ArrayList<CityBlock>();
    List<CityBlock> areaBlocks = new ArrayList<CityBlock>();
    List<CityBlockMaker> makers = new ArrayList<CityBlockMaker>();
    CityBlock area;

    int counter = 0;

    @Override
    public void initGui()
    {
        super.initGui();

        this.buttonList.add(new GuiButton(1, 5, 5, "Regenerate"));

        makers.clear();
        makers.add(new CityBlockMakerSmall());
        makers.add(new CityBlockMakerMedium());
        makers.add(new CityBlockMakerLarge());
        Collections.sort(makers);
        for (CityBlockMaker maker : makers) System.out.println(maker.getClass().getSimpleName());

        int areaWidth = width - 20;
        int remains = areaWidth % 8;
        areaWidth -= remains;
        int areaHeight = height - 5 - 20 - 5 - 8 - 10 - 10;
        areaHeight -= areaHeight % 8;
        area = new CityBlock(10 + remains / 2, 5 + 20 + 5 + 8 + 10, areaWidth, areaHeight);
        this.generate(new Random());
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        /*counter++;

        if (counter > 0)
        {
            counter = 0;
            if (!areaBlocks.isEmpty())
            {
                Random random = new Random();
                int i = areaBlocks.size() - 1;
                CityBlock currentBlock = areaBlocks.get(i);
                CityBlock result = null;
                for (int type = makers.size() - 1; type >= 0 && result == null; type--)
                {
                    CityBlockMaker maker = makers.get(type);
                    if (i == 0 || random.nextInt((maker.getSize())) == 0)
                        result = maker.makeBlock(currentBlock, areaBlocks, i, random, this);
                }
                this.blocks.add(result);
            }
        }*/
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 1)
            this.generate(new Random());
    }

    private void generate(Random random)
    {
        this.blocks.clear();
        this.areaBlocks = new ArrayList<CityBlock>();
        for (int z = 0; z < area.depth; z += 8)
        {
            for (int x = 0; x < area.width; x += 8)
            {
                areaBlocks.add(new CityBlock(area.x + x, area.z + z, 8, 8));
            }
        }
        Collections.shuffle(this.areaBlocks);
        List<CityBlock> source = new ArrayList<CityBlock>(blocks);

        while (!areaBlocks.isEmpty())
        {
            int i = areaBlocks.size() - 1;
            CityBlock currentBlock = areaBlocks.get(i);
            CityBlock result = null;
            for (int type = makers.size() - 1; type >= 0 && result == null; type--)
            {
                CityBlockMaker maker = makers.get(type);
                if (i == 0 || random.nextInt(maker.getSize() * 2) == 0)
                    result = maker.makeBlock(currentBlock, areaBlocks, i, random, this);
            }
            this.blocks.add(result);
        }
    }

    /*private CityBlock teeBlock(CityBlock block, List<CityBlock> blocks, int index, Random rand)
    {
        int adjacent[] = this.getAdjacent(block, blocks);
        if (adjacent[POSITIVE_Z] >= 0)
        {
            CityBlock result = null;
            switch (rand.nextInt(3))
            {
                case 0:
                    result = teeBlockDownLeft(block, blocks, index, rand);
                case 1:
                    if (result == null)
                        result = teeBlockDownUp()
            }
        }
        return null;
    }

    private CityBlock largeBlock(CityBlock block, List<CityBlock> blocks, int index, Random rand)
    {
        int[] adjacent = this.getAdjacent(block, blocks);
        if (adjacent[POSITIVE_Z] >= 0)
        {
            int[] northAdjacent = this.getAdjacent(new CityBlock(block.x, block.z + 8, 8, 8), blocks);
            if (adjacent[POSITIVE_X] >= 0 && northAdjacent[POSITIVE_X] >= 0)
            {
                this.remove(blocks, adjacent[POSITIVE_Z], adjacent[POSITIVE_X], northAdjacent[POSITIVE_X], index);
                return new CityBlock(block.x, block.z, 16, 16);
            } else if (adjacent[NEGATIVE_X] >= 0 && northAdjacent[NEGATIVE_X] >= 0)
            {
                this.remove(blocks, adjacent[POSITIVE_Z], adjacent[NEGATIVE_X], northAdjacent[NEGATIVE_X], index);
                return new CityBlock(block.x - 8, block.z, 16, 16);
            }
        } else if (adjacent[NEGATIVE_Z] >= 0)
        {
            int[] southAdjacent = this.getAdjacent(new CityBlock(block.x, block.z - 8, 8, 8), blocks);
            if (adjacent[POSITIVE_X] >= 0 && southAdjacent[POSITIVE_X] >= 0)
            {
                this.remove(blocks, adjacent[NEGATIVE_Z], adjacent[POSITIVE_X], southAdjacent[POSITIVE_X], index);
                return new CityBlock(block.x, block.z - 8, 16, 16);
            } else if (adjacent[NEGATIVE_X] >= 0 && southAdjacent[NEGATIVE_X] >= 0)
            {
                this.remove(blocks, adjacent[NEGATIVE_Z], adjacent[NEGATIVE_X], southAdjacent[NEGATIVE_X], index);
                return new CityBlock(block.x - 8, block.z - 8, 16, 16);
            }
        }
        return null;
    }

    private CityBlock midBlock(CityBlock block, List<CityBlock> blocks, int index, Random rand)
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
    }

    private CityBlock smallBlock(CityBlock block, List<CityBlock> blocks, int index, Random rand)
    {
        this.remove(blocks, index);
        return new CityBlock(block.x, block.z, 8, 8);
    }*/

    @Override
    public void remove(List<CityBlock> blocks, int... indexes)
    {
        if (indexes.length > 1)
        {
            Arrays.sort(indexes);
            for (int i = indexes.length - 1; i >= 0; i--)
                blocks.remove(indexes[i]);
        } else blocks.remove(indexes[0]);
    }

    @Override
    public int[] getAdjacent(CityBlock block, List<CityBlock> blocks)
    {
        int[] indexes = new int[]{-1, -1, -1, -1};
        for (int i = 0; i < blocks.size(); i++)
        {
            int xDiff = block.x - blocks.get(i).x;
            int zDiff = block.z - blocks.get(i).z;

            if (xDiff == 0 && zDiff == -8)
                indexes[POSITIVE_Z] = i;
            else if (xDiff == -8 && zDiff == 0)
                indexes[POSITIVE_X] = i;
            else if (xDiff == 0 && zDiff == 8)
                indexes[NEGATIVE_Z] = i;
            else if (xDiff == 8 && zDiff == 0)
                indexes[NEGATIVE_X] = i;
        }
        return indexes;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        mc.fontRendererObj.drawString(String.valueOf(counter), 5, 30, 0xFFFFFF);
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.color(1F, 0F, 0F, 0.5F);
        drawRect(area.x, area.z, area.width, area.depth);
        GlStateManager.color(0F, 1F, 0F, 0.5F);
        if (blocks != null)
            for (CityBlock block : blocks)
                if (block != null)
                    drawRect(block.x + 1, block.z + 1, block.width - 2, block.depth - 2);
        GlStateManager.popMatrix();
    }

    private void drawRect(int x, int y, int width, int height)
    {
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.startDrawingQuads();
        wr.addVertex(x, y, zLevel);
        wr.addVertex(x + width, y, zLevel);
        wr.addVertex(x + width, y + height, zLevel);
        wr.addVertex(x, y + height, zLevel);
        tess.draw();
    }
}
