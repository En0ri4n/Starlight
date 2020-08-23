package fr.eno.starlight.client.screen;

import net.minecraft.util.text.ITextComponent;

public class SimpleSpeechScreen extends SpeechScreen
{
	private ITextComponent text;
	
	public SimpleSpeechScreen(ITextComponent textIn)
	{
		super(null, null);
		this.text = textIn;
	}
	
	@Override
	public ITextComponent getSpeech()
	{
		return this.text;
	}
	
	@Override
	public void onClose() {}
}
