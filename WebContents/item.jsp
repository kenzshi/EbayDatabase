<html>
    <head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <title>eBay Search: Project 4</title>
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    </head>
    <body>
        <div class= "container">
    	<h1>Item Search</h1>
            <h4>Search Item by ID:<h4>
            <form class="form-inline" action="item" method="GET">
                <div class="form-group">
                    <input class="form-control" name="id" type="text"/>
                    <button class="btn btn-default" type="submit">Search</button>    
                </div>
            </form>
        </div>
        
        <div class= "container">
        <p><b>Item Name:</b> <span id="name"></span></p>
        <p><b>Item ID:</b> <span id="itemid"></span></p>
        <p><b>Categories:</b> <span id="categories"></span></p>
        <p><b>Seller ID:</b> <span id="sellerid"></span></p>
        <p><b>Seller Rating:</b> <span id="sellerrating"></span></p>
        <p><b>Current Price:</b> <span id="currently"></span></p>
        <p id="buy_display"> <b>Buy Price:</b><span id="buyprice"></span>
            <form id="buy_form" class="form-inline" action="purchase" method="POST">
                <input type="hidden" name="id" value="<%=request.getAttribute("id")%>" />
                <button class="btn btn-default" type="submit">Pay Now!</button>   
            </form>
        </p>
        <p><b>First Bid:</b> <span id="firstbid"></span></p>
        <p><b>Number of Bids:</b> <span id="numbids"></span></p>
        <p id="bids_display"><b>Bids:</b> <span id="bids"></span></p>
        <p><b>Location:</b> <span id="location"></span></p>
        <p><b>Country:</b> <span id="country"></span></p>
        <p id="long_display"><b>Longitude:</b> <span id="longitude"></span></p>
        <p id="lat_display"><b>Latitude:</b> <span id="latitude"></span></p>
        <p><b>Started:</b> <span id="started"></span></p>
        <p><b>Ends:</b> <span id="ends"></span></p>
        <p><b>Description:</b> <span id="description"></span></p>
        <div id="map_canvas" style="width:500px; height:500px"></div> 
        </div>
        <script type="text/javascript" 
            src="http://maps.google.com/maps/api/js?sensor=false"> 
        </script> 
        <script type="text/javascript"> 
          function initialize() { 
            var lat = 0;
            var lon = 0;
            if ($xml.find("Location").attr("Longitude") && $xml.find("Location").attr("Latitude")) {
                lat = $xml.find("Location").attr("Latitude");
                lon = $xml.find("Location").attr("Longitude");
            }
            var latlng = new google.maps.LatLng(lat,lon); 
            var myOptions = { 
              zoom: 14, // default is 8  
              center: latlng, 
              mapTypeId: google.maps.MapTypeId.ROADMAP 
            }; 
            var map = new google.maps.Map(document.getElementById("map_canvas"), 
                myOptions); 
          }
        </script>

        <script>
        var xml_string = '<%= request.getAttribute("item") %>';
        $xml = $(xml_string);

        function sort_by_time(a, b){
            var a_time = a.bid_time;
            var b_time = b.bid_time;

            return ((a_time > b_time) ? -1 : ((a_time < b_time ? 1 : 0)))
        }

        $("#name").text($xml.find("Name").text());
        $("#itemid").text(<%=request.getAttribute("id")%>);
        $("#sellerid").text($xml.find("Seller").attr("UserID"));
        $("#sellerrating").text($xml.find("Seller").attr("Rating"));
        $("#currently").text($xml.find("Currently").text());
        if($xml.find("Buy_Price").text() != "" && $xml.find("Buy_Price").text() != "$0"){
            $("#buyprice").text($xml.find("Buy_Price").text());
        } else {
            $("#buy_display").hide();
            $("#buy_form").hide();
        }
        $("#firstbid").text($xml.find("First_Bid").text());
        $("#numbids").text($xml.find("Number_of_Bids").text());
        if($xml.find("Number_of_Bids").text() == "0")
            $("#bids_display").hide();
        $("#location").text($xml.find("Location").text());
        $("#country").text($xml.find("Country").text());
        if($xml.find("Location").attr("Longitude") != null || $xml.find("Latitude").attr("Longitude") != null){
            $("#longitude").text($xml.find("Location").attr("Longitude"));
            $("#latitude").text($xml.find("Location").attr("Latitude"));
        }else{
            $("#long_display").hide();
            $("#lat_display").hide();
        }
        $("#started").text($xml.find("Started").text());
        $("#ends").text($xml.find("Ends").text());
        $("#description").text($xml.find("Description").text());

        $xml.find("Category").each(function(category){
          if($("#categories").text() != "")
            $("#categories").append(" , ");
          $("#categories").append(this);
        });

        var bids_array = []
        $xml.find("Bids Bid").each(function(bid){
            var data = {
                bidder_rating: $(this).find("Bidder").attr("Rating"),
                bidder_id: $(this).find("Bidder").attr("UserID"),
                bid_location: $(this).find("Location").text(),
                bid_country: $(this).find("Country").text(),
                bid_time: $(this).find("Time").text(),
                bid_amount: $(this).find("Amount").text()
                };
        bids_array.push(data);
        });

        bids_array = bids_array.sort(sort_by_time);

        var bid_num = 1;
        $.each(bids_array, function(_, bid) {
        var print_bids = "</br>";
        print_bids += "<b>" + bid_num + ". </b>"
        print_bids += "Rating: " + bid.bidder_rating + "<br/>";
        print_bids += "Bidder ID: " + bid.bidder_id + "<br/>";
        print_bids += "Location: " + bid.bid_location + "<br/>";
        print_bids += "Country: " + bid.bid_country + "<br/>";
        print_bids += "Time: " + bid.bid_time + "<br/>";
        print_bids += "Amount: " + bid.bid_amount + "<br/>";
        bid_num++;
        $("#bids").append(print_bids);
      });
        initialize();
        </script>
    </body>
</html>
