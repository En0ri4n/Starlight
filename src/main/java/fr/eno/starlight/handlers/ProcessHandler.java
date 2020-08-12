package fr.eno.starlight.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ProcessHandler
{
	private static List<IProcess> processes = new ArrayList<IProcess>();
	private static List<IProcess> newProcesses = new ArrayList<IProcess>();

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			Iterator<IProcess> i = processes.iterator();

			while (i.hasNext())
			{
				IProcess process = i.next();
				if (process.isDead())
				{
					i.remove();
				} else
				{
					process.updateProcess();
				}
			}

			if (!newProcesses.isEmpty())
			{
				processes.addAll(newProcesses);
				newProcesses.clear();
			}
		}
	}

	public static void clearHandler()
	{
		processes.clear();
		newProcesses.clear();
	}

	public static void addProcess(IProcess process)
	{
		newProcesses.add(process);
	}

}