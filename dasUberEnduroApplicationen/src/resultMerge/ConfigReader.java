package resultMerge;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ConfigReader {

	
	
	public static void main(String[] args) {

		String type = null;

		try {

			FileReader reader = new FileReader("config.json");

			JSONObject root = new JSONObject(new JSONTokener(reader));

			type = root.getString("race type");

		} catch (JSONException e) {

			System.out.println(e);
			System.exit(0);

		} catch (FileNotFoundException e) {

			System.out.println(e);
			System.exit(0);
		}

		System.out.println("Type of race: " + type);
	}
}
