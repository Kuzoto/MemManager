/**
 * Record class
 *
 * @author Christian Calvo
 * @version 1.0
 */
public class Record
{
    private int key; //The id of the seminar
    private MemBlock handle; //The node representing this seminar
    
    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new record object
     * 
     * @param k
     *          The key for this record
     * @param b
     *          The block for this record
     */
    public Record(int k, MemBlock b)
    {
        key = k;
        handle = b;
    }
    
    /**
     * Return the key for this record
     * 
     * @return
     *      The key of this record
     */
    public int key()
    {
        //Check that the key for this record is not null
        return key;
    }
    
    /**
     * Return the block for this record
     * 
     * @return
     *      The block of this record
     */
    public MemBlock handle()
    {
        return handle;
    }
}