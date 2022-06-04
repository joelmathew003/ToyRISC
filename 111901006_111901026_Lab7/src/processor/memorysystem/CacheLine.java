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

public class CacheLine{
	int data;
	int tag;
	public void set_data(int Data)
	{
		data = Data;
	}
	public int get_data()
	{
		return data;
	}
	public void set_tag(int address)
	{
		tag = address;
	}
	public int get_tag()
	{
		return tag;
	}
}
