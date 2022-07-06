import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
	
    private ArrayList<Grade> grades;
    private final String BREAKDOWN_STR = "Input the percentage of your grade that this section is worth.\nFor example, if you want to put homework as 5%, enter the number 5.\nType DONE to get your grade in the class.";
    private final String VAL_STR = "Input your current grade in this section.\nIf you have a 95%, enter 95.\nCan also enter 5/10 for 50%";

    /**
     * default constructer, init the ArrayList
     */
    public Calculator(){
        grades = new ArrayList<>();
    }
    /**
     * fill the arraylist of grades from the console
     */
    public void fill(){
        Scanner scan = new Scanner(System.in);
        String input; //the string to get from console
        double breakdown, val;
        System.out.println("\n\n");
        System.out.println("Current grade calculator\n");
        System.out.println(BREAKDOWN_STR);
        System.out.print("Input here: ");
        input = scan.nextLine();
        //loop to get all the breakdowns
        while(!input.equals("DONE")){
            breakdown = handleIn(input);
            //check for valid input
            if(breakdown < 0){
                System.out.print("\n");
                System.out.println("Invallid input");
                System.out.print("\n"); 
                System.out.println(BREAKDOWN_STR);
                System.out.print("Input here: ");
                input = scan.nextLine(); //get the new input and re enter main loop
                continue;
            }
            System.out.println("\n\n");
            System.out.println(VAL_STR);
            System.out.print("Input here: ");
            input = scan.nextLine();
            //loop until a valid grade is input
            while(true){
                val = handleIn(input);
                if(val < 0){
                    System.out.print("\n");
                    System.out.println("Invallid input");
                    System.out.print("\n"); 
                    System.out.println(VAL_STR);
                    System.out.print("Input here: ");
                    input = scan.nextLine(); //get the new input and re enter main loop
                } else {
                    break;
                }
            }
            grades.add(new Grade(val, breakdown));
            //print out what was entered to the List
            System.out.println("\n\n");
            System.out.print("Breakdown: " + breakdown + "% \nGrade: " + val + "%");
            System.out.println("\n\n");
            //prep to go back into main loop
            System.out.println(BREAKDOWN_STR);
            System.out.print("Input here: ");
            input = scan.nextLine();
            scan.close();
        }
    }

    /**
     * handles input strings and changes them to doubles, can handle
     * fractions and normal numbers
     * @param in the input string
     * @return the percent (90 is 90%) returns -1 if err
     */
    public double handleIn(String in){
        double out = 0;
        int slshInd = in.indexOf('/');
        String sub1, sub2;
        if(slshInd == -1) {
            try {
                out = Double.parseDouble(in);
            } catch(NumberFormatException e) {
                return -1;
            }
        } else { //to handle fraction inputs
            try{
                //break the fraction into numerator and denominator
                sub1 = in.substring(0, slshInd);
                sub2 = in.substring(slshInd + 1, in.length());
            } catch (IndexOutOfBoundsException e) {
                return -1;
            }
            try {
                double numerator = Double.parseDouble(sub1);
                double denominator = Double.parseDouble(sub2);
                out = numerator/denominator * 100;
            } catch(NullPointerException e) {
                return -1;
            }
        }
        return out;
    }

    /**
     * calculate the current grade in the class
     * @return the current grade (90% is 90)
     */
    public double getGrade(){
        double grade = 0;
        double totalBreak = 0;
        for(int i = 0; i < grades.size(); i++) {
            totalBreak += grades.get(i).getBreakdown();
            grade += grades.get(i).getBreakdown() * grades.get(i).getVal();
        }
        return grade/totalBreak;
    }
    /**
     * function to print and calculate needed grade on final for 
     * desired overall grade
     */
    public void finalCalc(){
        Scanner scan = new Scanner(System.in);
        String input; //the string to get from console
        double curGrade, finalPer, wantGrd;
        System.out.println("\n\n");
        System.out.println("Final grade calculator\n");
        //get current grade
        while(true) {
            System.out.println("\n\n");
            System.out.println("What is your current grade? Enter 90 for 90%");
            System.out.print("Input: ");
            input = scan.nextLine();
            curGrade = handleIn(input);
            if(curGrade < 0) {
                System.out.print("\n");
                System.out.println("Invallid input");
                System.out.print("\n");
            } else {
                break;
            }
        }
        
        //get final percent
        while(true) {
            System.out.println("\n\n");
            System.out.println("What percent of your total grade is your final worth? Enter 90 for 90%");
            System.out.print("Input: ");
            input = scan.nextLine();
            finalPer = handleIn(input);
            if(finalPer < 0 || finalPer > 100) {
                System.out.print("\n");
                System.out.println("Invallid input");
                System.out.print("\n");
            } else {
                break;
            }
        }

        //get desired grade
        while(true) {
            System.out.println("\n\n");
            System.out.println("What is your desired overall grade? Enter 90 for 90%");
            System.out.print("Input: ");
            input = scan.nextLine();
            wantGrd = handleIn(input);
            if(wantGrd < 0) {
                System.out.print("\n");
                System.out.println("Invallid input");
                System.out.print("\n");
            } else {
                break;
            }
        }
        scan.close();
        //start calculations
        double curVal = (100 - finalPer)/100 * curGrade;
        curVal = (wantGrd - curVal)/finalPer * 100;
        System.out.println("\nNeeded final grade: " + curVal + "%\n\n");
    }

    /**
     * class that holds the score and percentage of overall grade
     */
    protected class Grade{
        double val; // expresses 5% as 5
        double breakdown; //expresses 5% as 5
        /**
         * constructor for a grade
         * @param val the percent you have in the section (90 is 90%)
         * @param breakdown the percent of your overall grade this section is (5 is 5%)
         */
        public Grade(double val, double breakdown){
            this.val = val;
            this.breakdown = breakdown;
        }

        /**
         * get the grade for this section
         * @return grade in decimal form
         */
        public double getVal(){
            return val;
        }

        /**
         * Get the percentage of grade that this is worth
         * @return percentage in decimal form
         */
        public double getBreakdown(){
            return breakdown;
        }
    }

    /**
     * Main function for calculator
     */
    public static void main(String[] args) {
		Calculator cal = new Calculator();
        Scanner in = new Scanner(System.in);
        String input;
        System.out.println("Welcome to Darren's grade calculator!");
        System.out.print("\n");
        while(true) {
            System.out.println("Choose mode. Type FINAL for final grade calculator, CURRENT for curent grade calculator, or DONE to exit");
            System.out.print("Input: ");
            input = in.nextLine();
            if(input.equals("FINAL")){
                cal.finalCalc();
                break;
            } else if(input.equals("CURRENT")){
                cal.fill();
                System.out.println("\n\n\n\n");
                if(cal.grades.size() == 0) {
                    System.out.println("No grades inputed");
                } else {
                    System.out.println("Current grade: " + cal.getGrade() + "%");
                }
                System.out.println("\n\n\n");
                break;
            } else if(input.equals("DONE")){
                break;
            } else {
                System.out.print("\n");
                System.out.println("Invalid Input");
                System.out.print("\n");
            }
        }
        in.close();
	}

}