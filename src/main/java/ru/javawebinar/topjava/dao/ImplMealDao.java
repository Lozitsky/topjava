package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Iterator;
import java.util.List;

public class ImplMealDao implements MealDao {
    public ImplMealDao() {
    }

    @Override
    public void addMeal(Meal meal) {
        getAllMeals().add(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        List <Meal> allMeals = getAllMeals();
        Meal next;
        int index = -1;
        for (Iterator <Meal> iterator = allMeals.iterator(); iterator.hasNext(); ) {

            next = iterator.next();
            if (next.getId() == meal.getId()) {
                index = allMeals.indexOf(next);
                allMeals.remove(next);
                allMeals.add(index, meal);
            }
        }
    }

    @Override
    public void deleteMeal(int id) {
        for (Iterator <Meal> iterator = getAllMeals().iterator(); iterator.hasNext(); ) {
            Meal next = iterator.next();
            if (next.getId() == id) {
                getAllMeals().remove(next);
            }
        }
    }

    @Override
    public List <Meal> getAllMeals() {
        return MealsUtil.getMeals();
    }

    @Override
    public Meal getMealById(int id) {
        for (Iterator <Meal> iterator = getAllMeals().iterator(); iterator.hasNext(); ) {
            Meal meal = iterator.next();
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }
}
