package ru.javawebinar.topjava.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.DataRepository.MealBase;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;


public class MealDAOImpl implements MealDAO {
    private static final Logger log = LoggerFactory.getLogger(MealDAOImpl.class);
    private final MealBase mealBase = new MealBase();

    @Override
    public Meal getMealById(long id) {
        return mealBase.getMealById(id);
    }

    @Override
    public Meal addMeal(Meal meal) {
        return mealBase.addMeal(meal);
    }

    @Override
    public boolean deleteMeal(long id) {
        return mealBase.deleteMeal(id);
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return mealBase.update(meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return mealBase.getAllMeals();
    }
}
