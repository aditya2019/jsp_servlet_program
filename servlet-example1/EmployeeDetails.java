import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class EmployeeDetails extends HttpServlet{
  static int i;
  Connection con;
  PrintWriter out;
  ResultSet rs;
  public void init(){
    i=0;
    con=null;
    out=null;
    rs=null;
  }


  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    i++;
    out=response.getWriter();
    out.println("<b>You are user number " + i + "to visit this site</b><br><br>");
    try{
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      con=DriverManager.getConnection("jdbc:odbc:EmployeesDB","sa","franklin");
      PreparedStatement pstmt=null;
      String query=null;
      query="select emp_fname,address,age,desig from Employee_Master where id=?";
      pstmt=con.prepareStatement(query);
      pstmt.setInt(1,Integer.parseInt(request.getParameter("id")));
      rs=pstmt.executeQuery();
      out.println("<b><center>Employee Details</center></b><br><br>");
      ResultSetMetaData rsmd=rs.getMetaData();
      int colcount=rsmd.getColumnCount();
      out.println("<table align=center border=1 cellpadding=2>");
      out.println("<tr>");
      for(int i=1; i<=colcount; i++){
        out.println("<th>" + rsmd.getColumnLabel(i) + "</th>");
      }
      out.println("<tr>");
      while(rs.next()){
        out.println("<tr>");
        out.println("<td>" + rs.getString("emp_fname") + "</td>");
        out.println("<td>" + rs.getString("address") + "</td>");
        out.println("<td>" + rs.getString("age") + "</td>");
        out.println("<td>" + rs.getString("desig") + "</td>");
        out.println("</tr>");
      }
      out.println("</table>");
      out.println("</body>");
    }
    catch(Exception e){
      out.println(e.toString());
    }
  }
  public void destroy(){
    try{
      i=0;
      con.close();
      out.close();
      rs.close();
    }
    catch(SQLException se){
      out.println(se.toString());
    }
  }
}
