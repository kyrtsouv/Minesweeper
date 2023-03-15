import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class CustomTimer extends Timer {

    App app;
    Game game;

    TimerTask task;

    public CustomTimer(App app, Game game) {
        this.app = app;
        this.game = game;

        task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (game.getRemainingTime() > 0) {
                        game.decreaseRemainingTime();
                        app.updateTime(game.getRemainingTime());
                    } else {
                        game.over(Result.LOSE);
                    }
                });
            }
        };

        this.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void clear() {
        task.cancel();
        this.cancel();
        this.purge();
    }
}
