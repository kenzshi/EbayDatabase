<html>
    <head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <title>eBay Search: Project 4</title>
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    </head>
    <body>
        <div class= "container">
    	<h1>Confirmation Page:</h1>
        <h4>Thanks for purchasing! Here's a receipt of your information (don't worry, it's secure)!</h4>
        <p><b>Item Name:</b> <span id="name"><%=request.getAttribute("name")%></span></p>
        <p><b>Item ID:</b> <span id="itemid"><%=request.getAttribute("id")%></span></p>
        <p><b>Buy Price:</b> <span id="buyprice"><%=request.getAttribute("buyprice")%></span></p> 
        <p><b>Credit Card Number:</b> <span id="creditcard"><%=request.getAttribute("creditcard")%></span></p> 

        </div>
    </body>
</html>

