package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserAbstractServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;


@ActiveProfiles(Profiles.JDBC)
public class UserServiceJdbcTest extends UserAbstractServiceTest {
    @Test(expected = UnsupportedOperationException.class)
    public void testGetWithUserNotFound() throws Exception {
        service.getWithMeals(MEAL1_ID);
    }
}
