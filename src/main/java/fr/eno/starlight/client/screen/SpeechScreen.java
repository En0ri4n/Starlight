package fr.eno.starlight.client.screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.eno.starlight.References;
import fr.eno.starlight.client.screen.button.TextSpeechButton;
import fr.eno.starlight.client.utils.Speechs;
import fr.eno.starlight.init.InitDimensions;
import fr.eno.starlight.packets.NetworkManager;
import fr.eno.starlight.packets.RewardPlayerPacket;
import fr.eno.starlight.packets.UpdateStarEntityPacket;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TextComponent;

public class SpeechScreen extends Screen
{
	private Speechs speech;
	private TextSpeechButton nextButton;
	private int currentLine;
	private List<String> lines;
	private boolean showSpeech;
	private UUID starId;
	
	public SpeechScreen(Speechs managerIn, UUID starIdIn)
	{
		super(References.getTranslate("screen.SpeechScreen.title"));
		this.speech = managerIn;
		this.showSpeech = true;
		this.currentLine = 0;
		this.starId = starIdIn;
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
				minecraft.displayGuiScreen((Screen) null);
				
				if(InitDimensions.UTOPIAN_DIM_TYPE == this.speech.getType())
				{
					NetworkManager.getNetwork().sendToServer(new RewardPlayerPacket(this.starId));
				}
			}
		}));
		
		this.buttons.forEach(b -> 
		{
			b.visible = false;
		});

		lines = new ArrayList<String>(this.font.listFormattedStringToWidth(this.getSpeech().getFormattedText(), window.getScaledWidth() - (window.getScaledWidth() / 2 + 20)));
		
		nextButton.visible = true;
		
		if(lines.isEmpty())
		{
			SpeechScreen.this.buttons.forEach(button -> button.visible = true);
			nextButton.visible = false;
			nextButton.active = false;
			this.showSpeech = false;
		}
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
		
		if(showSpeech)
		{
			this.minecraft.fontRenderer.drawStringWithShadow(this.getCurrentLine(this.currentLine), this.width / 2 - font.getStringWidth(this.getCurrentLine(this.currentLine)) / 2, y - height + 20, Color.WHITE.getRGB());
			this.minecraft.fontRenderer.drawStringWithShadow(this.getCurrentLine(this.currentLine + 1), this.width / 2 - font.getStringWidth(this.getCurrentLine(this.currentLine + 1)) / 2, y - height + 40, Color.WHITE.getRGB());
			this.minecraft.fontRenderer.drawStringWithShadow(this.getCurrentLine(this.currentLine + 2), this.width / 2 - font.getStringWidth(this.getCurrentLine(this.currentLine + 2)) / 2, y - height + 60, Color.WHITE.getRGB());
		}
		
		super.render(mouseX, mouseY, partialTicks);
	}
	
	public String getCurrentLine(int index)
	{
		if(index > lines.size() - 1)
			return "";
		
		return lines.get(index);
	}
	
	public final Speechs getManager()
	{
		return this.speech;
	}
	
	public TextComponent getSpeech()
	{
		return this.getManager().getSpeech();
	}
	
	@Override
	public void onClose()
	{
		NetworkManager.getNetwork().sendToServer(new UpdateStarEntityPacket(starId));
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	@Override
	public boolean shouldCloseOnEsc()
	{
		return true;
	}
}