import question.Question;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;

@XmlRootElement(name = "questionsList")
@XmlAccessorType (XmlAccessType.FIELD)
public class QuestionsList {
  @XmlElement(name = "question")
  private ArrayList<Question> questionList;

  public QuestionsList() {}

  public static ArrayList<Question> getQuestionList() {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(QuestionsList.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      QuestionsList questionsList = (QuestionsList) jaxbUnmarshaller.unmarshal(new File("file.xml"));
      return questionsList.questionList;
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return null;
  }
}
