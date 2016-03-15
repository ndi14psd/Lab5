package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comparison.DataSource;
import comparison.DataSourceComparison;
import comparison.DataSourceFactory;

/**
 * Servlet implementation class ComparisonServlet
 */
@WebServlet("/ComparisonServlet")
public class ComparisonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ComparisonServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean pretty = Boolean.valueOf(request.getParameter("pretty"));
		String source1 = request.getParameter("source1");
		String source2 = request.getParameter("source2");
		String comp = new DataSourceComparison(source1, source2).getComparedData();
		JsonFormatter formatter = new JsonFormatter();
		response.getWriter().append(pretty? formatter.format(comp) : comp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
