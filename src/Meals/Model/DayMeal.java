package Meals.Model;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * 
 */
public class DayMeal implements Comparable<DayMeal> {
    protected Breakfast breakfast;
    protected Dinner dinner;
    protected Lunch lunch;
    protected Snack snack1, snack2;
    /**
     * 
     */
    public DayMeal() {
        this(null, null, null, null, null);
    }

    public DayMeal(Breakfast breakfast, Lunch lunch, Dinner dinner, Snack snack1, Snack snack2) {
        this.breakfast = breakfast;
        this.dinner = dinner;
        this.lunch = lunch;
        this.snack1 = snack1;
        this.snack2 = snack2;
    }

    @Override
    public int compareTo(@NotNull DayMeal other) {
        return 0;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof DayMeal))
            return false;

        DayMeal _other = (DayMeal) other;
        return breakfast.equals(_other.breakfast)   &&
                dinner.equals(_other.dinner)        &&
                lunch.equals(_other.lunch)          &&
                snack1.equals(_other.snack1)        &&
                snack2.equals(_other.snack2);
    }
    
    @Override
    public String toString()
    {
        return "B: " + breakfast.toString() + " L: " + lunch.toString() + " D:" + dinner.toString() + " S1:" +
                snack1.toString() + " S2:" + snack2.toString();
    }
}