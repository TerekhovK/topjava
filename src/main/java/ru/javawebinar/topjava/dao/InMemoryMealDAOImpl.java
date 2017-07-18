package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class InMemoryMealDAOImpl implements MealDAO {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealDAOImpl.class);
    private AtomicLong counter = new AtomicLong();
    private Map<Long, Meal> map = new ConcurrentHashMap<>();

    {

        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }

    public Meal getById(long id) {
        return map.get(id);
    }

    public Meal add(Meal meal) {
        log.debug("Set id to meal: " + meal);
        meal.setId(counter.getAndIncrement());
        log.debug("Put meal:\"" + meal + "\"to map");
        map.put(meal.getId(), meal);

        return meal;
    }

    public boolean delete(long id) {
        log.debug("Delete meal by id " + id);
        return map.remove(id) != null;
    }

    public Meal update(Meal meal) {

        if (meal.isNullId()) {
            add(meal);
        } else {
            log.debug("Update meal:" + meal + " by id:" + meal.getId());
            map.put(meal.getId(), meal);
        }
        return meal;
    }

    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }
}

