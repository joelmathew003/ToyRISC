package generic;

import java.util.Comparator;
import java.util.PriorityQueue;

import generic.Event;
import processor.Clock;

public class EventQueue {
	
	PriorityQueue<Event> queue;
	
	public EventQueue()
	{
		queue = new PriorityQueue<Event>(new EventComparator());
	}
	public void removeEvent()
	{
		Event e;
		
		if(queue.isEmpty() == false && queue.peek().getEventType() == Event.EventType.values()[1]){
			e = queue.poll();
			System.out.println(e);
		}
		//System.out.println(e.getEventType());
		//System.out.println(Event.EventType.values()[1]);}
		//e = queue.poll();
		//System.out.println(e);
	}
	public void addEvent(Event event)
	{
		queue.add(event);
	}

	public void processEvents()
	{
		//System.out.println("1");
		while(queue.isEmpty() == false && queue.peek().getEventTime() <= Clock.getCurrentTime())
		{
			//System.out.println("1");
			Event event = queue.poll();
			//System.out.println("1");
			event.getProcessingElement().handleEvent(event);
		}
	}
}

class EventComparator implements Comparator<Event>
{
	@Override
    public int compare(Event x, Event y)
    {
		if(x.getEventTime() < y.getEventTime())
		{
			return -1;
		}
		else if(x.getEventTime() > y.getEventTime())
		{
			return 1;
		}
		else
		{
			return 0;
		}
    }
}
