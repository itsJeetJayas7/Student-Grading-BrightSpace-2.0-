import java.util.ArrayList;
import java.util.Arrays;

public class testClassJeet {
    public static void main(String[] args) {
        String[] options = {"a","b"};
        Question quest = new Question("hello", "MCQ", options);
        //System.out.println(quest.toString());
        String[] options2 = {"a","b"};
        Question quest2 = new Question("hello", "TrueFalse");
        String[] options3 = {"a","b"};
        Question quest3 = new Question("hello", "MCQ", options);
        String[] options4 = {"a","b"};
        Question quest4 = new Question("hello", "MCQ", options);
        Question[] questions2 = {quest,quest2,quest3,quest4};
        Quiz quiz = new Quiz("Hey", new ArrayList<>(Arrays.asList(questions2)));
        System.out.println(quiz.toString());
//        String line = quiz.toString();
//        line = line.substring(1, line.length() - 1);
//        String[] quizInfo = line.split(",");
//        quizInfo[3] = quizInfo[3].substring(1, quizInfo[3].length() - 1);
//        String[] questionInfo = quizInfo[3].split("#");
//        Question[] questions = new Question[questionInfo.length];
//        for (int i = 0; i < questionInfo.length; i++) {
//            questionInfo[i] =questionInfo[i].substring(1, questionInfo[i].length() - 1);
//            String[] questionSetter = questionInfo[i].split("-");
//            questionSetter[2] = questionSetter[2].substring(1, questionSetter[2].length() - 1);
//            String[] questionOptions = questionSetter[2].split("~");
//            questions[i] = new Question(questionSetter[0], questionSetter[1], questionOptions);
//        }
//
//        Quiz quiz2 = new Quiz(quizInfo[0], new ArrayList<>(Arrays.asList(questions)), Boolean.parseBoolean(quizInfo[1]), Integer.parseInt(quizInfo[2]));
//        System.out.println(quiz2.toString());
////        String s = "blah blah teacher";
////        String checkType = s.substring(s.length() - 7);
////        System.out.println(checkType);
    }
}
