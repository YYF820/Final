package ua.nure.hanzha.SummaryTask4.listener;


import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.db.dao.entrant.EntrantDao;
import ua.nure.hanzha.SummaryTask4.db.dao.entrant.EntrantDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.entrantInfoAdmin.EntrantInfoAdminDao;
import ua.nure.hanzha.SummaryTask4.db.dao.entrantInfoAdmin.EntrantInfoAdminImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.faculty.FacultyDao;
import ua.nure.hanzha.SummaryTask4.db.dao.faculty.FacultyDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.facultysubject.FacultySubjectDao;
import ua.nure.hanzha.SummaryTask4.db.dao.facultysubject.FacultySubjectDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.subject.SubjectDao;
import ua.nure.hanzha.SummaryTask4.db.dao.subject.SubjectDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDao;
import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManagerImpl;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.security.AuthorizationMap;
import ua.nure.hanzha.SummaryTask4.security.XmlAuthorizationMap;
import ua.nure.hanzha.SummaryTask4.service.facultyAdmin.FacultyAdminService;
import ua.nure.hanzha.SummaryTask4.service.facultyAdmin.FacultyAdminServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.entrantInfoAdmin.EntrantInfoAdminService;
import ua.nure.hanzha.SummaryTask4.service.entrantInfoAdmin.EntrantInfoAdminServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyService;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.registration.RegistrationService;
import ua.nure.hanzha.SummaryTask4.service.registration.RegistrationServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectService;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.service.user.UserServiceImpl;
import ua.nure.hanzha.SummaryTask4.util.TicketsWriterReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 19/07/15.
 */
public class AppInitListener implements ServletContextListener {

    private static final String PATH_TO_PROPERTIES_SQL = "WEB-INF/classes/sqlQueries.properties";
    private static final String PATH_TO_PROPERTIES_TICKETS = "WEB-INF/classes/tickets.properties";
    private static final String CONTEXT_PARAM_SECURITY_CONFIG = "SECURITY_CONFIG";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String contextPath = servletContext.getRealPath("/");

        TransactionManager txManager = new TransactionManagerImpl();

        SqlQueriesHolder.initSqlQueriesHolder(contextPath + PATH_TO_PROPERTIES_SQL);
        TicketsWriterReader.initSqlQueriesHolder(contextPath + PATH_TO_PROPERTIES_TICKETS);

        String securityConfigName = servletContext.getInitParameter(CONTEXT_PARAM_SECURITY_CONFIG);
        setAuthorizationMap(servletContext, securityConfigName);

        setUpServices(servletContext, txManager);
        System.out.println("INITIALAIZED");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void setUpServices(ServletContext servletContext, TransactionManager txManager) {
        UserDao userDao = new UserDaoImpl();
        EntrantDao entrantDao = new EntrantDaoImpl();
        EntrantInfoAdminDao entrantInfoAdminDao = new EntrantInfoAdminImpl();
        FacultyDao facultyDao = new FacultyDaoImpl();
        SubjectDao subjectDao = new SubjectDaoImpl();
        FacultySubjectDao facultySubjectDao = new FacultySubjectDaoImpl();


        UserService userService = new UserServiceImpl(txManager, userDao);
        servletContext.setAttribute(AppAttribute.USER_SERVICE, userService);

        EntrantService entrantService = new EntrantServiceImpl(txManager, entrantDao);
        servletContext.setAttribute(AppAttribute.ENTRANT_SERVICE, entrantService);

        RegistrationService registrationService = new RegistrationServiceImpl(txManager, userDao, entrantDao);
        servletContext.setAttribute(AppAttribute.REGISTRATION_SERVICE, registrationService);

        EntrantInfoAdminService entrantInfoAdminService = new EntrantInfoAdminServiceImpl(txManager, entrantInfoAdminDao);
        servletContext.setAttribute(AppAttribute.ENTRANT_INFO_ADMIN_SERVICE, entrantInfoAdminService);

        FacultyService facultyService = new FacultyServiceImpl(txManager, facultyDao);
        servletContext.setAttribute(AppAttribute.FACULTY_SERVICE, facultyService);

        SubjectService subjectService = new SubjectServiceImpl(txManager, subjectDao);
        servletContext.setAttribute(AppAttribute.SUBJECT_SERVICE, subjectService);

        FacultyAdminService facultyAdminService = new FacultyAdminServiceImpl(txManager, facultyDao, facultySubjectDao);
        servletContext.setAttribute(AppAttribute.FACULTY_ADMIN_SERVICE, facultyAdminService);
    }

    private void setAuthorizationMap(ServletContext servletContext, String fileName) {
        try {
            File file = new File(this.getClass().getResource(fileName).toURI());
            AuthorizationMap authorizationMap = new XmlAuthorizationMap(file);
            servletContext.setAttribute(AppAttribute.AUTHORIZATION_MAP, authorizationMap);
        } catch (Exception ex) {
            throw new RuntimeException("Can't load authorization map file: '" + fileName + "'");
        }
    }


}
