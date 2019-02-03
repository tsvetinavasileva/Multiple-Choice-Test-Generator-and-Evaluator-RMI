import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import question.Question;

public class FXQuestion {
  private Label titleLabel;
  private RadioButton[] radios;
  private int correctAnswerIndex;
  private int chosenAnswerIndex;

  public FXQuestion(Question question) {
    correctAnswerIndex = question.getCorrectAnswerIndex();
    chosenAnswerIndex = question.getChosenAnswerIndex();
    titleLabel = new Label(question.getTitle());
    radios = new RadioButton[4];
    for (RadioButton radio : radios) {
      radio = new RadioButton();
    }
    radios[0] = new RadioButton();
    radios[1] = new RadioButton();
    radios[2] = new RadioButton();
    radios[3] = new RadioButton();

    radios[0].setText(question.getAnswers()[0]);
    radios[1].setText(question.getAnswers()[1]);
    radios[2].setText(question.getAnswers()[2]);
    radios[3].setText(question.getAnswers()[3]);

    ToggleGroup toggleGroup = new ToggleGroup();
    radios[0].setToggleGroup(toggleGroup);
    radios[1].setToggleGroup(toggleGroup);
    radios[2].setToggleGroup(toggleGroup);
    radios[3].setToggleGroup(toggleGroup);
    toggleGroup.selectToggle(null);
  }

  public int getCorrectAnswerIndex() {
    return correctAnswerIndex;
  }

  public int getChosenAnswerIndex() {
    return chosenAnswerIndex;
  }

  public RadioButton[] getRadios() {
    return radios;
  }

  public Label getTitleLabel() {
    return titleLabel;
  }

  boolean isCorrect() {
    return correctAnswerIndex == chosenAnswerIndex;
  }

  public void setChosenAnswer(int chosenAnswerIndex) {
    this.chosenAnswerIndex = chosenAnswerIndex;
  }

  public void setCorrectAnswerIndex(int correctAnswerIndex) {
    this.correctAnswerIndex = correctAnswerIndex;
  }

  public String toString() {
    String result = isCorrect()
        ? " The given answer is correct. 1/1" : " The given answer is incorrect. 0/1";
    return String.format("%s\n%s\n%s\n%s\n%s\n"
            + " The correct answer is: %s\n "
            + "The given answer is: %s\n%s\n\n",
        titleLabel.getText(), radios[0].getText(), radios[1].getText(),
       radios[2].getText(),radios[3].getText(),
        radios[correctAnswerIndex].getText(),radios[chosenAnswerIndex].getText(),result);
  }
}
