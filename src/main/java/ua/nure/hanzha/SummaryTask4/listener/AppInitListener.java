package ua.nure.hanzha.SummaryTask4.listener;


import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.db.dao.entrant.EntrantDao;
import ua.nure.hanzha.SummaryTask4.db.dao.entrant.EntrantDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.entrantInfoAdmin.EntrantInfoAdminDao;
import ua.nure.hanzha.SummaryTask4.db.dao.entrantInfoAdmin.EntrantInfoAdminImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.entrantfinalsheet.EntrantFinalSheetDao;
import ua.nure.hanzha.SummaryTask4.db.dao.entrantfinalsheet.EntrantFinalSheetDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.extramark.ExtraMarkDao;
import ua.nure.hanzha.SummaryTask4.db.dao.extramark.ExtraMarkDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.faculty.FacultyDao;
import ua.nure.hanzha.SummaryTask4.db.dao.faculty.FacultyDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.facultyentrant.FacultyEntrantDao;
import ua.nure.hanzha.SummaryTask4.db.dao.facultyentrant.FacultyEntrantDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.facultysubject.FacultySubjectDao;
import ua.nure.hanzha.SummaryTask4.db.dao.facultysubject.FacultySubjectDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.mark.MarkDao;
import ua.nure.hanzha.SummaryTask4.db.dao.mark.MarkDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.subject.SubjectDao;
import ua.nure.hanzha.SummaryTask4.db.dao.subject.SubjectDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDao;
import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDaoImpl;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManagerImpl;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.security.AuthorizationMap;
import ua.nure.hanzha.SummaryTask4.security.XmlAuthorizationMap;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet.EntrantFinalSheetService;
import ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet.EntrantFinalSheetServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.entrantInfoAdmin.EntrantInfoAdminService;
import ua.nure.hanzha.SummaryTask4.service.entrantInfoAdmin.EntrantInfoAdminServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.extraMark.ExtraMarkService;
import ua.nure.hanzha.SummaryTask4.service.extraMark.ExtraMarkServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyService;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.facultyAdmin.FacultyAdminService;
import ua.nure.hanzha.SummaryTask4.service.facultyAdmin.FacultyAdminServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.facultyEntrant.FacultyEntrantService;
import ua.nure.hanzha.SummaryTask4.service.facultyEntrant.FacultyEntrantServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.facultySubject.FacultySubjectService;
import ua.nure.hanzha.SummaryTask4.service.facultySubject.FacultySubjectServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.mark.MarkService;
import ua.nure.hanzha.SummaryTask4.service.mark.MarkServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.registration.RegistrationService;
import ua.nure.hanzha.SummaryTask4.service.registration.RegistrationServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectService;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectServiceImpl;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.service.user.UserServiceImpl;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;
import ua.nure.hanzha.SummaryTask4.util.TicketsWriterReaderUtilities;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 19/07/15.
 */
public class AppInitListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    private static final String PATH_TO_PROPERTIES_SQL = "WEB-INF/classes/sqlQueries.properties";
    private static final String PATH_TO_PROPERTIES_TICKETS = "WEB-INF/classes/tickets.properties";
    private static final String CONTEXT_PARAM_SECURITY_CONFIG = "SECURITY_CONFIG";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String contextPath = servletContext.getRealPath("/");

        TransactionManager txManager = new TransactionManagerImpl();

        SqlQueriesUtilities.initSqlQueriesHolder(contextPath + PATH_TO_PROPERTIES_SQL);
        TicketsWriterReaderUtilities.initSqlQueriesHolder(contextPath + PATH_TO_PROPERTIES_TICKETS);

        String securityConfigName = servletContext.getInitParameter(CONTEXT_PARAM_SECURITY_CONFIG);
        setAuthorizationMap(servletContext, securityConfigName);

        setUpServices(servletContext, txManager);
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
        MarkDao markDao = new MarkDaoImpl();
        ExtraMarkDao extraMarkDao = new ExtraMarkDaoImpl();
        FacultyEntrantDao facultyEntrantDao = new FacultyEntrantDaoImpl();
        EntrantFinalSheetDao entrantFinalSheetDao = new EntrantFinalSheetDaoImpl();


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

        MarkService markService = new MarkServiceImpl(txManager, markDao);
        servletContext.setAttribute(AppAttribute.MARK_SERVICE, markService);

        ExtraMarkService extraMarkService = new ExtraMarkServiceImpl(txManager, extraMarkDao);
        servletContext.setAttribute(AppAttribute.EXTRA_MARK_SERVICE, extraMarkService);

        FacultyEntrantService facultyEntrantService = new FacultyEntrantServiceImpl(txManager, facultyEntrantDao);
        servletContext.setAttribute(AppAttribute.FACULTY_ENTRANT_SERVICE, facultyEntrantService);

        EntrantFinalSheetService entrantFinalSheetService = new EntrantFinalSheetServiceImpl(txManager, entrantFinalSheetDao);
        servletContext.setAttribute(AppAttribute.ENTRANT_FINAL_SHEET_SERVICE, entrantFinalSheetService);

        FacultySubjectService facultySubjectService = new FacultySubjectServiceImpl(txManager, facultySubjectDao);
        servletContext.setAttribute(AppAttribute.FACULTY_SUBJECT_SERVICE, facultySubjectService);
    }

    private void setAuthorizationMap(ServletContext servletContext, String fileName) {
        try {
            File file = new File(this.getClass().getResource(fileName).toURI());
            AuthorizationMap authorizationMap = new XmlAuthorizationMap(file);
            servletContext.setAttribute(AppAttribute.AUTHORIZATION_MAP, authorizationMap);
            LOGGER.debug("AuthorisationMap authorized");
        } catch (Exception ex) {
            LOGGER.error("Can't load authorization map file: " + fileName, ex);
            throw new RuntimeException("Can't load authorization map file: '" + fileName + "'");
        }
    }
}
