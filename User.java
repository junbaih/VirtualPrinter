import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class User
    extends Thread
{
	//public static DirectoryManager manager;
	int index;
	// for hw8, to simply test the program, each user has its own disk and printer with the same id. 
	//public Disk disk;// = new Disk(10240,index);
	//Printer printer;// = new Printer(index);
	//
	
	StringBuffer buffer = new StringBuffer();
    User(int id)
    {
    	super();
    	index = id;
    	//disk= new Disk(10240,index);
    	//printer = new Printer(index);
    }
   public void run()
    {
	   BufferedReader reader = null;
	   String line = null;
	try {
		reader = new BufferedReader(new FileReader("USER"+index));
		line = reader.readLine();
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	
   	//System.out.println(line.substring(0,5));
   	
   	while(line!=null)
   	{
   		//System.out.println(line+"END");
   		
   		if(line.length()>4&&line.substring(0,5).equals(".save"))
   		{
   			System.out.println("User "+index+" issues a WRITE command");
   			int diskindex = 0;
   			int startsector = 0;
   			int seclength = 0;
   			String filename = line.substring(6);
   			try
   			{
   				FileInfo fi= os.directory.lookup(filename);
   				diskindex = fi.diskNumber;
   				startsector = fi.startingSector;
   				System.out.println("DIRECTORY OVERWRITE DISK "+diskindex+" SECTOR "+startsector);
   				//seclength = fi.fileLength;   // could not actually handle memory overwrite 
   			}
   			catch(NullPointerException e)
   			{
   				try {
					diskindex = os.DiskManager.request();
					startsector = os.disks[diskindex].nextemptySector();
	   				System.out.println("DIRECTORY NEW DISK "+diskindex+"FOR USER "+index);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
   				
   			}
   			//System.out.println("Jump in ");
   			// need to modify for hw9
   			//String Diskname = line.substring(6, 8);
   			seclength  = 1;
   			
   			Disk assigneddisk = os.disks[diskindex];
   			//line = reader.readLine();
   			while(!line.substring(0, 4).equals(".end"))
   			{
   				try {
					line = reader.readLine();
					if(line.equals(".end"))
	   					break;
	   				
	   				buffer.delete(0, buffer.capacity());
	   				buffer.append(line);
	   				buffer.append("\r");
	   				//
	   				//System.out.println(buffer.toString());
	   				//
	   				assigneddisk.write(startsector+seclength, buffer);
	   				++seclength;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
   				
   				
   			}
   			buffer.delete(0, buffer.capacity());
   			FileInfo tostore = new FileInfo(diskindex,startsector,seclength);
   			os.directory.enter(filename, tostore);
   			os.DiskManager.release(diskindex);
   			//debug
   			//for(int i=0;i<10;++i)
   			//{
   			//	System.out.println(disk.sectors[i]);
   			//}
   		}
   		if(line.length()>6&&line.substring(0,6).equals(".print"))
   		{
   			// need to modify for hw9
   			/*StringBuffer printerBuffer = new StringBuffer();
   			for(int i = 0; i<10;++i)
   			{	
   				disk.read(i, printerBuffer);
   				printer.print(printerBuffer);
   			}*/
   			String filename = line.substring(7);
   			PrintJobThread printjob;
			try {
				printjob = new PrintJobThread(filename);
				printjob.start();
	   			System.out.println("User "+index+" issues a PRINTING command");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   			
   		}
   		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		
   	}
   	try {
		reader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	System.out.println("RETURN");
    }
}
