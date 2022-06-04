package processor.memorysystem;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Simulator;
import processor.Clock;
import generic.Element;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Event;
import generic.Event.EventType;
import configuration.Configuration;
import processor.memorysystem.MainMemory;
import java.lang.Integer;
import processor.memorysystem.CacheLine;
public class Cache implements Element{
	Processor containingProcessor;

	CacheLine[] lines;
	int Cachesize = 0;
	int nooflines,Latency;
	Element req_element;
	//PriorityQueue<Event> queue;
	int j = 0;
	public boolean isnotFull()
	{
		if(Cachesize < nooflines)
			return true;
		return false;
	}
	public Cache(Processor containingProcessor,int latency,int size)
	{
		this.containingProcessor = containingProcessor;
		nooflines = size/4;
		lines = new CacheLine[nooflines];		
		Latency = latency;
	}
	public void cacheRead(int address,Element reqelement)
	{
		int value;
		for(int i=0;i<Cachesize;i++)
		{
			if(lines[i].get_tag() == address)
			{
				System.out.println("in cache");
				value = lines[i].get_data();
				Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime() + Latency,
										   this,
										   reqelement,
										   value));
				return ;
			}	
		}
		handleCacheMiss(address);
		req_element = reqelement;
	}
	public void cacheWrite(int address, int value,Element reqelement)
	{
		int f = 0;
		for(int i=0;i<Cachesize;i++)
		{
			if(lines[i].get_tag() == address)
			{
				f = 1;
				lines[i].set_data(value);
				containingProcessor.getMainMemory().setWord(address,value);
				Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency + Latency,
										   this,
										   reqelement,
										   value));
				//Memory Response
			}
		}
		if(f == 0)
		{
			if(isnotFull())
			{
				CacheLine line = new CacheLine();
				line.set_tag(address);
				line.set_data(value);
				lines[Cachesize] = line;
				Cachesize++;
			}
			else
			{
				lines[j].set_tag(address);
				lines[j].set_data(value);
				j = (j+1) % nooflines;
			}
			containingProcessor.getMainMemory().setWord(address,value);
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency + Latency,
										   this,
										   reqelement,
										   value));
			/*handleCacheMiss(address);
			req_element = reqelement;*/
		}		
	}
	public void handleCacheMiss(int address)
	{
		Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime(),
										this ,
										containingProcessor.getMainMemory(),
										address));
		if(isnotFull())
		{
			//Cacheline line;
			CacheLine line = new CacheLine();
			line.set_tag(address);
			line.set_data(Integer.MIN_VALUE);
			lines[Cachesize] = line;
			Cachesize++;
		}
		else
		{
			lines[j].set_tag(address);
			lines[j].set_data(Integer.MIN_VALUE);
			j = (j+1) % nooflines;
		}
		
	}	
	public void handleResponse(int value)
	{
		for(int i=0;i<Cachesize;i++)
		{
			if(lines[i].get_data() == Integer.MIN_VALUE)
			{	
				lines[i].set_data(value);
				break;
			}
		}
		Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime()+ Latency,
										   this,
										   req_element,
										   value));
	}
	@Override
	public void handleEvent(Event e) 
	{
		if(e.getEventType() == EventType.MemoryResponse)
		{
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			handleResponse(event.getValue());
			/*for(int i=0;i<lines.length;i++)
			{
				if(lines[i].get_data() == Integer.MIN_VALUE)
				{	
					lines[i].set_data(event.getValue());
					break;
				}
			}
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime()+ 2,
										   this,
										   event.getRequestingElement(),
										   getWord(event.getAddressToReadFrom())));*/
			
		}
		if(e.getEventType() == EventType.MemoryRead)
		{
			System.out.println("Request Cache");
			MemoryReadEvent event = (MemoryReadEvent) e;
			cacheRead(event.getAddressToReadFrom(),event.getRequestingElement());
		}
		if(e.getEventType() == EventType.MemoryWrite)
		{
			MemoryWriteEvent event = (MemoryWriteEvent) e;
			cacheWrite(event.getAddressToWriteTo(),event.getValue(),event.getRequestingElement());
		}
	}	
}
