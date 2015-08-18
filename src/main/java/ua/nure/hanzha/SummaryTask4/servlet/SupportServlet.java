package ua.nure.hanzha.SummaryTask4.servlet;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.servlet.callable.mailSupport.MailSupportCallable;
import ua.nure.hanzha.SummaryTask4.servlet.callable.mailSupport.MailSupportOperationsMap;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public class SupportServlet extends HttpServlet {


    private static final String PARAM_COMMAND = "command";
    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    @Override
    public void init() throws ServletException {
        MailSupportOperationsMap.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String command = request.getParameter(PARAM_COMMAND);
        MailSupportOperationsMap.initMailSupportCallableMap(request, response, session);
        MailSupportCallable mailSupportCallable = MailSupportOperationsMap.getMailSupportCallable(command);
        if (mailSupportCallable != null) {
            mailSupportCallable.call();
        } else {
            //UNSUPPORTED COMMAND
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.SUPPORT_HTML);
        requestDispatcher.forward(request, response);
    }
}
