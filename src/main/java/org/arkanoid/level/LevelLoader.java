package org.arkanoid.level;

import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelLoader {

    public static LevelConfig loadFromCSV(String path) {
        try (InputStream is = LevelLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException("Not found " + path);
            }

            try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReader(reader)) {
                String[] firstLine = csvReader.readNext();
                int backgroundId = firstLine[0].trim().charAt(0) - '0';
                List<int[]> rows = new ArrayList<>();
                String[] nextLine;

                while ((nextLine = csvReader.readNext()) != null) {
                    int[] row = Arrays.stream(nextLine)
                        .mapToInt(s -> Integer.parseInt(s.trim()))
                        .toArray();
                    rows.add(row);
                }

                int[][] brickMap = rows.toArray(new int[0][]);

                return new LevelConfig(backgroundId, brickMap);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
