
/**
 * Controller class
 *
 * @author kuzoto
 * @version November 2024
 */
public class Controller 
{
    //The Hash Table
    private Hash table;
    //The memory manager 
    private MemManager memory;
    
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new controller.
     * 
     * @param initMemSize
     *          The initial size of the memory pool
     * @param initHashSize
     *          The initial size of the hash table
     */
    public Controller(int initMemSize, int initHashSize)
    {
        table = new Hash(initHashSize);
        memory = new MemManager(initMemSize);
    }
    
    /**
     * Insert a new Seminar into each of the Binary Search Trees
     * 
     * @param id
     *            The ID of the new seminar we want to insert
     * @param title
     *            The title of the new seminar we want to insert
     * @param date
     *            The date of the new seminar we want to insert
     * @param len
     *            The len of the new seminar we want to insert
     * @param x
     *            The x position of the new seminar we want to insert
     * @param y
     *            The y position of the new seminar we want to insert
     * @param cost
     *            The cost of the new seminar we want to insert
     * @param keywords
     *            The keywords related to the new seminar we want to insert
     * @param desc
     *            The description of the new seminar we want to insert
     */
    public void insert(int id, String title, String date, int len, 
        short x, short y, int cost, 
        String[] keywords, String desc) throws Exception
    {
        //Find the seminar
        Record s = table.find(id);
        MemBlock handle;
        
        //Check if the song is in the table already
        if (s == null)
        {
            Seminar temp = new Seminar(id, title, date, len, x, y, 
                cost, keywords, desc);
            byte[] in = temp.serialize();
            //Create a new node in graph for this song and save its index
            handle = memory.insert(in, in.length);
            //Create a new Record in the table for this song
            //and connect the record to the graph
            table.insert(id, handle);
            //Print success
            System.out.println("Successfully inserted record with ID " + id);
            System.out.println(temp.toString());
            System.out.println("Size: " + in.length);
        }
        else
        {
            System.out.println("Insert FAILED - There is already "
                + "a record with ID " + id);
            return;
        }     
    }
    
    /**
     * Removes a song or artist from the hash tables and graph
     * 
     * @param id
     *            The id of what we want to remove
     */
    public void remove(int id)
    {
        MemBlock index;
        Record r;
        
        //Find the record in the table
        r = table.find(id);
        //Check if the artist was found
        if (r != null)
        {
            //Get the index for this Record
            index = r.handle();
            //Remove the node connected to this Record
            memory.release(index);
        }
        //Remove the record from the table
        table.remove(id);
    }
    
    /**
     * Search for a seminar with given id
     * @param id
     *       The id we want to search for
     */
    public void search(int id) throws Exception
    {
        Record result = table.find(id);
        if (result == null) 
        { 
            System.out.println("Search FAILED -- There is no record with ID " 
                + id);
            return; 
        }
        System.out.println("Found record with ID " + id + ":");
        byte[] data = memory.getRecord(result.handle());
        Seminar sem = Seminar.deserialize(data);
        System.out.println(sem.toString());
    }
    
    /**
     * Print the song or artist from the hash table or graph
     * 
     * @param type
     *            The type we want to print
     */
    public void print(String type)
    {
        //Check the type we want to print
        if (type.equals("hashtable"))
        {
            //Print the artists table and the number of artists
            System.out.println("Hashtable:");
            
            int count = table.print();
            
            System.out.println("total records: " + count);
        }
        else if (type.equals("blocks"))
        {
            //Print the graph
            System.out.println("Freeblock List:");
            memory.print();
        }
    }
}