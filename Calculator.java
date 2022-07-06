import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
	
    protected ArrayList<Grade> grades;
    protected final static String BREAKDOWN_STR = "Input the percentage of your grade that this section is worth.\nFor example, if you want to put homework as 5%, enter the number 5.\nType E to get your grade in the class.";
    protected final static String VAL_STR = "Input your current grade in this section.\nIf you have a 95%, enter 95.\nCan also enter 5/10 for 50%";
    protected final static String INPUT = "Input here: ";

    /**
     * default constructer, init the ArrayList
     */
    public Calculator(){
        grades = new ArrayList<>();
    }

    /**
     * handles input strings and changes them to doubles, can handle
     * fractions and normal numbers
     * @param in the input string
     * @return the percent (90 is 90%) returns -1 if err
     */
    public static double handleIn(String in){
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
     * prints a new empty line
     */
    public static void newLn(){
        System.out.print("\n");
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
        Scanner in = new Scanner(System.in);
        String input;
        System.out.println("Welcome to Darren's grade calculator!");
        newLn();
        while(true) {
            System.out.println("Choose mode. Type F for final grade calculator, C for curent grade calculator, or E to exit");
            newLn();
            System.out.print(Calculator.INPUT);
            input = in.nextLine();
            if(input.equals("F")){
                FinalCalculator finCal = new FinalCalculator();
                finCal.finalCalc();
                break;
            } else if(input.equals("C")){
                CurrentCalculator curCal = new CurrentCalculator();
                curCal.fill();
                newLn();
                if(curCal.grades.size() == 0) {
                    System.out.println("No grades inputed");
                } else {
                    System.out.println("Current grade: " + curCal.getGrade() + "%");
                }
                newLn();
                break;
            } else if(input.equals("E")){
                break;
            } else {
                newLn();
                System.out.println("Invalid Input");
                newLn();
            }
        }
        in.close();
	}
}