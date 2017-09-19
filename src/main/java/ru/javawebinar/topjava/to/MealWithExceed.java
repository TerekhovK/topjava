package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class MealWithExceed extends MealTo {

    private final boolean exceed;

    public MealWithExceed(@JsonProperty("id") Integer id,
                          @JsonProperty("dateTime") LocalDateTime dateTime,
                          @JsonProperty("description") String description,
                          @JsonProperty("calories") int calories,
                          @JsonProperty("exceed") boolean exceed) {
        super(id, dateTime, description, calories);
        this.exceed = exceed;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + super.getId() +
                ", dateTime=" + super.getDateTime() +
                ", description='" + super.getDescription() + '\'' +
                ", calories=" + super.getCalories() +
                ", exceed=" + exceed +
                '}';
    }
}