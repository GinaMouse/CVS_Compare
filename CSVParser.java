package csvCompare;

import java.io.*;
import java.util.*;

import javax.xml.transform.Source;

public class CSV_Compare {
	public static void main(String[] args) throws IOException {
		
		String csv1FileName = "/Users/Home/Downloads/1.csv";
		String csv2FileName = "/Users/Home/Downloads/2.csv";
		String reportName = "/Users/Home/Downloads/DifferenceReport.txt";
		
		FileWriter fw = new FileWriter(reportName);
		
		List<String[]> c1Lines = new ArrayList<String[]>();
		List<String[]> c2Lines = new ArrayList<String[]>();
		
		//scan each row to the array
		scanToArray(csv1FileName, c1Lines);
		scanToArray(csv2FileName, c2Lines);
		
//		System.out.println("c2 size = " + c1Lines.size());
//		System.out.println("c4 size = " + c2Lines.size());
		
		System.out.println("Comparing files...");
		fw.write("Comparing files...\n");
		printVerdict(hasSameData(c1Lines, c2Lines, fw), fw);
		
		System.out.println("The end.");
		fw.write("The end.");
		fw.close();

		
	}
	
	//Take in 2 ArrayList<String> and compare that they contain the same data
	public static boolean hasSameData(List<String[]> c2, List<String[]>  c4, FileWriter fw) throws IOException {
		
		boolean hasSame = true;
		int tally = 0;
		int numTally = 0;
		int nilTally = 0;
		String[] c2Row;
		String[] c4Row;
		String r2ID;
		String r4ID;
		
		System.out.print("\n====================================\n");
		fw.write("\n====================================\n");

		if(c2.size() != c4.size()) {
			System.out.println("Different number of rows.");
			fw.write("Different number of rows.\n");
		} else {
			
//			System.out.println("c2 size = " + c2.size());
//			System.out.println("c4 size = " + c4.size());
			
			for (int row = 0; row < c2.size(); row++) {
				String[] header = c2.get(0);
				c2Row = c2.get(row);
				c4Row = c4.get(row);
				if (c2Row.length != c4Row.length) {
					System.out.println("Different number of columns.");
					fw.write("Different number of columns.");
				} else {
					
					r2ID = c2Row[0];
					r4ID = c4Row[0];
					for (int cell = 0; cell < c2Row.length; cell++) {
						String cell2 = c2Row[cell];
						String cell4 = c4Row[cell];
						if (!cell2.equals(cell4)) {
							if (cell2.equals("") && cell4.equals("nil")) {
								nilTally++;
								hasSame = false;
							} else {
								tally++;
								if (isNumber(cell2) && isNumber(cell4)) {
									if ((cell2.length() != cell4.length()) && (!cell2.isEmpty() || !cell4.isEmpty())) {
//										System.out.println("Row " + cell + ", R2:" + cell2 + " :: R4:" + cell4);
										if (Double.parseDouble(cell2) != Double.parseDouble(cell4)) {
											
											hasSame = false;
											System.out.print(r2ID + ": \"" + header[cell] + "\" = " + cell2 + "\n");
											System.out.print(r4ID + ": \"" + header[cell] + "\" = " + cell4 + "\n\n");
											fw.write(r2ID + ": " + header[cell] + " = " + cell2 + "\n");
											fw.write(r4ID + ": " + header[cell] + " = " + cell4 + "\n\n");
										} else {
											numTally++;
										}
									}
								} else {
									tally++;
									hasSame = false;
									System.out.print(r2ID + ": \"" + header[cell] + "\" = " + cell2 + "\n");
									System.out.print(r4ID + ": \"" + header[cell] + "\" = " + cell4 + "\n\n");
									fw.write(r2ID + ": " + header[cell] + " = " + cell2 + "\n");
									fw.write(r4ID + ": " + header[cell] + " = " + cell4 + "\n\n");
								}
								
								
							}
							
						}
					}
				}
			}
		}
		System.out.println("\nDone looking. " + tally + " differences.");
		System.out.println("Nill Diff = " + nilTally);
		System.out.println("Num Format Diff = " + numTally);
		fw.write("Done looking. " + tally + " differences.\n");
		return hasSame;
	}
	
	public static boolean isNumber(String s) {
		String lower = s.toLowerCase();
		boolean isNum = true;
		int decimal = 0;
		int neg = 0;
		for (int i = 0; i < lower.length(); i++) {
			char letter = lower.charAt(i);
			if (!isANum(letter)) {
				if (letter == '.') {
					decimal++;
				} else if (letter == '-') {
					neg++;
				} else {
					return false;
				}
			}
		}
		if ((decimal > 1) || (neg > 1)) {
			return false;
		}
		return isNum;
	}
	
	public static boolean isANum(char ch) {
		if ((47 < ch) && (ch < 58)) {
			return true;
		}
		return false;
	}
	
	//Take in a file name and Array and populate the array with each line of the file
	public static void scanToArray(String csvFileName, List<String[]> cLines) throws FileNotFoundException {
		String line = "";
		int listRow = 0;
		
		Scanner scan = new Scanner(new File(csvFileName));
//		scan.useDelimiter("\r");
		while (scan.hasNext()) {
			line = scan.nextLine();
			
			if (listRow > 0) {
//				System.out.println(line);
				String[] result = line.split(",", -1);
//				System.out.println("Row array length: " + result.length);
				cLines.add(result);
//				System.out.println("cLines length: " + cLines.size());
			}
			listRow++;
		}
		scan.close();
	}
	
	
	
	//Print verdict of comparison
	public static void printVerdict(boolean verdict, FileWriter fw) throws IOException {
		if (verdict) {
			System.out.println("They have the same data.\n");
		} else {
			System.out.println("====================================");
			System.out.println("They DO NOT have the same data.\n"); 
			fw.write("====================================\nThey DO NOT have the same data.\n");
		}	
	}

}
