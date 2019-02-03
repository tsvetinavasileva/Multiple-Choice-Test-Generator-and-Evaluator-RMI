import question.Question;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public final class StudentServerInterfaceImpl extends UnicastRemoteObject
    implements StudentServerInterface {
  private int bank = 0;
  private int totalNumberOfQuestions;
  private int totalTestTime;
  private ArrayList<Question> questionList;

  public StudentServerInterfaceImpl(int totalNumberOfQuestions, int totalTestTime) throws RemoteException {
    initializeQuestions(totalNumberOfQuestions,totalTestTime);
  }


  protected void initializeQuestions(int totalNumberOfQuestions, int totalTestTime) {
    setTotalTestTime(totalTestTime);
    setTotalNumberOfQuestions(totalNumberOfQuestions);
    questionList = QuestionsList.getQuestionList();
  }

  public int getTotalNumberOfQuestions() throws RemoteException {
    return totalNumberOfQuestions;
  }

  @Override
  public int getTotalTime() throws RemoteException {
    return totalTestTime;
  }

  public void setTotalTestTime(int totalTestTime) {
    this.totalTestTime = totalTestTime;
  }

  public void setTotalNumberOfQuestions(int totalNumberOfQuestions) {
    this.totalNumberOfQuestions = totalNumberOfQuestions;
  }


  @Override
  public ArrayList<Question> generateSubsetOfQuestions (int numberOfQuestions) throws RemoteException {
    boolean[] arrayOfIndex = new boolean[numberOfQuestions];
    Random random = new Random();
    int count = numberOfQuestions;
    ArrayList<Question> result = new ArrayList<>();
    for(int i = 0; i < count; i++){
        int index = random.nextInt(numberOfQuestions);
        if(!arrayOfIndex[index]){
          result.add(questionList.get(index));
          arrayOfIndex[index] = true;
        }
        else{
          count++;
        }
    }
    return result;
  }

  public String getBankTitle() throws RemoteException{
  bank++;
  return "Bank" + bank;
  }

  public void addReports(String report) throws RemoteException{
    PrintStream printStream = null;
    try {
      printStream = new PrintStream("reports.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if(printStream != null) {
      printStream.println(report);
      printStream.close();
    }
  }

}





