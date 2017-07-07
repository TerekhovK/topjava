package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 *
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> userCaloriesPerDay = mealList.stream()
                .collect(Collectors.groupingBy(p -> p.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        List<UserMealWithExceed> userMealWithExceedList;
        return userMealWithExceedList = mealList.stream()
                .filter(p -> TimeUtil.isBetween(p.getDateTime().toLocalTime(), startTime, endTime))
                .map((s) -> new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(), userCaloriesPerDay.get(s.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> userCaloriesPerDay = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal meal : mealList
                ) {
            userCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (u, a) -> u + a);
        }
        for (UserMeal meal : mealList
                ) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean exceed = false;
                if (userCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                    exceed = true;
                userMealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
            }

        }

        return userMealWithExceedList;
    }
}
