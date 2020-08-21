package fr.eno.starlight;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class References
{
	public static final String MOD_ID = "starlight";
	public static final String MOD_NAME = "Star Light Mod";
	public static final String MOD_VERSION = "1.0.0";
	
	public static ResourceLocation getLoc(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}
	
	public static TextComponent getTranslate(String arg)
	{
		return new TranslationTextComponent(References.MOD_ID + "." + arg);
	}
}
