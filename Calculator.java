import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
	
    private ArrayList<Grade> grades;
    private final String BREAKDOWN_STR = "Input the percentage of your grade that this section is worth.\nFor example, if you want to put homework as 5%, enter the number 5.\nType DONE to get your grade in the class.";
    private final String VAL_STR = "Input your current grade in this section.\nIf you have a 95%, enter 95";

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
        System.out.println("Welcome to Darren's grade calculator!");
        System.out.println(BREAKDOWN_STR);
        System.out.print("Input here: ");
        input = scan.nextLine();
        //loop to get all the breakdowns
        while(!input.equals("DONE")){
            try {
                breakdown = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invallid input"); 
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
                try {
                    val = Double.parseDouble(input);
                    break;
                } catch(NumberFormatException e){
                    System.out.println("Invallid input"); 
                    System.out.println(VAL_STR);
                    System.out.print("Input here: ");
                    input = scan.nextLine(); //get the new input and re enter main loop
                    continue;
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
        }
    }

    public double getGrade(){
        double grade = 0;
        double totalBreak = 0;
        for(int i = 0; i < grades.size(); i++) {
            totalBreak += grades.get(i).getBreakdown();
            grade += grades.get(i).getBreakdown() * grades.get(i).getVal();
        }
        return grade/totalBreak;
    }

    protected class Grade{
        double val; // expresses 5% as 5
        double breakdown; //expresses 5% as 5

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
        System.out.println("\n\n\n\n");
        cal.fill();
        System.out.println("\n\n\n\n");
        if(cal.grades.size() == 0) {
            System.out.println("No grades inputed");
        } else {
            System.out.println("Final grade: " + cal.getGrade() + "%");
        }
        System.out.println("\n\n\n");
	}

}