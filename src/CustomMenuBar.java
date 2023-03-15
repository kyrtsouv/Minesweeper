import java.io.File;
import java.io.FileWriter;
import java.util.*;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class CustomMenuBar extends MenuBar {
    private MenuItem Create;
    private MenuItem Load;
    private MenuItem Start;
    private MenuItem Exit;

    private MenuItem Rounds;
    private MenuItem Solution;

    private Menu Application;
    private Menu Details;

    private int difficulty;

    public CustomMenuBar(Game game, App app, PreviousGames previousGames) {
        Create = new MenuItem("Create");
        Load = new MenuItem("Load");
        Start = new MenuItem("Start");
        Exit = new MenuItem("Exit");

        Rounds = new MenuItem("Rounds");
        Solution = new MenuItem("Solution");

        Application = new Menu("Application");
        Details = new Menu("Details");

        Create.setOnAction((event) -> {
            Optional<Map<String, Object>> result = (new CreateDialog()).showAndWait();
            if (result.isPresent()) {
                Map<String, Object> validResult = result.get();
                try {
                    if (!validResult.get("id").toString().equals("")) {
                        FileWriter writer = new FileWriter(new File("medialab/" + validResult.get("id") + ".txt"));
                        writer.write(validResult.get("difficulty").toString() + "\n");
                        writer.write(validResult.get("mines").toString() + "\n");
                        writer.write(validResult.get("time").toString() + "\n");
                        writer.write(validResult.get("supermine").toString());
                        writer.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Load.setOnAction((event) -> {
            Optional<String> result = (new LoadDialog()).showAndWait();
            if (result.isPresent()) {
                try {
                    app.cancel();
                    File file = new File("medialab/" + result.get() + ".txt");
                    Scanner scanner = new Scanner(file);

                    int next = 0;

                    if (!scanner.hasNextLine()) {
                        scanner.close();
                        throw new InvalidDescriptionException("Difficulty is not given");
                    }
                    if (scanner.hasNextInt()) {
                        difficulty = scanner.nextInt();
                        if (difficulty != 1 && difficulty != 2) {
                            scanner.close();
                            throw new InvalidValueException("Difficulty must be 1 or 2");
                        }
                    } else {
                        scanner.close();
                        throw new InvalidValueException("Difficulty must be 1 or 2");
                    }
                    game.setDifficulty(difficulty);

                    if (!scanner.hasNext()) {
                        scanner.close();
                        throw new InvalidDescriptionException("Mines are not given");
                    }
                    if (scanner.hasNextInt()) {
                        next = scanner.nextInt();
                        if (((next < 9 || next > 11) && difficulty == 1)
                                || ((next < 35 || next > 45) && difficulty == 2)) {
                            scanner.close();
                            throw new InvalidValueException(
                                    "Mines must be between 9 and 11 for difficulty 1 and between 35 and 45 for difficulty 2");
                        }
                    } else {
                        scanner.close();
                        throw new InvalidValueException(
                                "Mines must be between 9 and 11 for difficulty 1 and between 35 and 45 for difficulty 2");
                    }
                    game.setMines(next);

                    if (!scanner.hasNext()) {
                        scanner.close();
                        throw new InvalidDescriptionException("Time is not given");
                    }
                    if (scanner.hasNextInt()) {
                        next = scanner.nextInt();
                        if (((next < 120 || next > 180) && difficulty == 1)
                                || ((next < 240 || next > 360) && difficulty == 2)) {
                            scanner.close();
                            throw new InvalidValueException(
                                    "Time must be between 120 and 180 for difficulty 1 and between 240 and 360 for difficulty 2");
                        }
                    } else {
                        scanner.close();
                        throw new InvalidValueException(
                                "Time must be between 120 and 180 for difficulty 1 and between 240 and 360 for difficulty 2");
                    }
                    game.setTime(next);

                    if (!scanner.hasNext()) {
                        scanner.close();
                        throw new InvalidDescriptionException("Supermine existence is not given");
                    }
                    if (scanner.hasNextInt()) {
                        next = scanner.nextInt();
                        if ((next != 0 && difficulty == 1) || (next != 0 && next != 1 && difficulty == 2)) {
                            scanner.close();
                            throw new InvalidValueException(
                                    "Supermine existence must be 0 for difficulty 1 and 0 or 1 for difficulty 2");
                        }
                    } else {
                        scanner.close();
                        throw new InvalidValueException(
                                "Supermine existence must be 0 for difficulty 1 and 0 or 1 for difficulty 2");
                    }
                    game.setSupermineExists((next == 1) ? true : false);

                    scanner.close();

                    game.setValid(true);
                } catch (Exception e) {
                    game.setValid(false);
                    new ResultDialog(e.toString(), false).show();
                }
            }
        });

        Start.setOnAction((event) -> {
            if (game.isPlayed()) {
                app.cancel();
            }
            if (game.isValid()) {
                game.prepare();
                app.build(game);
            }
        });

        Exit.setOnAction((event) -> {
            previousGames.save();
            System.exit(0);
        });

        Application.getItems().addAll(Create, Load, Start, Exit);

        Rounds.setOnAction((event) -> {
            new RoundsDialog(previousGames).show();
        });

        Solution.setOnAction((event) -> {
            if (game.isPlayed()) {
                game.over(Result.LOSE);
            }
        });

        Details.getItems().addAll(Rounds, Solution);

        this.getMenus().addAll(Application, Details);
    }
}
