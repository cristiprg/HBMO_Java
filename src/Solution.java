import Meals.Model.*;
import com.sun.istack.internal.NotNull;

import java.lang.Comparable;
import java.lang.Math;
import java.lang.Override;
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
        return 1;
        //TODO
/*
        double f1 = this.getFitness();
        double f2 = other.getFitness();
        // if this is > then other => fitness is better, thus return 1
        if (f1 < f2)
            return -1;
        else
            return 1;*/
    }

    /**
     * The final value of the fitness function will be the mean value between the gaussian applied
     * to x and y i.e.: e^(-x*x) + e^(-y*y) / 2. Resulting values between 0 and 1. The higher the
     * better Gauss function: http://mathworld.wolfram.com/GaussianFunction.html
     *
     * @return The fitness function of this solution.
     */
    public double getFitness() {
        return (Math.exp(-x * x) + Math.exp(-y * y)) / 2.0;
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
        double x, y;
        Random r = new Random();
        x = r.nextBoolean() ? this.x : drone.x;
        y = r.nextBoolean() ? this.y : drone.y;
        //return new Solution(x, y);
        //return new Solution(x, y);//TODO
        return new Solution();
    }

    public boolean hasEnergy() {
        return energy > 0;
    }

    @Override
    public String toString() {
        //return "(" + ((int) (x * 100)) / 100.0 + "," + ((int) (y * 100)) / 100.0 + ")";
        ////TODO
        return dayMeal.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof Solution))
            return false;

        //return Double.compare(_other.x, this.x) == 0 && Double.compare(_other.y, this.y) == 0;
        //TODO
        return this.dayMeal.equals(((Solution) other).getDayMeal());
    }

}