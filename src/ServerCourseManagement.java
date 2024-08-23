import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerCourseManagement {

    /*
    Course Section
    * */
    private ArrayList<Course> courses = new ArrayList<>();

    public ServerCourseManagement() {
        ArrayList<String> courseNames = getCourseArraylist();
        System.out.println(courseNames.size());
        for (int j = 0; j < courseNames.size(); j++) {
            try {
                File f = new File(courseNames.get(j) + ".txt");
                BufferedReader br = new BufferedReader(new FileReader(f));
                ArrayList<Quiz> quizzes2 = new ArrayList<>();
                ArrayList<QuizAnswers> quizAnswers2 = new ArrayList<>();
                String line;
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
                }
                line = br.readLine();
                while ((line) != null) {
                    if (line.length() < 3) {
                        line = br.readLine();
                        quizAnswers2.add(new QuizAnswers());
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
                        answers[2] = answers[2].substring(1, answers[2].length() - 1);
                        String[] questions = answers[0].split("-");
                        String[] answerList = answers[1].split("-");
                        String[] gradeList = answers[2].split("-");

                        ArrayList<String> quest = new ArrayList<>(Arrays.asList(questions));
                        ArrayList<String> answ = new ArrayList<>(Arrays.asList(answerList));
                        ArrayList<Double> grades = new ArrayList<>();
                        for (int k = 0; k < gradeList.length; k++) {
                            grades.add(Double.parseDouble(gradeList[k]));
                        }
                        Answers ans = new Answers(quest, answ, grades);
                        Student stud = new Student(studentData[0], studentData[1], ans,
                                Boolean.parseBoolean(studentData[2]), Double.parseDouble(studentData[3]), studentData[4]);
                        qA.addStudent(stud);
                    }
                    quizAnswers2.add(qA);
                    line = br.readLine();
                }
                br.close();
                this.courses.add(new Course(courseNames.get(j), quizzes2, quizAnswers2));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            if (j == 0) {
//                for (int i = 0; i < this.courses.get(0).getQuizAnswers().size(); i++) {
//                    System.out.println(this.courses.get(0).getQuizAnswers().size());
//                    System.out.println(this.courses.get(0).getQuizzes().size());
//                    System.out.println(this.courses.get(0).getQuizAnswers().get(i).getStudents());
//                }
//
//            }
        }
    }

    public void addCourses(String courseName) {
        if (courseName == null || courseName.equals("")) {
            return;
        }
        Course course = new Course(courseName);
        this.courses.add(course);
        System.out.println(this.courses.get(this.courses.indexOf(course)).getCourseName());
        courseNamesWriter();
        courseWriter(this.courses.indexOf(course));
    }

    public int getTotalCourses() {
        return this.courses.size();
    }

    public ArrayList<String> getCourseMenu() {
        ArrayList<String> courseNames = new ArrayList<>();
        for (int i = 0; i < this.courses.size(); i++) {
            courseNames.add(this.courses.get(i).getCourseName());
        }
        return courseNames;
    }

    public ArrayList<String> getCourseArraylist() {
        ArrayList<String> courseNames = new ArrayList<>();
        try {
            File f = new File("CourseNames.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                courseNames.add(line);
                line = br.readLine();
            }
            return courseNames;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Course updateCourse(String courseName) {
        for (int i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(courseName)) {
                return this.courses.get(i);
            }
        }
        return null;
    }

    public Course findCourse(int k) {
        //System.out.println(this.courses.get(k).getCourseName());
        return this.courses.get(k);
    }

    public void deleteCourse(String courseName) {

        for (int i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(courseName)) {
                this.courses.remove(i);
                break;
            }
        }
        courseNamesWriter();
    }

    public void changeCourseName(String newName) {
        String[] courseNames = newName.split("~");
        int i;
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(courseNames[0])) {
                break;
            }
        }
        this.courses.get(i).changeThisCourseName(courseNames[1]);
        courseNamesWriter();
        courseWriter(i);
//        File f2 = new File(courseNames[0] + ".txt");
//        f2.renameTo(new File(courseNames[1] + ".txt"));
        //courseWriter(i);

    }



    public void addQuizThroughFile(File file, String courseName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<Quiz> quizzes2 = new ArrayList<>();
            //ArrayList<QuizAnswers> quizAnswers2 = new ArrayList<>();
            String line;
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
            }
