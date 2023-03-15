import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RoundsDialog extends Stage {

    GridPane gridPane;

    public RoundsDialog(PreviousGames previousGames) {
        super();
        this.setTitle("Previous Games");

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 40, 10, 10));

        gridPane.add(new Label("#"), 0, 0);
        gridPane.add(new Label("Mines"), 1, 0);
        gridPane.add(new Label("Attempts"), 2, 0);
        gridPane.add(new Label("Time"), 3, 0);
        gridPane.add(new Label("Winner"), 4, 0);

        for (int i = 0; i < previousGames.size(); i++) {
            GameOverview game = previousGames.get(i);
            gridPane.add(new Label(String.valueOf(i + 1)), 0, i + 1);
            gridPane.add(new Label(String.valueOf(game.getMines())), 1, i + 1);
            gridPane.add(new Label(String.valueOf(game.getAttempts())), 2, i + 1);
            gridPane.add(new Label(String.valueOf(game.getTime())), 3, i + 1);
            gridPane.add(new Label(String.valueOf(game.getWinner())), 4, i + 1);
        }

        this.setScene(new Scene(gridPane));
    }

}
