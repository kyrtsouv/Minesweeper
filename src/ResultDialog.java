import javafx.scene.control.Alert;

public class ResultDialog extends Alert {

    public ResultDialog(String result, boolean gameOver) {
        super(AlertType.INFORMATION);
        if (gameOver) {
            this.setTitle("Game Over");
        } else {
            this.setTitle("Error");
        }
        this.setHeaderText(null);
        this.setContentText(result);

        this.show();
    }

}
