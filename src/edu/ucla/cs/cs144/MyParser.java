/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;
import java.lang.*;


class MyParser {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }

    /**************************************************************/
    /**************************************************************/
    /**************************************************************/

    //Helper classes for organizing the database tables

    public static class Users { 
        String user_name;
        String user_location;
        String user_rating;
        String user_country;

        Users(String name, String rating, String location, String country) { 
            user_name = name; 
            user_rating = rating;
            user_location = location;
            user_country = country;
        } 

        //Override equals function so our set can check equality
        @Override
        public boolean equals(Object obj){
            return (((Users)obj).user_name).equals(this.user_name);
        }

        //Overrides the hashcode function
        @Override 
        public int hashCode() { 
            return user_name.hashCode();
        }
    }


    //Set for the category, using a set to get rid of duplicates
    static Set<String> set_category = new HashSet<String>();
    //static Map<String,Integer> map_category = new HashMap<String,Integer>();

    //Set for Users
    static Set<Users> set_users = new HashSet<Users>();

    //Hashmap for file-category
    static Map<Integer,Integer> map_filecategory = new HashMap<Integer,Integer>(); 


    /**************************************************************/
    /**************************************************************/
    /**************************************************************/

    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);


        /**************************************************************/
        /**************************************************************/
        /**************************************************************/
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
        
        
        //**************************************************************/
        //********** ITERATING THROUGH XML DOCUMENTS
        //**************************************************************/

        try { //Try-catch for FileWriter

        Element[] current = getElementsByTagNameNR(doc.getDocumentElement(), "Item");

        // Looping through all Items in XML file
        for(int i = 0; i < current.length ; i++){

            //Get item id
            int item_id = Integer.parseInt(current[i].getAttribute("ItemID"));

            //Get buyer information and add to Users.dat
            Element bids = getElementByTagNameNR(current[i], "Bids");
            Element[] bid = getElementsByTagNameNR(bids, "Bid");

            // Iterate through the bids and find the buyers
            for (int j=0; j<bid.length; j++) {
                // Get buyer information
                Element buyer = getElementByTagNameNR(bid[j], "Bidder");

                Users b = new Users(
                        buyer.getAttribute("UserID"), 
                        buyer.getAttribute("Rating"), 
                        getElementTextByTagNameNR(buyer, "Location"), 
                        getElementTextByTagNameNR(buyer, "Country"));
                set_users.add(b);

            }

            //Get seller information and add to Users.dat
            Element seller = getElementByTagNameNR(current[i], "Seller");

            Users s = new Users(
                    seller.getAttribute("UserID"),
                    seller.getAttribute("Rating"),
                    getElementTextByTagNameNR(current[i], "Location"),
                    getElementTextByTagNameNR(current[i], "Country"));
            set_users.add(s);

            //Get Category information, loop through and set Category.dat file
            Element[] categoryXML = getElementsByTagNameNR(current[i], "Category");

            for(int cat = 0; cat < categoryXML.length ; cat++){

                String category_name = getElementText(categoryXML[cat]); 

                //Add category name to our Category set
                set_category.add(category_name);

                //Adding category name's hash value with item_id
                map_filecategory.put(Math.abs(category_name.hashCode()), item_id);
                //file_category_buffer.append(item_id + " " + columnSeparator + " " + Math.abs(category_name.hashCode()) + "\n");

            }

        }

        //**************************************************************/
        //********** WRITING TO ITEM-CATEGORY.DAT -- NOT WORKING
        //**************************************************************/

        FileWriter file_category = new FileWriter("file-category.dat");
        BufferedWriter file_category_buffer = new BufferedWriter(file_category);

        for (Map.Entry<Integer, Integer> entry: map_filecategory.entrySet()) {
            file_category_buffer.append(entry.getValue() + " " + columnSeparator + " " + entry.getValue() + "\n");
        }

        //Close file/buffer writer
        file_category_buffer.close();
        file_category.close();

        } catch (IOException e) {
            System.out.println("ERROR: FileWriter error");
        }
        
        
        /**************************************************************/
        /**************************************************************/
        /**************************************************************/
        
    }

        /* Method to convert the EBay date's to MySQL format of yyyy-MM-dd HH:mm:ss */
    public static String convertDateTime(String dateString) {
        //Using java.text.SimpleDateFormat to convert date with http://www.cs.ucla.edu/classes/winter15/cs144/projects/java/simpledateformat.html

        //Defining format of the ebay string and the new converted string
        SimpleDateFormat ebay_string = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat converted_string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String output = "";

        try {
            Date parsed = ebay_string.parse(dateString);
            output = converted_string.format(parsed);
            
            }
            catch(ParseException pe) {
                System.out.println("ERROR: Cannot parse \"" + dateString + "\"");
            }

        return output;
    }

    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }

        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }

        //**************************************************************/
        //********** WRITING TO CATEGORY.DAT 
        //**************************************************************/

        try{
            FileWriter category = new FileWriter("Category.dat");
            BufferedWriter category_buffer = new BufferedWriter(category);

            //category_id is a value we generate on our own
            //int category_id = 1;

            for (String curr : set_category) {

                //Assign categiry ID with positive hash value
                String entry = String.format("%d " + columnSeparator + " %s\n", 
                            Math.abs(curr.hashCode()),
                            curr);
                category_buffer.append(entry); 

                //map_category.put(curr,category_id);

                //Increase our category ID
                //category_id++;
            }

        //Close file/buffer writer
        category_buffer.close();
        category.close();

        } catch (IOException e) {
            System.out.println("ERROR: FileWriter error");
        }

        //**************************************************************/
        //********** WRITING TO USERS.DAT 
        //**************************************************************/

        try{
            FileWriter users = new FileWriter("users.dat");
            BufferedWriter users_buffer = new BufferedWriter(users);
            
            for (Users u : set_users) {
                users_buffer.append(String.format("%s " + columnSeparator + " %s " + columnSeparator + " %s " + columnSeparator + " %s\n", 
                    u.user_name,
                    u.user_rating,
                    u.user_location,
                    u.user_country));
        }
        //Close file/buffer writer
        users_buffer.close();
        users.close();


        } catch (IOException e) {
            System.out.println("ERROR: FileWriter error");
        }
    }
}
