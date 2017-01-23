package resultMerge;

public class Main {
	public static void main(String[] args) {
		
		String startFilePath = "/path";
		String finishFilePath = "/path";
		String nameFilePath = "/path";
		String resultFilePath = "/path";
		
		// TODO: Fix path
		Database db = new Database();
		IOReader.readTimes(startFilePath, finishFilePath, db);
		IOReader.readNames(nameFilePath, db);
		ResultWriter.write(resultFilePath, db);
	}
}
