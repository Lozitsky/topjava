package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);
    @Autowired
    private MealService service;

    public Meal get (int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get({})", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        int userId = SecurityUtil.authUserId();
        log.info("create {}, user id", meal, userId);
        return service.create(meal);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {}, user id", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} with id={}, user id {}", meal, id, userId);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List <MealWithExceed> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll({})", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List <MealWithExceed> getMealsBetweenLocalDateTimes(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getMealsBetweenLocalDateTimes({}, {}, {}, {}), user id = {}", startDate, startTime, endDate, endTime, userId);
        LocalTime timeStart = startTime == null ? DateTimeUtil.Min_Time : startTime;
        LocalTime timeEnd = endTime == null ? DateTimeUtil.Max_Time : endTime;
        LocalDate dateStart = startDate == null ? DateTimeUtil.Min_Date : startDate;
        LocalDate dateEnd = endDate == null ? DateTimeUtil.Max_Date : endDate;
//        return MealsUtil.getFilteredWithExceeded(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay(), startDate, startTime, endDate, endTime);
        return MealsUtil.getFilteredWithExceeded(service.getMealsBetweenLocalDate(dateStart, dateEnd, userId), SecurityUtil.authUserCaloriesPerDay(), timeStart, timeEnd);
    }



}