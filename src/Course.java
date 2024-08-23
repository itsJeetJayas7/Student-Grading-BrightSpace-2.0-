import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Course class for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class Course implements Serializable {
    // "Teachers will be able to create as many courses as they like."
    private String courseName;
    private ArrayList<Quiz> quizzes = new ArrayList<>();
    private ArrayList<QuizAnswers> quizAnswers = new ArrayList<>(quizzes.size());
    int p = 0;

    /**
     * To make the program easier... a file will be there containing only the coarse names which
     * will automatically get edited whenever a new Course is created
     * Whenever a new coarse will be created...a new file ".txt" will created under its name consisting
     * all the quizzes infos
     */

    public Course() {
    }

    /**
     * When you have a new Course name and quiz array and print it on the coursename.txt
     */

    public Course(String courseName, ArrayList<Quiz> quizzes, ArrayList<QuizAnswers> quizAnswers) {
        this.courseName = courseName;
        this.quizzes = quizzes;
        this.quizAnswers = quizAnswers;

    }

    /**
     * When a new course is created and the quizzes later
     */
    public Course(String courseName) {
        this.courseName = courseName;
//        try {
//            File f = new File("CourseNames.txt");
//            PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
//            pw.println(this.courseName);
//            pw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        courseWriter();
    }

    public void changeThisCourseName(String newName) {
        this.courseName = newName;
    }

    public String getCourseName() {
        return courseName;
    }

    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    /**
     * This will change the quiz array, *IT WILL NOT ADD* and print it on the coursename.txt
     */
    public void setQuizzes(ArrayList<Quiz> quizzes) {
        this.quizzes = quizzes;
        courseWriter();
    }

    /**
     * if a file is provided with my specific format...it will sort the quiz out of the file and print
     * it on the coursename.txt
     */
    public void setQuizzes(String filename) {
        try {
            File f = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            line = br.readLine();
            while (line != null) {
                line = line.substring(1, line.length() - 1);
                String[] quizInfo = line.split(",");
                quizInfo[3] = quizInfo[3].substring(1, quizInfo[3].length() - 1);
                String[] questionInfo = quizInfo[3].split("#");
                Question[] questions1 = new Question[questionInfo.length];
                for (int i = 0; i < questionInfo.length; i++) {
                    questionInfo[i] = questionInfo[i].substring(1, questionInfo[i].length() - 1);
                    String[] questionSetter = questionInfo[i].split("-");
                    questionSetter[2] = questionSetter[2].substring(1, questionSetter[2].length() - 1);
                    String[] questionOptions = questionSetter[2].split("~");
                    questions1[i] = new Question(questionSetter[0], questionSetter[1], questionOptions);
                }
                ArrayList<Question> questions = new ArrayList<>(Arrays.asList(questions1));
                Quiz quiz = new Quiz(quizInfo[0], questions, Boolean.parseBoolean(quizInfo[1]),
                        Integer.parseInt(quizInfo[2]));
                addQuiz(quiz);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If you want to add a quiz... it will edit the respective file
     */
    public void addQuiz(Quiz quiz) {
        /*Quiz[] quizzes = new Quiz[this.quizzes.length + 1];
        for (int i = 0; i < quizzes.length; i++) {
            quizzes[i] = this.quizzes[i];
        }
        quizzes[this.quizzes.length] = quiz;
        this.quizzes = quizzes;*/
        quizzes.add(quiz);
        courseWriter();
    }

    /**
     * This method is only for writing in the coarse file, *DO NOT USE IT*...I will make it private later...
     */
    public void courseWriter() {
        try {
            String filename = this.courseName + ".txt";
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }

            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            if (quizzes != null) {
                for (Quiz quiz : this.quizzes) {
                    pw.println(quiz.toString());
                }
            }
            pw.println("$");
            if (quizAnswers != null) {
                for (int i = 0; i < this.quizAnswers.size(); i++) {
                    if (quizAnswers.get(i) != null) {
                        String toPrint = "/" + (i + 1) + quizAnswers.get(i).fileString();
                        pw.println(toPrint);
                    } else {
                        String toPrint = "/" + (i + 1);
                        pw.println(toPrint);
                    }
                }
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCourseName(String name) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File f = new File("CourseNames.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                arrayList.add(line);
            }
            br.close();
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).equals(this.courseName)) {
                    pw.println(name);
                    continue;
                }
                pw.println(arrayList.get(i));
            }
            pw.flush();
            pw.close();
            File f2 = new File(this.courseName + ".txt");
            f2.renameTo(new File(name + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //For the use of findcourse method
    public String getCourseName(int i) {
        try {
            File f = new File("CourseNames.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = "";
            for (int j = 0; j < i; j++) {
                line = br.readLine();
            }
            br.close();
            return line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //finds and returns course through its index
    public Course findCourse(int k) {
        String name = getCourseName(k);
        try {
            File f = new File(name + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<Quiz> quizzes2 = new ArrayList<>();
            ArrayList<QuizAnswers> quizAnswers2 = new ArrayList<>();
            String line;
            int lineCounter = 0;
            line = br.readLine();
            while (line != null && !line.equals("$")) {
                line = line.substring(1, line.length() - 1);
                String[] quizInfo = line.split(",");
                quizInfo[3] = quizInfo[3].substring(1, quizInfo[3].length() - 1);
                String[] questionInfo = quizInfo[3].split("#");
                Question[] questions1 = new Question[questionInfo.length];
                for (int i = 0; i < questionInfo.length; i++) {
                    questionInfo[i] = questionInfo[i].substring(1, questionInfo[i].length() - 1);
                    String[] questionSetter = questionInfo[i].split("-");
                    questionSetter[2] = questionSetter[2].substring(1, questionSetter[2].length() - 1);
                    String[] questionOptions = questionSetter[2].split("~");
                    questions1[i] = new Question(questionSetter[0], questionSetter[1], questionOptions);
                }
//                ArrayList<Question> questions = new ArrayList<>(Arrays.asList(questions1));
                Quiz quiz = new Quiz(quizInfo[0], new ArrayList<>(Arrays.asList(questions1)), Boolean
                        .parseBoolean(quizInfo[1]),
                        Integer.parseInt(quizInfo[2]));
                quizzes2.add(quiz);
                line = br.readLine();
                lineCounter++;
            }
            line = br.readLine();
            while ((line) != null) {
                if (line.length() < 3) {
                    continue;
                }

                line = line.substring(2);
                String[] studentInfo = line.split("#");
                QuizAnswers qA = new QuizAnswers();
                for (int i = 0; i < studentInfo.length; i++) {
                    studentInfo[i] = studentInfo[i].substring(1, studentInfo[i].length() - 1);
                    String[] studentData = studentInfo[i].split(",");
                    studentData[5] = studentData[5].substring(1, studentData[5].length() - 1);
                    String[] answers = studentData[5].split("~");
                    answers[0] = answers[0].substring(1, answers[0].length() - 1);
                    answers[1] = answers[1].substring(1, answers[1].length() - 1);
                    String[] questions = answers[0].split("-");
                    String[] answerList = answers[1].split("-");
                    ArrayList<String> quest = new ArrayList<>(Arrays.asList(questions));
                    ArrayList<String> answ = new ArrayList<>(Arrays.asList(answerList));
                    Answers ans = new Answers(quest, answ);
                    Student stud = new Student(studentData[0], studentData[1], ans,
                            Boolean.parseBoolean(studentData[2]), Double.parseDouble(studentData[3]), studentData[4]);
                    qA.addStudent(stud);
                }
                quizAnswers2.add(qA);
//                int diff = 0;
//                if (quizAnswers2.size() < quizzes.size()) {
//                    diff = quizzes.size() - quizAnswers2.size();
//                    for (int i = 0; i < diff; i++) {
//                        quizAnswers2.add(null);
//                    }
//                }
                line = br.readLine();
            }
            br.close();
            return new Course(name, quizzes2, quizAnswers2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //finds and returns quiz given quiz name and array of quizzes
    public Quiz getQuiz(int i) {
        return this.quizzes.get(i);
    }

    //View Quizzes -- print all quizzes out
    public void viewQuizzes(Course course) {
        //Course course = findCourse(courses, courseName);
        ArrayList<Quiz> allQuizzes = course.getQuizzes();
        for (int i = 0; i < allQuizzes.size(); i++) {
            System.out.println((i + 1) + ". " + allQuizzes.get(i).getQuizName());
        }
    }

    public void deleteCourse(Course course) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File f = new File("CourseNames.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                arrayList.add(line);
            }
            br.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            File f = new File("CourseNames.txt");
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).equals(course.courseName)) {
                    continue;
                }
                pw.println(arrayList.get(i));
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //delete quiz
    public void deleteQuiz(Quiz quiz) {
        quizzes.remove(quiz);
        this.courseWriter();
    }

    //Add questions
    public void addQuestions(Quiz quiz, String name, String type, String[] options) {
        //Quiz quiz = findQuiz(quizzes, quizName);
        Question newQuestion;
        if (options != null) {
            newQuestion = new Question(name, type, options);
        } else {
            newQuestion = new Question(name, type);
        }
        quiz.addQuestion(newQuestion);
    }

    public int getCourseMenu() {
        try {
            File f = new File("CourseNames.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            int i = 1;
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(i + ". " + line);
                i++;
            }
            System.out.println(i++ + ". Exit");
            System.out.println("Enter option: ");
            return i - 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean getBooleanGraded(int quiznumber, String userName) {
        for (int i = 0; i < quizAnswers.get(quiznumber).getStudents().size(); i++) {
            if (quizAnswers.get(quiznumber).getStudents().get(i).getUserName().equals(userName)) {
                return quizAnswers.get(quiznumber).getStudents().get(i).isGraded();
            }
        }
        return false;
    }

    public double getGrade(int quiznumber, String username) {
        for (int i = 0; i < quizAnswers.get(quiznumber).getStudents().size(); i++) {
            if (quizAnswers.get(quiznumber).getStudents().get(i).getUserName().equals(username)) {
                return quizAnswers.get(quiznumber).getStudents().get(i).getGrades();
            }
        }
        return 0;
    }

    public void addQuizAnswer(int quizNumber, Student student) {
        if (this.quizAnswers.size() <= quizNumber) {
            while (true) {
                this.quizAnswers.add(new QuizAnswers());
                if (quizAnswers.size() > quizNumber) {
                    break;
                }
            }
        }
        this.quizAnswers.get(quizNumber).addStudent(student);
        courseWriter();
    }

    public void singleStudentResponse(int quizNumber, int studentNumber) {
        this.quizAnswers.get(quizNumber).printOneStudentResponse(studentNumber);
    }

    public void studentResponseShowMenu(int quizNumber) {
        this.quizAnswers.get(quizNumber).printStudentResponseList();
    }

    public ArrayList<QuizAnswers> getQuizAnswers() {
        return quizAnswers;
    }
}
