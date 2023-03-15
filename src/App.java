import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {

    HBox infoPane;
    GridPane gridPane;

    Tile[][] tiles;

    Label totalMines;
    Label flaggedMines;
    Label time;

    CustomTimer timer;
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        PreviousGames previousGames = PreviousGames.load();
        Game game = new Game(this, previousGames);

        stage = primaryStage;

        CustomMenuBar menu = new CustomMenuBar(game, this, previousGames);

        BorderPane outerPane = new BorderPane();
        BorderPane innerPane = new BorderPane();
        infoPane = new HBox();
        gridPane = new GridPane();

        totalMines = new Label("Total Mines: 0");
        time = new Label("Time: 0");
        flaggedMines = new Label("Flagged Mines: 0");

        infoPane.setSpacing(10);
        infoPane.setAlignment(Pos.CENTER);

        infoPane.getChildren().addAll(totalMines, time, flaggedMines);

        stage.setTitle("MediaLab Minesweeper");

        innerPane.setTop(infoPane);
        innerPane.setCenter(gridPane);
        outerPane.setTop(menu);
        outerPane.setCenter(innerPane);
        stage.setScene(new Scene(outerPane));
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            previousGames.save();
            if (timer != null) {
                timer.clear();
            }
        });
        stage.show();

    }

    public void build(Game game) {
        infoPane.getChildren().clear();

        totalMines.setText("Total Mines: " + game.getMines());
        time.setText("Time: " + game.getRemainingTime());
        flaggedMines.setText("Remaining Flags: " + game.getAvailableFlags());

        infoPane.getChildren().addAll(totalMines, time, flaggedMines);

        timer = new CustomTimer(this, game);

        tiles = game.getTiles();
        gridPane.setGridLinesVisible(false);
        gridPane.getChildren().clear();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                gridPane.add(tiles[i][j], i, j);
            }
        }
        stage.sizeToScene();
        gridPane.setGridLinesVisible(true);
    }

    public void cancel() {
        if (tiles != null)
            for (Tile[] tileRow : tiles)
                for (Tile tile : tileRow) {
                    tile.setDisable(true);
                    tile.setOpacity(1);
                }
        if (timer != null) {
            timer.clear();
        }
    }

    public void updateFlaggedMines(int flaggedMines) {
        this.flaggedMines.setText("Remaining Flags: " + flaggedMines);
    }

    public void updateTime(int time) {
        this.time.setText("Time: " + time);
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
