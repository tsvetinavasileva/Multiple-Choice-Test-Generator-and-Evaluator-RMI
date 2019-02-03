import java.rmi.registry.*;

public class RegisterWithRMIServer {
  /** Main method
     * @param args */
  public static void main(String[] args)   {
    try {
      StudentServerInterface obj = new StudentServerInterfaceImpl(4,1000);
      Registry registry = LocateRegistry.createRegistry(1099);
      registry.rebind("StudentServerInterfaceImpl", obj);
      System.out.println("Student server " + obj + " registered");
      System.out.println("Press Return to quit.");
      int key = System.in.read();

      /*List<String> strings = new ArrayList<String>(2);
      strings.add("foo");
      strings.add("bar");

      System.exit(0);*/
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }          
  }
}
