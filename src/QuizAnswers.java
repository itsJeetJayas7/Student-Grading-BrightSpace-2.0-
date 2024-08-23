import java.io.Serializable;
import java.util.ArrayList;

/**
 * QuizAnswers method for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class QuizAnswers implements Serializable {
    private ArrayList<Student> students = new ArrayList<>();

    public QuizAnswers(ArrayList<Student> students) {
        this.students = students;
    }

    public QuizAnswers() {
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public String printOneStudentResponse(int i) {
        String toString = "";
        toString += students.get(i).getAnswers().toString();
        return toString;
    }

    public void printStudentResponseList() {
        for (int i = 0; i < students.size(); i++) {
            System.out.print((i + 1) + ". " + students.get(i).getStudentName() + " Graded: ");
            if (students.get(i).isGraded()) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }

    }

    public String fileString() {
        String toString = "";
        for (int i = 0; i < students.size(); i++) {
            if (i == students.size() - 1) {
                toString += students.get(i).fileString();
            } else {
                toString += students.get(i).fileString() + "#";
            }
        }
        return toString;
    }
}
