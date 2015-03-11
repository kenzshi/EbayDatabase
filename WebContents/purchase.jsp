<html>
    <head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <title>eBay Search: Project 4</title>
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    </head>
    <body>
        <div class= "container">
    	<h1>Item Purchase</h1>
            <h4>Enter Credit Care Information Below:<h4>
        <p><b>Item Name:</b> <span id="name"><%=request.getAttribute("name")%></span></p>
        <p><b>Item ID:</b> <span id="itemid"><%=request.getAttribute("id")%></span></p>
        <p><b>Buy Price:</b> <span id="buyprice"><%=request.getAttribute("buyprice")%></span> </p> 

        <% String ssl_url = "https://" + request.getServerName() + ":8443" + request.getContextPath() + "/purchase"; %>
        <form class="form-inline" action="<%=ssl_url%>" method="POST">
            <p><b>Credit Card Number:</b></p> 
            <input type="hidden" name="id" value="<%=request.getAttribute("id")%>" />
            <input type="hidden" name="buyprice" value="<%=request.getAttribute("buyprice")%>" />
            <input class="form-control" type="number" name="credit_card" />
            <button class="btn btn-default" type="submit">Submit!</button>
        </form>
    
        </div>
    </body>
</html>
