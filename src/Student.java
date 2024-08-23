import java.io.*;

/**
 * Student method for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class Student implements Serializable {
    private String studentName;
    private String userName;
    private Answers answers;
    private boolean graded;
    private double  grades;
    private String date;


    public String getStudentName() {
        return studentName;
    }


    public double getGrades() {
        return grades;
    }

    public void setGrades(double grades) {
        this.grades = grades;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUserName() {
        return userName;
    }

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }

    public boolean isGraded() {
        return graded;
    }

    public void setGraded(boolean graded) {
        this.graded = graded;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Student(String studentName, String userName, Answers answers, String date) {
        this.studentName = studentName;
        this.userName = userName;
        this.answers = answers;
        this.graded = false;
        this.grades = 0;
        this.date = date;
    }

    public Student(String studentName, String userName, Answers answers, boolean graded, double grades, String date) {
        this.studentName = studentName;
        this.userName = userName;
        this.answers = answers;
        this.graded = graded;
        this.grades = grades;
        this.date = date;
    }

    public Student(User user) {
        this.studentName = user.getFullName();
        this.userName = user.getUserName();
    }

    public String getDate() {
        return date;
    }

    private String individualStudentString() {
        String toString = "";
        toString += userName + "," + studentName + "\n";
        toString += answers.toString();
        return toString;
    }

    public String fileString() {
        String toString = "";
        toString += "<";
        toString += studentName + "," + userName + "," + graded + "," + grades + "," + date + ",";
        toString += answers.fileString();
        toString += ">";
        return toString;
    }

}
