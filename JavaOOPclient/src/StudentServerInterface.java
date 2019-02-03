import question.Question;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface StudentServerInterface extends Remote {
  /**
   * Return the score for specified the name
   * @return  returns a Report
     * @throws RemoteException
   */

   ArrayList<Question> generateSubsetOfQuestions(int numberOfQuestions) throws RemoteException;
   String getBankTitle() throws RemoteException;
   void addReports(String report) throws RemoteException;
   int getTotalTime() throws RemoteException;
   int getTotalNumberOfQuestions() throws RemoteException;



  // String generateString() throws RemoteException;
}

