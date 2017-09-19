package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/ajax/profile/meals")
public class MealAjaxController extends AbstractMealController {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MealTo getTo(@PathVariable("id") int id) {
        return MealsUtil.asTo(super.get(id));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid MealTo mealTo, BindingResult result) {
        if (result.hasErrors()) {
            String body = result.getFieldErrors().stream()
                    .map(fe -> fe.getDefaultMessage().startsWith(fe.getField())
                            ? fe.getDefaultMessage()
                            : fe.getField() + ' ' + fe.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Meal meal = MealsUtil.createNewFromTo(mealTo);
        if (mealTo.isNew()) {
            super.create(meal);
        } else {
            super.update(meal, meal.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "startTime", required = false) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
