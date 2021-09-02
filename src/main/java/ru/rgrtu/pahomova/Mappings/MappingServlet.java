package ru.rgrtu.pahomova.Mappings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rgrtu.pahomova.Utils.Connect;
import ru.rgrtu.pahomova.Utils.UserDataHolder;
import ru.rgrtu.pahomova.Utils.UserTools;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static ru.rgrtu.pahomova.Utils.UserTools.*;

public class MappingServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(MappingServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        Connect.warmup();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String path = req.getServletPath();
        logger.debug("Got GET request: " + path);
        UserDataHolder userData = (UserDataHolder) req.getSession().getAttribute("userData");

        switch (path) {
            case ("/login"):
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
                break;
            case ("/logout"):
                req.getSession().setAttribute("isAuthorized", "false");
                logger.info("User @" + userData.username + " was logout");
                req.getSession().setAttribute("username", "");
                resp.sendRedirect("/login");
                break;
            case ("/parentCab"):
                if (userData.isWorker) {
                    resp.sendRedirect("/login");
                } else {
                    getServletContext().getRequestDispatcher("/parentCab.jsp").forward(req, resp);
                }
                break;
            case ("/teacherCab"):
                if (!userData.isWorker || "2".equalsIgnoreCase(userData.profCode)) {
                    resp.sendRedirect("/login");
                } else {
                    getServletContext().getRequestDispatcher("/teacherCab.jsp").forward(req, resp);
                }
                break;
            case ("/workerCab"):
                if (!userData.isWorker || "1".equalsIgnoreCase(userData.profCode)) {
                    resp.sendRedirect("/login");
                } else {
                    getServletContext().getRequestDispatcher("/workerCab.jsp").forward(req, resp);
                }
                break;
            case ("/cab_redirect"):
                if (userData.isWorker) {
                    if ("1".equalsIgnoreCase(userData.profCode)) {
                        resp.sendRedirect("/teacherCab");
                    } else {
                        resp.sendRedirect("/workerCab");
                    }
                } else {
                    resp.sendRedirect("/parentCab");
                }
                break;
            case ("/"):
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String path = req.getServletPath();
        HttpSession session = req.getSession();
        logger.debug("Got POST request: " + path);

        switch (path) {
            case ("/login"):
                String username = req.getParameter("username");
                String pass = req.getParameter("password");
                logger.debug("Got login: " + username + " and pass: " + pass);
                if (UserTools.checkLoginAndPass(username, pass)) {

                    logger.debug("Login and pass exists");

                    session.setAttribute("isAuthorized", true);
                    UserDataHolder user = null;
                    try {
                        user = getUserData(username, pass);
                    } catch (SQLException e) {
                        logger.error(e.toString());
                    }
                    user.username = username;
                    session.setAttribute("userData", user);

                    logger.info("User @" + user.username + " was authorized");
                    resp.sendRedirect("/");
                } else {
                    resp.sendRedirect("/login");
                }
                break;
            case ("/parentCab"):
                String childFirstName = req.getParameter("firstName");
                String childSecondName = req.getParameter("secondName");
                String childLastName = req.getParameter("lastName");
                String childBirthday = req.getParameter("birthday");
                String childSex = req.getParameter("sex");
                try {
                    addNewListener(childFirstName, childSecondName, childLastName, childBirthday, childSex);
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                resp.sendRedirect("/");
                break;
            case ("/handleStudentRequest"):
                String listenerId = req.getParameter("listenerId");
                String yesOrNo = req.getParameter("yes_no");
                try {
                    handleStudentRequest(listenerId, yesOrNo);
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                resp.sendRedirect("/");
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
