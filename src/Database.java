
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection con = null;
    
    public void printStuff(String stuff) {
        System.out.println("in MySQL.printStuff");
        System.out.println(stuff);
    }
    
    public boolean connectDB(String user, String password, String host, String database) throws SQLException {
        try {
            // Load the JDBC driver
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            String url = "jdbc:mysql://" + host + "/" + database; // a JDBC url
            con = DriverManager.getConnection(url, user, password);
        } 
        catch (ClassNotFoundException ex) {    
            //Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Cannot connect MySQL because... " + ex.toString());
        }
        return true;
    }
    
    
    public void disconnect() throws SQLException {
        con.close();
    }
    
    public class Sample {
        public int id;
        public String title;
        public String body;
        public String tags;
        Sample (int givenId, String givenTitle, String givenBody, String givenTags) {
            this.id = givenId;
            this.title = givenTitle;
            this.body = givenBody;
            this.tags = givenTags;
        }
    }

    
}