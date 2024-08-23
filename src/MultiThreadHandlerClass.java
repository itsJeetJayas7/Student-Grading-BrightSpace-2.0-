import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadHandlerClass implements Runnable {
    private Socket user;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public MultiThreadHandlerClass(Socket user) {
        //System.out.println("hey");
        this.user = user;
        try {
            this.ois = new ObjectInputStream(user.getInputStream());
            this.oos = new ObjectOutputStream(user.getOutputStream());
            this.oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                //System.out.println("Waiting");
                Object obj = ois.readObject();
                //System.out.println("waiting completed");
                if (obj instanceof String) {
                    String input = (String) obj;
                    /*if (input.equals("exit")) {
                        ois.close();
                        oos.close();
                        user.close();
                        break;
                    } else*/
                    if (input.substring(0, 7).equals("addQuiz")) {
                        //addQuiz<courseName>
                        System.out.println("received request to add new quiz");
                        Quiz quiz = (Quiz) ois.readObject();
                        System.out.println("read quiz object");
                        ServerClass.sCM.addQuiz(input.substring(8, input.length() - 1), quiz);
                        System.out.println("added quiz!");
                    } else if (input.substring(0, 8).equals("editUser")) {
                        String check = ServerClass.sCM.synchronizedmethod(3, input.substring(9, input.length() - 1));
                        oos.writeObject(check);
                        oos.flush();
                    } else if (input.substring(0, 8).equals("editQuiz")) {
                        //editQuiz<courseName>
                        Quiz quiz = (Quiz) ois.readObject();
                        ServerClass.sCM.editQuiz(input.substring(9, input.length() - 1), quiz);
                    } else if (input.substring(0,9).equals("checkUser")) { //tested
                        String type = ServerClass.sCM.synchronizedmethod(1, input.substring(10, input.length() - 1));;
                        oos.writeObject(type);
                        oos.flush();
                    } else if (input.substring(0, 9).equals("addGrades")) {
                        //addGrades<courseName~quizName~userName>
                        ArrayList<Double> arrayList = (ArrayList<Double>) ois.readObject();
                        ServerClass.sCM.gradeStudents(input.substring(10, input.length() - 1), arrayList);
                    } else if (input.substring(0, 9).equals("addAnswer")) { // tested
                        /*
                        quizName,username,answer,datestamp
                        addAnswer<courseName~quizName~userName~datestamp>
                        * */
                        Answers ans = (Answers) ois.readObject();
                        ServerClass.sCM.addAnswers(input.substring(10, input.length() - 1), ans);
                    } else if (input.substring(0, 9).equals("newCourse")) { // tested
                        /*
                        newCourse<courseName>
                        * */
                        ServerClass.sCM.addCourses(input.substring(10, input.length() - 1));
                    } else if (input.substring(0,10).equals("deleteQuiz")) {
                        //deleteQuiz<courseName~quizName>
                        oos.writeObject(ServerClass.sCM.deleteQuiz(input.substring(11, input.length() - 1)));
                        oos.flush();
                    } else if (input.substring(0,10).equals("deleteUser")) { //tested
                        ServerClass.sCM.synchronizedmethod(2, input.substring(11, input.length() - 1));
                    } else if (input.substring(0, 10).equals("createUser")) { //tested
                        String check = ServerClass.sCM.synchronizedmethod(4, input.substring(11, input.length() - 1));
                        oos.writeObject(check);
                        oos.flush();
                    } else if (input.substring(0, 10).equals("updateFile")) {
                        oos.writeObject(ServerClass.sCM.updateFromFile(input.substring(11,input.length() - 1)));
                        oos.flush();
                    } else if (input.substring(0, 10).equals("findCourse")) { //tested
                        //System.out.println(input);
                        int index = Integer.parseInt(input.substring(10));
                        //System.out.println(index);
                        Course course = ServerClass.sCM.findCourse(index);
                        System.out.println(course.getCourseName() + "line 54");
                        oos.writeObject(course);
                        oos.flush();
                    } else if (input.equals("getCourseNames")) { //tested
                        ArrayList<String> courseNames = ServerClass.sCM.getCourseMenu();
                        oos.writeObject(courseNames);
                        oos.flush();
                    } else if (input.substring(0, 11).equals("addQuizFile")) { //tested
                        //addQuizFile<courseName>
                        File f = (File) ois.readObject();
                        ServerClass.sCM.addQuizThroughFile(f, input.substring(12, input.length() - 1));
                    } else if (input.equals("addQuizSubmission")) {
                        //addQuizSubmission<courseName~quizName>
                    } else if (input.substring(0, 12).equals("updateCourse")) {
                        oos.writeObject(ServerClass.sCM.updateCourse(input.substring(13, input.length() - 1)));
                        oos.flush();
                    } else if (input.substring(0, 12).equals("deleteCourse")) {
                        //deleteCourse<courseName>
                        ServerClass.sCM.deleteCourse(input.substring(13, input.length() - 1));
                    } else if (input.substring(0, 14).equals("deleteQuestion")) {
                        ServerClass.sCM.deleteQuestion(input.substring(15, input.length() - 1));
                    } else if (input.substring(0, 14).equals("changeQuizName")) {
                        Quiz quiz = (Quiz) ois.readObject();
                        ServerClass.sCM.changeQuizName(input.substring(15, input.length() - 1), quiz);
                    } else if (input.substring(0, 16).equals("changeCourseName")) { // tested
                        /*
                        changeCourseName<oldName~newName>
                        * */
                        ServerClass.sCM.changeCourseName(input.substring(17, input.length() - 1));
                    } else if (input.substring(0, 23).equals("getBooleanOfStudentQuiz")) {
                        //getBooleanOfStudentQuiz<courseName~quizName~username>
                        Boolean isGraded = ServerClass.sCM.getBooleanStudentGraded(input.substring(24, input.length() - 1));
                        oos.writeObject(isGraded);
                        oos.flush();
                    }
                } else if (obj instanceof Course) {
                    Course course = (Course) obj;
                    Object obj2 = ois.readObject();
                    if (obj2 instanceof String) {
                        String input = (String) obj2;
                        if (input.substring(0, 16).equals("changeCourseName")) {
                            //ServerClass.sCM.changeCourseName(course.getCourseName());
                        } else if (input.equals("addCourse")) {
                            //ServerClass.sCM.addCourses(course);
                        }
                    } else if (obj2 instanceof Quiz) {
                        String input = (String) ois.readObject();
                        if (input.substring(0, 7).equals("addQuiz")) {

                        } else if (input.substring(0, 7).equals("setQuiz")) {

                        }
                    }
                } else if (obj instanceof Integer) {
                    int i = (int) obj;
                    System.out.println("Received number: " + i);
                }

            }
        } catch (IOException e) {
//            e.printStackTrace();
            //System.out.println("User disconnected");
            try {
                oos.close();
                ois.close();
                user.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
