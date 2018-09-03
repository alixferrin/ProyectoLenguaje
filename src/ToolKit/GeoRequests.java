
package ToolKit;

import java.io.IOException;
import java.io.StringReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GeoRequests {
	
	public static Location requestLocation(String address) 
			throws ParserConfigurationException, SAXException, IOException{   
		
		if(address == null)
			return null;
		
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://maps.googleapis.com/maps/api/geocode/xml")
                .queryParam("address", address);
		
		try{
			String response = target.request(MediaType.APPLICATION_XML).get(String.class);
			DocumentBuilderFactory dbf =
            DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(response));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("result");
			if(nodes.getLength()>0){
				Element element = (Element) nodes.item(0);

				NodeList geometry = element.getElementsByTagName("geometry");
				Element element1 = (Element) geometry.item(0);

				NodeList locationx = element1.getElementsByTagName("location");
				Element element2 = (Element) locationx.item(0);
				//iterate location variables (lat, lon)
				NodeList latTag = element2.getElementsByTagName("lat");
				String lat = getCharacterDataFromElement((Element) latTag.item(0));

				NodeList lonTag = element2.getElementsByTagName("lng");
				String lng = getCharacterDataFromElement((Element) lonTag.item(0));
				
				Location location = new Location(Double.parseDouble(lat),
						Double.parseDouble(lng));
				
				return location;
			}
			System.out.println("Error while requesting location.");
			return null;
			
		}catch(Exception e){
			Tools.showAlert("No internet Connection", "Error", null, 0);
		}
		return null;
	}	
	
	private static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
		   CharacterData cd = (CharacterData) child;
		   return cd.getData();
		}
		return "?";
	}
}
