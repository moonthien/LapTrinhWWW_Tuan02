package fit.iuh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.Gson;

/**
 * Servlet implementation class Bai4
 */
@WebServlet()
public class Bai4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bai4() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

        // Tạo đối tượng User
        User user = new User("Thien", "trong", "trongthien@gmail.com");

        // Chuyển đối tượng thành JSON sử dụng Gson
        Gson gson = new Gson();
        String userJsonString = gson.toJson(user);

        // Thiết lập kiểu nội dung trả về là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Gửi chuỗi JSON ra response
        response.getWriter().write(userJsonString);
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
