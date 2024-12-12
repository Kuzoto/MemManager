import student.TestCase;

/**
 * @author Christian Calvo
 * @version 1.0
 */
public class HashTest extends TestCase 
{
    //table being used for testing
    private Hash table;
    private Hash smallTable;
    
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        table = new Hash(10);
        smallTable = new Hash(2);
    }
    
    /**
     * Check out the insert method
     */
    public void testInsert()
    {
        //Make sure that the table expands correctly
        smallTable.insert(1, null);
        assertTrue(smallTable.getSize() == 2);
        smallTable.insert(1, null);
        assertTrue(smallTable.getNumRecords() == 1);
        assertTrue(smallTable.getSize() == 4);
        //Make sure new Records are inserted correctly
        table.insert(1, null);
        assertTrue(table.getNumRecords() == 1);
        //Make sure no duplicate Records are inserted
        table.insert(1, null);
        assertTrue(table.getNumRecords() == 1);
        //Make sure insert is case sensitive
        table.insert(2, null);
        assertTrue(table.getNumRecords() == 2);
        table.insert(3, null);
        assertTrue(table.getNumRecords() == 3);
        table.insert(4, null);
        assertTrue(table.getNumRecords() == 4);
        table.insert(5, null);
        assertTrue(table.getNumRecords() == 5);
        assertTrue(table.getSize() == 10);
        //Make sure that the table expands when half full
        table.insert(6, null);
        assertTrue(table.getSize() == 20);
        assertTrue(table.getNumRecords() == 6);
        table.insert(7, null);
        assertTrue(table.getNumRecords() == 7);
        table.insert(8, null);
        assertTrue(table.getNumRecords() == 8);
        //Make sure the Records are inserted correctly after removes
        table.remove(8);
        table.insert(8, null);
        assertTrue(table.getNumRecords() == 8);
        table.remove(8);
        table.insert(9, null);
        assertTrue(table.getNumRecords() == 8);
        table.insert(10, null);
        assertTrue(table.getNumRecords() == 9);
        table.insert(11, null);
        assertTrue(table.getNumRecords() == 10);
        table.insert(12, null);
        assertTrue(table.getSize() == 40);
        assertTrue(table.getNumRecords() == 11);
    }
    
    /**
     * Check out the print method
     */
    public void testPrint()
    {
        //Make sure print handles empty tables correctly
        assertTrue(table.print() == 0);
        table.insert(1, null);
        table.insert(2, null);
        table.insert(10, null);
        table.insert(3, null);
        systemOut().clearHistory();
        //Make sure the table is printed correctly
        assertTrue(table.print() == 4);
        assertFuzzyEquals("0: 10\r\n"
            + "1: 1\r\n"
            + "2: 2\r\n"
            + "3: 3", systemOut().getHistory());
        table.remove(2);
        systemOut().clearHistory();
        //Make sure the table is printed correctly after a remove
        assertTrue(table.print() == 3);
        assertFuzzyEquals("0: 10\r\n"
            + "1: 1\r\n"
            + "2: TOMBSTONE\r\n"
            + "3: 3", systemOut().getHistory());
    }
    
    /**
     * Check out the remove method
     */
    public void testRemove()
    {
        systemOut().clearHistory();
        //Make sure that invalid removes are handled correctly
        table.remove(1);
        /*assertFuzzyEquals("|Sleepy| does not exist in the Song database.", 
            systemOut().getHistory());*/
        table.insert(1, null);
        table.insert(2, null);
        table.insert(10, null);
        table.insert(3, null);
        assertTrue(table.getNumRecords() == 4);
        systemOut().clearHistory();
        //Make sure that a successful remove gives the correct output
        table.remove(3);
        assertFuzzyEquals("Record with ID 3 successfully "
            + "deleted from the database", systemOut().getHistory());
        assertTrue(table.getNumRecords() == 3);
        table.remove(3);
        assertTrue(table.getNumRecords() == 3);
        table.remove(22);
        assertTrue(table.getNumRecords() == 3);
    }
    
    /**
     * Check out the probe method of Hash
     */
    public void testProbe()
    {
        table.insert(1, null);
        table.insert(2, null);
        table.insert(10, null);
        table.insert(3, null);
        table.insert(22, null);
        table.print();
        int home = table.h(22, 10);
        assertEquals(table.probe(home, 2), 5);
        assertEquals(table.probe(home, 0), 2);
        assertEquals(table.probe(table.h(10, 10), 1), 1);
        assertEquals(table.probe(table.h(10, 10), 2), 3);
        assertEquals(table.probe(table.h(2, 10), 0), 2);
    }
    
    /**
     * Check out the find method of Hash
     */
    public void testFind()
    {
        assertNull(table.find(10));
        table.insert(1, null);
        table.insert(2, null);
        table.insert(10, null);
        table.insert(3, null);
        table.insert(22, null);
        assertEquals(table.find(22).key(), 22);
    }
}