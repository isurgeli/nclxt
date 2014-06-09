package nc.itf.lxt.pub.jxabtool;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbTools {
	public static <T> T getObjectFromString(Class<T> type, String xml) throws JAXBException {
		JAXBContext jaxbContext;
		
		jaxbContext = JAXBContext.newInstance(type);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
		StringReader in = new StringReader(xml);
	    @SuppressWarnings("unchecked")
		T obj = (T)jaxbUnmarshaller.unmarshal(in); 
	    
	    return obj;  
	}
	
	public static <T> String getStringFromObject(T object) throws JAXBException {
		JAXBContext jaxbContext;

		jaxbContext = JAXBContext.newInstance(object.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter out = new StringWriter();
		jaxbMarshaller.marshal(object, out);

		return out.toString();
	}
}
