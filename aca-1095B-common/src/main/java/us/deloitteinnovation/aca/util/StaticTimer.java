package us.deloitteinnovation.aca.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A static helper object that helps timing critical batch steps. <br/>
 * Keep in mind that it is a static class, thus only one timing can undergo at the same time.
 *
 * @author yaojia
 * @since 2.1.0
 */
public class StaticTimer {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticTimer.class);
    private static final float MS_TO_SEC = 1024f;
    private static volatile long t0;
    private static volatile boolean started = false;

    public static synchronized void start() {
        t0 = System.currentTimeMillis();
        started = true;
    }

    public static synchronized void stop(Class classObj) {
        if (started) {
            LOGGER.info(String.format("%s finished in %.2f seconds.", classObj.getSimpleName(), (System.currentTimeMillis() - t0)/MS_TO_SEC));
        }
    }
}
