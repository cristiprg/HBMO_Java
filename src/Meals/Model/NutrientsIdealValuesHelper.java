package Meals.Model;

import Meals.Model.Dao.NutrientsIdealValuesDAOImpl;

import java.util.Map;

/**
 * Created by cristiprg on 02.03.2015.
 */
public class NutrientsIdealValuesHelper {
    private static Map<String, Double> nutrientsIdealValuesMap =
            new NutrientsIdealValuesDAOImpl(). // get new instance of NutrientsIdealValuesDAO
                    getNutrientsIdealValuesById(1). // get new instance of NutrientsIdealValues - index 1 TODO: different ideals for different diets
                    getNutrientsIdealValuesMap(); // get the map only

    public static Double getIdealValueForNutrient(String nutrient)
    {
        if( !nutrientsIdealValuesMap.containsKey(nutrient))
        {
            System.err.println("ERROR: nutrient not found!");
            System.exit(1);
        }

        return nutrientsIdealValuesMap.get(nutrient);
    }
}
