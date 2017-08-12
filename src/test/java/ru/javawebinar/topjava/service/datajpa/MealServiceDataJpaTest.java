package ru.javawebinar.topjava.service.datajpa;


import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealAbstractServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ActiveProfiles(Profiles.DATAJPA)
public class MealServiceDataJpaTest extends MealAbstractServiceTest {
    @Test

    public void testGetWithUser() {
        Meal meal=service.getWithUser(MEAL1_ID,USER_ID);
        MATCHER.assertEquals(MEAL1,meal);
    }

    @Test
    public void testGetWithUserNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getWithUser(MEAL1_ID+25,USER_ID);
    }

}
