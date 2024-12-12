import java.io.File;
import java.util.Scanner;
/**
 * Command Processor class
 *
 * @author kuzoto
 * @version September 2024
 */


public class CommandProcessor 
{
    //The file containing the instructions
    private String fileName;
    //The controller used to execute instructions
    private Controller controller;
    
    // ~ Constructor .................................................

    // ----------------------------------------------------------
    /**
     * Create a new CommandProcessor
     * 
     * @param fileName
     *          name of the file containing instructions
     * @param controller
     *          controller being used to execute instructions
     */
    public CommandProcessor(String fileName, Controller controller)
    {
        this.fileName = fileName;
        this.controller = controller;
    }
    
    /**
     * Parse all the lines in the file and run the commands
     * 
     * @throws
     *      FileNotFoundException
     */
    public void parseAllLines()
    {
        try 
        {   
            //Create our new scanner
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) //Loop through all lines in the file
            {
                //Get the command
                //Get the inputs for this command
                String line = sc.nextLine().trim();
                //Check if the line is empty
                if (!line.isEmpty())
                {
                    //parse the inputs for this command
                    Scanner oneLine = new Scanner(line);
                    //Check if the scanner has information to read
                    String cmd = oneLine.next().trim();
                    switch(cmd) {
                        case "print" : //Found a print command
                            //get type of list to print
                            if (!oneLine.hasNext())
                            {
                                System.out.println("Invalid input " + cmd);
                                break;
                            }
                            String type = oneLine.next().trim();
                            controller.print(type);
                            break;
                        case "insert" : //Found a insert command
                            //Change delimiter to match command format
                            oneLine.useDelimiter("\n");
                            //get id to insert
                            int id = Integer.parseInt(oneLine.next().trim());
                            //check if the command format is valid
                            /*if (!sc.hasNext())
                            {
                                System.out.println("Invalid input " + cmd);
                                break;
                            }*/
                            //get the title to insert
                            String title = sc.nextLine().trim();
                            //Get the third line of the insert command
                            oneLine = new Scanner(sc.nextLine().trim());
                            String date = oneLine.next().trim();
                            int len = Integer.parseInt(oneLine.next().trim());
                            short x = (short) Integer.parseInt(
                                oneLine.next().trim());
                            short y = (short) Integer.parseInt(
                                oneLine.next().trim());
                            int cost = Integer.parseInt(oneLine.next().trim());
                            String[] keywords = sc.nextLine().
                                trim().split("\\s+");
                            String desc = sc.nextLine().trim();
                            desc = desc.replaceAll("\\s+", " ");
                            controller.insert(id, title, date, len, x, 
                                y, cost, keywords, desc);
                            break;
                        case "delete" : //Found a delete command
                            //get the ID of the record we want to delete
                            id = Integer.parseInt(oneLine.next().trim());
                            controller.remove(id);
                            break;
                        case "search" : //Found a search command
                            //get the type we want to search for
                            //type = oneLine.next().trim();
                            if (!oneLine.hasNext())
                            {
                                System.out.println("Invalid input " + cmd);
                                break;
                            }
                            id = Integer.parseInt(oneLine.next().trim());
                            controller.search(id);
                            break;
                        default : //Found an unrecognized command
                            System.out.println("Invalid input " + cmd);
                            break;
                    }
                }
//                else
//                {
//                    System.out.println("Invalid input " + cmd.trim());
//                }
            }
            sc.close();
        } 
        catch (Exception e)
        {
            e.printStackTrace(); //catch FileNotFoundException
        }
    }

}