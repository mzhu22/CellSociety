package cellsociety_team08;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/***
 * Class used to read XML files and return CA specifications
 * @author Mike Zhu
 *
 */
public class XMLReader {
		
	public XMLReader(){
		
	}
	
	public CASettings read(File XMLFile){
		try
		{
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
			Document initDoc = dBuilder.parse(XMLFile);
			
			initDoc.getDocumentElement().normalize();
			
			String description = initDoc.getDocumentElement().getAttribute("type");
			
			NodeList nList = initDoc.getElementsByTagName("grid");
						
			Element elem = (Element) nList.item(0);
			
			int rows = Integer.parseInt(elem.getAttribute("rows"));
			int columns = Integer.parseInt(elem.getAttribute("columns"));
			String gridString = elem.getFirstChild().getNodeValue();
			
			String[] tempArray = gridString.split("\n");
			String[][] grid = new String[columns][rows];
			
			int i=-1;
			for(String s: tempArray){
				s = s.trim();
				if(s.length()>0){
					i++;
					String[] row = s.split(" ");
					grid[i] = row;
				}
			}
			
			CASettings settings = new CASettings(description, rows, columns, grid);
			return settings;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
