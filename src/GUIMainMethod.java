import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main method for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class GUIMainMethod extends JComponent implements Runnable {
    public static final String WELCOME_MESSAGE = "Hi, welcome to BrightSpace: Project Butterfly Effect!";
    public static final String FIRST_MENU = "1. Sign up (do not have a account?)\n2. Login in\n3. Edit " +
            "Account Info\n4. Exit";
    public static final String TEACHER_MENU = "1. Create New Courses \n" +
            "2. Edit Course \n" +
            "3. Delete Courses \n" +
            "4. View quizzes for a course \n" +
            "5. Logout";
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Quiz> quizzes = new ArrayList<>();
    private static ArrayList<Question> questions = new ArrayList<>();
    private static ArrayList<String> answers = new ArrayList<>();
    private static ArrayList<String> userLibraryList = new ArrayList<>();


    // ------ Fields for the GUIs ------ //
    GUIMainMethod main;
    JButton loginButton;
    JButton signUpButton;
    private JTextField userNameText;
    private JTextField nameText;
    private JTextField passwordText;
    private JButton signUpNextButton;
    private JButton loginNextButton;
    private JButton course;
    private JButton editAccountButton;
    private JButton deleteAccountButton;
    private JButton backButton;
    private JButton quiz;
    private JTextField response;
    private JButton nextQuestionButton;
    private JButton endQuizButton;
    private JButton continueButton;
    private JButton logoutButton;
    private JButton createCourseButton;
    private JButton editCourseButton;
    private JButton deleteCourseButton;
    private JButton viewQuizzesButton;
    private JTextField courseNameText;
    private JTextField quizFileNameText;
    private JButton exitButton;
    private JButton editNameButton;
    private JButton editQuizInfoButton;
    private JButton createNewQuizButton;
    private JButton deleteQuizButton;
    private JButton createQuizFileButton;
    private JTextField quizNameText;
    private JTextField quiznumQuestionsText;
    private JTextField questionText;
    private JCheckBox trueFalse;
    private JCheckBox multiChoice;
    private JCheckBox fillInBlank;
    private JTextField answerText;
    private JButton addAnswerButton;
    private JCheckBox randomized;
    private JTextField poolNum;
    private JTextField stuNum;
    private JTextField gradeNum;
    //JButton










    //Returns a timestamp for taking a quiz//
    public static String getTimeStamp() {
        long retryDate = System.currentTimeMillis();
        Timestamp original = new Timestamp(retryDate);
        return original.toString();
    }
    
    public static void simpleGUI(String message) {
        JOptionPane.showMessageDialog(null, message, "BrightSpace: Project Butterfly Effect",
                JOptionPane.ERROR_MESSAGE);
    }

    //Prompts the user to create a question list and returns the question list
    public static ArrayList<Question> createQuestionList(Scanner scanner) {
        ArrayList<Question> questionList = new ArrayList<>();
        int choice = 0;
        int optionChoice = 0;
        String questionName = null;
        String type = ""; //MCQ, true false, fill in blank
        int typeChoice = 0;
        List<String> optionList = new ArrayList<String>();

        do {
            boolean end = false;
            while (!end) {
                try {
                    System.out.println("Please enter the name of this quiz's new question: ");
                    questionName = scanner.nextLine();
                    end = true;
                } catch (InputMismatchException e) {
                    simpleGUI("Please enter a valid text input!");
                }
            }
            end = false;
            while (!end) {
                try {
                    System.out.println("Please choose the question type by entering it's corresponding number" +
                            "\n1. Multiple Choice\n2. True or False\n3. Fill in the Blank");
                    typeChoice = scanner.nextInt();
                    if (typeChoice < 1 || typeChoice > 3) {
                        throw new InputMismatchException();
                    }
                    end = true;
                } catch (InputMismatchException | NumberFormatException e) {
                    simpleGUI("Please input a valid number! (1-3)");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            switch (typeChoice) {
                case 1 -> type = "MCQ";
                case 2 -> type = "TrueFalse";
                case 3 -> type = "Fill";
                default -> {
                }
            }
            if (typeChoice == 1) {
                end = false;
                do {
                    end = false;
                    while (!end) {
                        try {
                            System.out.println("Please enter one of the answer choices for this MCQ question: ");
                            optionList.add(scanner.nextLine());
                            end = true;
                        } catch (InputMismatchException e) {
                            simpleGUI("Please enter a valid text input!");
                        }
                    }
                    end = false;
                    while (!end) {
                        try {
                            System.out.println("Would you like to add another answer choice?\n1. Yes\n2. No");
                            optionChoice = scanner.nextInt();
                            if (optionChoice < 1 | optionChoice > 2) {
                                throw new InputMismatchException();
                            }
                            end = true;
                        } catch (InputMismatchException e) {
                            simpleGUI("Please enter a valid text input!");
                            scanner.nextLine();
                        }
                    }
                    scanner.nextLine();
                } while (optionChoice != 2);
            }
            String[] options = new String[optionList.size()];
            options = optionList.toArray(options);
            Question question;
            if (typeChoice == 1) {
                question = new Question(questionName, type, options);
            } else {
                question = new Question(questionName, type);
            }
            questionList.add(question);
            end = false;
            while (!end) {
                try {
                    System.out.println("Would you like to add another question?\n1. Yes\n2. No");
                    choice = scanner.nextInt();
                    if (choice < 1 | choice > 2) {
                        throw new InputMismatchException();
                    }
                    end = true;
                } catch (InputMismatchException e) {
                    simpleGUI("Please enter a valid text input!");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
        } while (choice != 2);

        return questionList;
    }

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        User user = new User();
        Course cs = new Course(); //Just for utilization public methods
        System.out.println(WELCOME_MESSAGE);
        int i = 0;
        int option;
        String optionTwo;
        String username;
        String password = "";
        int booleanA = 0;
        do {
            //the main loop for handling for signouts
            do {
                System.out.println(FIRST_MENU);
                option = s.nextInt();
                s.nextLine();
            } while (option != 1 && option != 2 && option != 3 && option != 4);

            if (option == 1) {
                System.out.println("Sign up!");
                System.out.println("Enter full Name: ");
                String fullname = s.nextLine();
                boolean q = false;
                do {
                    do {
                        q = false;
                        System.out.println("Teacher EX: username@teacher \nStudent EX: username@student");
                        System.out.println("Enter username: ");
                        username = s.nextLine();
                        username = username.trim();
                        if (username.indexOf("@") != -1) {
                            if (!username.substring(username.indexOf("@") + 1).equals("teacher") &&
                                    !username.substring(username.indexOf("@") + 1).equals("student")) {
                                System.out.println("Please use correct format.");
                                q = true;
                            }
                        } else {
                            System.out.println("Please use correct format.");
                            q = true;
                        }
                    } while (q);
                    updateUserLibraryList("", "");
                    for (String x : userLibraryList) {
                        if (username.equals(x)) {
                            System.out.println("The username was already been used!");
                            booleanA = 1;
                            break;
                        } else {
                            booleanA = 0;
                        }
                    }

                    if (booleanA == 1) {
                        continue;
                    }

                    if (username.indexOf(" ") == -1) {
                        if (username.indexOf("@") != -1) {
                            if (username.substring(username.indexOf("@")).equals("@student") ||
                                    username.substring(username.indexOf("@")).equals("@teacher")) {
                                booleanA = 0;
                                break;
                            }
                            continue;
                        }
                        continue;
                    }
                } while (booleanA == 1);
                System.out.println("Enter password: ");
                password = s.nextLine();

                User newUser = new User(fullname, username, password);
                user = newUser;
                continue;
            }

            // Login && Edit account Info
            if (option == 2 || option == 3) {
                String type = null;
                String userName;
                do {
                    System.out.println("Username EX: username@student/username@teacher");
                    System.out.println("TO Exit PRESS 1");
                    System.out.println("Username :");
                    userName = s.nextLine();
                    if (userName.equals("1")) {
                        break;
                    }
                    System.out.println("Password :");
                    password = s.nextLine();
                    if (user.getUser(userName, password) != null) {
                        type = user.getUser(userName, password);
                    } else System.out.println("Incorrect username or password.");
                } while (type == null);
                //System.out.println(type);
                if (userName.equals("1")) {
                    continue;
                }
                user = new User(userName, password);

                // Account edit part
                if (option == 3) {
                    boolean qq = false;
                    System.out.println("1. Change Username\n2. Change Password\n3. Delete account");
                    System.out.println("TO Exit PRESS Any Key");
                    optionTwo = s.nextLine();
                    switch (optionTwo) {
                        case "1":
                            //List<String> allLines = Files.readAllLines(Paths.get("UserLibrary.txt"));
                            //PrintWriter pwOne = new PrintWriter(new FileOutputStream("UserLibrary.txt", false));
                            //for(String newLine : allLines){
                            //    pwOne.write(newLine + System.lineSeparator());
                            //}
                            boolean q;
                            String newUsername;
                            do {
                                q = false;
                                System.out.println("Teacher EX: username@teacher \nStudent EX: username@student");
                                System.out.println("Enter username: ");
                                newUsername = s.nextLine();
                                newUsername = newUsername.trim();
                                if (newUsername.indexOf("@") != -1) {
                                    if (!newUsername.substring(newUsername.indexOf("@") + 1).equals("teacher") &&
                                            !newUsername.substring(newUsername.indexOf("@") + 1).equals("student")) {
                                        System.out.println("Please use correct format.");
                                        q = true;
                                    }
                                } else {
                                    System.out.println("Please use correct format.");
                                    q = true;
                                }
                                if (userName.substring(userName.indexOf("@") + 1).equals(newUsername.substring(
                                        newUsername.indexOf("@") + 1))) {

                                    List<String> allLines = Files.readAllLines(Paths.get("UserLibrary.txt"));
                                    if (allLines.contains(newUsername)) {
                                        System.out.println("The username was already been used!");
                                        break;
                                    } else {
                                        updateUserLibraryList(userName, newUsername);
                                        userLibraryList.set(userLibraryList.indexOf(userName) + 1, newUsername);
                                        updateUserLibraryList(userName, newUsername);
                                        break;
                                    }

                                } else {
                                    System.out.println("Please enter a correct username!");
                                }
                            } while (q);
                            break;
                        case "2":
                            //PrintWriter pwTwo = new PrintWriter(new FileOutputStream("UserLibrary.txt",false));
                            System.out.println("Please Enter your new Password");
                            String newPassword = s.nextLine();
                            updateUserLibraryList(password, newPassword);
                            userLibraryList.set(userLibraryList.indexOf(userName) + 1, newPassword);
                            updateUserLibraryList(password, newPassword);
                            break;

                        case "3":
                            updateUserLibraryList("", "");
                            userLibraryList.remove(userLibraryList.indexOf(userName) - 1);
                            userLibraryList.remove(userLibraryList.indexOf(userName) + 1);
                            userLibraryList.remove(userName);
                            PrintWriter pwThree = new PrintWriter(new FileOutputStream("UserLibrary.txt"
                                    , false));

                            for (String x : userLibraryList) {
                                pwThree.println(x);
                            }
                            pwThree.close();
                            System.out.println("Deleting Account Successfully!");
                            qq = true;
                            break;
                        default:
                            break;

                    }
                    if (qq) break;
                }

                // student action part
                if (type.equals("student")) {
                    Student stu = new Student(user);
                    int correct;
                    int opt = 0;
                    boolean q;
                    //remember to put a logout option here
                    do {
                        q = false;
                        do {
                            q = false;
                            System.out.println("Courses:");
                            correct = cs.getCourseMenu();
                            try {
                                do {
                                    q = false;
                                    opt = s.nextInt();
                                } while (q);
                                if (opt < 1 || opt > correct) {
                                    q = true;
                                    System.out.println("Enter correct value.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Enter correct value.");
                                s.nextLine();
                                q = true;
                            }
                        } while (q);
                        if (opt == correct) break;
                        do {
                            Course c = cs.findCourse(opt);
                            //print list of quizzes in that course
                            do {
                                q = false;
                                System.out.println("Quizzes:");
                                correct = c.getQuizzes().size();
                                cs.viewQuizzes(c);
                                System.out.println(correct + 1 + ". Exit");
                                //Select a quiz
                                try {
                                    do {
                                        q = false;
                                        opt = s.nextInt();
                                    } while (q);
                                    if (opt < 1 || opt > correct + 1) {
                                        q = true;
                                        System.out.println("Enter correct value.");
                                    }
                                } catch (InputMismatchException e) {
                                    simpleGUI("Enter correct value.");
                                    s.nextLine();
                                    q = true;
                                }
                            } while (q);
                            if (opt == correct + 1) break;
                            int j = 0;
                            try {
                                boolean g = true;
                                try {
                                    for (j = 0; j < c.getQuizAnswers().get(opt - 1).getStudents().size(); j++) {
                                        if (c.getQuizAnswers().get(opt - 1).getStudents().get(j).getUserName()
                                                .equals(user.getFullName())) {

                                            System.out.println(c.getQuizAnswers().get(opt - 1).getStudents()
                                                    .get(j - 1).getUserName());
                                            break;
                                        }
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    g = false;
                                }
                                if (g) {
                                    if (c.getQuizAnswers().get(opt - 1).getStudents().get(j).isGraded()) {
                                        System.out.println("Your Grade: " + c.getQuizAnswers().get(opt - 1)
                                                .getStudents().get(j).getGrades() + " / " + c.getQuizAnswers()
                                                .get(opt - 1).getStudents().get(j).getAnswers()
                                                .getQuestion().size() + " !");

                                    }
                                } else {
                                    Quiz quiz = c.getQuiz(opt - 1);

                                    // randomization of questions for quiz and pool of questions if needed //
                                    ArrayList<Question> quiz2 = new ArrayList<>();
                                    ArrayList<Question> quiz3 = new ArrayList<>();
                                    if (quiz.getRandomQuestionsCount() == 0) {
                                        if (quiz.isRandomization()) quiz2 = quiz.getRandomOrderQuestions();
                                        else quiz2 = quiz.getQuestions();
                                    } else {
                                        quiz3 = quiz.getQuestionsFromPool(quiz.getRandomQuestionsCount(),
                                                quiz.getQuestions());
                                        Quiz quiz10 = new Quiz("name", quiz3);
                                        if (quiz.isRandomization()) quiz2 = quiz10.getRandomOrderQuestions();
                                        else quiz2 = quiz10.getQuestions();
                                    }

                                    //print every question one by one with the students giving the
                                    // response or a filepath
                                    s.nextLine();
                                    Answers answer = new Answers();
                                    for (int x = 0; x < quiz.getQuestions().size(); x++) {
                                        System.out.println(quiz.getQuestions().get(x).getName());
                                        quiz.getQuestions().get(x).printOptions();
                                        System.out.println("Give your response or enter the file path as " +
                                                "\"#filepath.txt\"");
                                        //try {
                                        answer.setQuestion(quiz.getQuestions().get(x));
//                            } catch (NullPointerException e) {
//
//                            }

                                        String answerInput = s.nextLine();
                                        if (answerInput.substring(0, 1).equalsIgnoreCase("#")) {
                                            String answerInputFile = answerFromStudentFile(answerInput);
                                            answer.setAnswer(answerInputFile);
                                        } else {
                                            answer.setAnswer(answerInput);
                                        }
                                    }
                                    // Add answers //
                                    c.addQuizAnswer(opt - 1, new Student(user.getFullName(),
                                            user.getFullName(), answer, getTimeStamp()));
                                    //stu.addAnswer(answer);
                                    System.out.println("End of quiz...");
                                }
                            } catch (IndexOutOfBoundsException e) {
                                simpleGUI("No Questions listed yet.");
                                break;
                            }
                        } while (true);


                        do {
                            q = false;
                            try {
                                q = false;
                                System.out.println("Would you like to exit?\n1. Yes\n2. No");
                                opt = s.nextInt();
                                if (opt != 1 && opt != 2) {
                                    throw new InputMismatchException();
                                }
                            } catch (InputMismatchException e) {
                                simpleGUI("Enter a correct value.");
                                s.nextLine();
                                q = true;
                            }
                        } while (q);
                        if (opt == 2) q = true;
                    } while (q);
                    System.out.println("Logged out...");
                }
                // teacher action part
                if (type.equals("teacher")) {
                    do {
                        int opt;
                        do {
                            System.out.println(TEACHER_MENU);
                            opt = s.nextInt();
                            s.nextLine();
                        } while (opt < 1 || opt > 5);
                        switch (opt) {
                            case 1:
                                //Create new course (Surya)
                                String newCourseName;
                                Course csCreate = null;
                                String createCourseCreateQuiz = "";
                                do {
                                    boolean end = false;
                                    while (!end) {
                                        try {
                                            System.out.println("What is the name of the course you would like " +
                                                    "to create?");
                                            newCourseName = s.nextLine();
                                            csCreate = new Course(newCourseName);
                                            end = true;
                                        } catch (InputMismatchException e) {
                                            System.out.println("Please enter a valid text input!");
                                        }
                                    }
                                    int optionCase1 = 0;
                                    end = false;
                                    while (!end) {
                                        try {
                                            System.out.println("Would you like to\n1. Create another course\n2. " +
                                                    "Import Quiz Set\n3. Exit");
                                            optionCase1 = s.nextInt();
                                            if (optionCase1 < 1 || optionCase1 > 3) {
                                                throw new InputMismatchException("Input error!");
                                            }
                                            end = true;
                                        } catch (InputMismatchException e) {
                                            simpleGUI("Please enter a valid number input! (1-3)");
                                            s.nextLine();
                                        }
                                    }
                                    if (optionCase1 == 3) {
                                        break;
                                    }
                                    if (optionCase1 == 2) {
                                        // IMPORT QUIZ FILE!!!!!!!!!!!!!!!!!!!!!
                                        do {
                                            // IMPORT QUIZ !!!!!!!!!!!!!!!!!!!!!!
                                            System.out.println("The format of the quiz text file should be" +
                                                    "<QuizName,Randomization-True/False,0,[<QuestionName-" +
                                                    "QuestionType-[a,b]>]>");
                                            System.out.println("Enter name of filepath in format '#filepath.txt' ");
                                            s.nextLine();
                                            String quizFilePath = s.nextLine();
                                            quizFilePath = quizFilePath.substring(1);
                                            csCreate.setQuizzes(quizFilePath);
                                            //String createCourseCreateQuiz ;
                                            do {
                                                System.out.println("Would you like to create another Quiz within " +
                                                        "this same course?" +
                                                        "\n1. Yes\n2. No");
                                                createCourseCreateQuiz = s.nextLine();
                                                if (createCourseCreateQuiz.equalsIgnoreCase("1")
                                                        || createCourseCreateQuiz
                                                        .equalsIgnoreCase("2")) {
                                                    break;
                                                }
                                            } while (true);
                                            if (createCourseCreateQuiz.equalsIgnoreCase("2")) {
                                                break;
                                            }
                                        } while (true);
                                    }
                                    if (createCourseCreateQuiz.equals("2")) {
                                        break;
                                    }
                                } while (true);
                                break;
                            //ask whether they want to import the quiz set from a file
                            //if they do call the course constructor with just the name and then call the
                            // setquizzes function witht the file name
                            //if they want to add the quiz later then leave it up that
                            //have the options to go to the main menu
                            case 2:
                                //Edit a course(Abhi) (Basically editing course info, quiz infos, questions infos)
                                //list all courses and choose the course
                                System.out.println("Courses:");
                                int courseMax = cs.getCourseMenu();
                                int choiceLeave = 0;
                                String choice = "0";
                                int courseNum = 0;
                                String courseName = null;
                                String quizName = null;
                                boolean end = false;
                                do {
                                    //System.out.println("Please enter the number of the course you would
                                    // like to edit: ");

                                    while (!end) {

                                        try {
                                            courseNum = s.nextInt();
                                            if (courseNum < 1 || courseNum > courseMax) {
                                                throw new InputMismatchException();
                                            }
                                            end = true;

                                        } catch (InputMismatchException | NumberFormatException e) {
                                            simpleGUI("Please enter a valid number input! (1-" +
                                                    (courseMax) + ")");
                                            s.nextLine();
                                        }
                                    }
                                    s.nextLine();
                                    //System.out.println("course max " + courseMax);
                                    if (courseNum == courseMax) {
                                        break;
                                    }
                                    Course course = cs.findCourse(courseNum);
                                    if (course == null) {
                                        System.out.println("Please enter a valid course name");
                                    } else {
                                        end = false;
                                        while (!end) {
                                            System.out.println("Course: " + course.getCourseName() +
                                                    "\nWhat would you like to do?\n1. Edit the name of the " +
                                                    "course\n2. " +
                                                    "Edit a specific quiz's info\n3. Add a new quiz to the " +
                                                    "course\n4. " +
                                                    "Delete Quiz\n5. Exit");
                                            try {
                                                choiceLeave = s.nextInt();
                                                if (choiceLeave < 1 || choiceLeave > 5) {
                                                    throw new InputMismatchException();
                                                }
                                                end = true;
                                            } catch (InputMismatchException | NumberFormatException e) {
                                                simpleGUI("Please enter a valid input! (1-5)");
                                                s.nextLine();
                                            }
                                        }
                                        s.nextLine();
                                        switch (choiceLeave) {
                                            case 1 -> {
                                                //option to edit the name of the course
                                                end = false;
                                                while (!end) {
                                                    try {
                                                        System.out.println("Please enter the new name of " +
                                                                "the course: ");
                                                        courseName = s.nextLine();
                                                        end = true;
                                                    } catch (InputMismatchException e) {
                                                        simpleGUI("Please enter a valid text input!");
                                                    }
                                                }
                                                course.setCourseName(courseName);
                                                //courses.add(course);
                                            }
                                            case 2 -> {
                                                //option to edit the quiz
                                                System.out.println("Quizzes:");
                                                ArrayList<Quiz> courseQuizzes = course.getQuizzes();
                                                end = false;
                                                while (!end) {
                                                    try {
                                                        if (course.getQuizzes().size() == 0) {
                                                            System.out.println("This course has no quizzes.");
                                                            break;
                                                        }
                                                        System.out.println("What is the number of the quiz " +
                                                                "you want to edit?");
                                                        quizName = s.nextLine();
                                                        if (Integer.parseInt(quizName) < 1 ||
                                                                Integer.parseInt(quizName) > courseQuizzes.size()) {
                                                            throw new InputMismatchException();
                                                        }
                                                        end = true;
                                                    } catch (InputMismatchException e) {
                                                        simpleGUI("Please enter a valid number input! " +
                                                                "(1-" + courseQuizzes.size() + ")");
                                                    }
                                                }
                                                if (course.getQuizzes().size() == 0) {
                                                    break;
                                                }
                                                Quiz quiz = course.getQuiz(Integer.parseInt(quizName));
                                                end = false;
                                                while (!end) {
                                                    try {
                                                        System.out.println("What element of of the quiz do " +
                                                                "you want to edit?\n1. " +
                                                                "Quiz Name\n2. Question list\n3. " +
                                                                "Randomization\n4. " +
                                                                "Number of Random Questions\n5. Go Back");
                                                        choice = s.nextLine();
                                                        if (Integer.parseInt(choice) < 1 ||
                                                                Integer.parseInt(choice) > 5) {
                                                            throw new InputMismatchException();
                                                        }
                                                        end = true;
                                                    } catch (InputMismatchException e) {
                                                        simpleGUI("Please " +
                                                                "enter a valid number input! (1-5)");
                                                    }
                                                }
                                                switch (choice) {
                                                    case "1" -> {
                                                        //editing the name of the quiz
                                                        end = false;
                                                        while (!end) {
                                                            try {
                                                                System.out.println("Please enter the new " +
                                                                        "name of the quiz: ");
                                                                quizName = s.nextLine();
                                                                end = true;
                                                            } catch (InputMismatchException e) {
                                                                System.out.println("Please enter a valid " +
                                                                        "text input!");
                                                            }
                                                        }
                                                        quiz.setQuizName(quizName);
                                                        course.courseWriter();
                                                    }
                                                    case "2" -> {
                                                        //editing the question list of the quiz

                                                        end = false;
                                                        while (!end) {
                                                            try {
                                                                System.out.println("What would you like to " +
                                                                        "do to the question list?\n1. " +
                                                                        "Add new questions\n2. Delete a " +
                                                                        "question\n3. " +
                                                                        "Go back");
                                                                choice = "0";
                                                                choice = s.nextLine();
                                                                if (Integer.parseInt(choice) < 1 ||
                                                                        Integer.parseInt(choice) > 3) {
                                                                    throw new InputMismatchException();
                                                                }
                                                                end = true;
                                                            } catch (InputMismatchException e) {
                                                                simpleGUI("Please " +
                                                                        "enter a valid number " +
                                                                        "input! (1-3)");
                                                            }
                                                        }
                                                        if (choice.equals("1")) {
                                                            //add new questions
                                                            ArrayList<Question> questionList = new ArrayList<>();
                                                            questionList = createQuestionList(s);
                                                            ArrayList<Question> actualList = quiz.getQuestions();
                                                            actualList.addAll(questionList);
                                                            quiz.setQuestions(actualList);
                                                            course.courseWriter();
                                                        } else if (choice.equals("2")) {
                                                            //delete question
                                                            String deleteChoice = "";
                                                            end = false;
                                                            while (!end) {
                                                                try {
                                                                    System.out.println("Please enter " +
                                                                            "the question " +
                                                                            "number which you " +
                                                                            "would like to delete");
                                                                    for (int x = 0; x < quiz
                                                                            .getQuestions().size();
                                                                         x++) {
                                                                        System.out.println((x + 1) + ". " +
                                                                                quiz.getQuestions()
                                                                                        .get(x).getName());
                                                                    }
                                                                    deleteChoice = s.nextLine();
                                                                    if (Integer.parseInt(deleteChoice) < 1 ||
                                                                            Integer.parseInt(deleteChoice) >
                                                                                    quiz.getQuestions().size()) {
                                                                        throw new InputMismatchException();
                                                                    }
                                                                    end = true;
                                                                } catch (InputMismatchException e) {
                                                                    simpleGUI("Please enter " +
                                                                            "a valid number " +
                                                                            "input! (1-" +
                                                                            quiz.getQuestions().size() + ")");
                                                                }
                                                            }
                                                            quiz.deleteQuestion(Integer.parseInt(deleteChoice)
                                                                    - 1, course);
                                                            course.courseWriter();
                                                        }
                                                    }
                                                    case "3" -> {
                                                        //editing the randomization of the quiz
                                                        String input = "";
                                                        boolean random = false;
                                                        end = false;
                                                        while (!end) {
                                                            try {
                                                                System.out.println("Do you want the order of " +
                                                                        "quiz question and answer " +
                                                                        "option output to be " +
                                                                        "randomized?\n1. Yes\n2. No");
                                                                input = s.nextLine();
                                                                if (Integer.parseInt(input) < 1 ||
                                                                        Integer.parseInt(input) > 2) {
                                                                    throw new InputMismatchException();
                                                                }
                                                                end = true;
                                                            } catch (InputMismatchException e) {
                                                                simpleGUI("Please enter " +
                                                                        "a valid input! (1-" +
                                                                        "2)");
                                                            }
                                                        }
                                                        if (input.equals("1")) {
                                                            random = true;
                                                        }
                                                        quiz.setRandomization(random);
                                                        course.courseWriter();
                                                    }
                                                    case "4" -> {
                                                        //editing the amount of random questions
                                                        int randomQuestionsCount = 0;
                                                        end = false;
                                                        while (!end) {
                                                            try {
                                                                System.out.println("Please enter the number " +
                                                                        "of random questions you would " +
                                                                        "like per attempt of this quiz: ");
                                                                randomQuestionsCount = s.nextInt();
                                                                end = true;
                                                            } catch (InputMismatchException e) {
                                                                simpleGUI("Please enter " +
                                                                        "a valid number " +
                                                                        "input! (1-" + quiz
                                                                        .getQuestions().size() +
                                                                        ")");
                                                                s.nextLine();
                                                            }
                                                        }
                                                        s.nextLine();
                                                        quiz.setRandomQuestionsCount(randomQuestionsCount);
                                                        course.courseWriter();
                                                    }
                                                }

                                                break;
                                            }
                                            case 3 -> {
                                                //adding new quiz to course
                                                int randomChoice = 0;
                                                boolean randomization = false;
                                                Quiz quiz;
                                                end = false;
                                                while (!end) {
                                                    try {
                                                        System.out.println("Please enter the " +
                                                                "name of the new quiz: ");
                                                        //quiz name
                                                        quizName = s.nextLine();
                                                        end = true;
                                                    } catch (InputMismatchException e) {
                                                        simpleGUI("Please enter a v" +
                                                                "alid text input!");
                                                    }
                                                }
                                                //question list
                                                ArrayList<Question> questionList = createQuestionList(s);
                                                //String date
                                                end = false;
                                                while (!end) {
                                                    try {
                                                        System.out.println("Would you like to " +
                                                                "randomize the order " +
                                                                "of questions in this " +
                                                                "quiz?\n1. Yes\n2. No");
                                                        randomChoice = s.nextInt();
                                                        if (randomChoice < 1 || randomChoice > 2) {
                                                            throw new InputMismatchException();
                                                        }
                                                        end = true;
                                                    } catch (InputMismatchException | NumberFormatException e) {
                                                        simpleGUI("Please enter a " +
                                                                "valid input! (1-2)");
                                                        s.nextLine();
                                                    }
                                                }
                                                s.nextLine();
                                                //randomization boolean
                                                if (randomChoice == 1) {
                                                    randomization = true;
                                                }
                                                end = false;
                                                while (!end) {
                                                    try {
                                                        System.out.println("Would you like to " +
                                                                "have a random amount " +
                                                                "of questions chosen " +
                                                                "from the question list?\n1. Yes\n2. No");
                                                        randomChoice = s.nextInt();
                                                        end = true;
                                                    } catch (InputMismatchException | NumberFormatException e) {
                                                        simpleGUI("Please enter a " +
                                                                "valid input! (1-2)");
                                                        s.nextLine();
                                                    }
                                                }
                                                int randomQuestionsCount = 0;
                                                if (randomChoice == 1) {
                                                    end = false;
                                                    while (!end) {
                                                        try {
                                                            System.out.println("Please enter " +
                                                                    "the number of random" +
                                                                    " questions you would " +
                                                                    "like per attempt of this quiz: ");
                                                            randomQuestionsCount = s.nextInt();
                                                            end = true;
                                                            if (randomQuestionsCount < 1) {
                                                                throw new NumberFormatException();
                                                            }
                                                        } catch (InputMismatchException | NumberFormatException e) {
                                                            simpleGUI("Please enter " +
                                                                    "a valid number g" +
                                                                    "reater than 0!");
                                                            s.nextLine();
                                                        }
                                                    }
                                                    s.nextLine();
                                                    quiz = new Quiz(quizName, questionList, randomization,
                                                            randomQuestionsCount);
                                                }
                                                quiz = new Quiz(quizName, questionList, randomization,
                                                        0);
                                                course.addQuiz(quiz);
                                                course.courseWriter();
                                                break;
                                            }
                                            case 4 -> {
                                                //delete quiz from course
                                                //print entire list of quizzes
                                                boolean q;
                                                int correct;
                                                int opt2 = 0;
                                                do {
                                                    q = false;
                                                    System.out.println("Quizzes:");
                                                    correct = course.getQuizzes().size();
                                                    cs.viewQuizzes(course);
                                                    System.out.println(correct + 1 + ". Exit");
                                                    //Select a quiz
                                                    try {
                                                        do {
                                                            q = false;
                                                            opt2 = s.nextInt();
                                                        } while (q);
                                                        if (opt2 < 1 || opt2 > correct + 1) {
                                                            q = true;
                                                            System.out.println("Enter correct value.");
                                                        }
                                                        if (opt2 == correct) break;
                                                    } catch (InputMismatchException e) {
                                                        simpleGUI("Enter correct value.");
                                                        s.nextLine();
                                                        q = true;
                                                    }
                                                } while (q);
                                                if (opt2 == correct + 1) break;
                                                Quiz quiz = course.getQuiz(opt2);
                                                course.deleteQuiz(quiz);
                                                course.courseWriter();
                                            }
                                            case 5 -> {
                                                end = true;
                                            }
                                        }
                                    }
                                    if (course.getQuizzes().size() == 0) {
                                        break;
                                    }
                                } while (!end);
                                //or edit the quizzes
                                //if they selec to edit quizzed then list all quiz names
                                //edit the quiz name,questions,if they want it random or not,how many
                                // questions in random, option of the questions, delete quizzes or questions...
                                //have the option to go to the main menu...
                                break;
                            case 3:
                                //delete course
                                //list courses..
                                boolean q;
                                int correct;
                                int opt2 = 0;
                                do {
                                    q = false;
                                    do {
                                        q = false;
                                        System.out.println("Courses:");
                                        correct = cs.getCourseMenu();
                                        try {
                                            do {
                                                q = false;
                                                opt2 = s.nextInt();
                                            } while (q);
                                            if (opt2 < 1 || opt2 > correct) {
                                                q = true;
                                                System.out.println("Enter correct value.");
                                            }
                                        } catch (InputMismatchException e) {
                                            simpleGUI("Enter correct value.");
                                            s.nextLine();
                                            q = true;
                                        }
                                    } while (q);
                                    if (opt2 == correct) break;
                                    Course c = cs.findCourse(opt2);
                                    //Simply delete entire course
                                    cs.deleteCourse(c);
                                    do {
                                        q = false;
                                        try {
                                            q = false;
                                            System.out.println("Would you like to exit?\n1. Yes\n2. No");
                                            opt2 = s.nextInt();
                                            if (opt2 != 1 && opt2 != 2) {
                                                throw new InputMismatchException();
                                            }
                                        } catch (InputMismatchException e) {
                                            simpleGUI("Enter a correct value.");
                                            s.nextLine();
                                            q = true;
                                        }
                                    } while (q);
                                    if (opt2 == 2) q = true;
                                } while (q);
                                //have the option to go to the main menu
                                break;
                            case 4:
                                //select course
                                opt2 = 0;
                                do {
                                    do {
                                        q = false;
                                        System.out.println("Courses:");
                                        correct = cs.getCourseMenu();
                                        try {
                                            do {
                                                q = false;
                                                opt2 = s.nextInt();
                                            } while (q);
                                            if (opt2 < 1 || opt2 > correct) {
                                                q = true;
                                                System.out.println("Enter correct value.");
                                            }
                                        } catch (InputMismatchException e) {
                                            simpleGUI("Enter correct value.");
                                            s.nextLine();
                                            q = true;
                                        }
                                    } while (q);
                                    if (opt2 == correct) break;
                                    Course c = cs.findCourse(opt2);
                                    //listing quizzes...below
                                    //print entire list of quizzes
                                    do {
                                        q = false;
                                        System.out.println("Quizzes:");
                                        correct = c.getQuizzes().size();
                                        System.out.println(c.getQuizAnswers().get(0).getStudents().
                                                get(3).getStudentName());
                                        cs.viewQuizzes(c);
                                        System.out.println(correct + 1 + ". Exit");
                                        //Select a quiz
                                        try {
                                            do {
                                                q = false;
                                                opt2 = s.nextInt();
                                            } while (q);
                                            if (opt2 < 1 || opt2 > correct + 1) {
                                                q = true;
                                                System.out.println("Enter correct value.");
                                            }
                                        } catch (InputMismatchException e) {
                                            simpleGUI("Enter correct value.");
                                            s.nextLine();
                                            q = true;
                                        }
                                    } while (q);
                                    if (opt2 == correct + 1) break;
                                    Quiz quiz = c.getQuiz(opt2 - 1);
                                    //in the quiz chosen, print entire list of the student responses,
                                    // Layout"1. Name : studentName, Graded : Y/N (if graded then Y if not
                                    // then N), Date: date (datestamp : date submitted)

                                    //Answers a = new Answers();
                                    do {
                                        try {
                                            for (int j = 0; j < c.getQuizAnswers().get(opt2 - 1)
                                                    .getStudents().size();
                                                 j++) {
                                                System.out.println((j + 1) + ". " + c.getQuizAnswers()
                                                        .get(opt2 - 1).
                                                        getStudents().get(j).getStudentName()
                                                        + " | Graded: " + c.getQuizAnswers().get(opt2 - 1).
                                                        getStudents().get(j).isGraded());
                                                if (j == c.getQuizAnswers().get(opt2 - 1).getStudents()
                                                        .size() - 1) {
                                                    System.out.println(j + 2 + ". Exit");
                                                }
                                            }
//                                        System.out.println();
//                                        c.studentResponseShowMenu(opt -1);
                                        } catch (IndexOutOfBoundsException e) {
                                            simpleGUI("No student has attempted yet");
                                        }
                                        boolean check = false;
                                        int opt00 = 0;
                                        do {
                                            try {
                                                System.out.println("Which student would you like to grade " +
                                                        "?\nEnter Option: ");
                                                opt00 = s.nextInt();
                                                if (opt00 < 1 || opt00 > c.getQuizAnswers().get(opt2 - 1).
                                                        getStudents().size() + 1) {
                                                    simpleGUI("Invalid Input!");
                                                    continue;
                                                } else {
                                                    check = true;
                                                    break;
                                                }
                                            } catch (NumberFormatException e) {
                                                simpleGUI("Invalid option!");
                                                continue;
                                            } catch (InputMismatchException e) {
                                                simpleGUI("Invalid option!");
                                                continue;
                                            }
                                        } while (true);

                                        if (opt00 == c.getQuizAnswers().get(opt2 - 1).getStudents().size() + 1) {
                                            break;
                                        }

                                        if (check) {
                                            int j = 0;
                                            for (j = 0; j < c.getQuizAnswers().get(opt2 - 1).getStudents().
                                                    get(opt00 - 1).getAnswers().getAnswer().size(); j++) {
                                                System.out.println("Question " + (j + 1) + ": " +
                                                        c.getQuizAnswers().get(opt2 - 1).getStudents()
                                                                .get(opt00 - 1).
                                                                getAnswers().getQuestion().get(j));
                                                System.out.println("Answer: " + (j + 1) + ": " + c.
                                                        getQuizAnswers().get(opt2 - 1).getStudents()
                                                        .get(opt00 - 1).
                                                        getAnswers().getAnswer().get(j));
                                            }
                                            System.out.println("Date: " + c.getQuizAnswers().get(opt2 - 1).
                                                    getStudents().get(opt00 - 1).getDate());
                                            int grade = 0;
                                            do {
                                                try {
                                                    System.out.println("Input Grade: " + "? /" + j);
                                                    grade = s.nextInt();
                                                    if (grade < 0 || grade > j) {
                                                        simpleGUI("Invalid Marks");
                                                        continue;
                                                    } else {
                                                        break;
                                                    }
                                                } catch (NumberFormatException e) {
                                                    simpleGUI("Invalid Marks");
                                                    s.nextLine();
                                                    continue;
                                                } catch (InputMismatchException e) {
                                                    simpleGUI("Invalid Marks");
                                                    s.nextLine();
                                                    continue;
                                                }
                                            } while (true);
                                            s.nextLine();

                                            c.getQuizAnswers().get(opt2 - 1).getStudents().get(opt00 - 1).
                                                    setGraded(true);
                                            c.getQuizAnswers().get(opt2 - 1).getStudents().get(opt00 - 1).
                                                    setGrades(grade);
                                            c.courseWriter();
                                        }
                                    } while (true);


                                    //list exit options in each menus...
                                    do {
                                        q = false;
                                        try {
                                            System.out.println("Would you like to exit?\n1. Yes\n2. No");
                                            opt2 = s.nextInt();
                                            if (opt2 != 1 && opt2 != 2) {
                                                throw new InputMismatchException();
                                            }
                                        } catch (InputMismatchException e) {
                                            simpleGUI("Enter a correct value.");
                                            s.nextLine();
                                            q = true;
                                        }
                                    } while (q);
                                    if (opt2 == 2) q = true;
                                } while (q);
                                break;
                            case 5://its just exit so do nothing...
                                break;
                        }
                        if (opt == 5) {
                            break;
                        }
                    } while (true);
                }
            }

            if (option == 4) {
                System.out.println("Thanks for using! See u next time!");
                break;
            }

        } while (option != 3);

    }

    public static void updateUserLibraryList(String o, String n) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("UserLibrary.txt"));
        userLibraryList = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            if (line.equals(o)) userLibraryList.add(n);
            else userLibraryList.add(line);
            line = bfr.readLine();
        }

        PrintWriter pwOne = new PrintWriter(new FileOutputStream("UserLibrary.txt", false));
        for (String x : userLibraryList) {
            pwOne.println(x);
        }
        pwOne.flush();
        pwOne.close();
    }


    public static String answerFromStudentFile(String filePath) {
        filePath = filePath.substring(1);
        File f = new File(filePath);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            bfr.close();
            return line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {

    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    // set up frame for welcome screen
    public void welcomeFrame(){
        JFrame welcomeFrame = new JFrame("Welcome");
        Container content = welcomeFrame.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");


        this.loginButton = new JButton("Login");
        this.loginButton.addActionListener(this.actionListener);
        this.signUpButton = new JButton("Sign Up");
        this.signUpButton.addActionListener(this.actionListener);

        welcomeFrame.setSize(600, 400);
        welcomeFrame.setLocationRelativeTo((Component) null);
        welcomeFrame.setDefaultCloseOperation(3);
        welcomeFrame.setVisible(true);
        JPanel panel = new JPanel();
        panel.add(this.loginButton);
        panel.add(this.signUpButton);
        content.add(panel, "Center");

        // Action Listener
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUpFrame();
            }
        });
    }

    // set up frame for signUp screen
    public void signUpFrame(){
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = signUpFrame.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel signUpLabel = new JLabel("Sign Up!");
        JLabel nameLabel = new JLabel("Name:");
        this.nameText = new JTextField("First Last", 10);
        JLabel usernameLabel = new JLabel("Username:");
        this.userNameText = new JTextField("username@teacher or username@student", 15);
        JLabel passwordLabel = new JLabel("Password:");
        this.passwordText = new JTextField("", 10);
        this.signUpNextButton = new JButton("Sign Up!");
        this.signUpNextButton.addActionListener(this.actionListener);

        signUpFrame.setSize(600, 400);
        signUpFrame.setLocationRelativeTo((Component) null);
        signUpFrame.setDefaultCloseOperation(3);
        signUpFrame.setVisible(true);
        JPanel panel = new JPanel();
        panel.add(signUpLabel);
        panel.add(nameLabel);
        panel.add(this.nameText);
        panel.add(usernameLabel);
        panel.add(userNameText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(this.signUpNextButton);
        content.add(panel, "Center");

        // Action Listener
    }

    // set up frame for login screen
    public void loginFrame() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = loginFrame.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel loginLabel = new JLabel("Login");
        JLabel usernameLabel = new JLabel("Username:");
        this.userNameText = new JTextField("username@teacher or username@student", 15);
        JLabel passwordLabel = new JLabel("Password:");
        this.passwordText = new JTextField("", 10);
        this.loginNextButton = new JButton("Login");
        this.loginNextButton.addActionListener(this.actionListener);

        loginFrame.setSize(600, 400);
        loginFrame.setLocationRelativeTo((Component) null);
        loginFrame.setDefaultCloseOperation(3);
        loginFrame.setVisible(true);
        JPanel panel = new JPanel();
        panel.add(this.loginNextButton);
        content.add(panel, "Center");

        // Action Listener
    }
    
        // edit account screen

    // set up frame for editing an account POP-UP
    public void editAccountScreen() {
        JFrame createCourseScreen = new JFrame("Create a new course");
        createCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel infoText = new JLabel("Account Info");
        JLabel editAccount = new JLabel("Edit Account\nLeave Blank if you're not gonna change it\n");
        JLabel usernameLabel = new JLabel("Account Username:");
        JLabel passwordLabel = new JLabel("Account Password: ");
        JTextField userName = new JTextField("username@type");
        JTextField password = new JTextField("");
        this.exitButton = new JButton("Exit");
        this.exitButton.addActionListener(this.actionListener);

        content.setSize(600, 400);
        createCourseScreen.setLocationRelativeTo((Component) null);
        createCourseScreen.setDefaultCloseOperation(3);
        content.setVisible(true);

        // adds info for editing an account panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.add(infoText);
        panel.add(editAccount);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(courseNameText);
        panel.add(userName);
        panel.add(password);
        content.add(panel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        //adds exit button
        buttons.add(createCourseButton);
        buttons.add(exitButton);
        content.add(buttons, "Bottom Right");
        // Action Listener

    }
    
    // ------ Student Screens ----- //

    // set up frame for student main screen with courses
    public void studentMainScreen() throws IOException {
        JFrame studentMainScreen = new JFrame("Student Main Screen");
        studentMainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = studentMainScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel courseLabel = new JLabel("Courses");

        JLabel infoText = new JLabel("stu name");
        this.editAccountButton = new JButton("Edit Account");
        this.editAccountButton.addActionListener(this.actionListener);
        this.deleteAccountButton = new JButton("Delete Account");
        this.deleteAccountButton.addActionListener(this.actionListener);
        this.logoutButton = new JButton("Logout");
        this.logoutButton.addActionListener(this.actionListener);

        studentMainScreen.setSize(600, 400);
        studentMainScreen.setLocationRelativeTo((Component) null);
        studentMainScreen.setDefaultCloseOperation(3);
        studentMainScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds courses panel
        JPanel coursePanel = new JPanel();
        coursePanel.add(courseLabel);
        // adds all courses to the pane
        List<String> courses = Files.readAllLines(Paths.get("CourseNames.txt"));
        if(courses.get(0).equals("") || courses.get(0).equals(null)){
            JLabel noCourses = new JLabel("There are no courses available yet.");
            coursePanel.add(noCourses);
        }else {
            int x = courses.size();
            for (int i = 0; i < x; x++) {
                this.course = new JButton(courses.get(i));
                coursePanel.add(this.course);
            }
        }
        content.add(coursePanel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(editAccountButton);
        buttons.add(deleteAccountButton);
        buttons.add(logoutButton);
        content.add(buttons, "Bottom");

        // Action Listener
    }

    // set up frame for student quiz screen
    public void studentQuizScreen() throws IOException {
        JFrame studentMainScreen = new JFrame("Student Quizzes");
        studentMainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = studentMainScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel quizLabel = new JLabel("Course name: Quizzes");

        JLabel infoText = new JLabel("stu name");
        this.backButton = new JButton("Back");
        this.backButton.addActionListener(this.actionListener);

        studentMainScreen.setSize(600, 400);
        studentMainScreen.setLocationRelativeTo((Component) null);
        studentMainScreen.setDefaultCloseOperation(3);
        studentMainScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds quizzes panel
        JPanel quizPanel = new JPanel();
        quizPanel.add(quizLabel);
        // adds all quizzes to the pane
        /**need to do this still**/
        List<String> quizzes = Files.readAllLines(Paths.get("Course.txt"));
        if(quizzes.get(0).equals("") || quizzes.get(0).equals(null)){
            JLabel noQuizzes = new JLabel("There are no quizzes available for this course yet.");
            quizPanel.add(noQuizzes);
        }else {
            int x = quizzes.size();
            for (int i = 0; i < x; x++) {
                this.quiz = new JButton(quizzes.get(i));
                quizPanel.add(this.quiz);
            }
        }
        content.add(quizPanel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(backButton);
        content.add(buttons, "Bottom");

        // Action Listener
    }

    // set up frame for taking a quiz --->
    // num in case we want to put in question num and run for loop
    public void studentTakeQuiz(int num){
        JFrame studentTakeQuiz = new JFrame("Stduent Take Quiz");
        studentTakeQuiz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = studentTakeQuiz.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel quizNameLabel = new JLabel("Quiz Name:");

        JLabel infoText = new JLabel("stu name");
        this.backButton = new JButton("Back");
        this.backButton.addActionListener(this.actionListener);
        JLabel question = new JLabel("Question(quiz.get(i))");
        JLabel options = new JLabel("Options");
        JLabel responsePrompt = new JLabel("Enter your answer:");
        this.response = new JTextField("");
        this.nextQuestionButton = new JButton("Next Question");
        this.endQuizButton = new JButton("End Quiz");

        studentTakeQuiz.setSize(600, 400);
        studentTakeQuiz.setLocationRelativeTo((Component) null);
        studentTakeQuiz.setDefaultCloseOperation(3);
        studentTakeQuiz.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds question and options to panel
        JPanel quizPanel = new JPanel();
        quizPanel.add(quizNameLabel);
        quizPanel.add(question);
        quizPanel.add(options);
        quizPanel.add(responsePrompt);
        quizPanel.add(this.response);
        content.add(quizPanel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        if(question.equals("quiz.length")) buttons.add(endQuizButton);
        else buttons.add(nextQuestionButton);
        content.add(buttons, "Bottom");

        // Action Listener

    }

    // set up frame for Quiz completed
    public void quizComplete(){
        JFrame quizCompleted = new JFrame("Student Quiz Completed");
        quizCompleted.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = quizCompleted.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel quizNameLabel = new JLabel("Quiz Name:");
        JLabel submitted = new JLabel("Submitted!");
        JLabel infoText = new JLabel("stu name");
        this.continueButton = new JButton("Continue");
        this.continueButton.addActionListener(this.actionListener);


        quizCompleted.setSize(600, 400);
        quizCompleted.setLocationRelativeTo((Component) null);
        quizCompleted.setDefaultCloseOperation(3);
        quizCompleted.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds question and options to panel
        JPanel quizPanel = new JPanel();
        quizPanel.add(quizNameLabel);
        quizPanel.add(submitted);
        content.add(quizPanel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        content.add(buttons, "Bottom");

        // Action Listener
    }

    // ----- Teacher Section ----- //
    // adds teacher main menu screen
    public void teacherMainScreen() throws IOException {
        JFrame teacherMainScreen = new JFrame("Teacher Main Screen");
        teacherMainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = teacherMainScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel("teacher name");
        this.editAccountButton = new JButton("Edit Account");
        this.editAccountButton.addActionListener(this.actionListener);
        this.deleteAccountButton = new JButton("Delete Account");
        this.deleteAccountButton.addActionListener(this.actionListener);
        this.logoutButton = new JButton("Logout");
        this.logoutButton.addActionListener(this.actionListener);
        this.createCourseButton = new JButton("Create new course");
        this.createCourseButton.addActionListener(this.actionListener);
        this.editCourseButton = new JButton("Edit existing course");
        this.editCourseButton.addActionListener(this.actionListener);
        this.deleteCourseButton = new JButton("Delete existing course");
        this.deleteCourseButton.addActionListener(this.actionListener);
        this.viewQuizzesButton = new JButton("View quizzes for an existing course");
        this.viewQuizzesButton.addActionListener(this.actionListener);
        
        teacherMainScreen.setSize(600, 400);
        teacherMainScreen.setLocationRelativeTo((Component) null);
        teacherMainScreen.setDefaultCloseOperation(3);
        teacherMainScreen.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");
        
        // adds choices panel
        JPanel choices = new JPanel();
        choices.add(createCourseButton);
        choices.add(editCourseButton);
        choices.add(deleteCourseButton);
        choices.add(viewQuizzesButton);
        content.add(choices, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(editAccountButton);
        buttons.add(deleteAccountButton);
        buttons.add(logoutButton);
        content.add(buttons, "Bottom");

        // Action Listener
        
    }

    // set up frame for creating a course
    public void createCourseScreen(){
        JFrame createCourseScreen = new JFrame("Create a new course");
        createCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel("teacher name");
        JLabel createCourse = new JLabel("Create Course:");
        JLabel courseNameLabel = new JLabel("Course Name:");
        this.courseNameText = new JTextField("", 10);
        JLabel quizFileName = new JLabel("Quiz Set File(optinal):");
        this.quizFileNameText = new JTextField("name.txt", 10);
        JLabel quizFileInstructions = new JLabel("Instructions");
        this.createCourseButton = new JButton("Create another course");
        this.createCourseButton.addActionListener(this.actionListener);
        this.exitButton = new JButton("Exit");
        this.exitButton.addActionListener(this.actionListener);

        content.setSize(600, 400);
        createCourseScreen.setLocationRelativeTo((Component) null);
        createCourseScreen.setDefaultCloseOperation(3);
        content.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.add(createCourse);
        panel.add(courseNameLabel);
        panel.add(courseNameText);
        panel.add(quizFileName);
        panel.add(quizFileNameText);
        panel.add(quizFileInstructions);
        content.add(panel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(createCourseButton);
        buttons.add(exitButton);
        content.add(buttons, "Bottom Right");
        // Action Listener
    }

    // Teacher selects course to be edited
    public void teacherEditCourseScreen() throws IOException {
        JFrame teacherEditCourseScreen = new JFrame("Teacher Edit Course");
        teacherEditCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = teacherEditCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel courseLabel = new JLabel("Edit Courses:");

        JLabel infoText = new JLabel("teacher name");
        this.exitButton = new JButton("Exit");
        this.exitButton.addActionListener(this.actionListener);


        teacherEditCourseScreen.setSize(600, 400);
        teacherEditCourseScreen.setLocationRelativeTo((Component) null);
        teacherEditCourseScreen.setDefaultCloseOperation(3);
        teacherEditCourseScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds courses panel
        JPanel coursePanel = new JPanel();
        coursePanel.add(courseLabel);
        // adds all courses to the pane
        List<String> courses = Files.readAllLines(Paths.get("CourseNames.txt"));
        if(courses.get(0).equals("") || courses.get(0).equals(null)){
            JLabel noCourses = new JLabel("There are no courses available yet.");
            coursePanel.add(noCourses);
        }else {
            int x = courses.size();
            for (int i = 0; i < x; x++) {
                this.course = new JButton(courses.get(i));
                coursePanel.add(this.course);
            }
        }
        content.add(coursePanel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(exitButton);
        content.add(buttons, "Bottom");

        // Action Listener
    }

    // Teacher edit course section created
    public void selectEditCourseScreen() throws IOException {
        JFrame selectEditCourseScreen = new JFrame("Teacher Edit Course");
        selectEditCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = selectEditCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");
        JLabel courseLabel = new JLabel("Edit Course: course name");

        JLabel infoText = new JLabel("teacher name");
        this.exitButton = new JButton("Exit");
        this.exitButton.addActionListener(this.actionListener);
        this.editNameButton = new JButton("Edit Name");
        this.editNameButton.addActionListener(this.actionListener);
        this.editQuizInfoButton = new JButton("Edit Specific Quiz Info");
        this.editQuizInfoButton.addActionListener(this.actionListener);
        this.createQuizFileButton = new JButton("Create New Quiz by importing a file");
        this.createQuizFileButton.addActionListener(this.actionListener);
        this.createNewQuizButton = new JButton("Create New Quiz");
        this.createNewQuizButton.addActionListener(this.actionListener);
        this.deleteQuizButton = new JButton("Delete Specific Quiz");
        this.deleteQuizButton.addActionListener(this.actionListener);


        selectEditCourseScreen.setSize(600, 400);
        selectEditCourseScreen.setLocationRelativeTo((Component) null);
        selectEditCourseScreen.setDefaultCloseOperation(3);
        selectEditCourseScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds panel with buttons to edit
        JPanel editFeature = new JPanel();
        editFeature.add(courseLabel);
        editFeature.add(editNameButton);
        editFeature.add(editQuizInfoButton);
        editFeature.add(createNewQuizButton);
        editFeature.add(createQuizFileButton);
        editFeature.add(deleteQuizButton);
        content.add(editFeature, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(exitButton);
        content.add(buttons, "Bottom");

        // Action Listener
    }

    // set up frame for creating a quiz
    public void createQuizScreen(){
        JFrame createCourseScreen = new JFrame("Create a new quiz");
        createCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel("teacher name");
        JLabel heading = new JLabel("CourseName: New Quiz");
        JLabel quizNameLabel = new JLabel("Quiz Name:");
        this.quizNameText = new JTextField("", 10);
        JLabel numQuestions = new JLabel("Number of Questions on quiz?");
        this.quiznumQuestionsText = new JTextField("", 10);
        this.continueButton = new JButton("Continue");
        this.continueButton.addActionListener(this.actionListener);
        this.exitButton = new JButton("Exit");
        this.exitButton.addActionListener(this.actionListener);

        content.setSize(600, 400);
        createCourseScreen.setLocationRelativeTo((Component) null);
        createCourseScreen.setDefaultCloseOperation(3);
        content.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.add(heading);
        panel.add(quizNameLabel);
        panel.add(quizNameText);
        panel.add(numQuestions);
        panel.add(quiznumQuestionsText);
        content.add(panel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "Bottom Right");
        // Action Listener
    }

    // set up frame for creating a quiz question
    public void createQuestionScreen(){
        JFrame createQuestionScreen = new JFrame("Create a new question");
        createQuestionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createQuestionScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel("teacher name");
        JLabel heading = new JLabel("CourseName: New Quiz");
        JLabel questionLabel = new JLabel("Question?:");
        this.questionText = new JTextField("", 20);
        JLabel questionType = new JLabel("What type of question would you like, please select one");
        this.trueFalse = new JCheckBox("True or False");
        this.multiChoice = new JCheckBox("Multiple Choice");
        this.fillInBlank = new JCheckBox("Fill in the blank");
        this.continueButton = new JButton("Continue");
        this.continueButton.addActionListener(this.actionListener);
        this.exitButton = new JButton("Exit");
        this.exitButton.addActionListener(this.actionListener);

        content.setSize(600, 400);
        createQuestionScreen.setLocationRelativeTo((Component) null);
        createQuestionScreen.setDefaultCloseOperation(3);
        content.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.add(heading);
        panel.add(questionLabel);
        panel.add(quizNameText);
        panel.add(questionType);;
        panel.add(trueFalse);
        panel.add(multiChoice);
        panel.add(fillInBlank);
        content.add(panel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "Bottom Right");
        // Action Listener
    }

    // set up frame for creating question Answers
    public void createChoicesScreen(){
        JFrame createChoicesScreen = new JFrame("Create a new question");
        createChoicesScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createChoicesScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel("teacher name");
        JLabel heading = new JLabel("CourseName: New Quiz");
        JLabel question = new JLabel("## Question: ...");
        JLabel questionLabel = new JLabel("Answer #:");
        this.answerText = new JTextField("", 20);
        this.addAnswerButton = new JButton("Add Answer");
        this.addAnswerButton.addActionListener(this.actionListener);
        this.nextQuestionButton = new JButton("Next Question");
        this.nextQuestionButton.addActionListener(this.actionListener);

        content.setSize(600, 400);
        createChoicesScreen.setLocationRelativeTo((Component) null);
        createChoicesScreen.setDefaultCloseOperation(3);
        content.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.add(heading);
        panel.add(question);
        panel.add(questionLabel);
        panel.add(answerText);
        content.add(panel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(addAnswerButton);
        buttons.add(nextQuestionButton);
        content.add(buttons, "Bottom Right");
        // Action Listener
    }

    // set up frame for creating quiz as randomized/pool questions
    public void quizRandomSelectScreen(){
        JFrame createChoicesScreen = new JFrame("Quiz Randomization");
        createChoicesScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createChoicesScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel("teacher name");
        JLabel heading = new JLabel("CourseName: New Quiz");
        this.randomized = new JCheckBox("Would you like questions in a random order?");
        JLabel pool = new JLabel("Would you like to have questions selected from a pool of questions?");
        this.poolNum = new JTextField("",5);
        JLabel poolInfo = new JLabel("If no, leave the text box blank.");
        this.continueButton = new JButton("Continue");
        this.continueButton.addActionListener(this.actionListener);

        content.setSize(600, 400);
        createChoicesScreen.setLocationRelativeTo((Component) null);
        createChoicesScreen.setDefaultCloseOperation(3);
        content.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.add(heading);
        panel.add(randomized);
        panel.add(pool);
        panel.add(poolNum);
        panel.add(poolInfo);
        content.add(panel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        content.add(buttons, "Bottom Right");
        // Action Listener
    }

    // set up frame for viewing quiz grades and submissions
    public void viewQuizInfo(){
        JFrame viewQuizInfo = new JFrame("Quiz Information");
        viewQuizInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = viewQuizInfo.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel("teacher name");
        JLabel heading = new JLabel("CourseName: QuizName");
        JLabel studentsInfo = new JLabel("All Student info...");
        JLabel stuNumLabel = new JLabel("Enter the student number you would like to view.");
        this.stuNum = new JTextField("",5);
        this.exitButton = new JButton("Exit");
        this.exitButton.addActionListener(this.actionListener);
        this.continueButton = new JButton("Continue");
        this.continueButton.addActionListener(this.actionListener);

        content.setSize(600, 400);
        viewQuizInfo.setLocationRelativeTo((Component) null);
        viewQuizInfo.setDefaultCloseOperation(3);
        content.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.add(heading);
        panel.add(studentsInfo);
        panel.add(stuNumLabel);
        panel.add(stuNum);
        content.add(panel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "Bottom Right");
        // Action Listener
    }

    // set up frame for grading a student quiz
    public void gradeStudentQuiz(){
        JFrame gradeStudentQuiz = new JFrame("Grading Quiz");
        gradeStudentQuiz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = gradeStudentQuiz.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GUIMainMethod();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel("teacher name");
        JLabel heading = new JLabel("CourseName: QuizName");
        JLabel student = new JLabel("StuName");
        JLabel questionsAndAnswers = new JLabel("Questions + Answer");
        JLabel grade = new JLabel("Enter Grade:");
        this.gradeNum = new JTextField("",5);
        this.exitButton = new JButton("Exit");
        this.exitButton.addActionListener(this.actionListener);
        this.continueButton = new JButton("Continue");
        this.continueButton.addActionListener(this.actionListener);

        content.setSize(600, 400);
        gradeStudentQuiz.setLocationRelativeTo((Component) null);
        gradeStudentQuiz.setDefaultCloseOperation(3);
        content.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "Top");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.add(heading);
        panel.add(student);
        panel.add(questionsAndAnswers);
        content.add(panel, "Center");

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(grade);
        buttons.add(gradeNum);
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "Bottom Right");
        // Action Listener
    }


}
