package us.deloitteinnovation.aca.util;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.deloitteinnovation.aca.security.UserSession;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yaojia on 11/10/2016.
 */
public class LoggingUtil {
    private static final Logger CW_LOGGER = LoggerFactory.getLogger("cwlogger");
    private static final String DATETIME_FORMAT = "MM/dd/YYYY HH:mm:ss.SSS";
    private static final String LOG_FORMAT =
            "%s - Request Start Time: %s, Request End Time: %s, " +
            "Duration: %d, IP Address: %s, State: %s, Session Id: %s, User Id: %s, Source Unique Id: %s";

    public static final void logRequest(String title,
                                          DateTime requestStart,
                                          HttpServletRequest request,
                                          UserSession userSession,
                                          String sourceUniqueId) {
        DateTime now = new DateTime();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if(ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        String log = String.format(LOG_FORMAT,
                title,
                now.toString(DATETIME_FORMAT),
                requestStart.toString(DATETIME_FORMAT),
                Math.abs(now.getMillis() - requestStart.getMillis()),
                ipAddress,
                userSession.getState(),
                request.getSession().getId(),
                userSession.getEmail(),
                sourceUniqueId);
        CW_LOGGER.info(log);
    }
}
