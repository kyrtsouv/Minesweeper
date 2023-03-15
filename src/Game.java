import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Game {

    private int difficulty;
    private int numberOfMines;
    private int time;
    private boolean supermineExists;

    private int availableFlags;
    private int flagsForSupermine = 4;

    private int remainingTime;
    private int remainingTiles;
    private int attempts;
    private String winner;

    private boolean valid;
    private boolean isPlayed;

    private PreviousGames previousGames;
    private int size;
    private Tile tiles[][];
    private ArrayList<Tile> mines = new ArrayList<Tile>();

    App app;

    public Game(App app, PreviousGames previousGames) {
        this.app = app;
        this.previousGames = previousGames;
    }

    public void prepare() {

        if (difficulty == 1)
            size = 9;
        if (difficulty == 2)
            size = 16;

        isPlayed = true;

        remainingTime = time;
        remainingTiles = size * size - numberOfMines;
        availableFlags = numberOfMines;
        attempts = 0;

        tiles = new Tile[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile(this, i, j);
            }
        }

        int x;
        int y;
        mines.clear();
        for (int i = 0; i < numberOfMines; i++) {
            do {
                x = (int) (Math.random() * size);
                y = (int) (Math.random() * size);
            } while (tiles[x][y] instanceof Mine);
            tiles[x][y] = new Mine(this, tiles[x][y]);
            mines.add(tiles[x][y]);
        }

        if (supermineExists) {
            int supermineIndex = (int) (Math.random() * numberOfMines);
            Supermine supermine = new Supermine(this, (Mine) mines.get(supermineIndex));
            mines.set(supermineIndex, supermine);
            tiles[supermine.getX()][supermine.getY()] = supermine;
            flagsForSupermine = 4;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!(tiles[i][j] instanceof Mine)) {
                    int count = 0;
                    for (int k = -1; k <= 1; k++)
                        for (int l = -1; l <= 1; l++)
                            if ((i + k >= 0 && i + k < size && j + l >= 0 && j + l < size)
                                    && tiles[i + k][j + l] instanceof Mine)
                                count++;
                    tiles[i][j].setAdjacentMines(count);
                }
            }
        }

        try {
            File file = new File("mines.txt");
            file.deleteOnExit();
            FileWriter writer = new FileWriter(file);
            for (Tile mine : mines)
                writer.write(mine.getX() + ", " + mine.getY() + ", " + (mine instanceof Supermine ? 1 : 0) + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void revealAdjacent(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0 && x + i < size && y + j >= 0 && y + j < size) {
                    Tile adjacent = tiles[x + i][y + j];
                    if (!adjacent.isRevealed())
                        adjacent.leftClick(false);
                }
            }
        }
    }

    public void revealAxes(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        for (int i = 0; i < size; i++) {
            tiles[i][y].leftClick(true);
        }

        for (int j = 0; j < size; j++) {
            tiles[x][j].leftClick(true);
        }
    }

    public void over(Result result) {
        isPlayed = false;
        app.cancel();

        if (result == null)
            return;
        if (result == Result.LOSE) {
            winner = "Computer";
            for (Tile mine : mines)
                if (!mine.isRevealed())
                    ((Mine) mine).reveal();
            new ResultDialog("You lost!", true);
        }
        if (result == Result.WIN) {
            winner = "Player";
            new ResultDialog("You won!", true);
        }

        previousGames.add(new GameOverview(this));
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setMines(int mines) {
        this.numberOfMines = mines;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setSupermineExists(boolean supermine) {
        this.supermineExists = supermine;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMines() {
        return numberOfMines;
    }

    public int getTime() {
        return time;
    }

    public boolean getSupermineExists() {
        return supermineExists;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void decreaseRemainingTime() {
        remainingTime--;
    }

    public int getAttempts() {
        return attempts;
    }

    public void increaseAttempts() {
        attempts++;
    }

    public String getWinner() {
        return winner;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getAvailableFlags() {
        return availableFlags;
    }

    public void decreaseAvailableFlags() {
        availableFlags--;
        app.updateFlaggedMines(availableFlags);
        flagsForSupermine--;
    }

    public void increaseAvailableFlags() {
        availableFlags++;
        app.updateFlaggedMines(availableFlags);
    }

    public int getFlagsForSupermine() {
        return flagsForSupermine;
    }

    public void decreaseRemainingTiles() {
        remainingTiles--;

        if (remainingTiles == 0) {
            this.over(Result.WIN);
        }
    }

}
