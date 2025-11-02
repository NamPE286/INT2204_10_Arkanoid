package org.arkanoid.leaderboard;

/**
 * Đại diện cho một người chơi trong bảng xếp hạng.
 * Dùng với LeaderBoard để lưu ra file txt.
 */
public class LeaderBoardEntry implements Comparable<LeaderBoardEntry> {
    private final String name;
    private final int score;
    private final int time;

    public LeaderBoardEntry(String name, int score, int time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    /**
     * So sánh điểm.
     *
     * @param other các người chơi khác đã có trong bảng xếp hạng
     * @return danh sách xếp hạng
     */
    @Override
    public int compareTo(LeaderBoardEntry other) {
        int scoreCompare = Integer.compare(other.score, this.score);

        if (scoreCompare != 0) {
            return scoreCompare;
        } else {
            return Integer.compare(this.time, other.time);
        }
    }

    @Override
    public String toString() {
        return name + " " + score + " " + time;
    }
}
