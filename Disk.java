import java.util.Hashtable;

class Disk
    extends Thread
{
	int index;
	int capacity;
	static final int NUM_SECTORS = 1024;
	StringBuffer sectors[]= new StringBuffer[NUM_SECTORS];
	Disk(int cap,int id)
	{
		super();
		capacity = cap;
		index = id;
		for(int i = 0; i<1024;++i)
		{
			sectors[i] = new StringBuffer(capacity/NUM_SECTORS);
		}
	}
	int nextemptySector()
	{
		for(int i = 0; i<1024;++i)
		{
			if(sectors[i].length()==0)
				{
					System.out.println("DISK "+ index +" FOUND NEXT EMPTY SECTor "+i);
					return i;
				}
		}
		return -1;
	}
    void read(int sector, StringBuffer data) throws InterruptedException
    { 
    	sleep(200);
    	data.delete(0, data.capacity());
    	StringBuffer temp = sectors[sector];
    	data.append(temp);
    	System.out.println("DEBUG Disk "+index+ "  READ "+ sectors[sector].toString()+" @ sector "+sector);
    }
    void write(int sector, StringBuffer data) throws InterruptedException
    {
    	sleep(200);
    	StringBuffer block = sectors[sector-1];
    	//block.delete(0, capacity/NUM_SECTORS);
    	block.append(data);
    	System.out.println("DEBUG Disk "+index+"writes "+data.toString()+" @ sector "+sector);
    }
}
class FileInfo
{
	public int diskNumber;
	public int startingSector;
	public int fileLength;
	FileInfo(int a, int b, int c)
	{	
		diskNumber =a;
		startingSector = b;
		fileLength = c;
	}
}
class DirectoryManager
{

	Hashtable<String,FileInfo> directory = new Hashtable<String,FileInfo>();
	DirectoryManager()
	{
	}
	void enter(String key, FileInfo file)
	{
		directory.put(key, file);
	}
	FileInfo lookup(String key)
	{
		return directory.get(key);
	}
}
