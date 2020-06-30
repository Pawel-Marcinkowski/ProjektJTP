package common;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Bullet;
import model.Enemy;
import model.Player;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public class GameSaver {
    private Vector<CsvGameRecord> records;
    private Vector<CsvGameRecord> savedGame;
    private Vector<CsvLoginRecord> accounts;

    public GameSaver() {
        records = new Vector<CsvGameRecord>();
    }

    public void createPositionsInfo(Vector<Enemy> enemies, Player player, Vector<Bullet> bullets, int points) {
        int recordsIterator = 0;
        records.add(new CsvGameRecord());
        records.get(recordsIterator).setID(0);
        records.get(recordsIterator).setNumberOfEnemies(enemies.size());
        records.get(recordsIterator).setRecordType("player");
        records.get(recordsIterator).setXpos(player.getXpos());
        records.get(recordsIterator).setYpos(player.getYpos());
        records.get(recordsIterator).setNumberOfBullets(bullets.size());
        records.get(recordsIterator).setPoints(points);
        recordsIterator++;

        for (int i = 0; i < enemies.size(); i++) {
            records.add(new CsvGameRecord());
            records.get(recordsIterator).setRecordType("enemy");
            records.get(recordsIterator).setID(enemies.get(i).getEnemyID());
            records.get(recordsIterator).setXpos(enemies.get(i).getXpos());
            records.get(recordsIterator).setYpos(enemies.get(i).getYpos());
            records.get(recordsIterator).setNumberOfEnemies(enemies.size());
            records.get(recordsIterator).setNumberOfBullets(bullets.size());
            records.get(recordsIterator).setDirection(enemies.get(i).getDirection());
            records.get(recordsIterator).setTurn(enemies.get(i).isTurn());
            records.get(recordsIterator).setPoints(points);
            recordsIterator++;
        }

        for (int i = 0; i < bullets.size(); i++) {
            records.add(new CsvGameRecord());
            records.get(recordsIterator).setRecordType("bullet");
            records.get(recordsIterator).setID(bullets.get(i).getBulletID());
            records.get(recordsIterator).setXpos(bullets.get(i).getXpos());
            records.get(recordsIterator).setYpos(bullets.get(i).getYpos());
            records.get(recordsIterator).setNumberOfEnemies(bullets.size());
            records.get(recordsIterator).setNumberOfBullets(bullets.size());
            records.get(recordsIterator).setDirection(bullets.get(i).getDirection());
            records.get(recordsIterator).setPoints(points);
            recordsIterator++;
        }
    }

    public boolean saveGame() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        Window stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(file.toURI()));
        ) {
            StatefulBeanToCsv<CsvGameRecord> recordToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            recordToCsv.write(records);
        } catch (CsvRequiredFieldEmptyException | IOException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void readSaveFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            savedGame = new Vector<CsvGameRecord>();
            fileChooser.setTitle("Pick a save file");
            Window stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);

            Reader reader = Files.newBufferedReader(file.toPath());

            CsvToBean<CsvGameRecord> csvToBean = new CsvToBeanBuilder(reader).withType(CsvGameRecord.class).withIgnoreLeadingWhiteSpace(true).build();

            for (CsvGameRecord csvRecord : csvToBean) {
                savedGame.add(csvRecord);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector<CsvGameRecord> getSavedGame() {
        return savedGame;
    }

    public void readLoginFile() {
        try {
            accounts = new Vector<CsvLoginRecord>();
            File file = new File("accounts.csv");
            if (!file.exists()) {
                file.createNewFile();
            }

            Reader reader = Files.newBufferedReader(file.toPath());

            CsvToBean<CsvLoginRecord> csvToBean = new CsvToBeanBuilder(reader).withType(CsvLoginRecord.class).withIgnoreLeadingWhiteSpace(true).build();

            for (CsvLoginRecord csvRecord : csvToBean) {
                accounts.add(csvRecord);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAccount() {
        File file = new File("accounts.csv");
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(file.toURI()));
        ) {
            StatefulBeanToCsv<CsvLoginRecord> recordToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            recordToCsv.write(accounts);
        } catch (CsvRequiredFieldEmptyException | IOException | CsvDataTypeMismatchException e) {
            e.printStackTrace();

        }

    }

    public Vector<CsvLoginRecord> getAccounts() {
        return accounts;
    }
}
