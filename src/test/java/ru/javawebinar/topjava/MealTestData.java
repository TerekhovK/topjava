package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import static java.time.LocalDateTime.of;


import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;


    public static final Meal MEAL1 = new Meal(MEAL1_ID, of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL1_ID + 1, of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL1_ID + 2, of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL1_ID + 3, of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL5 = new Meal(MEAL1_ID + 4, of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal MEAL6 = new Meal(MEAL1_ID + 5, of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID, of(2016, Month.APRIL, 1, 14, 0), "обед", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID + 1, of(2016, Month.APRIL, 1, 21, 0), "ужин", 1900);

    public static final List<Meal> USER_LIST_MEAL= Arrays.asList(MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1);
    public static final List<Meal> USER_DATE_FILTERED_LIST_MEAL= Arrays.asList(MEAL6,MEAL5,MEAL4);
    public static final List<Meal> USER_DATETIME_FILTERED_LIST_MEAL= Arrays.asList(MEAL6,MEAL5);

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
            (expected, actual) ->
                     expected == actual || Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getCalories(), actual.getCalories()));



}
