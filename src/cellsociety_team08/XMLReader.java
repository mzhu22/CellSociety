package cellsociety_team08;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/***
 * Class used to read XML files and return CA specifications
 * @author Mike Zhu
 *
 */
public class XMLReader {
	
	private int rows;
	private int columns;
	
	public XMLReader(){
		
	}
	
	public void read(File XMLFile){
		
		try{
			
		
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
		Document initDoc = dBuilder.parse(XMLFile);
		
		initDoc.getDocumentElement().normalize();
		System.out.println("Root element :" + initDoc.getDocumentElement().getNodeName());
		
		NodeList nList = initDoc.getElementsByTagName("grid");
		
		System.out.println("-----------------------");
		
		for(int i=0; i<nList.getLength(); i++){
			Node nNode = nList.item(i);
			Element elem = (Element) nNode;
			
			rows = Integer.parseInt(elem.getAttribute("rows"));
			columns = Integer.parseInt(elem.getAttribute("columns"));
			
			System.out.println(rows);
		
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
