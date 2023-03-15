import java.io.File;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Tile extends ToggleButton {

    protected boolean isFlagged;
    protected boolean isRevealed;
    protected int adjacentMines;

    protected int x;
    protected int y;
    protected Game game;

    public Tile(Game game, int x, int y) {
        super();

        this.x = x;
        this.y = y;
        this.game = game;

        this.setFocusTraversable(false);
        this.setPrefSize(50, 50);

        isFlagged = false;
        isRevealed = false;

        this.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                game.increaseAttempts();
                leftClick(false);
            } else if (event.getButton() == MouseButton.SECONDARY)
                rightClick();
        });
    }

    public void leftClick(boolean revealFromSupermine) {
        if (!isFlagged || revealFromSupermine) {

            isRevealed = true;
            this.selectedProperty().set(true);
            this.setDisable(true);
            this.setOpacity(1);

            game.decreaseRemainingTiles();

            this.setGraphic(null);

            if (adjacentMines > 0)
                this.setText(String.valueOf(adjacentMines));
            else if (!revealFromSupermine)
                game.revealAdjacent(this);
        } else
            this.selectedProperty().set(false);

    }

    public void rightClick() {
        if (!isFlagged && game.getAvailableFlags() > 0) {
            isFlagged = true;
            this.setGraphic(
                    new ImageView(new Image(new File("media/flag.png").toURI().toString())));
            game.decreaseAvailableFlags();
        } else if (isFlagged) {
            isFlagged = false;
            game.increaseAvailableFlags();
            this.setGraphic(null);
        }
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isRevealed() {
        return isRevealed;
    }
}
