package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;


import java.util.List;


public interface MealDAO {
    Meal getMealById(long id);

    Meal addMeal(Meal meal);

    boolean deleteMeal(long id);

    Meal updateMeal(Meal meal);

    List<Meal> getAllMeals();
}
