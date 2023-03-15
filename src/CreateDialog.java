import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class CreateDialog extends Dialog<Map<String, Object>> {

    private TextField scenarioIdTextField;
    private TextField difficultyTextField;
    private TextField numMinesTextField;
    private CheckBox superMineCheckBox;
    private TextField maxTimeTextField;

    private Label scenarioIdLabel;
    private Label difficultyLabel;
    private Label numMinesLabel;
    private Label superMineLabel;
    private Label maxTimeLabel;

    public CreateDialog() {
        this.setTitle("Enter Game Information");

        scenarioIdTextField = new TextField();
        difficultyTextField = new TextField();
        numMinesTextField = new TextField();
        superMineCheckBox = new CheckBox();
        maxTimeTextField = new TextField();

        scenarioIdLabel = new Label("Scenario ID:");
        difficultyLabel = new Label("Difficulty:");
        numMinesLabel = new Label("Number of Mines:");
        superMineLabel = new Label("Has Supermine:");
        maxTimeLabel = new Label("Maximum Time (seconds):");

        ButtonType confirmButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 10, 10));
        gridPane.add(scenarioIdLabel, 0, 0);
        gridPane.add(scenarioIdTextField, 1, 0);
        gridPane.add(difficultyLabel, 0, 1);
        gridPane.add(difficultyTextField, 1, 1);
        gridPane.add(numMinesLabel, 0, 2);
        gridPane.add(numMinesTextField, 1, 2);
        gridPane.add(superMineLabel, 0, 3);
        gridPane.add(superMineCheckBox, 1, 3);
        gridPane.add(maxTimeLabel, 0, 4);
        gridPane.add(maxTimeTextField, 1, 4);

        this.getDialogPane().setContent(gridPane);

        this.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                Map<String, Object> result = new HashMap<>();
                result.put("id", scenarioIdTextField.getText());
                result.put("difficulty", difficultyTextField.getText());
                result.put("mines", numMinesTextField.getText());
                result.put("time", maxTimeTextField.getText());
                result.put("supermine", superMineCheckBox.isSelected() ? "1" : "0");
                return result;
            }
            return null;
        });
    }
}
