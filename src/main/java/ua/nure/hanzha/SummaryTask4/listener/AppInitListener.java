package ua.nure.hanzha.SummaryTask4.listener;


import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.db.dao.entrant.EntrantDao;
import ua.nure.hanzha.SummaryTask4.db.dao.entrant.EntrantDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDao;
import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManagerImpl;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.registration.RegistrationService;
import ua.nure.hanzha.SummaryTask4.service.registration.RegistrationServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.service.user.UserServiceImpl;
import ua.nure.hanzha.SummaryTask4.util.TicketsWriterReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 19/07/15.
 */
public class AppInitListener implements ServletContextListener {

    private static final String PATH_TO_PROPERTIES_SQL = "WEB-INF/classes/sqlQueries.properties";
    private static final String PATH_TO_PROPERTIES_TICKETS = "WEB-INF/classes/tickets.properties";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String contextPath = servletContext.getRealPath("/");
        TransactionManager txManager = new TransactionManagerImpl();
        SqlQueriesHolder.initSqlQueriesHolder(contextPath + PATH_TO_PROPERTIES_SQL);
        try {
            TicketsWriterReader.initSqlQueriesHolder(contextPath  + PATH_TO_PROPERTIES_TICKETS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setUpServices(servletContext, txManager);
        System.out.println("INITIALAIZED");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void setUpServices(ServletContext servletContext, TransactionManager txManager) {
        UserDao userDao = new UserDaoImpl();
        EntrantDao entrantDao = new EntrantDaoImpl();


        UserService userService = new UserServiceImpl(txManager, userDao);
        servletContext.setAttribute(AppAttribute.USER_SERVICE, userService);

        EntrantService entrantService = new EntrantServiceImpl(txManager, entrantDao);
        servletContext.setAttribute(AppAttribute.ENTRANT_SERVICE, entrantService);

        RegistrationService registrationService = new RegistrationServiceImpl(txManager, userDao, entrantDao);
        servletContext.setAttribute(AppAttribute.REGISTRATION_SERVICE, registrationService);


    }


}
