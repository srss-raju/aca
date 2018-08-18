package us.deloitteinnovation.aca.jaxb;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by ergreen on 11/8/2015.
 */
public final class JaxbUtils {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(JaxbUtils.class);

    /**
     * Suppress instantiation as this class consists
     * solely of static utility attributes and methods.
     */
    private JaxbUtils() {
    }

    /**
     * DatatypeFactory is somewhat expensive to create,
     * so create and use only one for each thread.
     */
    private static final ThreadLocal<DatatypeFactory> threadDatatypeFactory =
            new ThreadLocal<DatatypeFactory>() {
                @Override
                protected DatatypeFactory initialValue() {
                    try {
                        return DatatypeFactory.newInstance();
                    } catch (DatatypeConfigurationException ex) {
                        LOGGER.error("Attempting to create DataTypeFactory", ex);
                        throw new ExceptionInInitializerError(ex);
                    }
                }
            };

    /**
     * @param date
     * @return Date converted to an XMLGregorianCalendar with the Timezone removed.
     */
    public static XMLGregorianCalendar createXmlGregorianCalendarNoTimezone(final Date date) {
        XMLGregorianCalendar calendar;
        try {
            calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Attempting to create XmlGregorianCalendar", e);
        }
        return calendar;
    }

    public static XMLGregorianCalendar createXmlGregorianCalendarNoTimezone(int taxYr) {
        XMLGregorianCalendar calendar;
        try {
            GregorianCalendar date = new GregorianCalendar(taxYr, 0, 1);
            calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy").format(date.getTime()));
            calendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Attempting to create XmlGregorianCalendar", e);
        }
        return calendar;
    }

    public static XMLGregorianCalendar createXmlGregorianCalendarDate(final Date date) {
        XMLGregorianCalendar calendar;
        try {
            calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd").format(date));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Attempting to create XmlGregorianCalendar", e);
        }
        return calendar;
    }

    /**
     * @return XMLGregorianCalendar with the Timezone removed, set to the current time UTC.
     */
    public static XMLGregorianCalendar createXmlGregorianCalendarUtcNow() {
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        XMLGregorianCalendar xmlGregorianCalendar = threadDatatypeFactory.get().newXMLGregorianCalendar(calendar);
        xmlGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        return xmlGregorianCalendar;
    }

    /**
     * @param jaxb2Marshaller
     * @param object
     * @return Renders the given object as XML.
     */
    public static String renderToString(Jaxb2Marshaller jaxb2Marshaller, Object object) {
        StringWriter out = new StringWriter();
        jaxb2Marshaller.marshal(object, new StreamResult(out));
        return out.toString();
    }

    /**
     * @param jaxb2Marshaller
     * @param object
     * @return Renders the given object as XML.
     */
    public static String renderToStringNoNamespace(Jaxb2Marshaller jaxb2Marshaller, Object object) throws Exception {
        JAXBContext context = JAXBContext.newInstance(object.getClass(), Form1095BUpstreamDetailType.class) ;
        Marshaller m = context.createMarshaller() ;
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);

        StringWriter out = new StringWriter();
        XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(out);
        xmlStreamWriter.setNamespaceContext(new EmptyNamespaceContext());
        m.marshal(object, xmlStreamWriter);
        return out.toString();
    }

}
