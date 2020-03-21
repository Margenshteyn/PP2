package dao;

import entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDAO {

    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }


    public User getUserByLogin(String login) {
        return session.get(User.class, login);
    }

    public void addUser(User user) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
                printTransactionError();
            } catch (Exception eRoll) {
                printRollbackError();
            }
        }
    }

    private void printRollbackError() {
        System.err.println("Couldn't rollback transaction");
    }

    private void printTransactionError() {
        System.err.println("Error happened, transaction rollback");
    }

    public boolean validateUser(String login, String password) {
        User user = getUserByLogin(login);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    public void updateUser(User user) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
                printTransactionError();
            } catch (Exception eRoll) {
                printRollbackError();
            }
        }

    }

    public List<User> getAllUsers() {
        Transaction transaction = session.beginTransaction();
        List<User> allUsers = session.createQuery("FROM User").getResultList();
        transaction.commit();
        return allUsers;
        //return session.createQuery("FROM User").getResultList();
    }

    public void deleteUser(String login) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = getUserByLogin(login);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
                printTransactionError();
            } catch (Exception eRoll) {
                printRollbackError();
            }
        }
    }
}