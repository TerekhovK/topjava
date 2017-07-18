package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;

import java.util.List;



public interface MealDAO {
    Meal getById(long id);

    Meal add(Meal meal);

    boolean delete(long id);

    Meal update(Meal meal);

    List<Meal> getAll();


}
