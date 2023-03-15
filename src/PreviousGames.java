import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class PreviousGames implements Serializable {

    LinkedList<GameOverview> games;
    final int SIZE = 5;

    private static final long serialVersionUID = 1;

    public PreviousGames() {
        games = new LinkedList<GameOverview>();
    }

    public void add(GameOverview game) {
        if (games.size() == SIZE)
            games.remove();
        games.add(game);
    }

    public int size() {
        return games.size();
    }

    public GameOverview get(int index) {
        return games.get(index);
    }

    public void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("previous_games.dat"));
            out.writeObject(this);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PreviousGames load() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("previous_games.dat"));
            PreviousGames previousGames = (PreviousGames) in.readObject();
            in.close();
            return previousGames;
        } catch (Exception e) {
            return new PreviousGames();
        }
    }

}
