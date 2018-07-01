package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.ImplMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.dao.CRUD.*;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String JSP = "";

    private ImplMealDao mealDao;

    public MealServlet() {
        super();
        mealDao = new ImplMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");


        if ("listMeals".equalsIgnoreCase(action)) {
            JSP = "/meals.jsp";
        } else if (D.action().equalsIgnoreCase(action)) {
            mealDao.deleteMeal(Integer.parseInt(req.getParameter(Id.action())));
            JSP = "/meals.jsp";
        } else if (U.action().equalsIgnoreCase(action)) {
            Meal meal = mealDao.getMealById(Integer.parseInt(req.getParameter(Id.action())));
            req.setAttribute("meal", meal);
            JSP = "/addMeal.jsp";
            log.debug(meal.toString());
        } else if (C.action().equalsIgnoreCase(action)) {
            JSP = "/addMeal.jsp";
        }

        if (JSP.equals("/meals.jsp")) {
            List <MealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealDao.getAllMeals(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
            req.setAttribute("exceededList", filteredWithExceeded);
        }

        log.debug("forwar to " + JSP);
        req.getRequestDispatcher(JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("post");
        String mealId = req.getParameter(Id.action());
        String expression = req.getParameter(DT.action()).contains("T") ? "yyyy-MM-dd'T'HH:mm" : "yyyy-MM-dd HH:mm";
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter(DT.action()), DateTimeFormatter.ofPattern(expression));
        String description = req.getParameter(DC.action());
        int calories = Integer.parseInt(req.getParameter(CR.action()));
        Meal meal;
        if (mealId.equals("")) {
            meal = new Meal(dateTime,
                    description,
                    calories);
            mealDao.addMeal(meal);
        } else {
            meal = new Meal(Integer.parseInt(mealId), dateTime,
                    description,
                    calories);
            mealDao.updateMeal(meal);
        }
        JSP = "/meals.jsp";
        List <MealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealDao.getAllMeals(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        req.setAttribute("exceededList", filteredWithExceeded);
        req.getRequestDispatcher(JSP).forward(req, resp);
    }
}
