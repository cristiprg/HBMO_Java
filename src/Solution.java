import java.lang.Comparable;
import java.lang.Math;
import java.lang.Override;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The bee.
 */
public class Solution implements Comparable<Solution>
{
    private double x;
    private double y;
    private int speed = 100;
    private int energy = 100;

    static double speedReductionFactor = 0.5;
    static double energyReductionAmount = 45;
    static double probabilityToMateDroneThreshold = 0.05;

    public Solution()
    {
        this(0, 0);
    }

    public Solution(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Solution other)
    {

        if(this.equals(other))
        {
            return 0;
        }

        double f1 = this.getFitness();
        double f2 = other.getFitness();

        if(f1 < f2)
            return -1;
        else
            return 1;
    }

    /**
     * Acuma, o consideram diferenta fata de 0, pentru ca stim deja ca minimul e 0 ... si care mai ii rostu' atunci?
     * INTREBARE: ce functie de fitness am alege daca nu stim ca raspunsul este 0?
     *
     * V2: tot functia lui Gauss daca tot stim ca raspunsul (parametrul b) este 0.
     *
     * @return The fitness function of this solution.
     */
    public double getFitness()
    {
        return Math.exp( -(Math.pow(FUNCTIA(x, y), 2) - 0 ) / 100 );
    }

    /**
     * Computes to probability that this solution (the queen, hopefully) will pick
     * the drone in the mating dance.
     *
     * @param drone
     * @return a double in interval [0, 1] representing the probability.
     */
    public double probabilityToMateDrone(Solution drone)
    {
        return Math.exp( - Math.abs(this.getFitness() - drone.getFitness()) / this.speed);
    }

    /**
     * Computes the number of broods this queen makes with the drone based on:
     *      The queen's energy
     *      The drone's fitness value
     *      A random float number in interval [0, 1]
     *
     * @param drone
     * @return A non-negative integer.
     */
    public int numberOfBroodsWithDrone(Solution drone)
    {
        return (int) (energy * drone.getFitness() * ( new Random().nextInt() )) % 10;
    }

    /**
     * Supposing this is the queen, the following parameters have to updated at each iteration:
     *  speed,
     *  energy
     */
    public void nextIteration()
    {
        speed *= speedReductionFactor;
        energy -= energyReductionAmount;
    }

    /**
     * Provides a set of solutions derived from this queen. Runs the genotypes combination
     * algorithm for each new brood.
     *
     * @param drone
     * @return A sorted set (TreeSet) of solutions.
     */
    public SortedSet<Solution> createBroods(Solution drone)
    {
        SortedSet<Solution> broods = new TreeSet<Solution>();
        int nr_broods = numberOfBroodsWithDrone(drone);
        //System.out.println("Creating " + nr_broods + " broods.");
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
    public Solution combineGenotypes(Solution drone)
    {
        Random r = new Random();
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Solution(x, y);
    }

    public boolean hasEnergy()
    {
        return energy > 0;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object other)
    {
        if(this == other)
            return true;

        if(! (other instanceof Solution))
            return false;

        Solution _other = (Solution) other;
        return
            Double.compare(_other.x, this.x) == 0 &&
            Double.compare(_other.y, this.y) == 0;
    }

    private double FUNCTIA(double x, double y)
    {
        double c1 = 20;
        double c2 = 0.2;
        double c3 = 2*Math.PI;

        return -c1 * Math.exp( -c2 * Math.sqrt( 1/2 * (x*x + y*y) ) ) -
                Math.exp(1/2 * (Math.cos( c3 * x ) + Math.cos(c3 * y))) + c1 + Math.exp(1);
    }

    public double FUNCTIA()
    {
        return FUNCTIA(x, y);
    }

}