package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.mail.MailManager;
import ua.nure.hanzha.SummaryTask4.mail.MailOperationsMap;

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


    private MailManager mailManager;

    @Override
    public void init() throws ServletException {
        mailManager = (MailManager) getServletContext().getAttribute(AppAttribute.MAIL_MANAGER);
        MailOperationsMap.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String command = (String) session.getAttribute(SessionAttribute.COMMAND);
        MailOperationsMap.initMailOperationsMap(session, request, response);
        mailManager.sendMail(MailOperationsMap.getMailCallable(command));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String command = (String) session.getAttribute(SessionAttribute.COMMAND);
        MailOperationsMap.initMailOperationsMap(session, request, response);
        mailManager.sendMail(MailOperationsMap.getMailCallable(command));
    }
}
