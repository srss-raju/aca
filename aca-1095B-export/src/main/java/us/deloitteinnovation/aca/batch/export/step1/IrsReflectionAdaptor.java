package us.deloitteinnovation.aca.batch.export.step1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yaojia on 1/18/2017.
 */
public class IrsReflectionAdaptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(IrsReflectionAdaptor.class);

    /* Generic method. Use with caution */
    public static void genericSetDate(Object target, String methodName, Date date) {
        try {
            // See if the target method is declared for String
            // IRS DateType accepts yyyy-MM-dd
            // per regex [1-9][0-9]{3}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])
            try {
                Method methodWithStringParam = target.getClass().getMethod(methodName, String.class);
                methodWithStringParam.invoke(target, new SimpleDateFormat("yyyy-MM-dd").format(date));
            } catch (NoSuchMethodException nsme) {

                // If not for String, try createXmlGregorianCalendarDate
                try {
                    Method methodWithCalendarParam = target.getClass().getMethod(methodName, XMLGregorianCalendar.class);
                    methodWithCalendarParam.invoke(target, JaxbUtils.createXmlGregorianCalendarDate(date));
                } catch (NoSuchMethodException nsme2) {

                    // Target method not found
                    LOGGER.warn(String.format("Method %s.%s(String or XMLGregorianCalendar) does not exist. Not invoking.",
                            target.getClass().getSimpleName(), methodName));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException ite) {
            LOGGER.error("Critical reflection error", ite);
            throw new RuntimeException(ite);
        }
    }

    public static void genericSetYear(Object target, String methodName, int year) {
        try {
            // See if the target method is declared for String
            try {
                // IRS YearType accepts 4-digit year
                // per regex [1-9][0-9]{3}
                Method methodWithStringParam = target.getClass().getMethod(methodName, String.class);
                methodWithStringParam.invoke(target, Integer.toString(year));
            } catch (NoSuchMethodException nsme) {

                // If not for String, try createXmlGregorianCalendarDate
                try {
                    Method methodWithCalendarParam = target.getClass().getMethod(methodName, XMLGregorianCalendar.class);
                    methodWithCalendarParam.invoke(target, JaxbUtils.createXmlGregorianCalendarNoTimezone(year));
                } catch (NoSuchMethodException nsme2) {

                    // Target method not found
                    LOGGER.warn(String.format("Method %s.%s(String or XMLGregorianCalendar) does not exist. Not invoking.",
                            target.getClass().getSimpleName(), methodName));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException ite) {
            LOGGER.error("Critical reflection error", ite);
            throw new RuntimeException(ite);
        }
    }

    public static void genericSetLong(Object target, String methodName, long value) {
        try {
            // See if the target method is declared for String
            try {
                Method methodWithStringParam = target.getClass().getMethod(methodName, String.class);
                methodWithStringParam.invoke(target, Long.toString(value));
            } catch (NoSuchMethodException nsme) {

                // If not for String, try BigInteger
                try {
                    Method methodWithCalendarParam = target.getClass().getMethod(methodName, BigInteger.class);
                    methodWithCalendarParam.invoke(target, BigInteger.valueOf(value));
                } catch (NoSuchMethodException nsme2) {

                    // Target method not found
                    LOGGER.warn(String.format("Method %s.%s(String or BigInteger) does not exist. Not invoking.",
                            target.getClass().getSimpleName(), methodName));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException ite) {
            LOGGER.error("Critical reflection error", ite);
            throw new RuntimeException(ite);
        }
    }

    public static <T> void setAttributeIfExist(Object target, String methodName, T value, Class<T> paramClass) {
        try {
            Method method = target.getClass().getMethod(methodName, paramClass);
            method.invoke(target, value);
        } catch (NoSuchMethodException nsme) {
            String paramType = null == value ? "?" : value.getClass().getSimpleName();
            LOGGER.debug(String.format("Method %s.%s(%s) does not exist. Not invoking.",
                    target.getClass().getSimpleName(), methodName, paramType));
        } catch (IllegalAccessException | InvocationTargetException ite) {
            LOGGER.error("Critical reflection error", ite);
            throw new RuntimeException(ite);
        }
    }

    public static int genericReturnBigIntOrIntOrString(Object returned) {
        if (BigInteger.class == returned.getClass()) {
            return ((BigInteger) returned).intValueExact();
        } else if (Integer.class == returned.getClass()) {
            return ((Integer) returned);
        } else if (String.class == returned.getClass()) {
            return Integer.parseInt((String) returned);
        } else {
            String message = String.format("Unexpected return type: %s", returned.getClass().getSimpleName());
            LOGGER.error(String.format("Unexpected return type: %s", returned.getClass().getSimpleName()));
            throw new RuntimeException(message);
        }
    }
}
