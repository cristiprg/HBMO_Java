import java.util.SortedSet;

/**
 * Created by cristiprg on 1/18/2015.
 */
public class Main
{
    public static void main(String args[])
    {
        SortedSet<Solution> solutions = InitialSolutionsGenerator.generateRandomSolutions();

        Solution queen = solutions.first();

        Solution bestSolution = null;
        while(queen.hasEnergy())
        {
            bestSolution = null;
            for (Solution drone : solutions)
            {
                if(queen.equals(drone))
                {
                    // This is the queen actually.
                    continue;
                }

                if(queen.probabilityToMateDrone(drone) > queen.probabilityToMateDroneThreshold)
                {


                    SortedSet<Solution> broods = queen.createBroods(drone);
                    if(broods.isEmpty())
                        continue;

                    Solution bestBrood = broods.first();
                    // Improve broods here, ca se blocheaza in minim local!
                    if(bestBrood.compareTo(queen) > 0)
                    {
                        bestSolution = bestBrood;
                        System.out.println("Found better!");
                    }
                }
            }

            if(bestSolution != null)
                queen = bestSolution;
            queen.nextIteration();
        }

        System.out.println(bestSolution + " " + bestSolution.FUNCTIA() + " " +
                bestSolution.getFitness());
    }
}
