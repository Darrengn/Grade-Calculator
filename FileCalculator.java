import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileCalculator extends Calculator{
    private final String MAKE = "What is the name of the class?";
    private boolean equWeight = true;
    private ArrayList<Section> sections; //list of all the sections of the gradebook
    
    /**
     * constructor
     */
    public FileCalculator() {
        super();
        sections = new ArrayList<>();
    }

    /**
     * call to start the file calculator
     */
    public void start() {
        String fileName;
        String confirm;
        scan = new Scanner(System.in);
        //loop to get the name of the class and confirm it  
        newLn();
        System.out.println("Welcome to the file calculator.");      
        while(true) {
            newLn();
            System.out.println(MAKE);
            newLn();
            System.out.print(INPUT);
            //get the name of the file
            fileName = scan.nextLine();
            newLn();
            System.out.println("Is the name " + fileName + " correct? Type Y to confirm");
            newLn();
            System.out.print(INPUT);
            confirm = scan.nextLine();
            if(confirm.equals("Y")) {
                break;
            }
        }
        File file = new File(fileName);
        if(file.exists()) {
            newLn();
            System.out.println("Are you adding grades? N for no");
            newLn();
            System.out.print(INPUT);
            confirm = scan.nextLine();
            if(confirm.equals("N")) {
                readFile(file);
                calcGrade();
            } else {
                readFile(file);
                newGrades();
                writeFile(file);
            }
            
        } else {
            makeFile(file);
        }
        scan.close();
    }

    /**
     * calculates and prints out the grade in this course to the console
     */
    public void calcGrade() {
        double total = 0;
        double curent = 0;
        double totBreak = 0;
        //loop through the Sections
        for(int i = 0; i < sections.size(); i++) {
            Section curSec = sections.get(i);
            //multiply by the breakdown to get the total value
            if(curSec.fractions.size() != 0) {
                curent += curSec.getGrade() * curSec.percentage;
                totBreak += curSec.percentage;
            }
            total += curSec.getGrade() * curSec.percentage;
        }
        curent = curent/totBreak*100;
        newLn();
        System.out.println("Total Grade: " + total + "%");
        newLn();
        System.out.println("Current Grade: " + curent + "%");
    }

    /**
     * Makes a file and records the breakdowns in the file
     * @return true if successful make
     */
    public void makeFile(File file) {
        String name;
        String percent;
        double val;
        try(PrintWriter output = new PrintWriter(file);) {
            newLn();
            System.out.println("This class doesn't exist yet, creating a new class...");
            newLn();
            System.out.println("Are all grades in each section weighted equally? Y for yes");
            newLn();
            System.out.print(INPUT);
            name = scan.nextLine();
            //if weighted equally, set equWeight and print to file
            if(name.equals("Y")) {
                equWeight = true;
                output.println("true");
            } else {
                equWeight = false;
                output.println("false");
            }
            newLn();
            System.out.println("Next add breakdown sections");
            newLn();
            //loop until all breakdowns have been added
            while(true) {
                //check for spaces in the name
                while(true) {
                    newLn();
                    System.out.println("What is the name of the breakdown? It can't contain any spaces. Type D to end.");
                    newLn();
                    System.out.print(INPUT);
                    name = scan.nextLine();
                    if(name.indexOf(' ') == -1){
                        break;
                    }
                    newLn();
                    System.out.println("Invallid Input");
                }
                
                if(name.equals("D")) {
                    break;
                }
                //loop to get the value of the breakdown
                while(true) {
                    newLn();
                    System.out.println("What percent of your grade is " + name + "?\nFor 5%, enter the number 5.");
                    newLn();
                    System.out.print(INPUT);
                    percent = scan.nextLine();
                    val = handleIn(percent);
                    if(val != -1){
                        break;
                    }
                    newLn();
                    System.out.println("Invallid Input");
                }
                output.println(name + " " + val);
                sections.add(new Section(name, val, equWeight));            
            }
            newLn();
            System.out.println("Do you want to enter grades? (Y) for yes");
            newLn();
            System.out.print(INPUT);
            name = scan.nextLine(); //name is used as temp variable
            if(name.equals("Y")){
                readFile(file);
                newGrades();
                writeFile(file);
            }
            output.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("Error");
        }
    }

    /**
     * add grades to the sections
     */
    public void newGrades(){
        String input;
        //loop until done
        while(true) {
            int index = -1;
            newLn();
            System.out.print("What section are you adding a grade to? Type E to exit. Sections are ");
            //print out the names of every section
            for(int i = 0; i < sections.size(); i++) {
                System.out.print(sections.get(i).name + ", ");
            }
            System.out.println();
            newLn();
            System.out.print(INPUT);
            input = scan.nextLine();
            if(input.equals("E")) {
                break;
            }
            //loop to check index of section
            for(int i = 0; i < sections.size(); i++) {
                if(input.equals(sections.get(i).name)) {
                    index = i;
                    break;
                }
            }
            if(index == -1) {
                continue;
            }
            //loop to check if grade is correct format
            while(true) {
                newLn();
                System.out.println("What grade are you entering? enter as a fraction without spaces.");
                newLn();
                System.out.print(INPUT);
                input = scan.nextLine();
                if(handleIn(input) == -1) {
                    newLn();
                    System.out.println("Error");
                } else if(input.indexOf('/') == -1) {
                    newLn();
                    System.out.println("Error");
                } else {
                    break;
                }
            }
            Fraction temp;
            int slshInd = input.indexOf('/');
            double numerator = Double.parseDouble(input.substring(0, slshInd));
            double denominator = Double.parseDouble(input.substring(slshInd + 1));
            temp = new Fraction(numerator, denominator);
            //add the fraction
            sections.get(index).fractions.add(temp);
        }
    }

    /**
     * add grades to the file
     * @param file
     */
    public void writeFile(File file) {
        String name = file.getPath();
        file.delete();
        File temp = new File(name);
        try (PrintWriter write = new PrintWriter(temp)) {
            write.println(equWeight);
            //loop through sections and print
            for(int i = 0; i < sections.size(); i++){
                Section curSec = sections.get(i);
                write.println(curSec.name + ' ' + curSec.percentage);
                for(int j = 0; j < curSec.fractions.size(); j++) {
                    Fraction curFrac = curSec.fractions.get(j);
                    //System.out.println(curFrac.numerator);
                    write.println(curFrac.numerator + "/" + curFrac.denominator);
                }  
            }

        } catch (Exception e) {
            System.out.println("Error occured");
        }
        calcGrade();
    }

    /**
     * reads from the file and populates the Sections
     * @param file the file to read the grades from
     */
    public void readFile(File file) {
        try (Scanner read = new Scanner(file)){
            String input;
            String secName;
            String secVal;
            int ind;
            //get the equWeight
            input = read.nextLine();
            if(input.equals("true")) {
                equWeight = true;
            } else {
                equWeight = false;
            }
            //start reading the sections
            input = read.nextLine();
            while(true) {
                //add a section
                ind = input.indexOf(' ');
                secName = input.substring(0, ind);
                secVal = input.substring(ind + 1);
                Section curSec = new Section(secName, Double.parseDouble(secVal), equWeight);
                sections.add(curSec);
                //check if the next line is a section or a grade
                input = read.nextLine();
                while(input.indexOf(' ') == -1){
                    ind = input.indexOf('/');
                    if(ind == -1) throw new Exception();
                    //create a new grade
                    Fraction grade = new Fraction(Double.parseDouble(input.substring(0,ind)), 
                        Double.parseDouble(input.substring(ind + 1)));
                    curSec.fractions.add(grade);
                    input = read.nextLine();
                }
            }
        } catch (NoSuchElementException e) {
            newLn();
            System.out.println("Loaded file");
        } catch (Exception exception) {
            newLn();
            System.out.println("Error occured");
        }
    }

    /**
     * prints out everything inside of sections
     */
    public void dumpSec(){
        for(int i = 0; i < sections.size(); i++){
            Section curSec = sections.get(i);
            System.out.println(curSec.name + ' ' + curSec.percentage);
            for(int j = 0; j < curSec.fractions.size(); j++) {
                Fraction curFrac = curSec.fractions.get(j);
                //System.out.println(curFrac.numerator);
                System.out.println(curFrac.numerator + "/" + curFrac.denominator);
            }  
        }
    }

    /**
     * contains the information for each breakdown
     */
    protected class Section {
        ArrayList<Fraction> fractions;// list of the grades in this section
        String name;
        double percentage; //the breakdown value, expresses 5% as 5
        boolean equWeight;

        /**
         * constructor for Section
         * @param name name of the section
         * @param percentage the breakdown value 5% is 5
         * @param equWeight is each grade have the same weight
         */
        public Section(String name, double percentage, boolean equWeight) {
            fractions = new ArrayList<>();
            this.name = name;
            this.percentage = percentage;
            this.equWeight = equWeight;
        }

        /**
         * add a grade to this section
         * @param numerator the numerator of the fraction
         * @param denominator the denominator of the fraction
         */
        public void addGrade(double numerator, double denominator) {
            Fraction temp = new Fraction(numerator, denominator);
            fractions.add(temp);
        }

        /**
         * add a grade to the section
         * @param frac the fraction for the grade
         */
        public void addGrade(Fraction frac) {
            fractions.add(frac);
        }

        /**
         * gets the grade of the section not taking into account the breakdown
         * @return 5% is 5
         */
        public double getGrade() {
            if(fractions.size() == 0) {
                return 0;
            }
            //run this if each grade has same value
            if(equWeight) {
                double total = 0;
                //loop and get the sum of the values
                for(int i = 0; i < fractions.size(); i++) {
                    total += fractions.get(i).getValue();
                }
                return total/fractions.size(); // times 100 to get percentage
            }
            //else do this
            double totalNum = 0;
            double totalDenom = 0;
            //loop and get the sum of the values
            for(int i = 0; i < fractions.size(); i++) {
                totalNum += fractions.get(i).getNumerator();
                totalDenom += fractions.get(i).getDenominator();
            }
            return totalNum/totalDenom;
        }

    }
 
}
