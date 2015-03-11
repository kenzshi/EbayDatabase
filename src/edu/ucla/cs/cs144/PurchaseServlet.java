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
        HttpSession session = request.getSession(true);

        String item_id = request.getParameter("id");

        if(item_id == null)
          item_id = "";

        String item_xml = (String)session.getAttribute(item_id);
        //Replace the newlines and spaces in our item_XML so our jQuery parser will work
        item_xml = item_xml.replace("\n", "").replace("\r", "").replace("\t","").replace("\'","\\\'");

        request.setAttribute("item", item_xml);
        request.setAttribute("id", item_id);
        request.getRequestDispatcher("/purchase.jsp").forward(request, response);

    }
}
