package fr.eno.starlight.client.utils;

public class GuiUtils
{
	public static boolean isMouseInRect(int x, int y, int width, int height, int mouseX, int mouseY)
	{
		return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;
	}
}