/*
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
                line = br.readLine();
            }
*/
            int i;
            for (i = 0; i < this.courses.size(); i++) {
                if (this.courses.get(i).getCourseName().equals(courseName)) {
                    break;
                }
            }
            for (int j = 0; j < quizzes2.size(); j++) {
                this.courses.get(i).addQuiz(quizzes2.get(j));
            }
            br.close();
            courseWriter(i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addQuiz(String courseName, Quiz quiz) {
        int i;
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(courseName)) {
                break;
            }
        }
        int j;
        for (j = 0; j < this.courses.get(i).getQuizzes().size(); j++) {
//            System.out.println(this.courses.get(i).getQuizzes().get(j).getQuizName());
//            System.out.println(quiz.getQuizName());
            if (this.courses.get(i).getQuizzes().get(j).getQuizName().equals(quiz.getQuizName())) {
                for (int k = 0; k < quiz.getQuestions().size(); k++) {
                    this.courses.get(i).getQuizzes().get(j).addQuestion(quiz.getQuestions().get(k));
                }
                System.out.println("edited quiz");
                courseWriter(i);
                return;
            }
        }
        this.courses.get(i).addQuiz(quiz);
        courseWriter(i);
    }

    public void editQuiz(String courseName, Quiz quiz) {
        int i;
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(courseName)) {
                break;
            }
        }
        int j;
        //System.out.println("Here in the edit Quiz");
        for (j = 0; j < this.courses.get(i).getQuizzes().size(); j++) {
//            System.out.println(this.courses.get(i).getQuizzes().get(j).getQuizName());
//            System.out.println(quiz.getQuizName());
            if (this.courses.get(i).getQuizzes().get(j).getQuizName().equals(quiz.getQuizName())) {
                this.courses.get(i).getQuizzes().set(j, quiz);
                System.out.println("edited quiz");
                break;
            }
        }
        courseWriter(i);
    }

    public void changeQuizName(String courseNameNQuizName, Quiz quiz) {
        String[] quizdata = courseNameNQuizName.split("~");
        int i;
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(quizdata[0])) {
                break;
            }
        }
        int j;
        //System.out.println("Here in the edit Quiz");
        for (j = 0; j < this.courses.get(i).getQuizzes().size(); j++) {
//            System.out.println(this.courses.get(i).getQuizzes().get(j).getQuizName());
//            System.out.println(quizdata[1]);
            if (this.courses.get(i).getQuizzes().get(j).getQuizName().equals(quizdata[1])) {
                this.courses.get(i).getQuizzes().set(j, quiz);
                System.out.println("edited quiz");
                break;
            }
        }
        courseWriter(i);
    }

    public Course deleteQuiz (String courseNameNQuizName) {
        String[] quizData = courseNameNQuizName.split("~");
        System.out.println(quizData[0]);
        System.out.println(quizData[1]);
        int i;
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(quizData[0])) {
                break;
            }
        }
