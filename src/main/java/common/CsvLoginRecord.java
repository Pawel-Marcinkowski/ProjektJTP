package common;

public class CsvLoginRecord {
    private String nickName;
    private int highScore;
    private Long lastLogin;

    public CsvLoginRecord() {
        highScore = 0;
        lastLogin = System.currentTimeMillis();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }
}
