package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.servlet.callable.mail.MailCallable;
import ua.nure.hanzha.SummaryTask4.servlet.callable.mail.MailOperationsMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public class MailSenderServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        MailOperationsMap.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String command = (String) session.getAttribute(SessionAttribute.COMMAND);
        MailOperationsMap.initMailCallabeMap(session, request, response);
        MailCallable mailCallable = MailOperationsMap.getMailCallable(command);
        if (mailCallable != null) {
            mailCallable.call();
        } else {
            //UNSUPPORTED MAIL SEND COMMAND
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String command = (String) session.getAttribute(SessionAttribute.COMMAND);
        MailOperationsMap.initMailCallabeMap(session, request, response);
        MailCallable mailCallable = MailOperationsMap.getMailCallable(command);
        if (mailCallable != null) {
            mailCallable.call();
        } else {
            //UNSUPPORTED MAIL SEND COMMAND
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
