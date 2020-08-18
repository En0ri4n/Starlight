package fr.eno.starlight;

import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class References
{
	public static final String MOD_ID = "starlight";
	public static final String MOD_NAME = "Star Light Mod";
	
	public static TextComponent getTranslate(String arg)
	{
		return new TranslationTextComponent(References.MOD_ID + "." + arg);
	}
}
