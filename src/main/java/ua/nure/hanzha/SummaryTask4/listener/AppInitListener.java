package ua.nure.hanzha.SummaryTask4.listener;


import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDao;
import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManagerImpl;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.service.user.UserServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by faffi-ubuntu on 19/07/15.
 */
public class AppInitListener implements ServletContextListener {

    private static final String PATH_TO_PROPERITES_SQL = "WEB-INF/classes/sqlQueries.properties";
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String contextpath = servletContext.getRealPath("/");
        TransactionManager txManager = new TransactionManagerImpl();
        SqlQueriesHolder.initSqlQueriesHolder(contextpath + PATH_TO_PROPERITES_SQL);
        setUpServices(servletContext, txManager);
        System.out.println("INITIALAIZED");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void setUpServices(ServletContext sevletContext, TransactionManager txManager) {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(txManager, userDao);
        sevletContext.setAttribute("userService", userService);
    }
}
