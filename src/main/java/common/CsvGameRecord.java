package common;

public class CsvGameRecord {
    private String recordType = "";
    private int ID = 0;
    private double Xpos = 0;
    private double Ypos = 0;
    private int numberOfEnemies = 0;
    private int numberOfBullets = 0;
    private String direction = "";
    private boolean turn = false;
    private int points = 0;

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getXpos() {
        return Xpos;
    }

    public void setXpos(double xpos) {
        Xpos = xpos;
    }

    public double getYpos() {
        return Ypos;
    }

    public void setYpos(double ypos) {
        Ypos = ypos;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public void setNumberOfEnemies(int numberOfEnemies) {
        this.numberOfEnemies = numberOfEnemies;
    }

    public int getNumberOfBullets() {
        return numberOfBullets;
    }

    public void setNumberOfBullets(int numberOfBullets) {
        this.numberOfBullets = numberOfBullets;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
