package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


/**
 * Created by User on 028 28.07.17.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.MIN,"NEW",1000);
        Meal created = service.save(newMeal,USER_ID);
        newMeal.setId(created.getId());
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1,newMeal), service.getAll(USER_ID));
    }


    @Test(expected = NotFoundException.class)
    public void testUpdateWithWrongUserIDSave() throws Exception {
        Meal newMeal=new Meal(MEAL1_ID,LocalDateTime.MIN,"NEW",1000);
        service.update(newMeal,ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ADMIN_MEAL_ID+1,ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL1), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
         service.delete(1,USER_ID);
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL1_ID,USER_ID);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
       service.get(MEAL1_ID,ADMIN_ID);

    }


    @Test
    public void testGetAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(USER_LIST_MEAL, all);
    }

    @Test
    public void testGetAllFilteredDate() throws Exception {
        List<Meal> all = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31), LocalDate.of(2015, Month.MAY, 31),USER_ID);
        MATCHER.assertCollectionEquals(USER_DATE_FILTERED_LIST_MEAL, all);
    }

    @Test
    public void testGetAllFilteredDateTime() throws Exception {
        List<Meal> all = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 31,13,00), LocalDateTime.of(2015, Month.MAY, 31,23,00),USER_ID);
        MATCHER.assertCollectionEquals(USER_DATETIME_FILTERED_LIST_MEAL, all);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updateMeal=new Meal(MEAL1_ID,LocalDateTime.now(),"UpdatedName",1000);
        service.update(updateMeal,USER_ID);
        MATCHER.assertEquals(updateMeal, service.get(MEAL1_ID,USER_ID));
    }
}