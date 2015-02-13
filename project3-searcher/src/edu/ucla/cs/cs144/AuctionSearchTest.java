package edu.ucla.cs.cs144;

import java.util.Calendar;
import java.util.Date;

import edu.ucla.cs.cs144.AuctionSearch;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

public class AuctionSearchTest {
	public static void main(String[] args1)
	{
		AuctionSearch as = new AuctionSearch();

		String message = "Test message";
		String reply = as.echo(message);
		System.out.println("Reply: " + reply);

		String query1 = "superman";
		SearchResult[] basicResults1 = as.basicSearch(query1, 0, 10000);
		System.out.println("Basic Search Query: " + query1);
		System.out.println("Received " + basicResults1.length + " results");

		String query2 = "kitchenware";
		SearchResult[] basicResults2 = as.basicSearch(query2, 0, 10000);
		System.out.println("Basic Search Query: " + query2);
		System.out.println("Received " + basicResults2.length + " results");

		String query3 = "star trek";
		SearchResult[] basicResults3 = as.basicSearch(query3, 0, 10000);
		System.out.println("Basic Search Query: " + query3);
		System.out.println("Received " + basicResults3.length + " results");


		for(SearchResult result : basicResults3) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}

		SearchRegion region =
		    new SearchRegion(33.774, -118.63, 34.201, -117.38);
		SearchResult[] spatialResults = as.spatialSearch("camera", region, 0, 20);
		System.out.println("Spatial Seacrh");
		System.out.println("Received " + spatialResults.length + " results");
		for(SearchResult result : spatialResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}
		//1497595357 Original value
		//1045678297 Needs Description to escape
		String itemId = "1043817906";
		String item = as.getXMLDataForItemId(itemId);
		System.out.println("XML data for ItemId: " + itemId);
		System.out.println(item);

		// Add your own test here
	}
}
