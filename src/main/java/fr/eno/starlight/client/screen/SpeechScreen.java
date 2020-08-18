package fr.eno.starlight.client.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.eno.starlight.References;
import fr.eno.starlight.client.screen.button.TextSpeechButton;
import fr.eno.starlight.utils.ScreenTravelManager;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TextComponent;

public abstract class SpeechScreen extends Screen
{
	private ScreenTravelManager manager;
	private TextSpeechButton nextButton;
	private int currentLine;
	private List<String> lines;
	private String requestString;
	
	public SpeechScreen(ScreenTravelManager managerIn)
	{
		super(References.getTranslate("screen.TravelRequestSpace.title"));
		this.manager = managerIn;
		this.currentLine = 0;
		requestString = "";
	}
	
	@Override
	protected void init()
	{
		MainWindow window = this.minecraft.getMainWindow();
		int width = window.getScaledWidth();
		int height = window.getScaledHeight();
		this.addButton(nextButton = new TextSpeechButton(width / 14 * 10 - 20, height - 40, 50, 20, References.getTranslate("screen.SpeechScreen.next").getFormattedText(), b ->
		{
			this.currentLine++;
			if(this.currentLine > this.lines.size() - 1)
			{
				SpeechScreen.this.buttons.forEach(button -> button.visible = true);
				b.visible = false;
				b.active = false;
				
				lines.clear();
				this.requestString = this.getRequest().getFormattedText();
				this.currentLine = 0;
			}
		}));
		
		this.buttons.forEach(b -> 
		{
			b.visible = false;
		});

		lines = new ArrayList<String>(this.font.listFormattedStringToWidth(this.getSpeech().getFormattedText(), window.getScaledWidth() - (window.getScaledWidth() / 2 + 10)));
		
		nextButton.visible = true;
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
		this.minecraft.fontRenderer.drawStringWithShadow(this.getCurrentLine(this.currentLine), x + 20, y - height + 20, Color.WHITE.getRGB());
		this.minecraft.fontRenderer.drawStringWithShadow(this.requestString, (x + width) / 2 - font.getStringWidth(this.requestString) / 2, y - height + 40, Color.WHITE.getRGB());
		this.minecraft.fontRenderer.drawStringWithShadow(this.getCurrentLine(this.currentLine + 1), x + 20, y - height + 40, Color.WHITE.getRGB());
		this.minecraft.fontRenderer.drawStringWithShadow(this.getCurrentLine(this.currentLine + 2), x + 20, y - height + 60, Color.WHITE.getRGB());
		super.render(mouseX, mouseY, partialTicks);
	}
	
	public String getCurrentLine(int index)
	{
		if(index > lines.size() - 1)
			return "";
		
		return lines.get(index);
	}
	
	public final ScreenTravelManager getManager()
	{
		return this.manager;
	}
	
	private final TextComponent getRequest()
	{
		return this.getManager().getRequest();
	}
	
	public final TextComponent getSpeech()
	{
		return this.getManager().getSpeech();
	}
}