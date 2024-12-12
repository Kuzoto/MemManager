//import java.io.IOException;
//import java.io.RandomAccessFile;

/**
 * Memory Manager class
 *
 * @author kuzoto
 * @version November 2024
 */
public class MemManager {
    private DoubleLL freed;
    private int numFreed;
    private int size;
    private int initSize;
    private int len;
    private byte[] memArray;
    
    
    // ~ Constructor ...........................................................
    
    /**
     * Create a new MemManager object
     * 
     * @param poolsize
     *          The initial size of the memory pool
     */
    public MemManager(int poolsize)
    {
        memArray = new byte[poolsize];
        freed = new DoubleLL();
        freed.getHead().setNext(new DoubleLL.DLLNode(new MemBlock(0, poolsize),
            freed.getHead(), null));
        numFreed = 1;
        initSize = poolsize;
        size = poolsize;
        len = 0;
    }
    
    /**
     * Store a record and return a handle to the memory block
     * 
     * @param info
     *          The serialized seminar information
     * @param inSize
     *          The amount of bytes being inserted
     * @return
     *          A MemBlock object representing this record in memory
     */
    public MemBlock insert(byte[] info, int inSize) 
    {
        int pos;
        if (numFreed > 0)
        {
            DoubleLL.DLLNode curr = freed.getHead();
            while ((curr.getNext() != null) && 
                (curr.getNext().getData().length() < inSize))
            {
                curr = curr.getNext();
            }
            if ((curr.getNext() != null))
            {
                pos = curr.getNext().getData().pos();
                if ((curr.getNext().getData().length()) - inSize == 0) 
                {
                    curr.setNext(curr.getNext().getNext());
                    numFreed--;
                }
                else
                {
                    MemBlock block = curr.getNext().getData();
                    block.setLength(block.length() - inSize);
                    block.setPos(block.pos() + inSize);
                }
            }
            else
            {
                expand();
                return insert(info, inSize);
            }
        }
        else
        {
            expand();
            return insert(info, inSize);
        }
    
        System.arraycopy(info, 0, memArray, pos, inSize);
        len += inSize;
        return new MemBlock(pos, inSize);
    }

    /**
     * Release the memory associated with a given MemBlock
     * 
     * @param h
     *          The MemBlock handle of the memory we want to release 
     */
    public void release(MemBlock h)
    {
        DoubleLL.DLLNode curr = freed.getHead();
        while ((curr.getNext() != null) && 
            (h.pos() > curr.getNext().getData().pos()))
        {
            curr = curr.getNext();
        }
        if (curr.getNext() == null)
        {
            curr.setNext(new DoubleLL.DLLNode(h, curr, null));
            numFreed++;
        }
        else if ((h.pos() + h.length()) == curr.getNext().getData().pos())
        {
            merge(h, curr.getNext().getData());
        }
        else
        {
            curr.setNext(new DoubleLL.DLLNode(h, curr, curr.getNext()));
            numFreed++;
        }
        if (curr != freed.getHead())
        {
            if ((curr.getData().pos() + curr.getData().length()) 
                == curr.getNext().getData().pos())
            {
                merge(curr.getData(), curr.getNext().getData());
                //Remove the node that was merged into the next node
                curr.getPrev().setNext(curr.getNext());
                curr.getNext().setPrev(curr.getPrev());
                numFreed--;
            }
        }
    }
    
    /**
     * Merge one MemBlock into another
     * 
     * @param src
     *          The MemBlock being merged
     * @param dest
     *          The MemBlock where the merged data will end up
     */
    public void merge(MemBlock src, MemBlock dest)
    {
        dest.setPos(src.pos());
        dest.setLength(src.length() + dest.length());
    }

    /**
     * Get the record associated with a given MemBlock
     * 
     * @param h
     *          The handle associated with the record we want
     * @return
     *          The record associated with the given handle
     */
    public byte[] getRecord(MemBlock h)
    {
        byte[] result = new byte[h.length()];
        System.arraycopy(memArray, h.pos(), result, 0, h.length());
        
        return result;
    }
    
    /**
     * Expand the memory pool for the byte array
     */
    public void expand()
    {
        int oldSize = size;
        size = size + initSize;
        DoubleLL.DLLNode curr = freed.getHead();
        if (numFreed > 0)
        {
            while ((curr.getNext() != null))
            {
                curr = curr.getNext();
            }
            MemBlock block = curr.getData();
            block.setLength(block.length() + initSize);
        }
        else
        {
            curr.setNext(new DoubleLL.DLLNode(new MemBlock(oldSize, initSize),
                curr, null));
            numFreed++;
        }
        //create a temp table to store our records 
        byte[] temp = memArray;
        //create a new table twice as large as the original
        memArray = new byte[size];
        System.arraycopy(temp, 0, memArray, 0, oldSize);
        System.out.println("Memory pool expanded to " + size + " bytes");
    }
    
    /**
     * Print the freeblocks list
     */
    public void print()
    {
        if (numFreed == 0)
        {
            System.out.println("There are no freeblocks in the memory pool");
            return;
        }
        DoubleLL.DLLNode curr = freed.getHead();
        while (curr.getNext() != null)
        {
            System.out.print("(" + curr.getNext().getData().pos() + 
                "," + curr.getNext().getData().length() + ")");
            curr = curr.getNext();
            if (curr.getNext() != null)
            {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
    
    /**
     * Return the length of the byte array
     * 
     * @return
     *          The number of bytes stored in the memory pool
     */
    public int len()
    {
        return len;
    }
    
    /**
     * Get the size of the memory pool
     * 
     * @return
     *          The size of the memory pool
     */
    public int size()
    {
        return size;
    }
    
    /**
     * Get the number of free blocks
     * 
     * @return
     *          The number of free blocks
     */
    public int numFreed()
    {
        return numFreed;
    }
}