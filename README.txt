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

(1) We decided to create our Lucene indexes on item id, item name, and the Union of (item id, name, description, and category), although we only need to store the item id and the item names, since we will be retrieving those. We decided on these indexes because we felt that since we had to search through the union of the name, description, and category, adding these indexes would make our searches a lot easier.
