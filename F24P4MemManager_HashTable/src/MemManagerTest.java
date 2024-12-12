import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  Test the MemManager class
 *
 * @author kuzoto
 * @version November 2024
 */
public class MemManagerTest extends TestCase {
    private MemManager trial;
    private int initSize;
    private Seminar mysem;
    private Seminar mysem2;
    private Seminar mysem3;
    private Seminar mysem4;
    private Seminar mysem5;
    private Seminar mysem6;
    private Seminar mysem7;
    
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() throws Exception
    {
        // Nothing Here
        String[] keywords = {"HCI", "Computer_Science", "VT", "Virginia_Tech"};
        String[] keywords2 = {"Bioinformatics", "computation_biology", 
            "Biology", "Computer_Science", "VT", "Virginia_Tech"};
        String[] keywords3 = {"high_performance_computing", "grids", 
            "VT", "computer", "science"};
        String[] keywords4 = {"HPC", "CSE", "computer_science"};
        String[] keywords5 = {"assembly", "machine_language", 
            "Computer_science", "VT", "Virginia_Tech"};
        String[] keywords6 = {"Virginia_Tech", "computation_biology", 
            "Biology", "Computer_Science", "VT", "Bioinformatics"};
        mysem = new Seminar(1, 
            "Overview of HCI Research at VT", 
            "0610051600", 90, (short)10, (short)10, 45, 
            keywords, 
            "This seminar will present an overview of HCI research at VT");
        mysem2 = new Seminar(2, 
            "Computational Biology and Bioinformatics in CS at Virginia Tech", 
            "0610071600", 60, (short)10, (short)10, 30, 
            keywords2, 
            "Introduction to   bioinformatics and computation biology");
        mysem3 = new Seminar(10, 
            "Computing Systems Research at VT", 
            "0701250830", 30, (short)30, (short)10, 17, 
            keywords3, 
            "Seminar about the      Computing systems research at      VT");
        mysem4 = new Seminar(3, 
            "Overview of HPC and CSE Research at VT", 
            "1203301125", 35, (short)0, (short)0, 25, 
            keywords4, 
            "Learn what kind of    research is done on HPC  and CSE at VT");
        mysem5 = new Seminar(12, 
            "Computer Organization II", 
            "2405231000", 75, (short)15, (short)33, 125, 
            keywords5, 
            "Seminar about how computers execute commands "
            + "and process information");
        mysem6 = new Seminar(5, 
            "Computer Organization II", 
            "2405231000", 75, (short)15, (short)33, 125, 
            keywords5, 
            "Seminar about how computers execute commands "
            + "and process information");
        mysem7 = new Seminar(4, 
            "Computer Organization II", 
            "2405231000", 75, (short)15, (short)33, 125, 
            keywords6, 
            "Seminar about how computers execute commands "
            + "and process information");
        initSize = mysem.serialize().length;
        
        trial = new MemManager(initSize);
    }
    
    /**
     * Check out the len method of MemManager
     */
    public void testLen()
    {
        assertEquals(trial.len(), 0);
    }
    
    /**
     * Check out the size method of MemManager
     */
    public void testSize() throws Exception
    {
        assertEquals(trial.size(), mysem.serialize().length);
    }
    
    /**
     * Check out the numFreed method of MemManager
     */
    public void testNumFreed()
    {
        assertEquals(trial.numFreed(), 1);
    }
    
    /**
     * Check out the insert method of MemManager
     */
    public void testInsert() throws Exception
    {
        assertEquals(trial.len(), 0);
        assertEquals(trial.size(), initSize);
        assertEquals(trial.numFreed(), 1);
        trial.insert(mysem.serialize(), mysem.serialize().length);
        assertEquals(trial.len(), mysem.serialize().length);
        assertEquals(trial.size(), initSize);
        assertEquals(trial.numFreed(), 0);
        trial.insert(mysem2.serialize(), mysem2.serialize().length);
        assertEquals(trial.len(), mysem.serialize().length 
            + mysem2.serialize().length);
        assertEquals(trial.size(), initSize * 3);
        assertEquals(trial.numFreed(), 1);
        trial = new MemManager(initSize);
        trial.insert(mysem2.serialize(), mysem2.serialize().length);
        assertEquals(trial.len(), mysem2.serialize().length);
        assertEquals(trial.size(), initSize * 2);
        assertEquals(trial.numFreed(), 1);
        
    }
    
    /**
     * Check out the print method of MemManager
     */
    public void testPrint() throws Exception
    {
        assertEquals(trial.len(), 0);
        assertEquals(trial.size(), initSize);
        assertEquals(trial.numFreed(), 1);
        trial.insert(mysem.serialize(), mysem.serialize().length);
        assertEquals(trial.len(), mysem.serialize().length);
        assertEquals(trial.size(), initSize);
        assertEquals(trial.numFreed(), 0);
        systemOut().clearHistory();
        trial.print();
        assertFuzzyEquals("There are no freeblocks in the memory pool", 
            systemOut().getHistory());
        trial.insert(mysem2.serialize(), mysem2.serialize().length);
        assertEquals(trial.len(), mysem.serialize().length 
            + mysem2.serialize().length);
        assertEquals(trial.size(), initSize * 3);
        assertEquals(trial.numFreed(), 1);
        trial = new MemManager(initSize);
        trial.insert(mysem2.serialize(), mysem2.serialize().length);
        assertEquals(trial.len(), mysem2.serialize().length);
        assertEquals(trial.size(), initSize * 2);
        assertEquals(trial.numFreed(), 1);
        
    }
    
    /**
     * Check the release method of MemManager
     * 
     * @throws Exception
     */
    public void testRelease() throws Exception
    {
        MemBlock temp = trial.insert(mysem.serialize(),
            mysem.serialize().length);
        MemBlock temp2 = trial.insert(mysem2.serialize(),
            mysem2.serialize().length);
        MemBlock temp3 = trial.insert(mysem3.serialize(),
            mysem3.serialize().length);
        MemBlock temp4 = trial.insert(mysem4.serialize(),
            mysem4.serialize().length);
        trial.print();
        assertEquals(trial.numFreed(), 1);
        trial.release(temp2);
        assertEquals(trial.numFreed(), 2);
        trial.print();
        trial.release(temp3);
        assertEquals(trial.numFreed(), 2);
        trial.print();
        trial.release(temp4);
        assertEquals(trial.numFreed(), 1);
        trial.print();
        trial = new MemManager(initSize);
        temp = trial.insert(mysem.serialize(), mysem.serialize().length);
        assertEquals(trial.numFreed(), 0);
        trial.release(temp);
        assertEquals(trial.numFreed(), 1);
        trial.print();
        trial = new MemManager(initSize);
        temp = trial.insert(mysem.serialize(), mysem.serialize().length);
        temp2 = trial.insert(mysem2.serialize(), mysem2.serialize().length);
        temp3 = trial.insert(mysem3.serialize(), mysem3.serialize().length);
        temp4 = trial.insert(mysem4.serialize(), mysem4.serialize().length);
        trial.print();
        assertEquals(trial.numFreed(), 1);
        trial.release(temp);
        assertEquals(trial.numFreed(), 2);
        trial.print();
        trial.release(temp4);
        assertEquals(trial.numFreed(), 2);
        trial.print();
    }
    
    /**
     * Check out the expand method of MemManager
     * @throws Exception
     */
    public void testExpand() throws Exception
    {
        trial.insert(mysem.serialize(), mysem.serialize().length);
        assertEquals(trial.numFreed(), 0);
        trial.expand();
        assertEquals(trial.numFreed(), 1);
    }
}