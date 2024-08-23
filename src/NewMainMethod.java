import java.awt.print.Printable;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main method for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class NewMainMethod {
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

    //Returns a timestamp for taking a quiz//
    public static String getTimeStamp() {
        long retryDate = System.currentTimeMillis();
        Timestamp original = new Timestamp(retryDate);
        return original.toString();
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
                    System.out.println("Please enter a valid text input!");
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
                    System.out.println("Please input a valid number! (1-3)");
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
                            System.out.println("Please enter a valid text input!");
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
                            System.out.println("Please enter a valid text input!");
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
                    System.out.println("Please enter a valid text input!");
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
                                    System.out.println("Enter correct value.");
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
                                System.out.println("No Questions listed yet.");
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
                                System.out.println("Enter a correct value.");
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
                                            System.out.println("Please enter a valid number input! (1-3)");
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
                                            System.out.println("Please enter a valid number input! (1-" +
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
                                                System.out.println("Please enter a valid input! (1-5)");
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
                                                        System.out.println("Please enter a valid text input!");
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
                                                        System.out.println("Please enter a valid number input! " +
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
                                                        System.out.println("Please " +
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
                                                                System.out.println("Please " +
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
                                                                    System.out.println("Please enter " +
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
                                                                System.out.println("Please enter " +
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
                                                                System.out.println("Please enter " +
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
                                                        System.out.println("Please enter a v" +
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
                                                        System.out.println("Please enter a " +
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
                                                        System.out.println("Please enter a " +
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
                                                            System.out.println("Please enter " +
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
                                                        System.out.println("Enter correct value.");
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
                                            System.out.println("Enter correct value.");
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
                                            System.out.println("Enter a correct value.");
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
                                            System.out.println("Enter correct value.");
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
                                            System.out.println("Enter correct value.");
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
                                            System.out.println("No student has attempted yet");
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
                                                    System.out.println("Invalid Input!");
                                                    continue;
                                                } else {
                                                    check = true;
                                                    break;
                                                }
                                            } catch (NumberFormatException e) {
                                                System.out.println("Invalid option!");
                                                continue;
                                            } catch (InputMismatchException e) {
                                                System.out.println("Invalid option!");
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
                                                        System.out.println("Invalid Marks");
                                                        continue;
                                                    } else {
                                                        break;
                                                    }
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Invalid Marks");
                                                    s.nextLine();
                                                    continue;
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Invalid Marks");
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
                                            System.out.println("Enter a correct value.");
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
}
