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
import java.math.BigDecimal;
import java.sql.*;

@WebServlet(name = "SelectServlet", urlPatterns = "/select")
public class SelectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/company");
             PreparedStatement stmt = conn.prepareStatement(// iskljuchaet sql injection
                     "SELECT * FROM PERSON WHERE FULL_NAME = ?");

             PrintWriter out = response.getWriter();
        ) {
            stmt.setString(1,fullName);
            ResultSet rs = stmt.executeQuery();
            out.format("| %5s | %-30s | %15s | %10s | %10s |\n", "ID", "Name", "Salary", "Position", "Department");
            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("FULL_NAME");
                long posId = rs.getLong("POSITION_ID");
                long depId = rs.getLong("DEPARTMENT_ID");
                BigDecimal salary = rs.getBigDecimal("SALARY");
                out.format("| %5s | %-30s | %15s | %10s | %10s |\n", id, name, salary, posId, depId);

            }
        } catch (SQLException e) {
            System.out.println("Something wrong");
            e.printStackTrace();
        }
    }
}
