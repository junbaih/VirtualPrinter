import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class Printer
    extends Thread
{
	int index;
	//static DirectoryManager directory= new DirectoryManager();
    Printer(int id)
    {
    	super();
    	index = id;
    }
    void print(StringBuffer b) throws InterruptedException, IOException 
    {
    	sleep(2750);
    	BufferedWriter writer = new BufferedWriter(new FileWriter("PRINTER"+index,true));
    	writer.write(b.toString());
    	writer.close();
    	//Path path= Paths.get("PRINTER"+index);
    	//byte[] stringtobytes = b.toString().getBytes();
    	//Files.write(path,stringtobytes,StandardOpenOption.APPEND);
    }
}
class PrintJobThread  
	extends Thread
{
	String filename;
	//static DirectoryManager directory = os.directory;
	
	PrintJobThread(String fn) throws InterruptedException
	{
		super();
		filename = fn;
	}
	public void run()
	{
		int printerindex;
		try {
			
			printerindex = os.PrinterManager.request();
			Printer AssignedPrinter = os.printers[printerindex];
			FileInfo fl = os.directory.lookup(filename);
			Disk disktoread =os.disks[fl.diskNumber];
			int startsector = fl.startingSector;
			int seclength = fl.fileLength;
			for(int i = 0;i<seclength-1;++i )
			{
				StringBuffer b = new StringBuffer();
				disktoread.read(startsector+i, b);
				AssignedPrinter.print(b);
				System.out.println("Printer "+(printerindex+1)+" prints "+b.toString());
			}
			os.PrinterManager.release(printerindex);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}