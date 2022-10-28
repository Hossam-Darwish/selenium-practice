package file_utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CsvFileManager {

    public static Object[][] getCsvData(String filePath) throws IOException {
        ArrayList<Object[]> records = new ArrayList<>();
        String record;

        BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

        /*Ignore First Line */
        csvReader.readLine();
        while ((record = csvReader.readLine()) != null) {
            String[] fields = record.split(",");
            records.add(fields);
        }

        /*Close the Buffer Reader */
        csvReader.close();
            /* finally i need a 2D Object Array to deal with @Dataprovider in TestNG
               so we have to convert this record to a Object[][]
             */

        /*   Object results [][] = new Object[records.size()][] ;*/

        Object[][] results = new Object[records.size()][];
        /* Do this conversion */
        for (int i = 0; i < records.size(); i++) {
            results[i] = records.get(i);
        }

        return results;
    }

}