//        ArrayList<Quiz> quizList = this.courses.get(i).getQuizzes();
//        System.out.println("quiz list name index 0" + quizList.get(0).getQuizName());
//        int j;
//        for (j = 0; j <= quizList.size(); j++) {
//            if (quizList.get(j).getQuizName().equals(quizData[1])) {
//                System.out.println(quizData[1]);
//                quizList.remove(j);
//                System.out.println(quizList.get(j).getQuizName());
//                this.courses.get(i).getQuizAnswers().remove(j);
//                break;
//            }
//        }
        int j;
        for (j = 0; j < this.courses.get(i).getQuizzes().size(); j++) {
            if (this.courses.get(i).getQuizzes().get(j).getQuizName().equals(quizData[1])) {
                this.courses.get(i).getQuizzes().remove(j);
                break;
            }
        }
        System.out.println("Here in the delete Quiz Method");

        for (int k = 0; k < this.courses.get(i).getQuizzes().size(); k++) {
            System.out.println(this.courses.get(i).getQuizzes().get(k));
        }

        courseWriter(i);
        return this.courses.get(i);
    }

    public Course updateFromFile(String courseName) {
        try {
            try {
                File f = new File(courseName + ".txt");
                BufferedReader br = new BufferedReader(new FileReader(f));
                ArrayList<Quiz> quizzes2 = new ArrayList<>();
                ArrayList<QuizAnswers> quizAnswers2 = new ArrayList<>();
                String line;
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
                }
                line = br.readLine();
                while ((line) != null) {
                    if (line.length() < 3) {
                        line = br.readLine();
                        quizAnswers2.add(new QuizAnswers());
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
                        answers[2] = answers[2].substring(1, answers[2].length() - 1);
                        String[] questions = answers[0].split("-");
                        String[] answerList = answers[1].split("-");
                        String[] gradeList = answers[2].split("-");

                        ArrayList<String> quest = new ArrayList<>(Arrays.asList(questions));
                        ArrayList<String> answ = new ArrayList<>(Arrays.asList(answerList));
                        ArrayList<Double> grades = new ArrayList<>();
                        for (int k = 0; k < gradeList.length; k++) {
                            grades.add(Double.parseDouble(gradeList[k]));
                        }
                        Answers ans = new Answers(quest, answ, grades);
                        Student stud = new Student(studentData[0], studentData[1], ans,
                                Boolean.parseBoolean(studentData[2]), Double.parseDouble(studentData[3]), studentData[4]);
                        qA.addStudent(stud);
                    }
                    quizAnswers2.add(qA);
                    line = br.readLine();
                }
                br.close();
                return new Course(courseName, quizzes2, quizAnswers2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteQuestion(String qData) {
        String[] questionData = qData.split("~");
        int i;
//        System.out.println("In the deleteQuestion Method");
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(questionData[0])) {
                break;
            }
        }
        int j;
        for (j = 0; j < this.courses.get(i).getQuizzes().size(); j++) {
            if (this.courses.get(i).getQuizzes().get(j).getQuizName().equals(questionData[1])) {
                break;
            }
        }
//        System.out.println(this.courses.get(i).getQuizzes().get(j).getQuizName());
        System.out.println(questionData[1]);
        for (int k = 0; k < this.courses.get(i).getQuizzes().get(j).getQuestions().size(); k++) {
//            System.out.println(this.courses.get(i).getQuizzes().get(j).getQuestions().get(k).getName());
//            System.out.println(questionData[2]);
            if (this.courses.get(i).getQuizzes().get(j).getQuestions().get(k).getName().equals(questionData[2])) {
                this.courses.get(i).getQuizzes().get(j).getQuestions().remove(k);
                break;
            }
        }
        courseWriter(i);
    }

    public String getStudentData(String userName) {
        try {
            File f = new File("UserLibrary.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            bfr.close();
            for (int i = 0; i < lines.size(); i += 3) {
                String checkType = lines.get(i).substring(lines.get(i).length() - 7);
                String checkUserName = lines.get(i + 1);
                String checkPW = lines.get(i + 2);
                if (checkUserName.equals(userName)) {
                    return lines.get(i).substring(0, lines.get(i).length() - 8);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAnswers(String answerdata, Answers answers) {
        String[] ansData = answerdata.split("~");
        int i;
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(ansData[0])) {
                break;
            }
        }

        int j;
        for (j = 0; j < this.courses.get(i).getQuizzes().size(); j++) {
            if (this.courses.get(i).getQuizzes().get(j).getQuizName().equals(ansData[1])) {
                break;
            }
        }
        try {
            for (int k = 0; k < this.courses.get(i).getQuizAnswers().get(j).getStudents().size(); k++) {
                if (this.courses.get(i).getQuizAnswers().get(j).getStudents().get(k).getUserName().equals(ansData[2])) {
                    System.out.println("Im here in the Check");
                    this.courses.get(i).getQuizAnswers().get(j).getStudents().set(k,new Student(getStudentData(ansData[2]), ansData[2], answers, ansData[3]));
                    courseWriter(i);
                    return;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.courses.get(i).addQuizAnswer(j, new Student(getStudentData(ansData[2]), ansData[2], answers, ansData[3]));
        courseWriter(i);
    }

    public void gradeStudents(String studentData, ArrayList<Double> grades) {
        String[] studentGradesData = studentData.split("~");
        int i;
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(studentGradesData[0])) {
                break;
            }
        }
        int j;
        for (j = 0; j < this.courses.get(i).getQuizzes().size(); j++) {
            if (this.courses.get(i).getQuizzes().get(j).getQuizName().equals(studentGradesData[1])) {
                break;
            }
        }
        System.out.println("Im here");
        System.out.println();
        System.out.println(studentGradesData[2]);
        for (int k = 0; k < this.courses.get(i).getQuizAnswers().get(j).getStudents().size(); k++) {
            System.out.println();
            System.out.println(this.courses.get(i).getQuizAnswers().get(j).getStudents().get(k).getUserName());
            System.out.println();
            if (this.courses.get(i).getQuizAnswers().get(j) != null) {
                if (this.courses.get(i).getQuizAnswers().get(j).getStudents().get(k).getUserName().equals(studentGradesData[2])) {
                    System.out.println("hey how are you");
                    this.courses.get(i).getQuizAnswers().get(j).getStudents().get(k).setGraded(true);
                    this.courses.get(i).getQuizAnswers().get(j).getStudents().get(k).getAnswers().setGrades(grades);
                    double total = 0;
                    for (int l = 0; l < grades.size(); l++) {
                        total += grades.get(l);
                    }
                    this.courses.get(i).getQuizAnswers().get(j).getStudents().get(k).setGrades(total);
                }
            }
        }
        courseWriter(i);
    }

    public boolean getBooleanStudentGraded(String courseNStudentData) {
        String[] studentGradesData = courseNStudentData.split("~");
        int i;
        for (i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getCourseName().equals(studentGradesData[0])) {
                break;
            }
        }
        int j;
        for (j = 0; j < this.courses.get(i).getQuizzes().size(); j++) {
            if (this.courses.get(i).getQuizzes().get(j).getQuizName().equals(studentGradesData[1])) {
                break;
            }
        }
        try {
            for (int k = 0; k < this.courses.get(i).getQuizAnswers().get(j).getStudents().size(); k++) {
                if (this.courses.get(i).getQuizAnswers().get(j).getStudents().get(k).getUserName().equals(studentGradesData[2])) {
                    return this.courses.get(i).getQuizAnswers().get(j).getStudents().get(k).isGraded();
                }
            }
        } catch (Exception exception) {
            return false;
        }
        return false;
    }

    public synchronized void courseNamesWriter() {
        try {
            File f = new File("CourseNames.txt");
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < this.courses.size(); i++) {
                pw.println(this.courses.get(i).getCourseName());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void courseWriter(int k) {
        try {
            Course course = this.courses.get(k);
            String filename = course.getCourseName() + ".txt";
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            if (course.getQuizzes() != null) {
                for (Quiz quiz : course.getQuizzes()) {
                    pw.println(quiz.toString());
                }
            }
            pw.println("$");
            if (course.getQuizAnswers() != null) {
                for (int i = 0; i < course.getQuizAnswers().size(); i++) {
                    if (course.getQuizAnswers().get(i) != null) {
                        String toPrint = "/" + (i + 1) + course.getQuizAnswers().get(i).fileString();
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

    /*
    User Section

    This method will return the type of the user (Either Teacher or Student as a String)
    The main method will check this string and will show the appropriate user interface
    if this method returns null, it means either the username and password doesn't exist in our database
    * */
    public String checkUser(String usernameNPassword) {
        String[] userData = usernameNPassword.split("~");
        if (userData.length < 2) {
            return null;
        }
        if (userData[1] == null || userData[1].equals("") || userData[1].indexOf(" ") != -1) {
            return null;
        }
        if (userData[0].indexOf("@student") == -1 && userData[0].indexOf("@teacher") == -1) {
            return null;
        }
        try {
            File f = new File("UserLibrary.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            bfr.close();
            for (int i = 0; i < lines.size(); i += 3) {
                String checkType = lines.get(i).substring(lines.get(i).length() - 7);
                System.out.println(checkType);
                String checkUserName = lines.get(i + 1);
                String checkPW = lines.get(i + 2);
                if (checkUserName.equals(userData[0]) && checkPW.equals(userData[1])) {
                    return checkType;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser(String userName) {
        try {
            File f = new File("UserLibrary.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            bfr.close();
            for (int i = 0;i < lines.size(); i += 3) {
                String checkType = lines.get(i).substring(lines.get(i).length() - 7);
                String checkUserName = lines.get(i + 1);
                String checkPW = lines.get(i + 2);
                if (checkUserName.equals(userName)) {
                    lines.remove(i);
                    lines.remove(i);
                    lines.remove(i);
                    break;
                }
            }
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            for (int i = 0; i < lines.size(); i++) {
                pw.println(lines.get(i));
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkExistingUserData(String userName) {
        try {
            File f = new File("UserLibrary.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            bfr.close();
            for (int i = 0; i < lines.size(); i += 3) {
                String checkType = lines.get(i).substring(lines.get(i).length() - 7);
                String checkUserName = lines.get(i + 1);
                String checkPW = lines.get(i + 2);
                if (checkUserName.equals(userName)) {
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkExistingUserData(String userName, String passWord) {
        try {
            File f = new File("UserLibrary.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            bfr.close();
            for (int i = 0; i < lines.size(); i += 3) {
                String checkType = lines.get(i).substring(lines.get(i).length() - 7);
                String checkUserName = lines.get(i + 1);
                String checkPW = lines.get(i + 2);
                if (checkUserName.equals(userName) && checkPW.equals(passWord)) {
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean userEdit(String userData) {
        String[] userInfo = userData.split("~");
        if (userInfo.length < 3) {
            return false;
        }
        if (userInfo[2] == null || userInfo[2].equals("") || userInfo[2].contains(" ")) {
            return false;
        }
        if (userInfo[1].indexOf("@student") == -1 && userInfo[1].indexOf("@teacher") == -1 || userInfo[1].contains(" ")) {
            return false;
        }
        if (checkExistingUserData(userInfo[1], userInfo[2])) {
            try {
                File f = new File("UserLibrary.txt");
                BufferedReader bfr = new BufferedReader(new FileReader(f));
                ArrayList<String> lines = new ArrayList<>();
                String line;
                while ((line = bfr.readLine()) != null) {
                    lines.add(line);
                }
                bfr.close();

                for (int i = 0; i < lines.size(); i += 3) {
                    String checkType = lines.get(i).substring(lines.get(i).length() - 7);
                    String checkUserName = lines.get(i + 1);
                    String checkPW = lines.get(i + 2);
                    if (checkUserName.equals(userInfo[0])) {
                        lines.remove(i + 1);
                        lines.add(i + 1, userInfo[1]);
                        lines.remove(i + 2);
                        lines.add(i + 2, userInfo[2]);
                        break;
                    }
                }
                PrintWriter pw = new PrintWriter(new FileOutputStream(f));
                for (int i = 0; i < lines.size(); i++) {
                    pw.println(lines.get(i));
                }
                pw.flush();
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean createUser(String userData) {
        //usename|nameusername|password
        String[] userInfo = userData.split("~");
        if (userInfo.length < 3) {
            return false;
        }
        if (userInfo[2] == null || userInfo[2].equals("")) {
            return false;
        }
        if (userInfo[1].indexOf("@student") == -1 && userInfo[1].indexOf("@teacher") == -1) {
            return false;
        }
        String type = userInfo[1].substring(userInfo[1].indexOf("@") + 1);
        if (checkExistingUserData(userInfo[1])) {
            try {
                File f = new File("UserLibrary.txt");
                PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));

                pw.println(userInfo[0] + " " + type);
                pw.println(userInfo[1]);
                pw.println(userInfo[2]);
                pw.flush();
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public synchronized String synchronizedmethod(int i, String string) {
        if (i == 1) {
            return checkUser(string);
        } else if (i == 2) {
            deleteUser(string);
            return null;
        } else if (i == 3) {
            return String.valueOf(userEdit(string));
        } else if (i == 4) {
            return String.valueOf(createUser(string));
        }
        return null;
    }
}

