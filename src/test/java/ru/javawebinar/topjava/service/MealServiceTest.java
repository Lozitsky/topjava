package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration(
        {
                "classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml"
        }
)
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_1, USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL_ID_1, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_1, USER_ID);
        service.delete(MEAL_ID_2, USER_ID);
        service.delete(MEAL_ID_3, USER_ID);
        service.delete(MEAL_ID_4, USER_ID);
        service.delete(MEAL_ID_5, USER_ID);
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEAL_6);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(MEAL_ID_1, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        List<Meal> mealsExpected = Arrays.asList(MEAL_ADMIN_1, MEAL_ADMIN_2);
        mealsExpected.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        assertMatch(meals, mealsExpected);
    }

    @Test
    public void update() {
        Meal mealUpdate = new Meal(MEAL_1);
        mealUpdate.setCalories(1111);
        mealUpdate.setDateTime(LocalDateTime.now());
        mealUpdate.setDescription("cake");
        service.update(mealUpdate, USER_ID);
        assertMatch(service.get(MEAL_ID_1, USER_ID), mealUpdate);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal mealUpdate = new Meal(MEAL_1);
        mealUpdate.setCalories(1111);
        mealUpdate.setDateTime(LocalDateTime.now());
        mealUpdate.setDescription("cake");
        service.update(mealUpdate, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Админ Snickers", 400);
        Meal meal = service.create(newMeal, ADMIN_ID);
        newMeal.setId(meal.getId());
        assertMatch(service.getAll(ADMIN_ID), newMeal, MEAL_ADMIN_2, MEAL_ADMIN_1);
    }

    @Test(expected = DataAccessException.class)
    public void createIdentityDate() throws Exception {
        Meal newMeal = new Meal(MEAL_ADMIN_2.getDateTime(), "Админ Snickers2", 400);
        service.create(newMeal, ADMIN_ID);
    }
}