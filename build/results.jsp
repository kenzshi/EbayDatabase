<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.ucla.cs.cs144.*" %>
<html>
	<head><title>eBay Search: Project 4</title></head>
	<body>
		<h1>Keyword Search</h1>
		<div>
		<h2>Search results for "<%= request.getAttribute("query") %>": </h2>
		    <form action="search" method="GET">
                <div>
                    <input name="q" type="text"/>    
                </div>
                <input name="numResultsToSkip" type="hidden" value="0" />
                <input name="numResultsToReturn" type="hidden" value="20" />
                <button type="submit">Search</button>
            </form>

			<%
			SearchResult[] basicResults = (SearchResult[]) request.getAttribute("results");
			SearchResult[] totalres = (SearchResult[]) request.getAttribute("total");
			String query = (String) request.getAttribute("query");
			int numResultsToSkip = Integer.parseInt(request.getParameter("numResultsToSkip"));
    		int numResultsToReturn = Integer.parseInt(request.getParameter("numResultsToReturn"));
    		int totalResults = totalres.length;
    		%>

    		<br/>
    		Total Results: <%= totalResults %> <br/>
    		<% if(totalResults > numResultsToSkip+numResultsToReturn){ %>
    		Showing Results <%= numResultsToSkip + 1 %> to <%= numResultsToSkip+numResultsToReturn %> <br/>
    		<%} else { %>
    		Showing Results <%= numResultsToSkip + 1 %> to <%= totalResults %> <br/>
    		<%}%>
			<br/>

			<table>
		      <c:forEach items="${results}" var="search">
		        <tr>
		            <td><a href='item?id=<c:out value="${search.itemId}"/>'><c:out value="${search.itemId}"/></a>:</td>
		            <td><a href='item?id=<c:out value="${search.itemId}"/>'><c:out value="${search.name}"/></a></td>
		        </tr>
		      </c:forEach>
			</table>

			<br/>
			<%if (numResultsToSkip > 0) { %>
			<a href="/eBay/search?q=<%= query %>&numResultsToSkip=<%= numResultsToSkip - numResultsToReturn %>&numResultsToReturn=<%= numResultsToReturn %>">Previous Page</a>
			<% } 

			if (totalResults - numResultsToReturn - numResultsToSkip >= 0) {%>
			<a href="/eBay/search?q=<%= query %>&numResultsToSkip=<%= numResultsToSkip + numResultsToReturn %>&numResultsToReturn=<%= numResultsToReturn %>">Next Page</a>
			<%}%>
		</div>
	</body>
</html>