package resultMerge;

import util.Getopt;
//TODO
public class MainWithConfigAndServer {

	public static void main(String[] args) {
		try {

			Getopt g = new Getopt(args, "c:p:", true);

			int c;
			int port = 10008; // Default

			String configFilePath = "config.json";
			
			while ((c = g.getOption()) != -1) {

				switch (c) {

				case 'c':
					configFilePath = g.getOptarg();
					break;
				case 'p':
					port = Integer.parseInt(g.getOptarg());
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
