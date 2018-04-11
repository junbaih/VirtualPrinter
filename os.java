import java.io.IOException;

class ResourceManager
{
	boolean isFree[];
	ResourceManager(int numberOfItems)
	{
		isFree = new boolean[numberOfItems];
		for(int i=0;i<isFree.length;++i)
			isFree[i]=true;
	}
	synchronized int request() throws InterruptedException
	{
		while(true)
		{
			for(int i =0;i<isFree.length;++i)
				if(isFree[i])
				{
					isFree[i]=false;
					return i;
				}
			this.wait();
		}
	}
	synchronized void release(int index)
	{
		isFree[index]=true;
		this.notify();
	}
}

public class os
{
	 static int NUMBER_OF_USERS = 4;
	 static int NUMBER_OF_DISKS = 2;
	 static int NUMBER_OF_PRINTERS = 3;
	 static ResourceManager DiskManager = new ResourceManager(NUMBER_OF_DISKS);
	 static ResourceManager PrinterManager = new ResourceManager(NUMBER_OF_PRINTERS);
	 static Printer[] printers = new Printer[NUMBER_OF_PRINTERS];
	 static Disk[] disks = new Disk[NUMBER_OF_DISKS]; 
	 static DirectoryManager directory = new DirectoryManager();
	public static void main(String[] args) throws IOException, InterruptedException
	{
//test disk & printer
		/*Disk disk = new Disk(1024,1);
		
		Printer printer6 = new Printer(6);
		StringBuffer b = new StringBuffer("what the heck");
		disk.write(1, b);
		printer6.print(b);*/
		//User user5 = new User(5);
		for(int i=0;i<NUMBER_OF_DISKS;++i)
			disks[i]=new Disk(10240,i+1);
		for(int i=0;i<NUMBER_OF_PRINTERS;++i)
			printers[i]=new Printer(i+1);
		User[] userlist = new User[NUMBER_OF_USERS];
		for(int i=0;i<NUMBER_OF_USERS;++i)
			userlist[i]=new User(i+1);
		for(int i = 0;i<NUMBER_OF_USERS;++i)
			userlist[i].start();
	

	}
}