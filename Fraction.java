/**
 * class for a fraction
 */
public class Fraction {
    double numerator; 
    double denominator; 
    /**
     * constructor for a grade
     * @param numerator numerator of the fraction
     * @param denominator denominator of the fraction
     */
    public Fraction(double numerator, double denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * get the grade for this section
     * @return grade in decimal form
     */
    public double getNumerator(){
        return numerator;
    }

    /**
     * Get the percentage of grade that this is worth
     * @return percentage in decimal form
     */
    public double getDenominator(){
        return denominator;
    }

    /**
     * get the decimal value for the fraction
     * @return
     */
    public double getValue(){
        return numerator/denominator;
    }
}
