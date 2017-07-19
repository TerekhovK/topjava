package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.InMemoryMealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;


import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new InMemoryMealDAOImpl();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String forward;
        String action = req.getParameter("action");
        action = (action == null ? "" : action);
        String addOrEdit = "/meal.jsp";
        if (action.equalsIgnoreCase("delete")) {
            long mealId = Long.valueOf(req.getParameter("mealId"));
            log.info("Delete meal with id:" + mealId);
            dao.delete(mealId);
            resp.sendRedirect("meals");
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = addOrEdit;
            long mealId = Long.valueOf(req.getParameter("mealId"));
            Meal meal = dao.getById(mealId);
            log.info("Update meal with id:" + mealId);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("add")) {
            forward = addOrEdit;
            log.info("Add new meal");
            Meal meal = dao.add(new Meal(LocalDateTime.now(), "", 0));
            req.setAttribute("meal", meal);
        } else {
            forward = "/meals.jsp";
            log.info("Get all meals");
            req.setAttribute("meals", MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_ONE_DAY));
        }
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("mealid");

        Meal meal = new Meal(
                Long.valueOf(id),
                LocalDateTime.parse(req.getParameter("DateTime"), TimeUtil.DEFAULT_DATE_TIME_FORMATTER),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));


        log.info(meal.isNullId()?"Add new meal "+meal:"Update meal "+meal);
        dao.update(meal);
        resp.sendRedirect("meals");
    }
}
