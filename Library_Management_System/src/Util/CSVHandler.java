package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {

    // Read data from a CSV file
    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            data.add(line.split(";"));
        }
        br.close();
        return data;
    }

    // Write data to a CSV file
    public static void writeCSV(String filePath, List<String[]> data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (String[] row : data) {
            bw.write(String.join(";", row));
            bw.newLine();
        }
        bw.close();
    }
}
