Project 5

**************************
Kenneth Shi
304063313

Jia Dan Duan
604022222
**************************


Q1: For which communication(s) do you use the SSL encryption? If you are encrypting the communication from (1) to (2) in Figure 2, for example, write (1)→(2) in your answer.

The communications we use SSL encryption for are (4)->(5) and from (5)->(6). We wanted to encrypt these communications because they deal with a potential customer's credit card number, which is something we do not want outsiders to be able to see/steal. 



Q2: How do you ensure that the item was purchased exactly at the Buy_Price of that particular item?

We ensure that the Buy_Price is unchanged by storing the original buy price from the Oak server into a session cookie. Then, when a user purchases an item, we add a check in our secure purchase/confirmation page using the PurchaseServlet to check the incoming price of the buy_price with the buy_price stored in our session cookie. If the values don't match up, then we do not display the confirmation page. If they do match up, then the confirmation page is displayed. 

Note: If you get help from any source other than those mentioned in this page, at the end of your README, please clearly cite all references you use, and breifly explain how they help you, such as which portion(s) is/are particularly helpful.

References: 

(Class tutorials to get started)
http://www.cs.ucla.edu/classes/winter15/cs144/projects/tomcat/ssl_tomcat_tutorial.html
http://www.cs.ucla.edu/classes/winter15/cs144/projects/java/session/index.html

(Tomcat documentation for usage information)
https://tomcat.apache.org/tomcat-5.5-doc/servletapi/javax/servlet/http/HttpSession.html