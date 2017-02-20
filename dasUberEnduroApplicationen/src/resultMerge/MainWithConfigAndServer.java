package resultMerge;

import util.Getopt;

public class MainWithConfigAndServer {

	public static void main(String[] args) {
		try {

			Getopt g = new Getopt(args, "c:", true);

			int c;

			String configFilePath = "config.json";
			
			while ((c = g.getOption()) != -1) {

				switch (c) {

				case 'c':
					configFilePath = g.getOptarg();
					break;

				default:
					System.out.print("getopt() returned " + c + "\n");
				}
			}

			ConfigReader.readConfig(configFilePath);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

}
