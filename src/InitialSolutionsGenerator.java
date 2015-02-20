import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by cristiprg on 1/18/2015.
 */
public class InitialSolutionsGenerator {

    public static final int NR_INITIAL_SOLUTIONS = 10;
    public static final double MIN = -5;
    public static final double MAX = 5;

    /**
     * Aicia vine Honey Bee Colony in actiune.
     * Deocamdata facem random
     *
     * @return A SortedSet (Tree) filled with random solutions.
     */
    public static SortedSet<Solution> generateRandomSolutions()
    {
        SortedSet<Solution> solutions = new TreeSet<Solution>();
        double x,y;
        Random r = new Random();
        for (int i = 0; i < NR_INITIAL_SOLUTIONS; i++) {
            x = MIN + (MAX - MIN) * r.nextDouble();
            y = MIN + (MAX - MIN) * r.nextDouble();
            solutions.add(new Solution(x, y));
        }

        return solutions;
    }
}

