package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 06/08/15.
 */
public class LogOutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.sendRedirect(Pages.INDEX_HTML);
    }
}
