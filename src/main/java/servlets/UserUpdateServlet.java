package servlets;

import entities.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserUpdateServlet extends HttpServlet {
    private UserService userService = UserService.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/userUpdate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        User user = getUser(req, resp);
        String password = req.getParameter("password");
        if (userService.updateUser(user, password)) {
            resp.sendRedirect("/list?updatedLogin=" + user.getLogin());
//                getServletContext().getRequestDispatcher("/list?updatedLogin=" + user.getLogin()).forward(req, resp);
        } else {
            resp.sendRedirect("/list?wrongUpdate=" + user.getLogin());
//                getServletContext().getRequestDispatcher("/list?wrongUpdate=" + user.getLogin()).forward(req, resp);
        }
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("newPassword");
        String name = req.getParameter("newName");
        double amount = Double.parseDouble(req.getParameter("newAmount"));
        return new User(login, password, name, amount);
    }
}
