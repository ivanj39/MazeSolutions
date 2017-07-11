package webapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/maze.do")
public class MazeServlet extends HttpServlet {

	private MazeService service = new MazeService();
	private final static String FILE_NAME = "./mazequestion.txt";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/views/maze.jsp").forward(request, response);
	}

	/**
	 * Read a maze maze input from the request and passes to the service to
	 * calculate and process
	 *
	 * @param request
	 *            and response
	 * @return
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		File file = new File(FILE_NAME);
		String filePathAbsolute = file.getAbsoluteFile().getParentFile().getAbsolutePath() + "mazequestion.txt";
		String filePath = filePathAbsolute.replace(".m", "m");
		file.createNewFile();
		String mazeQuestion = request.getParameter("mazeQuestion");
		BufferedWriter output = null;
		try {

			output = new BufferedWriter(new FileWriter(file));
			output.write(mazeQuestion);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				output.close();
			}
		}

		MazeVO mazeVO = service.solveMaze(filePath);
		request.setAttribute("result", mazeVO.toString());
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		out.println("  Maze Result: ");
		out.println("");
		out.println("");
		out.println("" + mazeVO.getResult());
		out.println("");
		out.println("");
		out.println("Time taken in second(s): " + mazeVO.getTimeInSec());
	}

}