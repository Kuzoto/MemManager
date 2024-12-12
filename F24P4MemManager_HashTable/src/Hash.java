/**
 * Hash table class
 *
 * @author Christian Calvo
 * @version 1.0
 */

public class Hash {
    
    //Table containing all records for this hash table
    private Record[] allrecords;
    //Stores the number of records in this hash table
    private int numberOfRecords;
    private Record tombstone;
    //Stores the size of the Table
    private int size;
    
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new Hash object
     * 
     * @param initSize
     *          The initial size of the hash table 
     */
    public Hash(int initSize)
    {
        allrecords = new Record[initSize];
        numberOfRecords = 0;
        size = initSize;
        tombstone = new Record(-1, null);
    }
    
    /**
     * Compute the hash function
     * 
     * @param i
     *            The int that we are hashing
     * @param length
     *            Length of the hash table (needed because this method is
     *            static)
     * @return
     *         The hash function value (the home slot in the table for this key)
     */
    public static int h(int i, int length) {
        return (int)(i % length);
    }
    
    /**
     * Insert a record into the hash table
     * 
     * @param input
     *            The id that we are inserting
     * @param handle
     *            The MemBlock handle of the seminar information
     * 
     */
    public void insert(int input, MemBlock handle) {
        //check if the numberOfRecords is greater than half the size of the list
        if (numberOfRecords == size / 2)
        {
            int oldSize = size;
            size = size * 2;
            //create a temp table to store our records 
            Record[] temp = allrecords;
            //create a new table twice as large as the original
            allrecords = new Record[size]; 
            numberOfRecords = 0;
            //loop through the temp table and rehash the records
            for (int i = 0; i < oldSize; i++)
            {
                //skip over null spaces and tombstones
                if (!(temp[i] == null) && !(temp[i] == tombstone))
                {
                    insert(temp[i].key(), temp[i].handle());
                }
            }
            System.out.println("Hash table expanded to " + size + " records");
        }
        
        int home = h(input, size); // Home position for e
        int pos = home; // Init probe sequence
        
        //probe until we find a null spot or tombstone
        for (int i = 1; (!(allrecords[pos] == null) && 
            !(tombstone == allrecords[pos])); i++) 
        {
            //Double Check for duplicates
            if (input == allrecords[pos].key()) 
            {
                return;
            }
            pos = probe(home, i); // probe
        }
        //create a new record and add it to the table in the current pos
        allrecords[pos] = new Record(input, handle);
        numberOfRecords++;
        
    }
    
    /**
     * Find a song or artist in the hash table
     * 
     * @param input
     *            The string that we are searching for
     * @return
     *          The record with given key
     */
    public Record find(int input)
    {
        int home = h(input, size); // Home position for e
        int pos = home; // Init probe sequence
        //probe until a null spot or a matching key is found
        for (int i = 1; !(allrecords[pos] == null) && 
            (input != (allrecords[pos]).key()); i++)
        {
            pos = probe(home, i);
        }
        
        return allrecords[pos];
    }
    
    /**
     * Remove a song or artist from the hash table
     * 
     * @param input
     *            The string that we are removing
     */
    public void remove(int input)
    {
        int home = h(input, size); // Home position for e
        int pos = home; // Init probe sequence
        //probe until a null spot or a matching key is found
        for (int i = 1; (!(allrecords[pos] == null) && 
            (input != allrecords[pos].key())); i++)
        {
            pos = probe(home, i);
        }
        /**check that the current pos is not null and 
         * that its key equals what we are searching for
         * this check may be redundant but I kept it for safety
        */
        if (!(allrecords[pos] == null))
        {
            //set the matching to a tombstone record
            allrecords[pos] = tombstone;
            numberOfRecords--; //remove the record from the count
            System.out.println("Record with ID " + input 
                + " successfully deleted from the database");
        }
        else
        {
            System.out.println("Delete FAILED -- There is no "
                + "record with ID " + input);
        }
    }
    
    /**
     * Print the contents of the hash table
     * 
     * @return
     *          The number of records printed
     */
    public int print()
    {
        int count = 0; //Records printed
        for (int i = 0; (i < size); i++)
        {
            //Check if a record is a tombstone
            if (!(allrecords[i] == null) && (tombstone == allrecords[i]))
            {
                System.out.println(i + ": TOMBSTONE");
                //do not include in count of printed records
            }
            else if (!(allrecords[i] == null)) //check if a record is not null
            {
                //Print the record and increment count
                System.out.println(i + ": " + allrecords[i].key());
                count++;
            }
        }
        return count;
    }
    
    /**
     * Return the number of records in the hash table
     * 
     * @return
     *          The number of records in the table
     */
    public int getNumRecords()
    {
        return numberOfRecords;
    }
    
    /**
     * Return the size of the hash table
     * 
     * @return
     *          The size of the table
     */
    public int getSize()
    {
        return size;
    }
    
    /**
     * Quadratic Probing function
     * 
     * @param home
     *          The home position for this probe
     * @param i
     *          The iteration of this probe
     * @return
     *          The probed position
     */
    public int probe(int home, int i)
    {
        return (home + ((i * i) + i) / 2) % size;
    }
}
