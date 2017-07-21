package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "signIn":
                String userId = request.getParameter("id");
                log.info("get userid" + userId);
                AuthorizedUser.setId(Integer.valueOf(userId));
                response.sendRedirect("meals");
                break;
            case "all":
                log.debug("forward to users");
                request.getRequestDispatcher("/users.jsp").forward(request, response);
        }


    }
}
