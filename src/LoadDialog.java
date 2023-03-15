import javafx.scene.control.TextInputDialog;

public class LoadDialog extends TextInputDialog {

    public LoadDialog() {
        super();
        this.setTitle("Load");
        this.setHeaderText("Load a game");
        this.setContentText("Enter the game ID:");
    }
}
