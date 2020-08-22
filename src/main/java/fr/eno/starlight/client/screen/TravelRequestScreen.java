package fr.eno.starlight.client.screen;

import java.util.UUID;

import fr.eno.starlight.References;
import fr.eno.starlight.client.screen.button.TextSpeechButton;
import fr.eno.starlight.packets.NetworkManager;
import fr.eno.starlight.packets.TravelToDimensionPacket;
import fr.eno.starlight.packets.UpdateStarEntityPacket;
import fr.eno.starlight.utils.Travels;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.screen.Screen;

public class TravelRequestScreen extends RequestScreen
{
	private UUID starId;
	
	public TravelRequestScreen(Travels manager, UUID starIdIn)
	{
		super(manager);
		this.starId = starIdIn;
	}
	
	@Override
	protected void init()
	{
		MainWindow window = this.minecraft.getMainWindow();
		int width = window.getScaledWidth();
		int height = window.getScaledHeight();
		this.addButton(new TextSpeechButton(width / 6 + 40, height - 40, 50, 20, References.getTranslate("screen.TravelRequestSpace.accept").getFormattedText(), b -> { NetworkManager.getNetwork().sendToServer(new TravelToDimensionPacket(this.getManager().getDimensionLocation(), starId)); }));
		this.addButton(new TextSpeechButton(width / 6 * 4, height - 40, 50, 20, References.getTranslate("screen.TravelRequestSpace.deny").getFormattedText(), b -> { this.minecraft.displayGuiScreen((Screen) null);}));
		super.init();
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	@Override
	public void onClose()
	{
		NetworkManager.getNetwork().sendToServer(new UpdateStarEntityPacket(starId));
	}
}