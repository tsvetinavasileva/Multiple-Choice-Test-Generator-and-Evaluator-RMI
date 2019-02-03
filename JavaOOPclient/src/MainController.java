import java.io.IOException;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Application implements Initializable {
  @FXML
  private Pagination pagination;
  @FXML
  private TextField studentNameText;
  @FXML
  private Label minutes;
  @FXML
  private Label seconds;
  @FXML
  private Button submitButton;
  @FXML
  private Label colon;
  @FXML
  private Label remainingTime;
  private Stage stage;
  private Report bank;
  private StudentServerInterface object;
  private boolean submitted = false;
  public java.lang.Integer questionsCount;

  private void executeTask(Runnable runnable) {
    Platform.runLater(runnable);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      initializeRMI();
      Calendar cal = Calendar.getInstance();
      questionsCount = object.getTotalNumberOfQuestions();
      bank = new Report(cal.getTime().toString(), object.getBankTitle(), object.generateSubsetOfQuestions(questionsCount));
      setPagination();
      int totalTimeInSeconds = object.getTotalTime();
      new Thread(() -> {
        try {
          CountdownTimer timer = new CountdownTimer(totalTimeInSeconds);
          timer.runTimer(minutes, seconds);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (!submitted) {
          System.exit(0);
        }
      }).start();
    } catch (RemoteException e) {
      e.printStackTrace();
    }

  }
 @FXML
  private void listenForSubmit() {
    executeTask(()->  {
      submitted = true;
      bank.setStudentName(studentNameText.getText());
      int i = 0;
      int countCorrect = 0;
      for (FXQuestion entry : bank.getQuestionList()) {
        pagination.setCurrentPageIndex(i);
        i++;
        for (int j = 0; j < 4; j++) {
          if (entry.getRadios()[j].isSelected()) {
            if (j == entry.getCorrectAnswerIndex()) {
              countCorrect++;
            }
            entry.setChosenAnswer(j);
          }
        }
      }
      bank.setCountCorrect(countCorrect);
      String report = bank.makeReport();
      TextArea textArea = new TextArea(report);
      try {
        object.addReports(report);
      } catch (RemoteException e1) {
        e1.printStackTrace();
      }
      textArea.setPrefHeight(500);
      textArea.setEditable(false);
      stage.close();
      GridPane reportPane = new GridPane();
      reportPane.getChildren().add(textArea);
      Stage stage = (Stage) submitButton.getScene().getWindow();
      stage.close();
      Scene scene = new Scene(reportPane, 400, 600);
      stage.setScene(scene);
      stage.show();
    });
  }


  protected void initializeRMI() {
    String host = "localhost";
    System.out.println("localhost");
    try {
      Registry registry = LocateRegistry.getRegistry(host, 1099);
      object = (StudentServerInterface) registry.lookup("StudentServerInterfaceImpl");
      System.out.println("Server object " + object + " found");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }


  public int itemsPerPage() {
    return 1;
  }

  private void setPagination() {
    pagination.setMaxPageIndicatorCount(questionsCount);
    pagination.setPageCount(questionsCount);
    pagination.setPageFactory(pageIndex -> createPage(pageIndex));
  }

  private VBox createPage(Integer pageIndex) {
    VBox box = new VBox(5);
    int page = pageIndex*itemsPerPage();
    for (int i = page; i < page + itemsPerPage(); i++) { ;
      box.getChildren().add(bank.getQuestionList().get(pageIndex).getTitleLabel());
      box.getChildren().addAll(bank.getQuestionList().get(pageIndex).getRadios());
      if (pageIndex == bank.getQuestionList().size() - 1) {
      }
    }
    return box;
  }

  public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
    Parent root = loader.load();
    MainController controller = loader.getController();
    controller.setStage(stage);
    Scene scene = new Scene(root, 600, 400);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

}



