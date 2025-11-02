package org.arkanoid.leaderboard;

import java.io.*;
import java.util.*;

/**
 * Quản lý danh sách điểm cao và lưu ra file txt.
 */
public class LeaderBoard {

    private static final String FILE_PATH = "leaderboard.txt";
    private static final int MAX_ENTRIES = 10;

    private final List<LeaderBoardEntry> entries = new ArrayList<>();

    public LeaderBoard() {
        load();
    }

    /** Thêm điểm mới và lưu ra file. */
    public void addEntry(String name, int score, int time) {
        entries.add(new LeaderBoardEntry(name, score, time));
        Collections.sort(entries);
        if (entries.size() > MAX_ENTRIES) {
            entries.subList(MAX_ENTRIES, entries.size()).clear();
        }
        save();
    }

    /** Trả về danh sách điểm cao. */
    public List<LeaderBoardEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /** Đọc từ file txt. */
    private void load() {
        entries.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length == 3) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    int time = Integer.parseInt(parts[2]);
                    entries.add(new LeaderBoardEntry(name, score, time));
                } else if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    entries.add(new LeaderBoardEntry(name, score, 0));
                }
            }
            Collections.sort(entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Ghi ra file txt. */
    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (LeaderBoardEntry entry : entries) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
