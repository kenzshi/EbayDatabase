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

public class PurchaseServlet extends HttpServlet implements Servlet {
       
    public PurchaseServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        if(request.isSecure()){ //If the session is secure 
          HttpSession session = request.getSession(true);

          String item_id = (String) request.getParameter("id");
          String item_name = (String) session.getAttribute("name");
          String buy_price = (String) session.getAttribute("buyprice");

          //Check if the purchase price is the same as the session price
          if(buy_price.equals(request.getParameter("buyprice"))){
            request.setAttribute("name",item_name);
            request.setAttribute("buyprice",buy_price);
            request.setAttribute("id", item_id);
            request.setAttribute("creditcard", request.getParameter("credit_card"));

            request.getRequestDispatcher("/confirm.jsp").forward(request, response);
        }
        }else{ //Session is not secure

          HttpSession session = request.getSession(true);

          String item_id = request.getParameter("id");

          if(item_id == null)
            item_id = "";

          String item_xml = (String) session.getAttribute(item_id);
          String item_name = (String) session.getAttribute("name");
          String buy_price = (String) session.getAttribute("buyprice");

          /*if (item_xml == null || item_xml.length() == 0) {
              item_xml = AuctionSearchClient.getXMLDataForItemId(item_id);
              session.setAttribute(item_id, item_xml); 
          } NOT NEEDED BECAUSE "In particular, you are NOT allowed to send a request to oak while you are generating the "credit-card-input page*/
          
          //Replace the newlines and spaces in our item_XML so our jQuery parser will work
          item_xml = item_xml.replace("\n", "").replace("\r", "").replace("\t","").replace("\'","\\\'");

          //Requests for purchases
          request.setAttribute("name",item_name);
          request.setAttribute("buyprice",buy_price);

          request.setAttribute("item", item_xml);
          request.setAttribute("id", item_id);
          request.getRequestDispatcher("/purchase.jsp").forward(request, response);
      }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
