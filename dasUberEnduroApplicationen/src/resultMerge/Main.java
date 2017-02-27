package resultMerge;

public class Main {

	public static void main(String[] args) {
		String configFilePath = "config.json";
		if (args.length == 2 && args[0].equals("-c"))
			configFilePath = args[1];
		try {
			ConfigReader.readConfig(configFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
