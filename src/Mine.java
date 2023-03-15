import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Mine extends Tile {

    public Mine(Game game, Tile tile) {
        super(game, tile.getX(), tile.getY());
    }

    @Override
    public void leftClick(boolean revealFromSupermine) {
        if (revealFromSupermine) {
            isRevealed = true;
            this.selectedProperty().set(true);
            this.setDisable(true);
            this.setOpacity(1);
            this.setText("X");
            this.setFont(Font.font("System", FontWeight.BOLD, 20));
        } else if (!isFlagged) {
            isRevealed = true;
            this.selectedProperty().set(true);
            this.setDisable(true);
            this.setOpacity(1);
            this.setGraphic(new ImageView(new Image(new File("media/mine.png").toURI().toString())));
            game.over(Result.LOSE);

        } else
            this.selectedProperty().set(false);
    }

    public void reveal() {
        isRevealed = true;
        this.selectedProperty().set(true);
        this.setDisable(true);
        this.setOpacity(1);
        this.setGraphic(new ImageView(new Image(new File("media/mine.png").toURI().toString())));
    }

}
