package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Product;
import utility.ConnectionManager;

public class ProductDAO implements ProductDaoInterface {
    ConnectionManager cm = new ConnectionManager();
    Connection con;
    
    // adding product to the database table
    public void addProduct(Product product, int quantity) throws Exception {
	 addStock(product,quantity);
	con = cm.getConnection();
	String  insertProduct = "insert into product(id,name,price, description) values(?,?,?,?)";
	 PreparedStatement ps = con.prepareStatement(insertProduct);
	 ps.setString(1, product.getProductId());
	 ps.setString(2, product.getProductName());
	 ps.setInt(3, product.getPrice());
	 ps.setString(4, product.getDescription());
	 int x = ps.executeUpdate();
	 if(x==1) {
		 System.out.println("");
	     System.out.println("/t ===========================");
	     System.out.println("/t PRODUCT SUCCESSFULLY ADDED.");
	     System.out.println("/t ===========================");
	 }else {
	     System.out.println("ERROR OCCURS TO ADD THE NEW PRODUCT.");
	 }
	
	 con.close();
    }
    
    // adding item stocks to database table
    public void addStock(Product product, int quantity) throws Exception {
	String insertStock = "insert into stock(stockid,quantity) values(?,?)";
	con = cm.getConnection();
	PreparedStatement ps = con.prepareStatement(insertStock);
	String stockid = product.getProductName();
	try {
	    	ps.setString(1, stockid);
	    	ps.setInt(2, quantity);
		ps.executeUpdate();
	}catch(Exception e) {
	    String updateStock = "update stock set quantity = "+quantity+"where stockid = "+stockid;
	    PreparedStatement ps1 = con.prepareStatement(updateStock);
	    ps1.setInt(1, quantity);
	}
	
    }
    
    public String generateProductId() throws Exception {
	 int id = 0;
	 String sql = "select count(id)+1 from product";
	     con = cm.getConnection();
	     Statement st = con.createStatement();
	     ResultSet rs = st.executeQuery(sql);
	     if(rs.next()) {
		 id = Integer.parseInt(rs.getString(1));
	     }
	     String prodid = "prod"+id;
	     con.close();
	     return prodid ;
    }
}
