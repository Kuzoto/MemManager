/**
 * The class creating blocks of memory.
 *
 * @author kuzoto
 * @version November 2024
 */
public class MemBlock {
    private int pos;
    private int length;
    private int key;
    
    // ~ Constructor ...........................................................
    
    /**
     * Create a new MemBlock object
     * 
     * @param pos
     *          The start position of this block in the memory pool
     * @param length
     *          The length of this memory block
     */
    public MemBlock(int pos, int length)
    {
        this.pos = pos;
        this.length = length;
    }
    
    /**
     * Change the start position of this memory block
     * 
     * @param newPos
     *          The new start position
     */
    public void setPos(int newPos)
    {
        pos = newPos;
    }
    
    /**
     * Get the position of this block
     * 
     * @return
     *          The position of this block
     */
    public int pos()
    {
        return pos;
    }
    
    /**
     * Change the length of this memory block
     * 
     * @param newLen
     *          The new length of this block
     */
    public void setLength(int newLen)
    {
        length = newLen;
    }
    
    /**
     * Get the length of this block
     * 
     * @return
     *          The length of this block
     */
    public int length()
    {
        return length;
    }
    
    /**
     * Change the key associated with this block
     * 
     * @param newKey
     *          The new key associated with this block
     */
    public void setKey(int newKey)
    {
        key = newKey;
    }
    
    /**
     * Get the key associated with this block
     * 
     * @return
     *          The key associated with this block
     */
    public long key()
    {
        return key;
    }
}