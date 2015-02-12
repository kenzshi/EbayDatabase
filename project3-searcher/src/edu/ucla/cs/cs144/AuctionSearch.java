package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

//Custom import to use the Java XML library
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult;
import java.text.SimpleDateFormat;
import java.io.StringWriter;

public class AuctionSearch implements IAuctionSearch {

	/* 
         * You will probably have to use JDBC to access MySQL data
         * Lucene IndexSearcher class to lookup Lucene index.
         * Read the corresponding tutorial to learn about how to use these.
         *
	 * You may create helper functions or classes to simplify writing these
	 * methods. Make sure that your helper functions are not public,
         * so that they are not exposed to outside of this class.
         *
         * Any new classes that you create should be part of
         * edu.ucla.cs.cs144 package and their source files should be
         * placed at src/edu/ucla/cs/cs144.
         *
         */
	
	
	private IndexSearcher searcher = null;
    private QueryParser parser = null;

	public SearchResult[] basicSearch(String query, int numResultsToSkip, 
			int numResultsToReturn) {
		// TODO: Your code here!
		// Code for Part A below

		/****************************************************
     		Following tutorial code provided by 
     		http://www.cs.ucla.edu/classes/winter15/cs144/projects/lucene/index.html
		****************************************************/

		SearchResult[] results = null;
		try{ //Set up trycatch for IOException and ParseException
			//Set up our Lucene index
			searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File("/var/lib/lucene/index1/"))));
	        parser = new QueryParser("union", new StandardAnalyzer());

	        Query queryString = parser.parse(query);

	        //Now search through our index
	        int totalResults = numResultsToSkip + numResultsToReturn;
			TopDocs topDocs = searcher.search(queryString, totalResults); 

			ScoreDoc[] hits = topDocs.scoreDocs;
			//Create a SearchResult array, initialize size to the number of hits minus the results we want to skip
			results = new SearchResult[hits.length - numResultsToSkip];

			// loop through the results starting at the results we want to skip
			for (int i = numResultsToSkip; i < hits.length; i++) {
			    Document doc = searcher.doc(hits[i].doc);
			    results[i - numResultsToSkip] = new SearchResult(doc.get("id"),doc.get("name"));;
			}
		} catch (IOException e) { 
            	System.out.println(e);
        	} catch (ParseException pe){
        		System.out.println(pe);
        	}

        return results;
	}

	public SearchResult[] spatialSearch(String query, SearchRegion region,
			int numResultsToSkip, int numResultsToReturn) {
		// TODO: Your code here!
		return new SearchResult[0];
	}

	public String getXMLDataForItemId(String itemId) {
		// TODO: Your code here!
		String xml_result = "";

		//Open up DB connection
		Connection conn = null;
		try{
			conn = DbManager.getConnection(true);


			// Query to get the Item with the given itemID
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM item WHERE id = " + itemId);

			// Query to get all categories
            Statement category_stmt = conn.createStatement();
            ResultSet category_rs = category_stmt.executeQuery("SELECT name FROM category,item_category WHERE item_category.item_id = " + itemId +  " AND item_category.category_id = category.id");

			//Grab first entry and make sure it's not empty
			result.first();
			if (result.getRow() != 0){

				//Referencing http://docs.oracle.com/javase/6/docs/api/javax/xml/parsers/DocumentBuilderFactory.html
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                org.w3c.dom.Document doc = builder.newDocument();

                // <Item> set up as root
                Element root = doc.createElement("Item");
                root.setAttribute("ItemID", itemId);
                doc.appendChild(root);

                // <Name> Add the name
                Element element_name = doc.createElement("Name");
                element_name.appendChild(doc.createTextNode(escapeChars(result.getString("name"))));
                root.appendChild(element_name);


                // <Category> Loop through categories
                Element category_enum;
                while(category_rs.next()){

                category_enum = doc.createElement("Category");
                category_enum.appendChild(doc.createTextNode(escapeChars(category_rs.getString("name"))));
                root.appendChild(category_enum);
                }

                // <Currently> Adding the current price
                Element currently = doc.createElement("Currently");
                currently.appendChild(doc.createTextNode("$" + result.getString("currently")));
                root.appendChild(currently);

                // <Buy_Price> If availible
                if (result.getString("Buy_Price") != null) {
                    Element buy_price = doc.createElement("Buy_Price");
                    buy_price.appendChild(doc.createTextNode("$" + result.getString("buy_price")));
                    root.appendChild(buy_price);
                }

                // <First_Bid> Get the first bid
                Element first_bid = doc.createElement("First_Bid");
                first_bid.appendChild(doc.createTextNode("$" + result.getString("first_bid")));
                root.appendChild(first_bid);

                //Transforming document into XML http://docs.oracle.com/javase/tutorial/jaxp/xslt/writingDom.html
                TransformerFactory tFactory = TransformerFactory.newInstance();
    			Transformer transformer = tFactory.newTransformer();

    			DOMSource source = new DOMSource(doc);
    			StringWriter str_writer = new StringWriter();
    			StreamResult stm_result = new StreamResult(str_writer);
    			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); //Get rid of the XML declaration in the beginning
    			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //we want indents
    			transformer.transform(source, stm_result);
    			xml_result = str_writer.toString(); //Transform to our XML string

			} else { //Else if unable to find the item ID, return empty string
				xml_result = "";
			}

		//Close up files
			category_stmt.close();
            category_rs.close();
            stmt.close();
			result.close();
            conn.close();

		} catch (SQLException ex) {
		    System.out.println(ex);
		} catch (TransformerConfigurationException tce){
			System.out.println(tce);
		} catch (ParserConfigurationException pce){
			System.out.println(pce);
		} catch (TransformerException te){
			System.out.println(te);
		}

		return xml_result;
	}

    /* Method converts from MySQL time to XML time format */
    public static String convertDateTime(String dateString){
        //Using java.text.SimpleDateFormat to convert date with http://www.cs.ucla.edu/classes/winter15/cs144/projects/java/simpledateformat.html

        String output = "";

    	try {
        //Defining format of the ebay string and the new converted string
        SimpleDateFormat ebay_string = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat converted_string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
        Date parsed = converted_string.parse(dateString);
        output = ebay_string.format(parsed);
        
        }catch (Exception pe){
        		System.out.println(pe);
        	}

        return output;
    }

    public static String escapeChars(String s) {
      	// Escapes all the characters 
      	// Based off of http://www.hdfgroup.org/HDF5/XML/xml_escape_chars.htm
        s.replaceAll("&", "&amp;");
        s.replaceAll("<", "&lt;");
        s.replaceAll(">", "&gt;");
        s.replaceAll("\"", "quot;"); 
        s.replaceAll("\'", "&apos;");
        return s;
    }
	
	public String echo(String message) {
		return message;
	}

}
