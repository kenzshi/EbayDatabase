package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
    

    /** Creates a new instance of Indexer */
    public Indexer() {
    }

    /****************************************************
     Following tutorial code provided by 
     http://www.cs.ucla.edu/classes/winter15/cs144/projects/lucene/index.html
    ****************************************************/

    // Set up indexwriter variable 
    private IndexWriter indexWriter = null;


    // Following tutorial to getIndexWriter set up
    public IndexWriter getIndexWriter(boolean create) throws IOException {
        if (indexWriter == null) {
            //Creating the directory in index1 as specified by spec
            Directory indexDir = FSDirectory.open(new File("/var/lib/lucene/index1/"));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
            if(create)
                config.setOpenMode(IndexWriterConfig.OpenMode.CREATE); // make it so we overwrite previous index instead of adding on
            indexWriter = new IndexWriter(indexDir, config);
        } 
        return indexWriter;
   }

    // Function to close the indexwriter
    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        } 
   }

   // Function to add to our Lucene index
    public void addToIndex(int id, String name, String description, String category) throws IOException  {
        //Following tutorial provided in spec
        IndexWriter writer = getIndexWriter(false);
        Document doc = new Document();

        //Save the id/name to index on
        doc.add(new StringField("id", String.valueOf(id), Field.Store.YES));
        doc.add(new StringField("name", name, Field.Store.YES));
        //This is the union of our description and category
        String fullSearchableText = String.valueOf(id) + " " + name +  " " + description + " " + category;
        doc.add(new TextField("union", fullSearchableText, Field.Store.NO));
        writer.addDocument(doc);
    }
 
    public void rebuildIndexes() {

        Connection conn = null;

        // create a connection to the database to retrieve Items from MySQL
	try {
	    conn = DbManager.getConnection(true);
	} catch (SQLException ex) {
	    System.out.println(ex);
	}


	/*
	 * Add your code here to retrieve Items using the connection
	 * and add corresponding entries to your Lucene inverted indexes.
         *
         * You will have to use JDBC API to retrieve MySQL data from Java.
         * Read our tutorial on JDBC if you do not know how to use JDBC.
         *
         * You will also have to use Lucene IndexWriter and Document
         * classes to create an index and populate it with Items data.
         * Read our tutorial on Lucene as well if you don't know how.
         *
         * As part of this development, you may want to add 
         * new methods and create additional Java classes. 
         * If you create new classes, make sure that
         * the classes become part of "edu.ucla.cs.cs144" package
         * and place your class source files at src/edu/ucla/cs/cs144/.
	 * 
	 */

    try { 

        // Initialize our index writer
        getIndexWriter(true);

        /****************************************************
        Following tutorial code provided by 
        http://www.cs.ucla.edu/classes/winter15/cs144/projects/jdbc/index.html
        ****************************************************/

        Statement stmt = conn.createStatement(); 

        // Set up and run SQL query to get our Item ID, Name, Description, Category, and union of (name, description, category) to search through
        String sqlQuery = "SELECT item.id, item.name, item.description, U.Category FROM " +
                                "(SELECT item_id, group_concat(category.name SEPARATOR ' ') AS Category " +
                                    "FROM item_category INNER JOIN category " +
                                    "ON item_category.category_id = category.id " +
                                "GROUP BY item_id) AS U " +
                            "INNER JOIN item ON item.id = U.item_id";

        ResultSet rs = stmt.executeQuery(sqlQuery);

        //Loop through the SQL results (all the items) and add them to our Lucene index
        while (rs.next()) {
            addToIndex(rs.getInt("id"), rs.getString("name"),rs.getString("description"), rs.getString("Category"));
        }

        //Finish by closing the indexwriter
        closeIndexWriter();

    } catch(IOException e) { // Catch IOException from our Lucene document creation
            System.out.println(e);
        } catch (SQLException ex) { // Catch SQL exception from our JDBC work
        System.out.println(ex);
    }


        // close the database connection
	try {
	    conn.close();
	} catch (SQLException ex) {
	    System.out.println(ex);
	}
    } // end of rebuild index   

    public static void main(String args[]) {
        Indexer idx = new Indexer();
        idx.rebuildIndexes();
    }   
}
