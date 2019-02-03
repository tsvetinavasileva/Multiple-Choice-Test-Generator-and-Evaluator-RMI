import question.Question;

import java.util.ArrayList;

public class Report {
  private static double numberOfQuestions = 4;
  private String studentName;
  private String dateAndTime;
  private String bankTitle;
  private double countCorrect;
  private ArrayList<FXQuestion> questionList;
  private String report;

  public String getReport() {
    return report;
  }

  public Report() {
    questionList = new ArrayList<>();
    report = "";
  }

  public Report(String dateAndTime, String bankTitle, ArrayList<Question> questionList) {
    this();
    setDateAndTime(dateAndTime);
    setQuestionList(questionList);
    setBankTitle(bankTitle);
    setQuestionList(questionList);

  }

  public Report(ArrayList<Question> questionList) {
        setQuestionList(questionList);
  }

  public static double getNumberOfQuestions() {
    return numberOfQuestions;
  }

  public static void setNumberOfQuestions(int numberOfQuestions) {
    Report.numberOfQuestions = numberOfQuestions;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getDateAndTime() {
    return dateAndTime;
  }

  public void setDateAndTime(String dateAndTime) {
    this.dateAndTime = dateAndTime;
  }

  public String getBankTitle() {
    return bankTitle;
  }

  public void setBankTitle(String bankTitle) {
    this.bankTitle = bankTitle;
  }

  public double getCountCorrect() {
    return countCorrect;
  }

  public void setCountCorrect(double countCorrect) {
    this.countCorrect = countCorrect;
  }

  public ArrayList<FXQuestion> getQuestionList() {
    return questionList;
  }

  public void setQuestionList(ArrayList<Question> questionList) {
    this.questionList = new ArrayList<>();
    for (Question question : questionList) {
      FXQuestion fxQuestion = new FXQuestion(question);
      this.questionList.add(fxQuestion);
    }
  }

  public int calculateGrade() {
    double percentage = (countCorrect * 100) / numberOfQuestions;
    if (percentage >= 0 && percentage < 55) {
      return 2;
    }
    if (percentage >= 55 && percentage < 65) {
      return 3;
    }
    if (percentage >= 65 && percentage < 75) {
      return 4;
    }
    if (percentage >= 75 && percentage < 85) {
      return 5;
    }
    if (percentage >= 85 && percentage <= 100) {
      return 6;
    }
    return 0;
  }

  public String makeReport() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("student name: %s\ndate and time: %s\nbank: %s\n", studentName,dateAndTime,bankTitle));
    for(FXQuestion question : questionList){
      stringBuilder.append(question);
    }
    stringBuilder.append(String.format("Total score: %.0f / %.0f\nGrade: %d\n",countCorrect,numberOfQuestions,calculateGrade()));
    return String.valueOf(stringBuilder);
  }




//  public static void main(String[] args) {
//
//
//  }
}
