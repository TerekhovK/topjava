package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;


import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log= LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Comparator<Meal> mealComparator = Comparator.comparing(Meal::getDateTime).reversed();
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {


        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            log.info("save meal {} and userId{}", meal, userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        else{
            if(repository.get(meal.getId()).getUserId()!=meal.getUserId()){
                return null;
            }
            else {
                log.info("save meal {} and userId{}", meal, userId);
                repository.put(meal.getId(), meal);
                return meal;
            }
        }


    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal=get(id,userId);
        log.info("delete by id {} and userId{}", id, userId);
        return (meal != null) && (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        log.info("get by id {} and userId{}", id, userId);
        return (meal==null||meal.getUserId()!=userId)?null:meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(mealComparator)
                .collect(Collectors.toList());

    }

    public Collection<Meal> getAllFiltered(int userId, LocalDate start, LocalDate end) {
        log.info("getAll filtered by startDate {} and endDate{}", start, end);
        return getAll(userId).stream()
                             .filter(meal -> DateTimeUtil.isBetween((meal.getDate()), start, end))
                             .collect(Collectors.toCollection(ArrayList::new));
    }

}

