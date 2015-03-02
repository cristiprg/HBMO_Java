import Meals.Model.*;
import com.sun.istack.internal.NotNull;

import java.lang.Comparable;
import java.lang.Math;
import java.lang.Override;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The bee.
 */
public class Solution implements Comparable<Solution> {

    private DayMeal dayMeal;

    private double x;
    private double y;
    private int speed = 100;
    private int energy = 100;

    static double speedReductionFactor = 0.5;
    static double energyReductionAmount = 5;
    static double probabilityToMateDroneThreshold = 0.05;

    public DayMeal getDayMeal() {
        return dayMeal;
    }


    public Solution()
    {
        this(null);
    }

    public Solution(DayMeal dayMeal) {
        this.dayMeal = dayMeal;
        computeNutrientsValues();
    }

    public Map<String, Double> computeNutrientsValues()
    {
        return dayMeal.computeNutrientsValues();
    }

    /*
    public Solution() {
        this(0, 0);
    }

    public Solution(double x, double y) {
        this.x = x;
        this.y = y;
    }
*/
    @Override
    public int compareTo(@NotNull Solution other) {
        if (this.equals(other)) {
            return 0;
        }

        double f1 = this.getFitness();
        double f2 = other.getFitness();
        // if this is > then other => fitness is better, thus return 1
        if (f1 < f2)
            return -1;
        else
            return 1;
    }

    /**
     * The final value of the fitness function will be the mean value between the gaussian applied
     * to x and y i.e.: e^(-x*x) + e^(-y*y) / 2. Resulting values between 0 and 1. The higher the
     * better Gauss function: http://mathworld.wolfram.com/GaussianFunction.html
     *
     * @return The fitness function of this solution.
     */
    public double getFitness() {
        //return (Math.exp(-x * x) + Math.exp(-y * y)) / 2.0;
        //return 0.5 * (getFitnessLevel1() + getFitnessLevel2());
        return getFitnessLevel1();
    }

    private double getFitnessLevel1() {
        Map<String, Double> nutrientsValueMap = dayMeal.getNutrientsValuesMap();
        Double sum = new Double(0);
        for (String key : nutrientsValueMap.keySet()) {
            // TODO implement weigths
            double err = errorMargin(nutrientsValueMap.get(key), NutrientsIdealValuesHelper.getIdealValueForNutrient(key));
            sum += err;

            //System.out.println(key + " " + " " + nutrientsValueMap.get(key) + " " + NutrientsIdealValuesHelper.getIdealValueForNutrient(key) + " " + err);

        }

        return sum / nutrientsValueMap.size();
    }

    private double getFitnessLevel2()
    {
        return 0;
    }

    private double errorMargin(Double x, Double ideal)
    {
        return Math.exp(- (x - ideal) * (x - ideal));
    }

    /**
     * Computes to probability that this solution (which is to be the queen) will pick the drone in
     * the mating dance. NEEDS REVISION!!!!! VALUES ARE NOT PROPERLY CORRELATED WITH THRESHOLD
     *
     * @param drone
     * @return a double in interval [0, 1] representing the probability.
     */
    public double probabilityToMateDrone(Solution drone) {
        double thisFitness = this.getFitness();
        double droneFitness = drone.getFitness();
        double prob = Math.exp(-Math.abs(thisFitness - droneFitness) / this.speed);
        return prob;
    }

    /**
     * Computes the number of broods this queen makes with the drone, based on: The queen's energy
     * The drone's fitness value A random float number in interval [0, 1] This function should
     * return between 5 and 15 drones Energy is between 0-100 Drone's fitness is between 0 and 1
     * Random value will be between
     *
     * @param drone
     * @return A non-negative integer.
     */
    public int numberOfBroodsWithDrone(Solution drone) {
        // return (int) (energy * drone.getFitness() * (new Random().nextInt()))
        // % 10;

        // values from 5 to 10
        int energyAndFitnessVar = (int) ((energy * drone.getFitness()) / 20 + 5);
        // random number from 0 to 5
        int randomNumber = new Random().nextInt(6);
        // result will be from 5 to 15
        int result = energyAndFitnessVar + randomNumber;
        return result;
    }

    /**
     * Supposing this is the queen, the following parameters have to updated at each iteration:
     * speed, energy
     */
    public void nextIteration() {
        speed *= speedReductionFactor;
        energy -= energyReductionAmount;
    }

    /**
     * Provides a set of solutions derived from this queen. Runs the genotypes combination algorithm
     * for each new brood.
     *
     * @param drone
     * @return A sorted set (TreeSet) of solutions.
     */
    public SortedSet<Solution> createBroods(Solution drone) {
        SortedSet<Solution> broods = new TreeSet<Solution>();
        int nr_broods = numberOfBroodsWithDrone(drone);
        // System.out.println("Creating " + nr_broods + " broods.");
        for (int i = 0; i < nr_broods; i++) {
            Solution brood = combineGenotypes(drone);
            broods.add(brood);
        }

        return broods;
    }

    /**
     * Runs the genotypes combination algorithm for this queen and the drone.
     *
     * @param drone
     * @return A new solution (brood).
     */
    public Solution combineGenotypes2(Solution drone) {
        Random r = new Random();
        double x = r.nextDouble();
        double y = r.nextDouble();
        //return new Solution(x, y); //TODO

        return new Solution();
    }

    /**
     * Runs the genotypes combination algorithm for this queen and the drone. Combination of
     * genotypes is done by doing a crossover between the queens genomes and the drone's genomes
     *
     * @param drone
     * @return A new solution (brood).
     */
    public Solution combineGenotypes(Solution drone) {
        DayMeal brood = new DayMeal();
        Random r = new Random();

        brood.setBreakfast(r.nextBoolean() ? // if random is true
                this.dayMeal.getBreakfast() : // take from this queen
                drone.dayMeal.getBreakfast()); // otherwise, take from drone.

        brood.setLunch(r.nextBoolean() ?
                this.dayMeal.getLunch() :
                drone.dayMeal.getLunch());

        brood.setDinner(r.nextBoolean() ?
                this.dayMeal.getDinner() :
                drone.dayMeal.getDinner());

        brood.setSnack1(r.nextBoolean() ?
                this.dayMeal.getSnack1() :
                drone.dayMeal.getSnack1());

        brood.setSnack2(r.nextBoolean() ?
                this.dayMeal.getSnack2() :
                drone.dayMeal.getSnack2());

        return new Solution(brood);
    }

    public boolean hasEnergy() {
        return energy > 0;
    }

    public boolean hasSpeed()
    {
        return speed > 0;
    }

    @Override
    public String toString() {
        return dayMeal.toString() + "\n" + dayMeal.getNutrientsValuesMap().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof Solution))
            return false;

        return this.dayMeal.equals(((Solution) other).getDayMeal());
    }

}