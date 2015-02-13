Project 3 

**************************
Kenneth Shi
304063313

Jia Dan Duan
604022222
**************************

We followed the tutorials provided in the spec for setting up our JDBC and Lucene:
http://www.cs.ucla.edu/classes/winter15/cs144/projects/jdbc/index.html
http://www.cs.ucla.edu/classes/winter15/cs144/projects/lucene/index.html

Other References:
http://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer
http://stackoverflow.com/questions/4850901/formatting-xml-file-indentation
http://docs.oracle.com/javase/7/docs/api/javax/xml/transform/OutputKeys.html
http://docs.oracle.com/javase/tutorial/jaxp/xslt/writingDom.html
http://docs.oracle.com/javase/6/docs/api/javax/xml/parsers/DocumentBuilderFactory.html

(1) We decided to create our Lucene indexes on item id, item name, and the Union of (item id, name, description, and category), although we only need to store the item id and the item names, since we will be retrieving those. We decided on these indexes because we felt that since we had to search through the union of the name, description, and category, adding these indexes would make our searches a lot easier.

(2) In creating the XML from itemID, we decided to use the Java XML DOM library to enforce better programming and make it easier for us to keep track of all the nodes. It automatically creates the < > tags that we need, and also takes care of indentations. 

(3) When deploying our code to the server, we ran the command ((ant build && cp build/AuctionSearchService.aar $CATALINA_BASE/webapps/axis2/WEB-INF/services/) && echo "Deploy Successful!") || echo "Deploy Failed!", which would echo Deploy Successful if we were able to copy our AuctionSearchService.aar to the Axis2 server. 

Issues:
- One of the big issues is that the Java XML lib we used automatically escapes & into &amp;. This made it very difficult to escape characters like < to &lt; because the library sees the & and transforms it into &amp;lt. We had a hacky solution to this by calling replaceAll on the final XML string to replace &amp;lt; &amp;gt; &amp;apos; to their original &lt, &gt and &apos values.
