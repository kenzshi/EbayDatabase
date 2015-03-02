<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.ucla.cs.cs144.*" %>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<title>eBay Search: Project 4</title>
	</head>
	<body>
		<div class= "container">
		<h1>Keyword Search</h1>
		<h4>Search results for "<%= request.getAttribute("query") %>": </h4>
		    <form class="form-inline" action="search" method="GET">
                <div class="form-group">
                    <input class="form-control" name="q" type="text"/>   
                    <button class="btn btn-default" type="submit">Search</button> 
                </div>
                <input name="numResultsToSkip" type="hidden" value="0" />
                <input name="numResultsToReturn" type="hidden" value="20" />
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

			<div class="text-right">
			<div class="lead">
			<%if (numResultsToSkip > 0) { %>
			<div class="pull-left"><a href="/eBay/search?q=<%= query %>&numResultsToSkip=<%= numResultsToSkip - numResultsToReturn %>&numResultsToReturn=<%= numResultsToReturn %>">Previous  </a></div>
			<% } 

			if (totalResults - numResultsToReturn - numResultsToSkip >= 0) {%>
			<a href="/eBay/search?q=<%= query %>&numResultsToSkip=<%= numResultsToSkip + numResultsToReturn %>&numResultsToReturn=<%= numResultsToReturn %>"> Next</a>
			<%}%>
			</div>
			</div>

			<table class="table">
		      <c:forEach items="${results}" var="search">
		        <tr>
		            <td><a href='item?id=<c:out value="${search.itemId}"/>'><c:out value="${search.itemId}"/></a>:</td>
		            <td><a href='item?id=<c:out value="${search.itemId}"/>'><c:out value="${search.name}"/></a></td>
		        </tr>
		      </c:forEach>
			</table>


		</div>
	</body>
</html>