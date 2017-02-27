import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

	public static void main(String[] args) throws IOException {
		String r2Name = "1.csv";
		String r4Name = "2.csv";
		String reportName = "DiffReport.txt";
		
		FileWriter fw = new FileWriter(reportName);
		ArrayList<String[]> r2 = new ArrayList<String[]>();
		ArrayList<String[]> r4 = new ArrayList<String[]>();
		
//		System.out.println("Scanning csv...");
		
		scanToArray(r2Name, r2);
		scanToArray(r4Name, r4);
		
//		testPrint(r2);
//		testPrint(r4);
		
		printVerdict(compare(r2, r4, fw), fw);

		

		System.out.println("The end.");
		fw.close();
	}

	
	
	private static void printVerdict(int diffTally, FileWriter fw) throws IOException {
		System.out.print("======================================\n       ");
		fw.write("======================================\n       ");
		if (diffTally == 0) {
			System.out.println("No differences found.");
			fw.write("No differences found.\n");
		} else {
			System.out.println(diffTally + " differences found.");
			fw.write(diffTally + " differences found.\n");
		}
		System.out.println("======================================\n");
		fw.write("======================================\n\n");
	}



	private static int compare(ArrayList<String[]> r2, ArrayList<String[]> r4, FileWriter fw) throws IOException {
		int tally = 0;
		
		int rowCount = Math.min(r2.size(), r4.size());
		
		for (int row = 0; row < rowCount; row++) {
			String r2ID = r2.get(row)[0];
			String r4ID = r4.get(row)[0];
			
			int colCount = Math.min(r2.get(row).length, r2.get(row).length);
			for (int col = 0; col < colCount; col++) {
				String r2cell = r2.get(row)[col];
				String r4cell = r4.get(row)[col];
				
				if (!r2cell.equals(r4cell)) {
					System.out.println("NOT A MATCH: ");
					System.out.println("R2 ID: " + r2ID + ": " + r2cell);
					System.out.println("R4 ID: " + r4ID + ": " + r4cell);
					tally++;
					
					fw.write("NOT A MATCH: \n");
					fw.write("R2 ID: " + r2ID + ": " + r2cell + "\n");
					fw.write("R4 ID: " + r4ID + ": " + r4cell + "\n\n");
				}
			}
			System.out.println();
//			fw.write("\n");
		}
		
		return tally;
	}



	//Take in a CSV file and parse it, by row, into a String array of cells
	public static void scanToArray(String csvFileName, List<String[]> lineArray) throws FileNotFoundException {
		String line = "";
		String delim = ",";
		FileReader file = new FileReader (csvFileName);
		
			 
		 try (BufferedReader br = new BufferedReader(file)) {
			 
			while ( (line = br.readLine()) != null) {
				
				 String[] cRow = line.split(delim);
				 
				 lineArray.add(cRow);
				 
//				 for (int col = 0; col < cRow.length; col++) {
//					 System.out.print(" [" + cRow[col] + "] ");
//				 }
//				 System.out.println();
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println("This List is size: " + lineArray.size());
	}

	
	
	
	public static void testPrint(ArrayList<String[]> r) {
		System.out.println("\n Array is size: " + r.size());
		for (int row = 0; row < r.size(); row++) {
			String[] cRow = r.get(row);
			
			for (int col = 0; col < cRow.length; col++) {
				System.out.print(" [" + cRow[col] + "]  ");
			}
			System.out.println();
		}
	}
}






