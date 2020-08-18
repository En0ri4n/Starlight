package fr.eno.starlight.client.screen.button;

import java.awt.Color;

import fr.eno.starlight.client.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;

public class TextSpeechButton extends Button
{
	private final Minecraft mc = Minecraft.getInstance();

	public TextSpeechButton(int xIn, int yIn, int widthIn, int heightIn, String msg, IPressable onPress)
	{
		super(xIn, yIn, widthIn, heightIn, msg, onPress);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		if(this.visible)
		{
			boolean hover = GuiUtils.isMouseInRect(x, y, width, height, mouseX, mouseY);

			AbstractGui.fill(this.x, this.y, this.x + this.width, this.y + this.height, Color.BLACK.getRGB());
			AbstractGui.fill(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, Color.WHITE.getRGB());
			
			if(!hover)
				AbstractGui.fill(this.x + 2, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, Color.BLACK.getRGB());
			else
				AbstractGui.fill(this.x + 2, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, Color.DARK_GRAY.getRGB());

			mc.fontRenderer.drawStringWithShadow(this.getMessage(), this.x + this.width / 2 - mc.fontRenderer.getStringWidth(getMessage()) / 2, this.y + this.height / 2 - this.mc.fontRenderer.FONT_HEIGHT / 2, Color.WHITE.getRGB());
		}
	}
}
