import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class CountdownTimer {
  private int minutes;
  private int seconds;

  public CountdownTimer(int totalTimeInSeconds) {
    this.minutes = totalTimeInSeconds / 60;
    this.seconds = totalTimeInSeconds % 60;
  }

  private void executeTask(Runnable runnable) {
    Platform.runLater(runnable);
  }

  public void runTimer(Label minutesLabel, Label secondsLabel) throws InterruptedException {
    executeTask(() -> {
      minutesLabel.setText(String.valueOf(minutes));
      secondsLabel.setText(String.valueOf(seconds));

    });
    while (minutes != 0 || seconds != 0) {
      if (seconds != 0) {
        executeTask(() -> secondsLabel.setText(String.valueOf(seconds)));
        seconds--;
      } else {
        minutes--;
        seconds = 59;
        executeTask(() -> {
          secondsLabel.setText(String.valueOf(seconds));
          minutesLabel.setText(String.valueOf(minutes));
        });
      }
      TimeUnit.SECONDS.sleep(1);
    }
  }
}
