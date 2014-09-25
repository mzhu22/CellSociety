package frontEnd;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/***
 * Class used to read XML files and return CA specifications
 * @author Mike Zhu
 */
public class XMLHandler {
		
	public XMLHandler(){
		
	}
	
	/**
	 * Reads XML file and returns a CASettings object containing stored info
	 * @param XMLFile 
	 * @return a CASettings object 
	 */
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

	/**
	 * Writes current simulation setup to an XML file.
	 * @param outputSettings = CASettings file representing current type, params, grid from simulation
	 */
	public void write(CASettings outputSettings){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Simulation type
			Document doc = docBuilder.newDocument();
			Element CARoot = doc.createElement("cellularautomata");
			
			CARoot.setAttribute("type", outputSettings.getType());
			doc.appendChild(CARoot);

			// List of all parameters
			Element paramsList = doc.createElement("parameters");
			CARoot.appendChild(paramsList);

			// The parameters themselves 
			for(String s: outputSettings.getParameters().keySet()){
				Element param = doc.createElement("parameter");
				param.setAttribute(s, (String) outputSettings.getParameters().get(s));
				paramsList.appendChild(param);
			}
			
			Element grid = doc.createElement("grid");
			grid.setAttribute("rows", outputSettings.getRows().toString());
			grid.setAttribute("columns", outputSettings.getColumns().toString());
			
			String[] formattedGrid = writeGrid(outputSettings.getGrid());
			
			//Create the grid according to the XML format
			String gridString = "";
			for(String row : formattedGrid){
				gridString = gridString + row + "\n";
			}
			grid.appendChild(doc.createTextNode(gridString));
			
			CARoot.appendChild(grid);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			//Creates standard xml file format - newline + indent for child elements of a parent node
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\Users\\Mike\\Documents\\file.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} 
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} 
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
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
	
	private String[] writeGrid(String[][] grid){
		String[] formattedGrid = new String[grid.length];
		for(int i=0; i<grid.length; i++){
			formattedGrid[i] = "";
			for(int j=0; j<grid[0].length; j++){
				formattedGrid[i] = formattedGrid[i] + " " + grid[i][j];
			}
		}
		return formattedGrid;
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
