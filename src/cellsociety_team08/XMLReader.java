package cellsociety_team08;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
			
			NodeList paramNodes = initDoc.getElementsByTagName("parameters");
			Element paramNode = (Element) paramNodes.item(0);
			NodeList params = paramNode.getElementsByTagName("parameter");
			Map<String, Object> parametersMap = readParameters(params);
			
			NodeList gridNodes = initDoc.getElementsByTagName("grid");
			Element gridElem = (Element) gridNodes.item(0);
			int rows = Integer.parseInt(gridElem.getAttribute("rows"));
			int columns = Integer.parseInt(gridElem.getAttribute("columns"));
			String[][] grid = readGrid(gridElem, rows, columns);
			
			CASettings settings = new CASettings(description, parametersMap, rows, columns, grid);
			return settings;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private String[][] readGrid(Element gridElem, int rows, int columns) {
		String gridString = gridElem.getFirstChild().getNodeValue();
		
		/*
		 * Code below handles reading of the XML grid layout. This is liable to change b/c the grid format 
		 * is not set in stone
		 */
		
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
		return grid;
	}

	private Map<String, Object> readParameters(NodeList params) {
		Map<String, Object> parametersMap = new HashMap<>();
		for(int i=0; i<params.getLength(); i++){
			Element param = (Element) params.item(i);
			String key = param.getAttribute("key");
			Object value = param.getAttribute("value");
			parametersMap.put(key, value);
		}
		return parametersMap;
	}
}
