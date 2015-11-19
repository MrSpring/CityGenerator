package dk.mrspring.city;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created on 19-11-2015 for CityGenerator.
 */
@Mod(modid = CityGenerator.MOD_ID, name = CityGenerator.NAME, version = CityGenerator.VERSION)
public class CityGenerator
{
    public static final String MOD_ID = "cities";
    public static final String NAME = "City Generator";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void gui(GuiOpenEvent event)
    {
        if (event.gui instanceof GuiMainMenu)
            event.gui = new GuiGenerator();
    }
}
