package fr.eno.starlight.client.utils;

import net.minecraft.util.text.ITextComponent;

public class Title
{
	private ITextComponent text;
	private double fade;
	private double time;
	
	public Title(ITextComponent text, double fade, double time)
	{
		this.text = text;
		this.fade = fade;
		this.time = time;
	}

	public ITextComponent getText()
	{
		return text;
	}

	public double getFade()
	{
		return fade;
	}

	public double getTime()
	{
		return time;
	}
}
