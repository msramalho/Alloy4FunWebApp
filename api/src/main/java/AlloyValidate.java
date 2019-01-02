import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.util.JSONPObject;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.parser.CompUtil;

/**
 * The post body should contain a JSON object with the following structure:
 * 
 * { "model": "..." }
 * 
 */
@Path("/validate")
public class AlloyValidate {
	@POST
	@Produces("text/json")
	public Response doPost(String body) throws IOException {
		String model = getModelFromJSON(body);
		A4Reporter rep = new A4Reporter();
		// File tmpAls = CompUtil.flushModelToFile(model, null);

		try {
			CompUtil.parseEverything_fromString(rep, model);
		} catch (Exception e) {
			int[] errorLocation = getErrorLocationFromException(e);
			String message = e.getMessage();
			message = fixMessage(message);
			String response = makeErrorJson(errorLocation, message);

			return Response.ok(response).build();
		}

		return Response.ok(makeValidJson()).build();
	}

	private Object makeValidJson() {
		JSONObject jo = new JSONObject();
		jo.put("success", true);
		return jo.toString();
	}

	/**
	 * removes newlines and trims the string.
	 * 
	 * @param message
	 * @return the fixed message
	 */
	private String fixMessage(String message) {
		return message.replace("\n", "").trim();
	}

	/**
	 * extracts the error location (line and column) from the stackTrace
	 * 
	 * @param e
	 * @return String containing the line and column of the syntax error
	 */
	private int[] getErrorLocationFromException(Exception e) {
		// TODO get line, col from e.pos.y, e.pos.x respectively
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String trace = sw.toString(); // stack trace as a string
		String firstLine = trace.split("\n")[0];
		String errorLocation = firstLine.split(" at ")[1];
		errorLocation = errorLocation.substring(0, errorLocation.length() - 1);
		int line, col;
		String[] exp;
		exp = errorLocation.split("column");
		for (int i = 0; i < exp.length; i++) {
			exp[i] = exp[i].trim();
		}
		line = Integer.parseInt(exp[0].replace("line ", ""));
		col = Integer.parseInt(exp[1]);

		return new int[] { line, col };

	}

	/**
	 * Generates a String of an encoded JSON Object with the response to a model
	 * with an invalid syntax.
	 * 
	 * @param errorLocation
	 * @param message
	 * @return String
	 */
	private String makeErrorJson(int[] errorLocation, String message) {
		JSONObject jo = new JSONObject();
		jo.put("success", false);
		JSONObject loca = new JSONObject();
		loca.put("line", errorLocation[0]);
		loca.put("column", errorLocation[1]);
		jo.put("errorLocation", loca);
		jo.put("errorMessage", message);
		return jo.toString();
	}

	/**
	 * Extracts the model from the JSON object received by POST.
	 * 
	 * @param body
	 * @return String the model
	 */
	private String getModelFromJSON(String body) {
		JSONObject jo = new JSONObject(body);
		String model = (String) jo.get("model");
		return model;
	}
}