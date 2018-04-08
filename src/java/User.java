    import java.sql.Connection;  
    import java.sql.DriverManager;  
    import java.sql.PreparedStatement;  
    import java.sql.Statement;  
    import java.sql.ResultSet;  
    import java.util.ArrayList;  
    import java.util.Map;  
    import javax.faces.bean.ManagedBean;
    import javax.faces.bean.RequestScoped;  
    import javax.faces.context.FacesContext;  
    @ManagedBean
    @RequestScoped  
    public class User{  
    String kodemk;  
    String nama;  
    String kodedsn;  
    String koderuang;  
    int semester;  
    int sks;  
    ArrayList usersList ;  
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  
    Connection connection;  

    public String getKodemk() {
        return kodemk;
    }

    public void setKodemk(String kodemk) {
        this.kodemk = kodemk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKodedsn() {
        return kodedsn;
    }

    public void setKodedsn(String kodedsn) {
        this.kodedsn = kodedsn;
    }

    public String getKoderuang() {
        return koderuang;
    }

    public void setKoderuang(String koderuang) {
        this.koderuang = koderuang;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getSks() {
        return sks;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }
   
    // Used to establish connection  
    public Connection getConnection(){  
    try{  
    Class.forName("com.mysql.jdbc.Driver");     
    connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/kuliah","root","hahaha111");  
    }catch(Exception e){  
    System.out.println(e);  
    }  
    return connection;  
    }  
    // Used to fetch all records  
    public ArrayList usersList(){  
    try{  
    usersList = new ArrayList();  
    connection = getConnection();  
    Statement stmt=getConnection().createStatement();    
    ResultSet rs=stmt.executeQuery("select * from matkul");    
    while(rs.next()){  
    User user = new User();  
    user.setKodemk(rs.getString("kodemk"));
    user.setNama(rs.getString("nama"));
    user.setKodedsn(rs.getString("kodedsn"));
    user.setKoderuang(rs.getString("koderuang"));
    user.setSemester(rs.getInt("semester"));
    user.setSks(rs.getInt("sks"));
    usersList.add(user);  
    }  
    connection.close();          
    }catch(Exception e){  
    System.out.println(e);  
    }  
    return usersList;  
    }  
    // Used to save user record  
    public String save(){  
    int result = 0;  
    try{  
    connection = getConnection();  
    PreparedStatement stmt = connection.prepareStatement(  
    "insert into matkul(kodemk,nama, kodedsn, koderuang, semester, sks) values(?,?,?,?,?,?)");  
    stmt.setString(1, kodemk);  
    stmt.setString(2, nama);  
    stmt.setString(3, kodedsn);  
    stmt.setString(4, koderuang);
    stmt.setInt(5, semester);
    stmt.setInt(6, sks);
    result = stmt.executeUpdate();  
    connection.close();  
    }catch(Exception e){  
    System.out.println(e);  
    }  
    if(result !=0)  
    return "index.xhtml?faces-redirect=true";  
    else return "create.xhtml?faces-redirect=true";  
    }  
    // Used to fetch record to update  
    public String edit(String kodemk){  
    User user = null;  
    System.out.println(kodemk);  
    try{  
    connection = getConnection();  
    Statement stmt=getConnection().createStatement();    
    ResultSet rs=stmt.executeQuery("select * from matkul where kodemk = \""+kodemk+"\"");  
    rs.next();  
    user = new User();  
    user.setKodemk(rs.getString("kodemk"));
    user.setNama(rs.getString("nama"));
    user.setKodedsn(rs.getString("kodedsn"));
    user.setKoderuang(rs.getString("koderuang"));
    user.setSemester(rs.getInt("semester"));
    user.setSks(rs.getInt("sks"));
    System.out.println(rs.getString("nama"));  
    sessionMap.put("editUser", user);  
    connection.close();  
    }catch(Exception e){
    System.out.println(user.getNama());
    System.out.println(e);  
    }         
    return "/edit.xhtml?faces-redirect=true";  
    }  
    // Used to update user record  
    public String update(User u){  
    //int result = 0;  
    try{  
    connection = getConnection();    
    PreparedStatement stmt=connection.prepareStatement(  
    "update matkul set nama=?,kodedsn=?,koderuang=?,sks=?,semester=? where kodemk=?");    
    stmt.setString(1,u.getNama());    
    stmt.setString(2,u.getKodedsn());    
    stmt.setString(3,u.getKoderuang());    
    stmt.setInt(4,u.getSks());    
    stmt.setInt(5,u.getSemester());    
    stmt.setString(6,u.getKodemk());    
    stmt.executeUpdate();  
    connection.close();  
    }catch(Exception e){  
    System.out.println();  
    }  
    return "/index.xhtml?faces-redirect=true";        
    }  
    // Used to delete user record  
    public void delete(String kodemk){  
    try{  
    connection = getConnection();    
    PreparedStatement stmt = connection.prepareStatement("delete from matkul where kodemk = \""+kodemk+"\"");
       System.out.println(stmt);
    stmt.executeUpdate();    
    }catch(Exception e){  
    System.out.println(e);
    }  
    }  
    // Used to set user gender  
    }  