package readXML;

/*
 * ParseXMLFile.java
 *
 * Created on 4. listopad 2003, 10:14
 */

import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/** This class represents short example how to parse XML file,
 * get XML nodes values and its values.<br><br>
 * It implements method to save XML document to XML file too
 * @author Martin Glogar
 *   
 * Edited by Torben Brams, December 2013
 */
public class ParseXMLFile {
    
    private final static String targetFileName = "src/readXML/SampleData2.xml";
    
    /** Creates a new instance of ParseXMLFile */
    public ParseXMLFile(String xmlFileName) {
        
    	// parse XML file -> XML document will be build
        Document doc = parseFile(xmlFileName);
        Node root = doc.getDocumentElement();
        System.out.println("XML document");
        writeDocumentToOutput(root,0);
        System.out.println("End of XML Document");

        // write Document into XML file
        saveXMLDocument(targetFileName, doc);
    }
    
    /** Returns element value
     * @param elem element (it is XML tag)
     * @return Element value otherwise empty String
     */
    public final static String getElementValue( Node elem ) {
        if( elem != null){
            if (elem.hasChildNodes()){
                for( Node child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    
    private String getIndentSpaces(int indent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }
    
    /** Writes node and all child nodes into System.out
     * @param node XML node from from XML tree wrom which will output statement start
     * @param indent number of spaces used to indent output
     */
    public void writeDocumentToOutput(Node node,int indent) {
        String nodeName = node.getNodeName();
        String nodeValue = getElementValue(node);
        // get attributes of element
        NamedNodeMap attributes = node.getAttributes();
        System.out.println(getIndentSpaces(indent) + "NodeName: " + nodeName + ", NodeValue: " + nodeValue);
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            System.out.println(getIndentSpaces(indent + 2) + "AttributeName: " + attribute.getNodeName() + ", attributeValue: " + attribute.getNodeValue());
        }
        // write all child nodes recursively
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                writeDocumentToOutput(child,indent + 2);
            }
        }
    }
    
    /** Saves XML Document into XML file.
     * @param fileName XML file name
     * @param doc XML document to save
     * @return <B>true</B> if method success <B>false</B> otherwise
     */    
    @SuppressWarnings("resource")
	public boolean saveXMLDocument(String fileName, Document doc) {
        System.out.println("Saving XML file... " + fileName);
        // open output stream where XML Document will be saved
        File xmlOutputFile = new File(fileName);
        FileOutputStream fos;
        Transformer transformer;
        try {
            fos = new FileOutputStream(xmlOutputFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error occured: " + e.getMessage());
            return false;
        }
        // Use a Transformer for output
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        }
        catch (TransformerConfigurationException e) {
            System.out.println("Transformer configuration error: " + e.getMessage());
            return false;
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(fos);
        // transform source into result will do save
        try {
            transformer.transform(source, result);
        }
        catch (TransformerException e) {
            System.out.println("Error transform: " + e.getMessage());
        }
        System.out.println("XML file saved.");
        return true;
    }
    
    /** Parses XML file and returns XML document.
     * @param fileName XML file to parse
     * @return XML document or <B>null</B> if error occured
     */
    public Document parseFile(String fileName) {
        System.out.println("Parsing XML file... " + fileName);
        DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            System.out.println("Wrong parser configuration: " + e.getMessage());
            return null;
        }
        File sourceFile = new File(fileName);
        try {
            doc = docBuilder.parse(sourceFile);
        }
        catch (SAXException e) {
            System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
        }
        catch (IOException e) {
            System.out.println("Could not read source file: " + e.getMessage());
        }
        System.out.println("XML file parsed");
        return doc;
    }
    

    
}
