package resultMerge;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import util.Getopt;
import util.IOReader;
import util.ResultWriter;

public class Main {

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
			
			System.err.println(e);
		}

	}

}
