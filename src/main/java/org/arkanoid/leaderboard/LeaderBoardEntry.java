package org.arkanoid.leaderboard;

/**
 * Đại diện cho một người chơi trong bảng xếp hạng.
 * Dùng với LeaderBoard để lưu ra file txt.
 */
public class LeaderBoardEntry implements Comparable<LeaderBoardEntry> {
    private final String name;
    private final int score;

    public LeaderBoardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(LeaderBoardEntry other) {
        // Sắp xếp giảm dần theo điểm.
        return Integer.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return name + " " + score;
    }
}
