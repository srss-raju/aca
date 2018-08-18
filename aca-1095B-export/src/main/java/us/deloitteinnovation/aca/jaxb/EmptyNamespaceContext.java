package us.deloitteinnovation.aca.jaxb;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

/**
 * Created by pebradley on 4/6/16.
 */
public class EmptyNamespaceContext implements NamespaceContext {

    @Override
    public String getNamespaceURI(String prefix) {
        return "";
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return "";
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        return null ;
    }
}
