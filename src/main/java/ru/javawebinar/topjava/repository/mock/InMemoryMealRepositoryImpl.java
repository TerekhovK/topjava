package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Comparator<Meal> mealComparator = Comparator.comparing(Meal::getDateTime).reversed();
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.getUserId() != userId) {
            return null;
        }

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        return userId == meal.getUserId() && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return userId == meal.getUserId() ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> meals = new ArrayList<>(repository.values());
        meals = meals.stream().filter(meal -> meal.getUserId() == userId).sorted(mealComparator).collect(Collectors.toList());
        return meals;
    }
}

