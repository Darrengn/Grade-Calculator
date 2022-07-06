import java.util.Scanner;

public class CurrentCalculator extends Calculator{
    
    public CurrentCalculator(){
        super();
    }
    
    /**
     * fill the arraylist of grades from the console
     */
    public void fill(){
        Scanner scan = new Scanner(System.in);
        String input; //the string to get from console
        double breakdown, val;
        newLn();
        System.out.println("Current grade calculator\n");
        System.out.println(BREAKDOWN_STR);
        newLn();
        System.out.print(INPUT);
        input = scan.nextLine();
        //loop to get all the breakdowns
        while(!input.equals("E")){
            breakdown = handleIn(input);
            //check for valid input
            if(breakdown < 0){
                newLn();
                System.out.println("Invallid input");
                newLn(); 
                System.out.println(BREAKDOWN_STR);
                newLn();
                System.out.print(INPUT);
                input = scan.nextLine(); //get the new input and re enter main loop
                continue;
            }
            newLn();
            System.out.println(VAL_STR);
            newLn();
            System.out.print(INPUT);
            input = scan.nextLine();
            //loop until a valid grade is input
            while(true){
                val = handleIn(input);
                if(val < 0){
                    newLn();
                    System.out.println("Invallid input");
                    newLn(); 
                    System.out.println(VAL_STR);
                    newLn();
                    System.out.print(INPUT);
                    input = scan.nextLine(); //get the new input and re enter main loop
                } else {
                    break;
                }
            }
            grades.add(new Grade(val, breakdown));
            //print out what was entered to the List
            newLn();
            System.out.println("Breakdown: " + breakdown + "% \nGrade: " + val + "%");
            newLn();
            //prep to go back into main loop
            System.out.println(BREAKDOWN_STR);
            newLn();
            System.out.print(INPUT);
            input = scan.nextLine();
        }
        scan.close();
    }
}
