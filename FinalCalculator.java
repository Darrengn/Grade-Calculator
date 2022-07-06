import java.util.Scanner;

public class FinalCalculator extends Calculator{
    
    public FinalCalculator(){
        super();
    }

    /**
     * function to print and calculate needed grade on final for 
     * desired overall grade
     */
    public void finalCalc(){
        Scanner scan = new Scanner(System.in);
        String input; //the string to get from console
        double curGrade, finalPer, wantGrd;
        newLn();
        System.out.println("Final grade calculator");
        //get current grade
        while(true) {
            newLn();
            System.out.println("What is your current grade? Enter 90 for 90%");
            newLn();
            System.out.print("Input: ");
            input = scan.nextLine();
            curGrade = handleIn(input);
            if(curGrade < 0) {
                newLn();
                System.out.println("Invallid input");
            } else {
                break;
            }
        }
        
        //get final percent
        while(true) {
            newLn();
            System.out.println("What percent of your total grade is your final worth? Enter 90 for 90%");
            newLn();
            System.out.print("Input: ");
            input = scan.nextLine();
            finalPer = handleIn(input);
            if(finalPer < 0 || finalPer > 100) {
                newLn();
                System.out.println("Invallid input");
            } else {
                break;
            }
        }

        //get desired grade
        while(true) {
            newLn();
            System.out.println("What is your desired overall grade? Enter 90 for 90%");
            newLn();
            System.out.print("Input: ");
            input = scan.nextLine();
            wantGrd = handleIn(input);
            if(wantGrd < 0) {
                newLn();
                System.out.println("Invallid input");
            } else {
                break;
            }
        }
        scan.close();
        //start calculations
        double curVal = (100 - finalPer)/100 * curGrade;
        curVal = (wantGrd - curVal)/finalPer * 100;
        newLn();
        System.out.println("Needed final grade: " + curVal + "%");
        newLn();
    }
}
