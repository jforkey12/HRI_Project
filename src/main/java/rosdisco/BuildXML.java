package rosdisco;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class BuildXML {
 
	public static void main(String argv[]) {
 
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("TaskModel");
		doc.appendChild(rootElement);
 
//		rootElement.setAttribute("about", "urn:disco.wpi.edu:examples:" + rootElement.getNodeName());
//		rootElement.setAttribute("xmlns", "http://ce.org/cea-2018");
		
		// sub elements
		Element subElement = doc.createElement("task");
		rootElement.appendChild(subElement);
 
		// set attribute to sub element
		Attr attr = doc.createAttribute("id");
		attr.setValue("SetCloth");
		subElement.setAttributeNode(attr);
 
		// short way
		// subElement.setAttribute("attributeName", "attributeValue");
		
		
		Element postCond = doc.createElement("postcondition");
		Attr postc = doc.createAttribute("sufficient");
		postc.setValue("true");
		postCond.setAttributeNode(postc);
		subElement.appendChild(postCond);
		postCond.appendChild(doc.createTextNode("$this.state != undefined"));
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		String home = System.getProperty("user.home");
		StreamResult result = new StreamResult(new File(home + "/file.xml"));
 
		// Output to console for quick debugging
		result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}