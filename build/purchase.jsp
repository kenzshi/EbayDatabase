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
        <p><b>Item Name:</b> <span id="name"></span></p>
        <p><b>Item ID:</b> <span id="itemid"></span></p>
        <p><b>Buy Price:</b> <span id="buyprice"></span> </p> 

        <form class="form-inline" action="purchase" method="POST">
            <p><b>Credit Card Number:</b></p> 

            <input type="hidden" name="id" value="<%=request.getAttribute("id")%>" />
            <input type="submit" value="Pay Now" />
        </form>
    
        </div>
        <script type="text/javascript" 
            src="http://maps.google.com/maps/api/js?sensor=false"> 
        </script> 

        <script>
        var xml_string = '<%= request.getAttribute("item") %>';
        $xml = $(xml_string);

        $("#name").text($xml.find("Name").text());
        $("#itemid").text(<%=request.getAttribute("id")%>);
        $("#buyprice").text($xml.find("Buy_Price").text());
        </script>
    </body>
</html>
