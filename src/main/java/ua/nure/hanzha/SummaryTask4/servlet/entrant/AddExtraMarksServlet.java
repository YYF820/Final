package ua.nure.hanzha.SummaryTask4.servlet.entrant;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.ExtraMark;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.extraMark.ExtraMarkService;
import ua.nure.hanzha.SummaryTask4.util.ValidationUtilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 11/08/15.
 */
public class AddExtraMarksServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String PARAM_CERTIFICATE_POINTS = "certificatePoints";
    private static final String PARAM_EXTRA_POINTS = "extraPoints";

    private ExtraMarkService extraMarkService;

    @Override
    public void init() throws ServletException {
        extraMarkService = (ExtraMarkService) getServletContext().getAttribute(AppAttribute.EXTRA_MARK_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String certificatePoints = request.getParameter(PARAM_CERTIFICATE_POINTS);
        String extraPoints = request.getParameter(PARAM_EXTRA_POINTS);
        boolean isEmptyFields = checkIsEmptyFields(session, certificatePoints, extraPoints);
        boolean isValidFields = checkIsValidFields(session, certificatePoints, extraPoints);
        if (isEmptyFields || !isValidFields) {
            setUpFields(session, certificatePoints, extraPoints);
            response.sendRedirect(Pages.ENTRANT_ACCOUNT_SETTINGS_ADD_EXTRA_MARKS_HTML);
        } else {
            int entrantId = (int) session.getAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_ENTRANT_ID);
            ExtraMark extraMark = new ExtraMark();
            extraMark.setEntrantId(entrantId);
            extraMark.setCertificatePoints(Double.parseDouble(certificatePoints));
            extraMark.setExtraPoints(Double.parseDouble(extraPoints));
            try {
                extraMarkService.addExtraMark(extraMark);
                response.sendRedirect(Pages.ACCOUNT_SETTINGS_HTML);
            } catch (DaoSystemException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    //check if any field is empty and set up session attribute for PRG
    private boolean checkIsEmptyFields(HttpSession session, String certificatePoints, String extraPoints) {
        boolean isEmptyFields = false;
        if (certificatePoints.equals(EMPTY_PARAM)) {
            isEmptyFields = true;
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_EMPTY_FIELDS, true);
        } else if (extraPoints.equals(EMPTY_PARAM)) {
            isEmptyFields = true;
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_EMPTY_FIELDS, true);
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_EMPTY_FIELDS, false);
        }
        return isEmptyFields;
    }

    //check if fields are valid and set up session attribute for PRG
    private boolean checkIsValidFields(HttpSession session, String certificatePoints, String extraPoints) {
        boolean isValidFields = true;
        if (!ValidationUtilities.validateCertificatePoints(certificatePoints)) {
            isValidFields = false;
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_VALID_CERTIFICATE_POINTS, false);
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_VALID_CERTIFICATE_POINTS, true);
        }
        if (!ValidationUtilities.validateExtraPoints(extraPoints)) {
            isValidFields = false;
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_VALID_EXTRA_POINTS, false);
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_VALID_EXTRA_POINTS, true);
        }
        return isValidFields;
    }

    private void setUpFields(HttpSession session, String certificatePoints, String extraPoints) {
        session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_CERTIFICATE_POINTS, certificatePoints);
        session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_EXTRA_POINTS, extraPoints);
    }

}
