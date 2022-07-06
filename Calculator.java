import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
	
    private ArrayList<Grade> grades;
    private final String BREAKDOWN_STR = "Input the percentage of your grade that this section is worth.\nFor example, if you want to put homework as 5%, enter the number 5.\nType DONE to get your grade in the class.";
    private final String VAL_STR = "Input your current grade in this section.\nIf you have a 95%, enter 95.\nCan also enter 5/10 for 50%";

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
            breakdown = handleIn(input);
            //check for valid input
            if(breakdown == -1){
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
                if(val == -1){
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
        }
    }

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
        cal.fill();
        System.out.println("\n\n\n\n");
        if(cal.grades.size() == 0) {
            System.out.println("No grades inputed");
        } else {
            System.out.println("Current grade: " + cal.getGrade() + "%");
        }
        System.out.println("\n\n\n");
	}

}