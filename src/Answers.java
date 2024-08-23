import java.io.Serializable;
import java.util.ArrayList;

/**
 * Answers method for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class Answers implements Serializable {
    private String courseName;
    private String quiz;
    private ArrayList<String> question = new ArrayList<>();
    private ArrayList<String> answer = new ArrayList<>();
    private ArrayList<Double> grades = new ArrayList<>();

    public Answers() {
    }

    public Answers(ArrayList<String> question, ArrayList<String> answer, ArrayList<Double> grades) {
        this.question = question;
        this.answer = answer;
        this.grades = grades;
    }

    public Answers(ArrayList<String> question, ArrayList<String> answer) {
        this.question = question;
        this.answer = answer;
        for (int i = 0; i < question.size(); i++) {
            this.grades.add(0.0);
        }
    }

    public void setGrades(ArrayList<Double> grades) {
        this.grades = grades;
    }

    public ArrayList<Double> getGrades() {
        return grades;
    }

    public ArrayList<String> getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question.add(question.getName());
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer.add(answer);
    }

    public void printStudentDataMenu() {
        for (int i = 0; i < answer.size(); i++) {
            System.out.println("Question : " + question.get(i));
            System.out.println("Answers : " + answer.get(i));
        }
    }

    public String fileString() {
        String toString = "";
        toString += "<[";
        for (int i = 0; i < question.size(); i++) {
            if (i == question.size() - 1) {
                toString += question.get(i);
            } else {
                toString += question.get(i) + "-";
            }
        }
        toString += "]~[";
        for (int i = 0; i < question.size(); i++) {
            if (i == question.size() - 1) {
                toString += answer.get(i);
            } else {
                toString += answer.get(i) + "-";
            }
        }
        toString += "]~[";
        for (int i = 0; i < question.size(); i++) {
            if (i == question.size() - 1) {
                toString += grades.get(i);
            } else {
                toString += grades.get(i) + "-";
            }
        }
        toString += "]>";
        return toString;
    }
}
