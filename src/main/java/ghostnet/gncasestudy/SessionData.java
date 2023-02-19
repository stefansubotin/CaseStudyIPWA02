package ghostnet.gncasestudy;

import jakarta.enterprise.context.*;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

@Named("data")
@SessionScoped
public class SessionData implements Serializable {

    private Salvager loggedInUser;
    
    private final String dbURL = "jdbc:derby://localhost:1527/sea-sheperd-ghostnet-db";
    private final String user = "ssu";
    private final String password = "1234";

    public SessionData() {
        
    }

    public Salvager getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Salvager loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public int addNet(GhostNet net) {
        int id = getKeyValue(1);
        String q = "INSERT INTO ssu.NETS (Id, Name, Status, Latitude, Longitude, EstimatedSize, ReportedBy, ReportersPhone";
        String r = ") VALUES (" + id + ", '" + net.getName() + "', " + Converter.StatusToInt(net.getStatus()) + ", " + net.getLatitude() +
                ", " + net.getLongitude() + ", " + net.getEstimatedSize() + ", '" + net.getReportedBy() + "', '" + net.getReportersPhone() + "'";
        if (net.getSalvager() != null) {
            q += ", Salvager";
            r += ", " + net.getSalvager().getId();
        }
        String query = q + r + ")";
        
        try {
            Connection con = DriverManager.getConnection(dbURL, user, password);
            Statement s = con.createStatement();s.executeUpdate(query);
            
            s.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }
    
    public int addSalvager(Salvager salvager) {
        int id = getKeyValue(2);
        String q = "INSERT INTO ssu.SALVAGERS (Id, Name, Phone, UserName, Password) VALUES (" + id + ", '" + salvager.getName() +
                "', '" + salvager.getPhone() + "', '" + salvager.getUserName() + "', '" + salvager.getPassword() + "')";
        
        try {
            Connection con = DriverManager.getConnection(dbURL, user, password);
            Statement s = con.createStatement();
            s.executeUpdate(q);
            
            s.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }
    
    public void saveNet(GhostNet net) {
        String q = "UPDATE ssu.NETS SET Name = '" + net.getName() + "', Status = " + Converter.StatusToInt(net.getStatus()) +
                ", Latitude = " + net.getLatitude() + ", Longitude = " + net.getLongitude() + ", EstimatedSize = " + net.getEstimatedSize() + ", ReportedBy = '" +
                net.getReportedBy() + "'";
        boolean cond = !net.getReportersPhone().equals("");
        if (cond) q = q + ", ReportersPhone = '" + net.getReportersPhone() + "'";
        if (net.getSalvager() != null) q = q + ", Salvager = " + net.getSalvager().getId();
        q = q + " WHERE Id = " + net.getId();
        try {
            Connection con = DriverManager.getConnection(dbURL, user, password);
            Statement s = con.createStatement();
            s.executeUpdate(q);
            
            s.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public ArrayList<GhostNet> getUnclaimedNets() {
        return getSpecifiedNets("SELECT * FROM ssu.Nets WHERE Status = 1");
    }
    
    public ArrayList<GhostNet> getMyNets() {
        return getSpecifiedNets("SELECT * FROM Nets WHERE Salvager = " + this.loggedInUser.getId());
    }
    
    public ArrayList<GhostNet> getOtherClaimedNets() {
        return getSpecifiedNets("SELECT * FROM Nets WHERE Status = 2 AND Salvager <> " + this.loggedInUser.getId());
    }
    
    public ArrayList<GhostNet> getMissingNets() {
        return getSpecifiedNets("SELECT * FROM Nets WHERE Status = 4");
    }
    
    public ArrayList<Salvager> getSalvagers() {
        ArrayList<Salvager> list = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(dbURL, user, password);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM ssu.SALVAGERS");
            while(rs.next()) {
                Salvager sal = new Salvager();
                sal.setId(rs.getInt("Id"));
                sal.setName(rs.getString("Name"));
                sal.setPhone(rs.getString("Phone"));
                sal.setUserName(rs.getString("UserName"));
                sal.setPassword(rs.getString("Password"));
                
                list.add(sal);
            }
            
            rs.close();
            s.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return list;
    }
    
    private ArrayList<GhostNet> getSpecifiedNets(String queryString) {
        ArrayList<GhostNet> nets = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(dbURL, user, password);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(queryString);
            
            while(rs.next()) {
                GhostNet net = new GhostNet();
                int id = rs.getInt("Id");
                if (rs.wasNull()) id = 0;
                String name = rs.getString("Name");
                if (rs.wasNull()) name = "unknown";
                int status = rs.getInt("Status");
                if (rs.wasNull()) status = 1;
                double latitude = rs.getDouble("Latitude");
                if (rs.wasNull()) latitude = 0.0;
                double longitude = rs.getDouble("Longitude");
                if (rs.wasNull()) longitude = 0.0;
                int estimatedSize = rs.getInt("EstimatedSize");
                if (rs.wasNull()) estimatedSize = 1;
                String reportedBy = rs.getString("ReportedBy");
                if (rs.wasNull()) reportedBy = "anonym";
                String reportersPhone = rs.getString("ReportersPhone");
                if (rs.wasNull()) reportersPhone = "";
                int salvagerId = rs.getInt("Salvager");
                if (rs.wasNull()) salvagerId = 0;
                
                net.setId(id);
                net.setName(name);
                net.setStatus(Converter.IntToStatus(status));
                net.setLatitude(latitude);
                net.setLongitude(longitude);
                net.setEstimatedSize(estimatedSize);
                net.setReportedBy(reportedBy);
                net.setReportersPhone(reportersPhone);
                net.setSalvager(getSalvager(salvagerId));
                
                nets.add(net);
            }
            
            rs.close();
            s.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            nets = null;
        }
        
        return nets;
    }
    
    private Salvager getSalvager(int id){
        Salvager sal = null;
        
        try {
            Connection con = DriverManager.getConnection(dbURL, user, password);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM ssu.SALVAGERS WHERE Id = " + id);
            while(rs.next()) {
                sal = new Salvager();
                sal.setId(id);
                sal.setName(rs.getString("Name"));
                sal.setPhone(rs.getString("Phone"));
                sal.setUserName(rs.getString("UserName"));
                sal.setPassword(rs.getString("Password"));
            }
            
            rs.close();
            s.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return sal;
    }
    
    private int getKeyValue(int i) {
        int res = 0;
        try {
            Connection con = DriverManager.getConnection(dbURL, user, password);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM ssu.MAXVALUESKEYS WHERE Id = " + i);
            while(rs.next()) {
                res = rs.getInt("Value");  
            }
            s.executeUpdate("UPDATE ssu.MAXVALUESKEYS SET Value = " + (++res) + " WHERE Id = " + i);
            rs.close();
            s.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return res;
    }
}
