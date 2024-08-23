import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * teacher method for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class Teacher implements Serializable {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean teacherPermission;
    private boolean studentPermission;

    public Teacher(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;

        this.teacherPermission = true;
        this.studentPermission = false;
    }

    // invoking
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    // edit
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // permission
    public boolean isTeacherPermission() {
        return teacherPermission;
    }

    public void setTeacherPermission(boolean teacherPermission) {
        this.teacherPermission = teacherPermission;
    }

    public boolean isStudentPermission() {
        return studentPermission;
    }

    public void setStudentPermission(boolean studentPermission) {
        this.studentPermission = studentPermission;
    }


    // view submissions
    /*public void viewSubmissions(Course course, String quizName) {
        Quiz[] quizzes = course.getQuizzes();
        Quiz quiz = findQuiz(quizzes, quizName);
    } */
    @Override
    public String toString() {
        String a = "";
        a = a + firstName + " " + lastName + " Teacher\n" + userName + "\n" + password;
        return a;
    } // based on UserLibrary template

}