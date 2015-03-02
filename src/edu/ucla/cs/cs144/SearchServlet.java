package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
      	String q = request.getParameter("q");
      	int numResultsToSkip = 0;
      	int numResultsToReturn = 0;

      	//If q is empty, set empty
      	if (q == null){
      		q = "";
      	}

      	try{

      	numResultsToSkip = Integer.parseInt(request.getParameter("numResultsToSkip"));
    	numResultsToReturn = Integer.parseInt(request.getParameter("numResultsToReturn"));

    	} catch(NumberFormatException ne){ //If parseInt throws exception
    		numResultsToSkip = 0;
      		numResultsToReturn = 0;
    	} catch (NullPointerException npe){ //If we're unable to get the parameter correctly
    		numResultsToSkip = 0;
      		numResultsToReturn = 0;
    	}

    	//Create the basic search using the AuctionSearchClient
    	SearchResult[] basicSearch = AuctionSearchClient.basicSearch(q, numResultsToSkip, numResultsToReturn);
    	//Total number of results
    	SearchResult[] total = AuctionSearchClient.basicSearch(q, 0, 100000);
    	
    	request.setAttribute("results", basicSearch);
    	request.setAttribute("total", total);
		request.setAttribute("query", q);
		request.setAttribute("numResultsToSkip", numResultsToSkip);
		request.setAttribute("numResultsToReturn", numResultsToReturn);
				
    	request.getRequestDispatcher("/results.jsp").forward(request, response);
    }
}
