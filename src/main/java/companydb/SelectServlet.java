package companydb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.awt.geom.QuadCurve2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "SelectServlet", urlPatterns = "/select")
public class SelectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/companydb");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PERSON ");
             PrintWriter out = response.getWriter();
        ) {
            out.format("| %5s | %-30s | %15s | %10s | %10s |\n", "ID", "Name", "Salary", "Position", "Department");
            while (rs.next()) {
                long id = rs.getLong("ID_PERSON");
                String name = rs.getString("FULL_NAME");
                long posId = rs.getLong("POS_ID");
                long depId = rs.getLong("DEP_ID");
                Double salary = rs.getDouble("SALARY");
                out.format("| %5s | %-30s | %15s | %10s | %10s |\n", id, name, salary, posId, depId);
                //System.out.println("id: " + id + "; name" + name + "Pos ID: "
                 //+ posId + " Dep ID: " + depId + "Dalary: " + salary);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong");
            e.printStackTrace();
        }
    }
}
