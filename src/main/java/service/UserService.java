package service;

import dao.UserDAO;
import entities.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBException;
import util.DBHelper;

import java.util.List;

public class UserService {
    private static UserService userService;
    private SessionFactory sessionFactory;

    private UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserService(DBHelper.getSessionFactory());
        }
        return userService;
    }

    public boolean addUser(User user){
        try (Session session = sessionFactory.openSession()) {
            if (new UserDAO(session).getUserByLogin(user.getLogin()) != null) {
                return false;
            } else {
                new UserDAO(session).addUser(user);
                return true;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user, String password){
        try (Session session = sessionFactory.openSession()) {
            if (new UserDAO(session).validateUser(user.getLogin(), password)) {
                new UserDAO(session).updateUser(user);
                return true;
            } else {
                return false;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void deleteUser(String login) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            new UserDAO(session).deleteUser(login);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<User> getUsersList() throws DBException {
        try (Session session = sessionFactory.openSession()) {
            return new UserDAO(session).getAllUsers();
        } catch (HibernateException e) {
           throw new DBException(e);
        }
    }
}
