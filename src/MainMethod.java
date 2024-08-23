//import java.io.*;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class MainMethod {
//    public static final String welcomeMessage = "Hi, welcome to bright space!";
//    public static final String firstMenu = "1. Sign up (do not have a account?)\n2. Login in";
//    public static final String teacherMainMenu = "1. Create New Courses \n" +
//            "2. Edit Course \n" +
//            "3. Delete Courses \n" +
//            "4. View Courses \n" +
//            "5. Quit ";
//
//    public static void main(String[] args) throws IOException {
//        int choiceOne = 0;
//        int choiceTwo = 0;
//        int teacherOrStudent = 0;
//        int a = 1;
//        int b = 1;
//        Scanner sc = new Scanner(System.in);
//        ArrayList<String> accountLibraryArrayList = new ArrayList<>();
//        String firstName = "";
//        String lastName = "";
//        String userName = "";
//        String password = "";
//
//        String loginUserName = null;
//        String loginPassword = null;
//        int teacherOrStudentChoiceOne = 0;
//
//        String userIdentity = "";
//
//        BufferedReader libraryReader = new BufferedReader(new FileReader("UserLibrary.txt"));
//        PrintWriter libraryWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("UserLibrary.txt", true)));
//
//        System.out.println(welcomeMessage);
//
//        while (true) {
//            System.out.println(firstMenu);
//            choiceOne = sc.nextInt();
//
//            if (choiceOne == 2 || choiceOne == 1) {
//                break;
//            } else {
//                System.out.println("this is invalid choose, please select 1 or 2!");
//            }
//        }
//
//        switch (choiceOne) {
//            case 1:
//                // make a new account for new user
//                while (true) {
//                    System.out.println("are you student or teacher?(Only input 1 or 2)\n1. Teacher\n2. Student");
//                    teacherOrStudent = sc.nextInt();
//                    sc.nextLine();
//                    if (teacherOrStudent == 1 || teacherOrStudent == 2) {
//                        break;
//                    } else {
//                        System.out.println("this is invalid choose, please select 1 or 2!");
//                    }
//                }
//
//                while (a != 0) {
//                    System.out.println("Please enter your firstName");
//                    firstName = sc.nextLine();
//                    System.out.println("Please enter your lastName");
//                    lastName = sc.nextLine();
//                    System.out.println("Please enter your Username");
//                    userName = sc.nextLine();
//                    System.out.println("Please enter your password");
//                    password = sc.nextLine();
//
//                    String line = libraryReader.readLine();
//                    while (line != null) {
//                        accountLibraryArrayList.add(line);
//                        line = libraryReader.readLine();
//                    }
//
//                    if (accountLibraryArrayList.size() == 0) {
//                        break;
//                    }
//
//                    for (int i = 0; i < accountLibraryArrayList.size(); i += 3) {
//                        if (accountLibraryArrayList.get(i + 1).equals(userName)) {
//                            System.out.println("Username already been used by teacher or student!\n please switch one!");
//                            break;
//                        } else {
//                            a = 0;
//                        }
//                    }
//                    accountLibraryArrayList = null;
//                }
//
//                if (teacherOrStudent == 1) {
//                    // teacher making account
//                    Teacher ta = new Teacher(firstName, lastName, userName, password);
//                    libraryWriter.write(ta.toString());
//                    libraryWriter.flush();
//                    System.out.println("Success! We are already create a teacher account for you!");
//                } else {
//                    // student making account
//                    //Student su = new Student(firstName, lastName, userName, password);
//                    //libraryWriter.write(su.toString());
//                    libraryWriter.flush();
//                    System.out.println("Success! We are already create a student account for you!");
//                }
//
//                System.out.println("Do u wanna login and open the main menu?(Enter 1 or 2)\n1. Yes\n2. No");
//                int loginOrQuit = sc.nextInt();
//                if (loginOrQuit == 2) {
//
//                    libraryReader.close();
//                    libraryWriter.close();
//                    break;
//                }
//
//            case 2:
//                // have account or just now make account, then login
//
//                sc.nextLine();
//                while (b != 0) {
//                    System.out.println("Please Enter your Username:");
//                    loginUserName = sc.nextLine();
//                    System.out.println("Please Enter your Password:");
//                    loginPassword = sc.nextLine();
//
//                    while (true) {
//                        System.out.println("Please enter your job:\n1. Teacher \n2. Student ");
//                        teacherOrStudentChoiceOne = sc.nextInt();
//                        sc.nextLine();
//
//                        if (teacherOrStudentChoiceOne == 1 || teacherOrStudentChoiceOne == 2) {
//                            break;
//                        } else {
//                            System.out.println("Please enter 1 or 2!");
//                        }
//                    }
//
//                    if (teacherOrStudentChoiceOne == 1) {
//                        // check teacher library
//                        String line = libraryReader.readLine();
//                        while (line != null) {
//                            accountLibraryArrayList.add(line);
//                            line = libraryReader.readLine();
//                        }
//
//                        for (int i = 0; i < accountLibraryArrayList.size(); i += 3) {
//                            String[] x = accountLibraryArrayList.get(i).split(" ");
//                            if (x[2].equals("Teacher")) {
//                                if (accountLibraryArrayList.get(i + 1).equals(loginUserName)) {
//                                    if (accountLibraryArrayList.get(i + 2).equals(loginPassword)) {
//                                        System.out.println("Login success!");
//                                        userIdentity = "Teacher";
//                                        b = 0;
//                                    } else {
//                                        System.out.println("Wrong Username or Password!");
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        // check student library
//                        String line = libraryReader.readLine();
//                        while (line != null) {
//                            accountLibraryArrayList.add(line);
//                            line = libraryReader.readLine();
//                        }
//
//                        for (int i = 0; i < accountLibraryArrayList.size(); i += 3) {
//                            String[] x = accountLibraryArrayList.get(i).split(" ");
//                            if (x[2].equals("Student")) {
//                                if (accountLibraryArrayList.get(i + 1).equals(loginUserName)) {
//                                    if (accountLibraryArrayList.get(i + 2).equals(loginPassword)) {
//                                        System.out.println("Login success!");
//                                        userIdentity = "Student";
//                                        b = 0;
//                                    } else {
//                                        System.out.println("Wrong Username or Password!");
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                // after login: main menu
//                if (userIdentity.equals("Teacher")) {
//                    System.out.println(teacherMainMenu);
//                    choiceTwo = sc.nextInt();
//                    sc.nextLine();
//                }
//                switch (choiceTwo) {
//                    case 1:
//                        // todo: create a new course object
//
//
//                        break;
//
//                    case 2:
//                        // todo: edit a course which already exist
//
//
//                        break;
//
//                    case 3:
//                        // todo: delete a course which already exist
//
//
//                        break;
//
//                    case 4:
//                        // todo: view courses list (only shows out name is fine)
//
//
//                        break;
//
//                    case 5:
//                        // todo: edit quiz and course from the course
//
//
//                        break;
//
//                    case 6:
//                        // todo: Quit from the system
//                        break;
//
//                    default:
//                        System.out.println("please enter a number from 1 to 6!");
//                        break;
//                }
//
//                // Quick sketch of what this could look like, give feedback if any.
//                // Also Surya you can add whatever you want
//                if (userIdentity.equals("Student")) {
//
//                    // todo: Read file with courses/quizzes/questions and have an array of them ready
//                    // Prints out the list of courses for the student to choose from
//                    System.out.println("Course menu:");
//                    /**
//                     for(int i = 0; i < courseLib.length(); i++){
//                     System.out.println(i+1 + ". " + courselib[i]);
//                     }
//                     System.out.println(courseLib.length()+1 + ". Exit");
//                     choiceTwo = sc.nextInt();
//                     if(choiceTwo = courselib.length()+1) break;
//                     Course stuCourse = new Course(courselib[choiceTwo]);
//                     do{
//                     // Prints out the list of quizzes for the student to choose from
//                     System.out.println("Quizzes Available:");
//                     for(int x = 0; x < quizLib.length; x++){
//                     System.out.println(x+1 + ". " + quizlib[x]);
//                     }
//                     System.out.println(quizLib.length()+1 + ". Exit");
//                     choiceTwo = sc.nextInt();
//                     if(choiceTwo = quizlib.length+1) break;
//                     Quiz stuQuiz = new Quiz(quizlib[choiceTwo]);
//                     System.out.println("Are you ready to take the quiz? \n1. Yes\n2. No");
//                     choiceTwo = sc.nextInt();
//                     String response;
//                     if(choiceTwo == 1){
//                     for(int x = 0; x < quesLib.length; x++){
//                     System.out.println(x+1 + ". " + queslib[x]);
//                     System.out.println("Answer: ");
//                     sc.nextLine();
//                     response = sc.nextLine();
//                     // todo: add method to save answer to an array
//                     }
//                     **/
//
//                }
//        }while(choiceTwo == 2);
//
//    }
//
//
//}
//
