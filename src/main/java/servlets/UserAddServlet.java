package servlets;

import entities.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userAdd")
public class UserAddServlet extends HttpServlet {
    private UserService userService = UserService.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = getUser(req, resp);
        if (userService.addUser(user)) {
//            resp.sendRedirect("/list?addUserLogin=" + user.getLogin());
            getServletContext().getRequestDispatcher("/list?addUserLogin=" + user.getLogin()).forward(req, resp);
        } else {
//            resp.sendRedirect("/list?wrongRequest=" + user.getLogin());
            getServletContext().getRequestDispatcher("/list?wrongRequest=" + user.getLogin()).forward(req, resp);
        }
    }

    private User getUser(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        double amount = Double.parseDouble(req.getParameter("amount"));
        return new User(login, password, name, amount);
    }
}
