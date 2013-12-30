package readXML;


	import java.io.File;
	import javax.xml.parsers.DocumentBuilder;
	import javax.xml.parsers.DocumentBuilderFactory;
	import org.w3c.dom.Document;
	import org.w3c.dom.Element;
	import org.w3c.dom.NodeList;
	/**
	 * Use DOM Parser to display all books: isbn, title and authors.
	 */
	public class DOMParser {
		public static void go() throws Exception {
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder docBuilder = factory.newDocumentBuilder();
	      File file = new File("src/readXML/bookStore.xml");
	      Document doc = docBuilder.parse(file);
	   
	      // Get a list of all elements in the document
	      // The wild card * matches all tags
	      NodeList list = doc.getElementsByTagName("*");
	      int bookCount = 0;
	      for (int i = 0; i < list.getLength(); i++) {
	         // Get the elements book (attribute isbn), title, author
	         Element element = (Element)list.item(i);
	         String nodeName = element.getNodeName();
	         if (nodeName.equals("book")) {
	            bookCount++;
	            System.out.println("BOOK " + bookCount);
	            String isbn = element.getAttribute("ISBN");
	            System.out.println("\tISBN:\t" + isbn);
	         } else if (nodeName.equals("title")) {
	            System.out.println("\tTitle:\t"
	                  + element.getChildNodes().item(0).getNodeValue());
	         } else if (nodeName.equals("author")) {
	            System.out.println("\tAuthor:\t" 
	                  + element.getChildNodes().item(0).getNodeValue());
	         }
	      }
	   }
	
}
