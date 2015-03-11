package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Date;

//Document builder for parsing
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.StringReader;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        HttpSession session = request.getSession(true);

        String item_id = request.getParameter("id");

        if(item_id == null)
        	item_id = "";

        String item_xml = AuctionSearchClient.getXMLDataForItemId(item_id);

        //Document building for parsing purchases
        try{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource input = new InputSource(new StringReader(item_xml));
        Document doc = builder.parse(input);

        String buy_price = "";
        //Get Name && buy price
        String item_name = doc.getElementsByTagName("Name").item(0).getTextContent();
        if (doc.getElementsByTagName("Buy_Price").getLength() != 0) {
            buy_price = doc.getElementsByTagName("Buy_Price").item(0).getTextContent();
        }
        //Replace the newlines and spaces in our item_XML so our jQuery parser will work
        item_xml = item_xml.replace("\n", "").replace("\r", "").replace("\t","").replace("\'","\\\'");
        session.setAttribute(item_id, item_xml); 

        //Setting information for purchases
        session.setAttribute("name",item_name);
        session.setAttribute("buyprice",buy_price);

        //Other information for jquery parsing
        request.setAttribute("item", item_xml);
        request.setAttribute("id", item_id);
        request.getRequestDispatcher("/item.jsp").forward(request, response);
        } catch (Exception e) {
            
        } 

    }
}
