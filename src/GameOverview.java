import java.io.Serializable;

/*
 * This class stores all the information needed from a game to be displayed when the "Rounds" button in the MenuBar is clicked.
 */

public class GameOverview implements Serializable {
    int mines;
    int attempts;
    int time;
    String winner;

    /*
     * Constructor: GameOverview
     * 
     * @param game: The game of which the info will be stored.
     */

    public GameOverview(Game game) {
        this.mines = game.getMines();
        this.attempts = game.getAttempts();
        this.time = game.getTime() - game.getRemainingTime();
        this.winner = game.getWinner();
    }

    /*
     * Getters
     */

    /*
     * @return: The number of mines in the game.
     */
    public int getMines() {
        return mines;
    }

    /*
     * @return: The number of attempts that have been done in the game.
     */
    public int getAttempts() {
        return attempts;
    }

    /*
     * @return: The time that has passed until the end of the game.
     */
    public int getTime() {
        return time;
    }

    /*
     * @return: The winner of the game.
     */
    public String getWinner() {
        return winner;
    }
}