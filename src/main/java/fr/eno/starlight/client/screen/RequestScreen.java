package fr.eno.starlight.client.screen;

import java.awt.Color;

import fr.eno.starlight.References;
import fr.eno.starlight.utils.Travels;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TextComponent;

public abstract class RequestScreen extends Screen
{
	private Travels manager;
	private String requestString;

	public RequestScreen(Travels managerIn)
	{
		super(References.getTranslate("screen.RequestScreen.title"));
		this.manager = managerIn;
		requestString = "";
	}

	@Override
	protected void init()
	{
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		MainWindow window = this.minecraft.getMainWindow();
		int x = window.getScaledWidth() / 6;
		int y = window.getScaledHeight() - 10;
		int width = window.getScaledWidth() - x;
		int height = window.getScaledHeight() / 2;
		Screen.fill(x, y, width, height, Color.BLACK.getRGB());
		Screen.fill(x + 4, y - 4, width - 4, height + 4, Color.WHITE.getRGB());
		Screen.fill(x + 6, y - 6, width - 6, height + 6, Color.BLACK.getRGB());

		this.minecraft.fontRenderer.drawStringWithShadow(this.getRequest().getFormattedText(), this.width / 2 - font.getStringWidth(this.requestString) / 2, y - height + 40, Color.WHITE.getRGB());
		
		super.render(mouseX, mouseY, partialTicks);
	}

	public final Travels getManager()
	{
		return this.manager;
	}

	private final TextComponent getRequest()
	{
		return this.getManager().getRequest();
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
}