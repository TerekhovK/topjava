package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;


    @Override
    public Meal update(Meal meal, int userId) {
        return checkNotFound(repository.save(meal, userId), "meal:" + meal + " userId:" + userId);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        checkNew(meal);
        return checkNotFound(repository.save(meal, userId), "meal:" + meal + " userId:" + userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFound(repository.delete(id, userId), "id:" + id + " userId:" + userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFound(repository.get(id, userId), "id:" + id + " userId:" + userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new ArrayList<>(repository.getAll(userId));
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return new ArrayList<>(repository.getAllFiltered(userId, startDate, endDate));
    }
}