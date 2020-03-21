package servlets;

import entities.User;
import service.UserService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/list")
public class UsersListServlet extends HttpServlet {
    private UserService userService = UserService.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = null;
        try {
            users = userService.getUsersList();
        } catch (DBException e) {
            e.printStackTrace();
        }
        req.setAttribute("usersList", users);
        req.getRequestDispatcher("views/list.jsp").forward(req, resp);
//       getServletContext().getRequestDispatcher("views/list.jsp").forward(req, resp);
    }
}
