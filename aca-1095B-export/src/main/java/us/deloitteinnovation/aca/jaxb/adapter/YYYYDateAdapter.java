package us.deloitteinnovation.aca.jaxb.adapter;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YYYYDateAdapter extends XmlAdapter<String, Date> {

    private static final DateFormat YYYY_FMT = new SimpleDateFormat("yyyy");

    @Override
    public String marshal(Date date) throws Exception {
        return YYYY_FMT.format(date);
    }

    @Override
    public Date unmarshal(String dateStr) throws Exception {
        return DatatypeConverter.parseDate(dateStr).getTime();
    }

    public static Date parseDate(String dateStr) {
        return DatatypeConverter.parseDate(dateStr).getTime();
    }

    public static String printDate(Date date) {
        return YYYY_FMT.format(date);
    }
}