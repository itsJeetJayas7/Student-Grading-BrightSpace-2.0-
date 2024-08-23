import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuiTester extends JComponent implements Runnable {
    GuiTester main;
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
    private String userName;
    private String password;
    private ArrayList<String> c;
    Socket socket = new Socket("localhost", 1427);
    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    GuiTester guiTester;
    private JLabel studentsInfo;
    private JButton submitButton;
    private JButton quizUploadButton;
    private JButton addQuestions;
    private JButton deleteQuestions;
    private ArrayList<Question> questions;
    private JTextField questNum;
    private JLabel quests1;
    ArrayList <Quiz> quizzes = new ArrayList<>();

    public GuiTester() throws IOException {
        courseGlobal = null;
    }

    public static void simpleGUI(String message) {
        JOptionPane.showMessageDialog(null, message, "BrightSpace: Project Butterfly Effect",
                JOptionPane.ERROR_MESSAGE);
    }

    public static int deleteGUI(String message) {
        int reply = JOptionPane.showConfirmDialog(null, message,
                "BrightSpace: Project Butterfly Effect", JOptionPane.YES_NO_OPTION);
        int x;
        if (reply == 0) x = 0;
        else x = 1;

        return x;
    }

    /**
     * Helper method to write to server easier
     *
     * @param object object to be sent to the server
     * @return void
     */
    private void writeToServer(Object object) throws IOException {

        this.oos.writeObject(object);
        this.oos.flush();
        // this.oos.close();
    }

    private static String getTimeStamp() {
        long retryDate = System.currentTimeMillis();
        Timestamp original = new Timestamp(retryDate);
        return original.toString();
    }

    private static int getLineNumber() {
        return new Throwable().getStackTrace()[0].getLineNumber();
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signUpNextButton) {
                //nameText.setText("");
                userNameText.setText("");
                passwordText.setText("");

            }
            if (e.getSource() == exitButton) {
                userNameText.setText("");
                passwordText.setText("");
            }
            if (e.getSource() == continueButton) {
                if (stuNum != null) {
                    stuNum.setText("");
                }
            }
        }
    };

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new GuiTester());
        //SwingWorker
    }

    // set up frame for welcome screen
    public void welcomeFrame() throws IOException {
        JFrame welcomeFrame = new JFrame("Welcome");
        Container content = welcomeFrame.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, BorderLayout.CENTER);


        this.loginButton = new JButton("Login");
        this.loginButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.loginButton.addActionListener(this.actionListener);
        this.signUpButton = new JButton("Sign Up");
        this.signUpButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.signUpButton.addActionListener(this.actionListener);
//        this.exitButton = new JButton("Exit");
//        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));

        welcomeFrame.setSize(1280, 720);
        welcomeFrame.setLocationRelativeTo((Component) null);
        welcomeFrame.setDefaultCloseOperation(3);
        welcomeFrame.setVisible(true);

        JPanel panel = new JPanel();
//        panel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.ipady = 10;  //make this component tall
//        gbc.weightx = 0.0;
//        gbc.gridheight = 3;
//
//        gbc.gridx = 1;
//        gbc.gridy = 0;
        panel.add(new JLabel(new ImageIcon("Butterfly_Image.png")));
        content.add(panel, "North");

        JPanel panel1 = new JPanel();
        panel1.add(this.loginButton);
        panel1.add(this.signUpButton);
        content.add(panel1, "South");
        // Action Listener
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welcomeFrame.dispose();
                try {
                    loginFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welcomeFrame.dispose();
                try {
                    signUpFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    // set up frame for signUp screen
    public void signUpFrame() throws IOException {
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = signUpFrame.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        JLabel signUpLabel = new JLabel("Sign Up!");
        signUpLabel.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel nameLabel = new JLabel("Name: \n (First Last)");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.nameText = new JTextField("", 10);
        JLabel usernameLabel = new JLabel("Username: \n (username@teacher or username@student)");
        usernameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.userNameText = new JTextField("", 15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.passwordText = new JTextField("", 10);
        this.signUpNextButton = new JButton("Sign Up!");
        this.signUpNextButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.signUpNextButton.addActionListener(this.actionListener);
        this.backButton = new JButton("Back");
        this.backButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.backButton.addActionListener(this.actionListener);

        signUpFrame.setSize(1280, 720);
        signUpFrame.setLocationRelativeTo((Component) null);
        signUpFrame.setDefaultCloseOperation(3);
        signUpFrame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;

        gbc.gridy = 1;
        panel.add(signUpLabel, gbc);
        gbc.gridy = 2;
        panel.add(nameLabel, gbc);
        panel.add(this.nameText, gbc);
        gbc.gridy = 3;
        panel.add(usernameLabel, gbc);
        panel.add(userNameText, gbc);
        gbc.gridy = 4;
        panel.add(passwordLabel, gbc);
        panel.add(passwordText, gbc);
        gbc.gridy = 5;
        panel.add(this.backButton, gbc);
        panel.add(this.signUpNextButton, gbc);
        content.add(panel);


        // Action Listener
        signUpNextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean legitName = false;
                //do {
                String typeSub;
                String type = userNameText.getText();
                typeSub = type.substring(type.indexOf("@") + 1);
                System.out.println(type);

                        /*if (!typeSub.equals("student") && !typeSub.equals("teacher")) {
                            simpleGUI("Please enter a username which ends in @teacher or @student!");
                            signUpFrame.dispose();
                            try {
                                signUpFrame();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            //JOptionPane.getRootFrame().dispose();
                        }*/
                try {

                    writeToServer("createUser<" + nameText.getText() + "~" + userNameText
                            .getText() + "~" +
                            passwordText.getText() + ">");
                    System.out.println("wrote to server");
                    legitName = Boolean.parseBoolean((String) ois.readObject());
                    System.out.println(legitName);
                    if (!legitName) {
                        simpleGUI("This username has already been taken or is it formatted incorrectly!\n " +
                                "Please enter a different one and end it with @teacher or @student.");
                    } else {
                        System.out.println("Right before dispose");
                        signUpFrame.dispose();
                        System.out.println("After dispose");
                        loginFrame();
                        System.out.println("loginframeopened!");
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                //} while (!legitName);

            }
        });

        this.backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUpFrame.dispose();
                try {
                    welcomeFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for login screen
    public void loginFrame() throws IOException {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = loginFrame.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Serif", Font.BOLD, 30));
        this.nameText = new JTextField("", 10);
        JLabel usernameLabel = new JLabel("Username: ends with @teacher or @student)");
        usernameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.userNameText = new JTextField("", 15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.passwordText = new JTextField("", 10);
        this.loginNextButton = new JButton("Login");
        this.loginNextButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.loginNextButton.addActionListener(this.actionListener);
        this.backButton = new JButton("Back");
        this.backButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.backButton.addActionListener(this.actionListener);

        loginFrame.setSize(1280, 720);
        loginFrame.setLocationRelativeTo((Component) null);
        loginFrame.setDefaultCloseOperation(3);
        loginFrame.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 4;
        gbc.gridy = 1;
        panel.add(loginLabel, gbc);
        gbc.gridy = 2;
        panel.add(usernameLabel, gbc);
        panel.add(this.userNameText, gbc);
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);
        panel.add(passwordText, gbc);
        gbc.gridy = 4;
        panel.add(this.backButton, gbc);
        panel.add(loginNextButton, gbc);
        content.add(panel);

        // Action Listener
        loginNextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String type = null;
                try {

                    writeToServer("checkUser<" + userNameText
                            .getText() + "~" +
                            passwordText.getText() + ">");
                    System.out.println("wrote to server");
                    type = ((String) ois.readObject());
                    System.out.println(type);
                    if (type == null || (!type.equals("student") && !type.equals("teacher"))) {
                        simpleGUI("Incorrect username or password!\nPlease try again!");
                    } else {
                        System.out.println("Right before dispose");
                        userName = userNameText.getText();
                        password = passwordText.getText();
                        loginFrame.dispose();
                        System.out.println("After dispose");


                        if (type.equals("student")) {
                            studentMainScreen();
                        } else {
                            teacherMainScreen();
                        }
                        System.out.println("login complete!");
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                try {
                    welcomeFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    //--Edit account Screen--//
    public void editAccountScreen(int user /** 0 for student, 1 for teacher **/) throws IOException {
        JFrame editAccountScreen = new JFrame("Edit account");
        editAccountScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = editAccountScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel editAccount = new JLabel("Edit Account");
        editAccount.setFont(new Font("Serif", Font.PLAIN, 30));
        JLabel infoTxt = new JLabel("Please leave blank if you're not going to change it.");
        infoTxt.setFont(new Font("Serif", Font.PLAIN, 10));
        JLabel usernameLabel = new JLabel("New Username:");
        usernameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel passwordLabel = new JLabel("New Password: ");
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.userNameText = new JTextField("username@type", 12);
        userNameText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.passwordText = new JTextField("", 12);
        passwordText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        this.submitButton = new JButton("Submit Changes");
        this.submitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.submitButton.addActionListener(this.actionListener);

        editAccountScreen.setSize(1280, 720);
        editAccountScreen.setLocationRelativeTo((Component) null);
        editAccountScreen.setDefaultCloseOperation(3);
        editAccountScreen.setVisible(true);

        // adds info for editing an account panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 0;
        gbc.gridy = 1;
        panel.add(editAccount, gbc);
        gbc.gridy = 2;
        panel.add(usernameLabel, gbc);
        panel.add(userNameText, gbc);
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);
        panel.add(passwordText, gbc);
        gbc.gridy = 4;
        panel.add(infoTxt);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        //adds exit button
        //  buttons.add(createCourseButton);
        buttons.add(submitButton);
        buttons.add(exitButton);
        content.add(buttons, "South");
        // Action Listener
        this.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editAccountScreen.dispose();
                try {
                    if (user == 0) studentMainScreen();
                    else teacherMainScreen();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    if (userNameText.getText().equals("")) {
                        writeToServer("editUser<" + userName + "~" + userName + "~" +
                                passwordText.getText() + ">");
                    } else if (passwordText.getText().equals("")) {
                        writeToServer("editUser<" + userName + "~" + userNameText.getText() + "~" +
                                password + ">");
                    } else if (userNameText.getText().equals("") && passwordText.getText().equals("")) {
                        throw new IOException();
                    } else {
                        writeToServer("editUser<" + userName + "~" + userNameText.getText() + "~" +
                                passwordText.getText() + ">");
                    }

                    boolean exists = Boolean.parseBoolean((String) ois.readObject());
                    System.out.println(exists);
                    if (!exists) {
                        simpleGUI("This username has already been taken or is it formatted incorrectly!");
                    } else {
                        System.out.println("account editing completed");
                        System.out.println("Right before dispose");
                        if (!userNameText.getText().equals("")) {
                            userName = userNameText.getText();
                        }
                        if (!passwordText.getText().equals("")) {
                            password = passwordText.getText();
                        }

                        editAccountScreen.dispose();
                        System.out.println("After dispose");
                        if (user == 0) {
                            studentMainScreen();
                        } else {
                            teacherMainScreen();
                        }
                        System.out.println("main screen opened!");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    //ex.printStackTrace();
                    simpleGUI("Please enter a valid Input");
                }

//                editAccountScreen.dispose();
//                try {
//                    if (user == 0) studentMainScreen();
//                    else teacherMainScreen();
//                } catch (IOException | ClassNotFoundException ex) {
//                    ex.printStackTrace();
//                }
            }
        });

    }
    // ------ Student Screens ----- //

    // set up frame for student main screen with courses

    Course courseGlobal;

//    public void indexMaker(int i) throws IOException, ClassNotFoundException {
//        courseGlobal = playingWithCourse(i);
//    }

    int firstTime = 0;

    public void studentMainScreen() throws IOException, ClassNotFoundException {
        JFrame studentMainScreen = new JFrame("Student Main Screen");
        studentMainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = studentMainScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel courseLabel = new JLabel("Courses");
        courseLabel.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.editAccountButton = new JButton("Edit Account");
        this.editAccountButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.editAccountButton.addActionListener(this.actionListener);
        this.deleteAccountButton = new JButton("Delete Account");
        this.deleteAccountButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.deleteAccountButton.addActionListener(this.actionListener);
        this.logoutButton = new JButton("Logout");
        this.logoutButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.logoutButton.addActionListener(this.actionListener);

        studentMainScreen.setSize(1280, 720);
        studentMainScreen.setLocationRelativeTo((Component) null);
        studentMainScreen.setDefaultCloseOperation(3);
        studentMainScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds courses panel
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        coursePanel.add(courseLabel, gbc);
        // adds all courses to the pane
        // gets info from server
        if (c.size() == 0) {
            JLabel noCourses = new JLabel("There are no courses available yet.");
            noCourses.setFont(new Font("Serif", Font.PLAIN, 15));
            coursePanel.add(noCourses);
        } else {
            int x = c.size();
            for (int j = 0; j < x; j++) {
                gbc.gridy++;
                this.course = new JButton(c.get(j));
                //System.out.println(c.get(j));
                coursePanel.add(this.course, gbc);
                // Action Listener
                this.course.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String courseName = ((JButton) e.getSource()).getText();
                            //System.out.println(c.indexOf(courseName));
                            //writeToServer("findCourse" + c.indexOf(courseName));

                            /*for (int i = 0; i < 4; i++) {
                                writeToServer(i);
                            }*/
                            writeToServer("findCourse" + c.indexOf(courseName));
                            if (firstTime == 1) {
                                ois.readObject();
                                firstTime++;
                            }
                            Course obj = (Course) ois.readObject();
                            if (obj == null) {
                                if (firstTime == 0) {
                                    firstTime++;
                                    writeToServer("findCourse" + c.indexOf(courseName));
                                    obj = (Course) ois.readObject();
                                    if (obj == null) {
                                        System.out.println("Server and client are OUT OF SYNC!!");
                                    }
                                }
                            }

                            System.out.println(obj);
                            courseGlobal = obj;

//                            main.indexMaker(c.indexOf(((JButton) e.getSource()).getText()));
//                            System.out.println(((JButton) e.getSource()).getText());
//                            courseGlobal = new Course(((JButton) e.getSource()).getText());
                            studentMainScreen.dispose();
                            studentQuizScreen();
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                if (j > 4) {
                    gbc.gridx++;
                }
            }
        }
        content.add(coursePanel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(editAccountButton);
        buttons.add(deleteAccountButton);
        buttons.add(logoutButton);
        content.add(buttons, "South");

        // Action Listeners
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentMainScreen.dispose();
                try {
                    welcomeFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.editAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentMainScreen.dispose();
                try {
                    editAccountScreen(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.deleteAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (deleteGUI("Are you sure you want to delete your account?") == 0) {
                    try {
                        writeToServer("deleteUser<" + userName + ">");
                        System.out.println("Account deleted!");
                        studentMainScreen.dispose();
                        welcomeFrame();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    // set up frame for student quiz screen
    ArrayList<Question> questionOriginal = new ArrayList<>();
    public void studentQuizScreen() throws IOException, ClassNotFoundException {
        JFrame studentQuizScreen = new JFrame("Student Quizzes");
        studentQuizScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = studentQuizScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        // quizLabel = new JLabel("Course is null????");
        // if (courseS != null) {
        JLabel quizLabel = new JLabel(courseGlobal.getCourseName() + ": Quizzes");
        // }
        quizLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.backButton = new JButton("Back");
        this.backButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.backButton.addActionListener(this.actionListener);

        studentQuizScreen.setSize(1280, 720);
        studentQuizScreen.setLocationRelativeTo((Component) null);
        studentQuizScreen.setDefaultCloseOperation(3);
        studentQuizScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds quizzes panel
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 0;
        gbc.gridy = 1;
        quizPanel.add(quizLabel, gbc);
        // adds all quizzes to the pane

        //updating the course Object
        writeToServer("findCourse" + c.indexOf(courseGlobal.getCourseName()));
        courseGlobal = (Course) ois.readObject();

        ArrayList<Quiz> q = courseGlobal.getQuizzes();
        if (q.size() == 0) {
            JLabel noQuizzes = new JLabel("There are no quizzes available for this course yet.");
            noQuizzes.setFont(new Font("Serif", Font.PLAIN, 15));
            quizPanel.add(noQuizzes);
        } else {
            int x = q.size();
            int w = 0;
            for (int i = 0; i < x; i++) {
                this.quiz = new JButton(q.get(i).getQuizName());
                gbc.gridy++;
                quizPanel.add(this.quiz, gbc);
                // Action Listener
                int finalW = i;
                this.quiz.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String quizName = ((JButton) e.getSource()).getText();
                            writeToServer("updateFile<" + courseGlobal.getCourseName() + ">");
                            Course course = null;
                            try {
                                System.out.println("Im on Line 772");
                                course = (Course) ois.readObject();
                            } catch (ClassNotFoundException ex) {
                                System.out.println("Im now in the catch block");
                                ex.printStackTrace();
                            }

                            Quiz q = course.getQuizzes().get(finalW);

                            boolean ee = false;
                            try {
                                writeToServer("getBooleanOfStudentQuiz<" + courseGlobal.getCourseName()
                                        + "~" + q.getQuizName() + "~" + userName + ">");
                                boolean graded = (boolean) ois.readObject();
                                /*if(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q))
                                        .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
                                                .indexOf(q)).getStudents().indexOf(userName)).isGraded())*/
                                System.out.println(quizName + ":" + graded + getLineNumber());
                                if (graded) {
//                                    studentViewQuizGrade(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q) + 1)
//                                                    .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
//                                                            .indexOf(q)).getStudents().indexOf(userName)).getStudentName(),
//                                            courseGlobal, q, 0, courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q) + 1)
//                                                    .getStudents().indexOf(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q) - 1)
//                                                            .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
//                                                                    .indexOf(q) + 1).getStudents().indexOf(userName))));
                                    String stringForSimpleGui = "";
                                    try {
                                        for (int j = 0; j < courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)).getStudents().size(); j++) {
                                            if (courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)).getStudents().get(j).getUserName().equals(userName)) {
                                                stringForSimpleGui += "Submission Date: " + courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)).getStudents().get(j).getDate() + "\n";
                                                for (int k = 0; k < courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)).getStudents().get(j).getAnswers().getQuestion().size(); k++) {
                                                    String questionString = courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)).getStudents().get(j).getAnswers().getQuestion().get(k);
                                                    Double doubleGrade = courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)).getStudents().get(j).getAnswers().getGrades().get(k);
                                                    stringForSimpleGui += (k + 1) + ". Question: " + questionString + " Grade: " + doubleGrade + "\n";
                                                }
                                                stringForSimpleGui += "Total Grade: " + courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)).getStudents().get(j).getGrades();
                                                break;
                                            }
                                        }
                                        simpleGUI(stringForSimpleGui);
                                    } catch (Exception ignored) {

                                    }
                                } else {
                                    studentQuizScreen.dispose();
                                    answers =  new ArrayList<>();
                                    ArrayList<Question> randomQ = new ArrayList<>();
                                    if (q.isRandomization()) {
                                        questionOriginal = q.getQuestions();
                                        randomQ = q.getRandomOrderQuestions();
                                        q.setQuestions(randomQ);
                                    }
                                    if(q.getRandomQuestionsCount() != 0) {
                                        if(q.isRandomization()) {
                                            q.setQuestions(q.getQuestionsFromPool(q.getRandomQuestionsCount(),
                                                    randomQ));
                                        } else {
                                            q.setQuestions(q.getQuestionsFromPool(q.getRandomQuestionsCount(),
                                                    questionOriginal));
                                        }
                                    }
                                    studentTakeQuiz(q, 0);
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                ee = true;
                            }
                            //studentTakeQuiz(q, 0);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }
        content.add(quizPanel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(backButton);
        content.add(buttons, "South");

        // Action Listener
        this.backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentQuizScreen.dispose();
                try {
                    studentMainScreen();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for taking a quiz --->
    // num in case we want to put in question num and run for loop
    ArrayList<String> questionsStu = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();

    public void studentTakeQuiz(Quiz quizQ, int i) throws IOException {
        JFrame studentTakeQuiz = new JFrame("Student Take Quiz");
        studentTakeQuiz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = studentTakeQuiz.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        JLabel quizNameLabel = new JLabel(quizQ.getQuizName());
        quizNameLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel question = new JLabel("Question " + (i + 1) + ": " + quizQ.getQuestions().get(i).getName());
        question.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel options = new JLabel("");
        options.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel responsePrompt = new JLabel("Enter your answer:");
        responsePrompt.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel enterFileInstructions = new JLabel("If you want to upload an answer with a file,");
        enterFileInstructions.setFont(new Font("Serif", Font.PLAIN, 13));
        JLabel enterFileInstructions2 = new JLabel("begin your answer with a # and end your answer with .txt");
        enterFileInstructions2.setFont(new Font("Serif", Font.PLAIN, 13));
        JLabel enterFileInstructions3 = new JLabel("Make sure the file is in this project directory");
        enterFileInstructions3.setFont(new Font("Serif", Font.PLAIN, 13));
        JLabel enterFileInstructions4 = new JLabel("and that it contains just your answer");
        enterFileInstructions4.setFont(new Font("Serif", Font.PLAIN, 13));
        this.response = new JTextField("", 10);
        this.nextQuestionButton = new JButton("Next Question");
        this.nextQuestionButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.endQuizButton = new JButton("End Quiz");
        this.nextQuestionButton.setFont(new Font("Serif", Font.PLAIN, 20));
        studentTakeQuiz.setSize(1280, 720);
        studentTakeQuiz.setLocationRelativeTo((Component) null);
        studentTakeQuiz.setDefaultCloseOperation(3);
        studentTakeQuiz.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds question and options to panel
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 0;
        gbc.gridy = 1;
        quizPanel.add(quizNameLabel, gbc);
        gbc.gridy = 2;
        quizPanel.add(question, gbc);
        gbc.gridy = 3;

        for (int y = 0; y < quizQ.getQuestions().get(i).getOptions().length; y++) {
            options = new JLabel(quizQ.getQuestions().get(i).getOptions()[y]);
            quizPanel.add(options, gbc);
            gbc.gridy++;
        }
        quizPanel.add(responsePrompt, gbc);
        quizPanel.add(this.response, gbc);
        gbc.gridy++;
        quizPanel.add(enterFileInstructions, gbc);
        gbc.gridy++;
        quizPanel.add(enterFileInstructions2, gbc);
        gbc.gridy++;
        quizPanel.add(enterFileInstructions3, gbc);
        gbc.gridy++;
        quizPanel.add(enterFileInstructions4, gbc);
        content.add(quizPanel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        if (i + 1 < quizQ.getQuestions().size()) {
            buttons.add(nextQuestionButton);
            questionsStu.add(quizQ.getQuestions().get(i).getName());
        } else {
            questionsStu.add(quizQ.getQuestions().get(i).getName());
            buttons.add(endQuizButton);
        }
        content.add(buttons, "South");

        Quiz q = quizQ;
        int x = i + 1;
        // Action Listener
        this.nextQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String answer = response.getText();
                    if (answer.charAt(0) == '#' && answer.endsWith(".txt")) {
                        String ans = Files.readString(Paths.get(answer.substring(1)));
                        answers.add(ans);
                    } else {
                        answers.add(response.getText());
                    }
                    studentTakeQuiz.dispose();
                    studentTakeQuiz(quizQ, x);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.endQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String answer = response.getText();
                    if (answer.charAt(0) == '#' && answer.endsWith(".txt")) {
                        String ans = Files.readString(Paths.get(answer.substring(1)));
                        answers.add(ans);
                    } else {
                        answers.add(response.getText());
                    }
                    for (int j = 0; j < answers.size(); j++) {
                        System.out.println(answers.get(j));
                    }
                    for (int j = 0; j < questionsStu.size(); j++) {
                        System.out.println(questionsStu.get(j));
                    }
                    writeToServer("addAnswer<" + courseGlobal.getCourseName() + "~" + quizQ.getQuizName()
                            + "~" + userName + "~" + getTimeStamp() + ">");
                    writeToServer(new Answers(questionsStu, answers));
                    studentTakeQuiz.dispose();
                    quizComplete(quizQ);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for Quiz completed
    public void quizComplete(Quiz quizQ) throws IOException {
        JFrame quizCompleted = new JFrame("Student Quiz Completed");
        quizCompleted.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = quizCompleted.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel quizNameLabel = new JLabel(quizQ.getQuizName());
        quizNameLabel.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel submitted = new JLabel("Submitted!");
        submitted.setFont(new Font("Serif", Font.BOLD, 30));
        quizNameLabel.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);
        Icon icon = new ImageIcon("dance-dance-boy.gif");
        JLabel pic = new JLabel();

        quizCompleted.setSize(1280, 720);
        quizCompleted.setLocationRelativeTo((Component) null);
        quizCompleted.setDefaultCloseOperation(3);
        quizCompleted.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 0;
        gbc.gridy = 1;
        info.add(infoText);
        gbc.gridy = 2;
        //info.add(quizNameLabel, gbc);
        info.add(submitted, gbc);
        content.add(info, "North");

        // adds question and options to panel
        JPanel quizPanel = new JPanel();
        //quizPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.ipady = 10;  //make this component tall
//        gbc.weightx = 0.0;
//        gbc.gridwidth = 0;
//        gbc.gridy = 1;
//        quizPanel.add(quizNameLabel, gbc);
//        quizPanel.add(submitted, gbc);
//        gbc.gridy++;
        pic.setIcon(icon);
        gbc.gridy++;
        quizPanel.add(pic, "Center");
        content.add(quizPanel);


        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        content.add(buttons, "South");

        // Action Listener
        this.continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quizQ.setQuestions(questionOriginal);
                quizCompleted.dispose();
                try {
                    studentQuizScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    //Submission screen for when a teacher makes a quiz
    public void quizCreated(Quiz quizQ) throws IOException {
        JFrame quizCreated = new JFrame("Quiz Created");
        quizCreated.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = quizCreated.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel quizNameLabel = new JLabel(quizQ.getQuizName());
        quizNameLabel.setFont(new Font("Serif", Font.BOLD, 25));
        JLabel submitted = new JLabel("Created");
        submitted.setFont(new Font("Serif", Font.BOLD, 25));
        quizNameLabel.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.continueButton = new JButton("Make another quiz");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);
        this.exitButton = new JButton("Exit to main menu");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        Icon icon = new ImageIcon("_quizCreated.gif");
        JLabel pic = new JLabel();

        quizCreated.setSize(1280, 720);
        quizCreated.setLocationRelativeTo((Component) null);
        quizCreated.setDefaultCloseOperation(3);
        quizCreated.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 0;
        gbc.gridy = 1;
        info.add(infoText);
        gbc.gridy = 2;
        //info.add(quizNameLabel, gbc);
        info.add(submitted, gbc);
        content.add(info, "North");

        // adds question and options to panel
        JPanel quizPanel = new JPanel();
        //quizPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.ipady = 10;  //make this component tall
//        gbc.weightx = 0.0;
//        gbc.gridwidth = 0;
//        gbc.gridy = 1;
//        quizPanel.add(quizNameLabel, gbc);
//        quizPanel.add(submitted, gbc);
        gbc.gridy++;
        pic.setIcon(icon);
        gbc.gridy++;
        quizPanel.add(pic, "Center");
        content.add(quizPanel);


        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "South");

        // Action Listener
        this.continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quizCreated.dispose();
                try {
                    createQuizScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quizCreated.dispose();
                try {
                    teacherMainScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for student viewing graded quiz
    public void studentViewQuizGrade(String stu, Course course, Quiz quizQ, int qNum, int s) throws IOException {
        JFrame studentViewQuizGrade = new JFrame("Student Grading Quiz");
        studentViewQuizGrade.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = studentViewQuizGrade.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel heading = new JLabel(course.getCourseName() + ": " + quizQ.getQuizName());
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel student = new JLabel(stu);
        student.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel questionsAndAnswers = new JLabel();
        questionsAndAnswers.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel grade = new JLabel("Grade: " + courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(quizQ) - 1)
                .getStudents().get(s).getGrades());
        grade.setFont(new Font("Serif", Font.PLAIN, 15));
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        this.nextQuestionButton = new JButton("Next Question");
        this.nextQuestionButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.nextQuestionButton.addActionListener(this.actionListener);
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);

        studentViewQuizGrade.setSize(1280, 720);
        studentViewQuizGrade.setLocationRelativeTo((Component) null);
        studentViewQuizGrade.setDefaultCloseOperation(3);
        studentViewQuizGrade.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        panel.add(heading, gbc);
        gbc.gridy = 2;
        panel.add(student, gbc);
        gbc.gridy = 3;

        ArrayList<Question> q = quizQ.getQuestions();
        int f = course.getQuizzes().indexOf(quizQ);
        questionsAndAnswers = new JLabel("Question: " + quizQ.getQuestions().get(qNum).getName());
        panel.add(questionsAndAnswers, gbc);
        questionsAndAnswers = new JLabel("Answer: " + course.getQuizAnswers().get(f).getStudents()
                .get(s - 1).
                getAnswers().getAnswer().get(qNum));
        gbc.gridy++;
        panel.add(grade);
        panel.add(questionsAndAnswers, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(grade);
        if (qNum + 1 < quizQ.getQuestions().size()) buttons.add(nextQuestionButton);
        else buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "South");

        int x = qNum + 1;
        // Action Listener
        this.nextQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentViewQuizGrade.dispose();
                try {
//                    studentViewQuizGrade(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(quizQ) + 1)
//                                    .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
//                                            .indexOf(quizQ) + 1).getStudents().indexOf(userName)).getStudentName(),
//                            courseGlobal, quizQ, x, courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(quizQ) + 1)
//                                    .getStudents().indexOf(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(quizQ))
//                                            .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
//                                                    .indexOf(quizQ) + 1).getStudents().indexOf(userName))));
                    studentViewQuizGrade(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(quizQ))
                                    .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
                                            .indexOf(quizQ)).getStudents().indexOf(userName)).getStudentName(),
                            courseGlobal, quizQ, x, courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(quizQ))
                                    .getStudents().indexOf(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(quizQ))
                                            .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
                                                    .indexOf(quizQ)).getStudents().indexOf(userName))));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentViewQuizGrade.dispose();
                try {
                    studentQuizScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentViewQuizGrade.dispose();
                try {
                    quizGrade(quizQ);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for Quiz grade student
    public void quizGrade(Quiz quizQ) throws IOException {
        JFrame quizGrade = new JFrame("Student Quiz Grade");
        quizGrade.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = quizGrade.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel quizNameLabel = new JLabel(quizQ.getQuizName() + ":");
        quizNameLabel.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel grade = new JLabel("Grade: grade");
        grade.setFont(new Font("Serif", Font.BOLD, 30));
        quizNameLabel.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);


        quizGrade.setSize(1280, 720);
        quizGrade.setLocationRelativeTo((Component) null);
        quizGrade.setDefaultCloseOperation(3);
        quizGrade.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds question and options to panel
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 0;
        gbc.gridy = 1;
        quizPanel.add(quizNameLabel, gbc);
        quizPanel.add(grade, gbc);
        content.add(quizPanel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        content.add(buttons, "South");

        // Action Listener
    }

    // ----- Teacher Section ----- //
    // adds teacher main menu screen
    public void teacherMainScreen() throws IOException {
        JFrame teacherMainScreen = new JFrame("Teacher Main Screen");
        teacherMainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = teacherMainScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.editAccountButton = new JButton("Edit Account");
        this.editAccountButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.editAccountButton.addActionListener(this.actionListener);
        this.deleteAccountButton = new JButton("Delete Account");
        this.deleteAccountButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.deleteAccountButton.addActionListener(this.actionListener);
        this.logoutButton = new JButton("Logout");
        this.logoutButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.logoutButton.addActionListener(this.actionListener);
        this.createCourseButton = new JButton("Create new course");
        this.createCourseButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.createCourseButton.addActionListener(this.actionListener);
        this.editCourseButton = new JButton("Edit existing course");
        this.editCourseButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.editCourseButton.addActionListener(this.actionListener);
        this.deleteCourseButton = new JButton("Delete existing course");
        this.deleteCourseButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.deleteCourseButton.addActionListener(this.actionListener);
        this.viewQuizzesButton = new JButton("Grade quizzes");
        this.viewQuizzesButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.viewQuizzesButton.addActionListener(this.actionListener);

        teacherMainScreen.setSize(1280, 720);
        teacherMainScreen.setLocationRelativeTo((Component) null);
        teacherMainScreen.setDefaultCloseOperation(3);
        teacherMainScreen.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds choices panel
        JPanel choices = new JPanel();
        choices.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 3;
        gbc.gridy = 1;
        choices.add(createCourseButton, gbc);
        gbc.gridy = 2;
        choices.add(editCourseButton, gbc);
        gbc.gridy = 3;
        choices.add(deleteCourseButton, gbc);
        gbc.gridy = 4;
        choices.add(viewQuizzesButton, gbc);
        content.add(choices);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(editAccountButton);
        buttons.add(deleteAccountButton);
        buttons.add(logoutButton);
        content.add(buttons, "South");

        // Action Listener
        editAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //edit account popup
                teacherMainScreen.dispose();
                try {
                    editAccountScreen(1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.deleteAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (deleteGUI("Are you sure you want to delete your account?") == 0) {
                    try {
                        writeToServer("deleteUser<" + userName + ">");
                        System.out.println("Account deleted!");
                        teacherMainScreen.dispose();
                        welcomeFrame();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherMainScreen.dispose();
                try {
                    welcomeFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        createCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherMainScreen.dispose();
                try {
                    createCourseScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        editCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherMainScreen.dispose();
                try {
                    teacherEditCourseScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherMainScreen.dispose();
                try {
                    teacherDeleteCourseScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        viewQuizzesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherMainScreen.dispose();
                try {
                    teacherCourseScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                // viewQuizInfo();
            }
        });

    }

    // set up frame for creating a course
    public void createCourseScreen() throws IOException {
        JFrame createCourseScreen = new JFrame("Create a new course");
        createCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel createCourse = new JLabel("Create Course:");
        createCourse.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel courseNameLabel = new JLabel("Please enter the course name:");
        courseNameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.courseNameText = new JTextField("", 10);
        JLabel quizFileInstructions = new JLabel("You will be able to add quizzes to this course");
        quizFileInstructions.setFont(new Font("Serif", Font.ITALIC, 12));
        JLabel quizFileInstructions2 = new JLabel("when you click the Edit Existing Course button in the main menu");
        quizFileInstructions2.setFont(new Font("Serif", Font.ITALIC, 12));
        this.createCourseButton = new JButton("Create another course");
        this.createCourseButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.createCourseButton.addActionListener(this.actionListener);
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        this.continueButton = new JButton("Add Course");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);

        createCourseScreen.setSize(1280, 720);
        createCourseScreen.setLocationRelativeTo((Component) null);
        createCourseScreen.setDefaultCloseOperation(3);
        createCourseScreen.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 3;
        gbc.gridy = 1;
        panel.add(createCourse, gbc);
        gbc.gridy = 2;
        panel.add(courseNameLabel, gbc);
        panel.add(courseNameText, gbc);
        gbc.gridy = 3;
        panel.add(quizFileInstructions, gbc);
        gbc.gridy = 4;
        panel.add(quizFileInstructions2, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        buttons.add(createCourseButton);
        buttons.add(exitButton);
        content.add(buttons, "South");

        // Action Listener
        createCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    writeToServer("newCourse<" + courseNameText.getText() + ">");
                    courseNameText.setText("");
                    writeToServer("getCourseNames");
                    Object object = ois.readObject();
                    c = (ArrayList<String>) object;
                    createCourseScreen.dispose();
                    createCourseScreen();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    writeToServer("newCourse<" + courseNameText.getText() + ">");
                    courseNameText.setText("");
                    writeToServer("getCourseNames");
                    Object object = ois.readObject();
                    c = (ArrayList<String>) object;
                    createCourseScreen.dispose();
                    teacherMainScreen();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createCourseScreen.dispose();
                try {
                    writeToServer("newCourse<" + courseNameText.getText() + ">");
                    courseNameText.setText("");
                    writeToServer("getCourseNames");
                    Object object = ois.readObject();
                    c = (ArrayList<String>) object;
                    teacherMainScreen();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void uploadQuizWithFile() throws IOException {
        JFrame uploadQuizWithFile = new JFrame("Upload a quiz");
        uploadQuizWithFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = uploadQuizWithFile.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel quizUpload = new JLabel("Upload new quiz:");
        quizUpload.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel quizFileName = new JLabel("Quiz Set File(optional):");
        quizFileName.setFont(new Font("Serif", Font.PLAIN, 15));
        this.quizFileNameText = new JTextField("name.txt", 10);
        JLabel quizFileInstructions = new JLabel("Instructions:");
        quizFileInstructions.setFont(new Font("Serif", Font.ITALIC, 15));
        JLabel quizFileInstructions2 = new JLabel("Enter a " +
                "filename that ends in .txt");
        quizFileInstructions2.setFont(new Font("Serif", Font.ITALIC, 15));
        JLabel quizFileInstructions3 = new JLabel("Make sure the file is in the same directory " +
                "as this project");
        quizFileInstructions3.setFont(new Font("Serif", Font.ITALIC, 15));
        this.quizUploadButton = new JButton("Upload another quiz");
        this.quizUploadButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.quizUploadButton.addActionListener(this.actionListener);
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);

        uploadQuizWithFile.setSize(1280, 720);
        uploadQuizWithFile.setLocationRelativeTo((Component) null);
        uploadQuizWithFile.setDefaultCloseOperation(3);
        uploadQuizWithFile.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 3;
        gbc.gridy = 1;
        panel.add(quizUpload, gbc);
        //gbc.gridy = 2;
        //panel.add(courseNameLabel, gbc);
        // panel.add(courseNameText, gbc);
        gbc.gridy = 2;
        panel.add(quizFileName, gbc);
        panel.add(this.quizFileNameText, gbc);
        gbc.gridy = 3;
        panel.add(quizFileInstructions, gbc);
        gbc.gridy = 4;
        panel.add(quizFileInstructions2, gbc);
        gbc.gridy = 5;
        panel.add(quizFileInstructions3, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(quizUploadButton);
        buttons.add(exitButton);
        content.add(buttons, "South");

        // Action Listener
        quizUploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    writeToServer("addQuizFile<" + courseGlobal.getCourseName() + ">");
                    writeToServer(new File(quizFileNameText.getText()));
                    //uploadQuizWithFile.dispose();
                    quizFileNameText.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uploadQuizWithFile.dispose();
                try {
                    //writeToServer("newCourse<" + courseNameText.getText() + ">");
                    teacherMainScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // Teacher selects course to be edited
    public void teacherEditCourseScreen() throws IOException, ClassNotFoundException {
        JFrame teacherEditCourseScreen = new JFrame("Teacher Edit Course");
        teacherEditCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = teacherEditCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        JLabel courseLabel = new JLabel("Edit Courses:");
        courseLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);


        teacherEditCourseScreen.setSize(1280, 720);
        teacherEditCourseScreen.setLocationRelativeTo((Component) null);
        teacherEditCourseScreen.setDefaultCloseOperation(3);
        teacherEditCourseScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds courses panel
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 3;
        gbc.gridy = 1;
        coursePanel.add(courseLabel, gbc);
        // adds all courses to the pane

        if (c.size() == 0) {
            JLabel noCourses = new JLabel("There are no courses available yet.");
            noCourses.setFont(new Font("Serif", Font.PLAIN, 15));
            coursePanel.add(noCourses);
        } else {
            int x = c.size();
            for (int j = 0; j < x; j++) {
                gbc.gridy++;
                this.course = new JButton(c.get(j));
                coursePanel.add(this.course, gbc);
                this.course.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String courseName = ((JButton) e.getSource()).getText();
                            //System.out.println(c.indexOf(courseName));
                            //writeToServer("findCourse" + c.indexOf(courseName));

                            /*for (int i = 0; i < 4; i++) {
                                writeToServer(i);
                            }*/
                            writeToServer("findCourse" + c.indexOf(courseName));
                            if (firstTime == 1) {
                                ois.readObject();
                                firstTime++;
                            }
                            Course obj = (Course) ois.readObject();
                            if (obj == null) {
                                if (firstTime == 0) {
                                    firstTime++;
                                    writeToServer("findCourse" + c.indexOf(courseName));
                                    obj = (Course) ois.readObject();
                                    if (obj == null) {
                                        System.out.println("Server and client are OUT OF SYNC!!");
                                    }
                                }
                            }

                            System.out.println(obj);
                            courseGlobal = obj;

//                            main.indexMaker(c.indexOf(((JButton) e.getSource()).getText()));
//                            System.out.println(((JButton) e.getSource()).getText());
//                            courseGlobal = new Course(((JButton) e.getSource()).getText());
                            teacherEditCourseScreen.dispose();
                            selectEditCourseScreen(courseGlobal);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                if (j > 4) {
                    gbc.gridx++;
                }
            }
        }
        content.add(coursePanel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(exitButton);
        content.add(buttons, "South");

        // Action Listener
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherEditCourseScreen.dispose();
                try {
                    teacherMainScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // Teacher selects course to be edited
    public void teacherDeleteCourseScreen() throws IOException {
        JFrame teacherDeleteCourseScreen = new JFrame("Teacher Delete Course");
        teacherDeleteCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = teacherDeleteCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        JLabel courseLabel = new JLabel("Choose the course you want to delete:");
        courseLabel.setFont(new Font("Serif", Font.BOLD, 25));

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);


        teacherDeleteCourseScreen.setSize(1280, 720);
        teacherDeleteCourseScreen.setLocationRelativeTo((Component) null);
        teacherDeleteCourseScreen.setDefaultCloseOperation(3);
        teacherDeleteCourseScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds courses panel
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 3;
        gbc.gridy = 1;
        coursePanel.add(courseLabel, gbc);
        // adds all courses to the pane
        if (c.size() == 0) {
            JLabel noCourses = new JLabel("There are no courses available yet.");
            noCourses.setFont(new Font("Serif", Font.PLAIN, 15));
            coursePanel.add(noCourses);
        } else {
            int x = c.size();
            for (int j = 0; j < x; j++) {
                gbc.gridy++;
                this.course = new JButton(c.get(j));
                coursePanel.add(this.course, gbc);
                this.course.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String courseName = null;
                        try {
                            /*writeToServer("findCourse" + c.indexOf(courseName));
                            System.out.println("Wrote to server line:" + getLineNumber());
                            Course obj = (Course) ois.readObject();
                            System.out.println("Got course object title:" + obj.getCourseName() + "line:"
                                    + getLineNumber());*/
                            courseName = ((JButton) e.getSource()).getText();
                            //System.out.println(c.indexOf(courseName));
                            //writeToServer("findCourse" + c.indexOf(courseName));

                            /*for (int i = 0; i < 4; i++) {
                                writeToServer(i);
                            }*/
                            writeToServer("findCourse" + c.indexOf(courseName));
                            if (firstTime == 1) {
                                ois.readObject();
                                firstTime++;
                            }
                            Course obj = (Course) ois.readObject();
                            if (obj == null) {
                                if (firstTime == 0) {
                                    firstTime++;
                                    writeToServer("findCourse" + c.indexOf(courseName));
                                    obj = (Course) ois.readObject();
                                    if (obj == null) {
                                        System.out.println("Server and client are OUT OF SYNC!!");
                                    }
                                }
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you " +
                                "want to delete this course?", "Delete Course", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            try {
                                writeToServer("deleteCourse<" + courseName + ">");
                                //or i could send the course object instead?
                                System.out.println("Deleted course(hopefully)" + getLineNumber());
                                writeToServer("getCourseNames");
                                Object object = ois.readObject();
                                c = (ArrayList<String>) object;
                                teacherDeleteCourseScreen.dispose();
                                teacherEditCourseScreen();
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }
                });
                if (j > 4) {
                    gbc.gridx++;
                }
            }
        }
        content.add(coursePanel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(exitButton);
        content.add(buttons, "South");

        // Action Listener

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherDeleteCourseScreen.dispose();
                try {
                    teacherMainScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for teacher screen with courses
    public void teacherCourseScreen() throws IOException, ClassNotFoundException {
        JFrame teacherCourseScreen = new JFrame("Teacher course screen");
        teacherCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = teacherCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel courseLabel = new JLabel("Courses");
        courseLabel.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.backButton = new JButton("Back");
        this.backButton.setFont(new Font("Serif", Font.PLAIN, 20));

        teacherCourseScreen.setSize(1280, 720);
        teacherCourseScreen.setLocationRelativeTo((Component) null);
        teacherCourseScreen.setDefaultCloseOperation(3);
        teacherCourseScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds courses panel
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        coursePanel.add(courseLabel, gbc);
        // adds all courses to the pane
        // gets info from server
        if (c.size() == 0) {
            JLabel noCourses = new JLabel("There are no courses available yet.");
            noCourses.setFont(new Font("Serif", Font.PLAIN, 15));
            coursePanel.add(noCourses);
        } else {
            int x = c.size();
            for (int j = 0; j < x; j++) {
                gbc.gridy++;
                this.course = new JButton(c.get(j));
                coursePanel.add(this.course, gbc);
                //Action listener for each course button
                this.course.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String courseName = ((JButton) e.getSource()).getText();
                            //System.out.println(c.indexOf(courseName));
                            //writeToServer("findCourse" + c.indexOf(courseName));

                            /*for (int i = 0; i < 4; i++) {
                                writeToServer(i);
                            }*/
                            writeToServer("findCourse" + c.indexOf(courseName));
                            if (firstTime == 1) {
                                ois.readObject();
                                firstTime++;
                            }
                            Course obj = (Course) ois.readObject();
                            if (obj == null) {
                                if (firstTime == 0) {
                                    firstTime++;
                                    writeToServer("findCourse" + c.indexOf(courseName));
                                    obj = (Course) ois.readObject();
                                    if (obj == null) {
                                        System.out.println("Server and client are OUT OF SYNC!!");
                                    }
                                }
                            }

                            System.out.println(obj);
                            courseGlobal = obj;

//                            main.indexMaker(c.indexOf(((JButton) e.getSource()).getText()));
//                            System.out.println(((JButton) e.getSource()).getText());
//                            courseGlobal = new Course(((JButton) e.getSource()).getText());
                            teacherCourseScreen.dispose();
                            teacherQuizScreen(2);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                if (j > 4) {
                    gbc.gridx++;
                }
            }
        }
        content.add(coursePanel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(backButton);
        content.add(buttons, "South");

        // Action Listener


        this.backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherCourseScreen.dispose();
                try {
                    teacherMainScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    Course bugfixer;
    Boolean bugFixerBoolean = false;
    // set up frame for teacher quiz screen
    // choice 0 - delete, 1 - edit, 2 - view
    public void teacherQuizScreen(int choice) throws IOException, ClassNotFoundException {
        JFrame teacherQuizScreen = new JFrame("Teacher Quizzes");
        teacherQuizScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = teacherQuizScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        JLabel quizLabel = new JLabel(courseGlobal.getCourseName() + ": Quizzes");
        quizLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.backButton = new JButton("Back");
        this.backButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.backButton.addActionListener(this.actionListener);

        teacherQuizScreen.setSize(1280, 720);
        teacherQuizScreen.setLocationRelativeTo((Component) null);
        teacherQuizScreen.setDefaultCloseOperation(3);
        teacherQuizScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds quizzes panel
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 0;
        gbc.gridy = 1;
        quizPanel.add(quizLabel, gbc);
        // adds all quizzes to the pane

        quizzes = courseGlobal.getQuizzes();
        if (quizzes.size() == 0) {
            JLabel noQuizzes = new JLabel("There are no quizzes available for this course yet.");
            noQuizzes.setFont(new Font("Serif", Font.PLAIN, 15));
            quizPanel.add(noQuizzes);
        } else {
            int x = quizzes.size();
            //int w = 0;
            for (int i = 0; i < courseGlobal.getQuizzes().size(); i++) {
                System.out.println(courseGlobal.getQuizzes().get(i));
            }
            writeToServer("updateFile<" + courseGlobal.getCourseName() + ">");
            courseGlobal = (Course) ois.readObject();
            if (bugFixerBoolean) {
                courseGlobal = bugfixer;
                bugFixerBoolean = false;
            }

            for (int i = 0; i < courseGlobal.getQuizzes().size(); i++) {
                System.out.println(courseGlobal.getQuizzes().get(i).getQuizName());
                this.quiz = new JButton(courseGlobal.getQuizzes().get(i).getQuizName());

                gbc.gridy++;
                quizPanel.add(this.quiz, gbc);
                // Action Listener
                int finalW = i;
                this.quiz.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String quizName = null;
                        try {
                            quizName = ((JButton) e.getSource()).getText();
                            //String quizName = ((JButton) e.getSource()).getText();
                            /*if(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q))
                                    .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
                                            .indexOf(q)+1).getStudents().indexOf(userName)).isGraded())
                                studentViewQuizGrade(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)+1)
                                                .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
                                                        .indexOf(q)).getStudents().indexOf(userName)).getStudentName(),
                                        courseGlobal, q, 0, courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)+1)
                                                .getStudents().indexOf(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes().indexOf(q)-1)
                                                        .getStudents().get(courseGlobal.getQuizAnswers().get(courseGlobal.getQuizzes()
                                                                .indexOf(q)+1).getStudents().indexOf(userName))));
                            else*/
                            if (choice == 0) {
                                int option = JOptionPane.showConfirmDialog(null, "Are you " +
                                        "sure you want to delete this quiz?", "Delete Quiz", JOptionPane.YES_NO_OPTION);
                                if (option == JOptionPane.YES_OPTION) {
                                    System.out.println("Here in the delete quiz section");
                                    System.out.println(quizName);
                                    writeToServer("deleteQuiz<" + courseGlobal.getCourseName() + "~" + quizName + ">");
                                    //writeToServer("updateCourse<" + courseGlobal.getCourseName() + ">");
                                    Course course = (Course) ois.readObject();
                                    writeToServer("updateFile<" + course.getCourseName() + ">");
                                    course = (Course) ois.readObject();
                                    bugFixerBoolean = true;
                                    for (int j = 0; j < course.getQuizzes().size(); j++) {
                                        System.out.println(course.getQuizzes().get(j));
                                        System.out.println();
                                    }
                                    bugfixer = course;
                                    System.out.println(courseGlobal.getQuizzes().get(0).getQuizName());
                                    System.out.println(courseGlobal.getQuizzes().get(0).getQuizName());
                                    //quiz.removeActionListener(this);
                                    quizzes = courseGlobal.getQuizzes();
                                    teacherQuizScreen.dispose();
                                    teacherQuizScreen(0);
                                }
                            } else if (choice == 1) {
                                teacherQuizScreen.dispose();
                                selectEditQuizScreen(quizzes.get(finalW));
                            } else if (choice == 2) {
                                teacherQuizScreen.dispose();
                                viewQuizInfo(courseGlobal, quizzes.get(finalW));
                            }
                            //studentTakeQuiz(q, 0);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                                /*}
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            } catch (Exception exception) {
                                quizExist = true;
                            }

                            if (quizExist) {
                                simpleGUI("No student has submitted this quiz yet!");
                            }*/
                        //studentTakeQuiz(q, 0);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
                    }
                });
            }
        }
        content.add(quizPanel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(backButton);
        content.add(buttons, "South");

        // Action Listener
        this.backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherQuizScreen.dispose();
                try {
                    if(choice == 0){
                        selectEditCourseScreen(courseGlobal);
                    }else if(choice == 1){
                        selectEditCourseScreen(courseGlobal);
                    }else if(choice == 2){
                        teacherCourseScreen();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    // Teacher edit quiz section created
    public void selectEditQuizScreen(Quiz q) throws IOException {
        JFrame selectEditQuizScreen = new JFrame("Teacher Edit Course");
        selectEditQuizScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = selectEditQuizScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        JLabel quizLabel = new JLabel("Edit Quiz: " + q.getQuizName());
        quizLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        this.editNameButton = new JButton("Edit Name");
        this.editNameButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.editNameButton.addActionListener(this.actionListener);
        this.addQuestions = new JButton("Add Questions");
        this.addQuestions.setFont(new Font("Serif", Font.PLAIN, 20));
        this.addQuestions.addActionListener(this.actionListener);
        JTextField qNum = new JTextField( 5);
        qNum.setFont(new Font("Serif", Font.PLAIN, 10));
        JLabel qNum1 = new JLabel("Enter Number of questions:");
        qNum1.setFont(new Font("Serif", Font.PLAIN, 10));
        this.deleteQuestions = new JButton("Delete Questions");
        this.deleteQuestions.setFont(new Font("Serif", Font.PLAIN, 20));
        this.deleteQuestions.addActionListener(this.actionListener);


        selectEditQuizScreen.setSize(1280, 720);
        selectEditQuizScreen.setLocationRelativeTo((Component) null);
        selectEditQuizScreen.setDefaultCloseOperation(3);
        selectEditQuizScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds panel with buttons to edit
        JPanel editFeature = new JPanel();
        editFeature.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        editFeature.add(quizLabel, gbc);
        gbc.gridy = 2;
        editFeature.add(editNameButton, gbc);
        editFeature.add(qNum1, gbc);
        gbc.gridy = 3;
        editFeature.add(addQuestions, gbc);
        editFeature.add(qNum, gbc);
        gbc.gridy = 4;
        editFeature.add(deleteQuestions, gbc);
        content.add(editFeature);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(exitButton);
        content.add(buttons, "South");

        // Action Listener
        editNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Joption pane to edit name
                String newName;
                newName = JOptionPane.showInputDialog("Enter the Quiz's new name: ");

                try {
                    writeToServer("changeQuizName<" + courseGlobal.getCourseName() + "~" + q.getQuizName() + ">");
                    q.setQuizName(newName);
                    writeToServer(q);
                    writeToServer("getCourseNames");
                    Object object = ois.readObject();
                    c = (ArrayList<String>) object;
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                System.out.println("quiz name changed!");
                selectEditQuizScreen.dispose();
                try {
                    selectEditQuizScreen(q);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        addQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectEditQuizScreen.dispose();
                questions = new ArrayList<>();
                try {
                    try {
                        Integer.parseInt(qNum.getText());
                    } catch (NumberFormatException exception) {
                        qNum.setText("0");
                    }
                    createQuestionScreen(q, Integer.parseInt(qNum.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectEditQuizScreen.dispose();
                try {
                    deleteQuestionsScreen(q);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectEditQuizScreen.dispose();
                try {
                    teacherEditCourseScreen();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    String newCourseName = "";
    boolean newCourse = false;
    // Teacher edit course section created
    public void selectEditCourseScreen(Course course) throws IOException {
        JFrame selectEditCourseScreen = new JFrame("Teacher Edit Course");
        selectEditCourseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = selectEditCourseScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");
        String name = "";
        if (newCourse) {
            writeToServer("updateFile<" + newCourseName + ">");
            Course courseNamer = new Course();
            try {
                courseNamer = (Course) ois.readObject();
                name = courseNamer.getCourseName();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //newCourse = false;
        } else {
            name = courseGlobal.getCourseName();
        }

        JLabel courseLabel = new JLabel("Edit Course: " + name);
        courseLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        this.editNameButton = new JButton("Edit Name");
        this.editNameButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.editNameButton.addActionListener(this.actionListener);
        this.editQuizInfoButton = new JButton("Edit Specific Quiz Info");
        this.editQuizInfoButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.editQuizInfoButton.addActionListener(this.actionListener);
        this.editQuizInfoButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.createQuizFileButton = new JButton("Create New Quiz by importing a file");
        this.createQuizFileButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.createQuizFileButton.addActionListener(this.actionListener);
        this.createNewQuizButton = new JButton("Create New Quiz");
        this.createNewQuizButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.createNewQuizButton.addActionListener(this.actionListener);
        this.deleteQuizButton = new JButton("Delete Specific Quiz");
        this.deleteQuizButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.deleteQuizButton.addActionListener(this.actionListener);


        selectEditCourseScreen.setSize(1280, 720);
        selectEditCourseScreen.setLocationRelativeTo((Component) null);
        selectEditCourseScreen.setDefaultCloseOperation(3);
        selectEditCourseScreen.setVisible(true);

        // adds info of student panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds panel with buttons to edit
        JPanel editFeature = new JPanel();
        editFeature.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        editFeature.add(courseLabel, gbc);
        gbc.gridy = 2;
        editFeature.add(editNameButton, gbc);
        gbc.gridy = 3;
        editFeature.add(editQuizInfoButton, gbc);
        gbc.gridy = 4;
        editFeature.add(createNewQuizButton, gbc);
        gbc.gridy = 5;
        editFeature.add(createQuizFileButton, gbc);
        gbc.gridy = 6;
        editFeature.add(deleteQuizButton, gbc);
        content.add(editFeature);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(exitButton);
        content.add(buttons, "South");

        // Action Listener
        editNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Joption pane to edit name
                String newName;
                newName = JOptionPane.showInputDialog("Enter the course's new name: ");

                try {
                    String newCourseThingy = "";
                    if (newCourse) {
                        newCourseThingy = newCourseName;
                    } else {
                        newCourseThingy = courseGlobal.getCourseName();
                    }
                    writeToServer("changeCourseName<" + newCourseThingy + "~" + newName + ">");
                    newCourseName = newName;
                    newCourse = true;
                    writeToServer("getCourseNames");
                    courseGlobal.setCourseName(newName);
                    Object object = ois.readObject();
                    c = (ArrayList<String>) object;
//                    writeToServer("updateFile<" + courseGlobal.getCourseName() + ">");
//                    Course courseNamer = new Course();
//                    try {
//                        courseGlobal = (Course) ois.readObject();
//                    } catch (Exception eee) {
//                        eee.printStackTrace();
//                    }


                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                System.out.println("course name changed!");
                selectEditCourseScreen.dispose();
                try {
                    teacherEditCourseScreen();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        editQuizInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectEditCourseScreen.dispose();
                try {
                    teacherQuizScreen(1);
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        createNewQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectEditCourseScreen.dispose();
                try {
                    createQuizScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        createQuizFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectEditCourseScreen.dispose();
                try {
                    uploadQuizWithFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectEditCourseScreen.dispose();
                try {
                    teacherQuizScreen(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectEditCourseScreen.dispose();
                try {
                    teacherEditCourseScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for creating a quiz
    public void createQuizScreen() throws IOException {
        JFrame createQuizScreen = new JFrame("Create a new quiz");
        createQuizScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createQuizScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel heading = new JLabel(courseGlobal.getCourseName() + ": New Quiz");
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel quizNameLabel = new JLabel("Quiz Name:");
        quizNameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.quizNameText = new JTextField("", 10);
        JLabel numQuestions = new JLabel("Number of Questions on quiz?");
        numQuestions.setFont(new Font("Serif", Font.PLAIN, 15));
        this.quiznumQuestionsText = new JTextField("", 10);
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);

        createQuizScreen.setSize(1280, 720);
        createQuizScreen.setLocationRelativeTo((Component) null);
        createQuizScreen.setDefaultCloseOperation(3);
        createQuizScreen.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        panel.add(heading, gbc);
        gbc.gridy = 2;
        panel.add(quizNameLabel, gbc);
        panel.add(quizNameText, gbc);
        gbc.gridy = 3;
        panel.add(numQuestions, gbc);
        panel.add(quiznumQuestionsText, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "South");
        // Action Listener
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createQuizScreen.dispose();
                questions = new ArrayList<>();
                try {
                    //writeToServer("addQuiz<" + courseGlobal.getCourseName() + ">");
                    int numQ = Integer.parseInt(quiznumQuestionsText.getText());
                    Quiz q = new Quiz(quizNameText.getText(), null);
                    createQuestionScreen(q, numQ);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createQuizScreen.dispose();
                try {
                    teacherEditCourseScreen();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for deleting quiz questions
    public void deleteQuestionsScreen(Quiz q) throws IOException {
        JFrame deleteQuestionsScreen = new JFrame("Delete Questions");
        deleteQuestionsScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = deleteQuestionsScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel heading = new JLabel(courseGlobal.getCourseName() + ": " + q.getQuizName());
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel questNum1 = new JLabel("Enter the question number you would like to delete.");
        questNum1.setFont(new Font("Serif", Font.PLAIN, 15));
        this.questNum = new JTextField("", 5);
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);
        this.quests1 = new JLabel("");

        deleteQuestionsScreen.setSize(1280, 720);
        deleteQuestionsScreen.setLocationRelativeTo((Component) null);
        deleteQuestionsScreen.setDefaultCloseOperation(3);
        deleteQuestionsScreen.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        panel.add(heading, gbc);
        gbc.gridy = 2;
        String s = "";
        ArrayList<String> quests = new ArrayList<>();
        for (int j = 0; j < q.getQuestions().size(); j++) {
            s = (j + 1) + ". " + q.getQuestions().get(j).getName();
            this.quests1 = new JLabel(s);
            this.quests1.setFont(new Font("Serif", Font.PLAIN, 15));
            panel.add(quests1, gbc);
            gbc.gridy++;
        }
        panel.add(questNum1, gbc);
        panel.add(this.questNum, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "South");
        // Action Listener
        this.continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean invalid = false;
                int choice = 0;
                try {
                    choice = Integer.parseInt(questNum.getText());
                    if (choice < 1 || choice > q.getQuestions().size()) {
                        throw new NumberFormatException();
                    }
                    invalid = true;
                } catch (NumberFormatException p) {
                    invalid = false;
                }
                try {
                    if (!invalid) {
                        simpleGUI("Please enter a valid number!");
                    } else {
                        writeToServer("deleteQuestion<" + courseGlobal.getCourseName() +"~" + q.getQuizName() + "~" + q.getQuestions().get(choice - 1).getName() + ">");
                        deleteQuestionsScreen.dispose();
                        selectEditQuizScreen(q);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteQuestionsScreen.dispose();
                try {
                    selectEditQuizScreen(q);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for creating a quiz question
    public void createQuestionScreen(Quiz q, int numQ) throws IOException {
        JFrame createQuestionScreen = new JFrame("Create a new question");
        createQuestionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createQuestionScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel heading = new JLabel(courseGlobal.getCourseName() + ": " + q.getQuizName());
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel questionLabel = new JLabel("Question?:");
        questionLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.questionText = new JTextField("", 20);
        JLabel questionType = new JLabel("What type of question would you like, please select one");
        questionType.setFont(new Font("Serif", Font.PLAIN, 15));
        this.trueFalse = new JCheckBox("True or False");
        this.trueFalse.setFont(new Font("Serif", Font.PLAIN, 15));
        this.multiChoice = new JCheckBox("Multiple Choice");
        this.multiChoice.setFont(new Font("Serif", Font.PLAIN, 15));
        this.fillInBlank = new JCheckBox("Fill in the blank");
        this.fillInBlank.setFont(new Font("Serif", Font.PLAIN, 15));
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);

        createQuestionScreen.setSize(1280, 720);
        createQuestionScreen.setLocationRelativeTo((Component) null);
        createQuestionScreen.setDefaultCloseOperation(3);
        createQuestionScreen.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        panel.add(heading);
        gbc.gridy = 2;
        panel.add(questionLabel, gbc);
        panel.add(questionText, gbc);
        gbc.gridy = 3;
        panel.add(questionType, gbc);
        ;
        gbc.gridy = 4;
        panel.add(trueFalse, gbc);
        gbc.gridy = 5;
        panel.add(multiChoice, gbc);
        gbc.gridy = 6;
        panel.add(fillInBlank, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "South");
        // Action Listener

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int ab = 0;
                    int quest = numQ;
                    String type = "";
                    if (trueFalse.isSelected()) {
                        type = "TrueFalse";
                        ab++;
                    }
                    if (multiChoice.isSelected()) {
                        type = "MCQ";
                        ab++;
                    }
                    if (fillInBlank.isSelected()) {
                        type = "Fill";
                        ab++;
                    }
                    if (ab > 1 || ab == 0) {
                        throw new IOException();
                    }

                    if (type.equals("MCQ")) {
                        optionList = new ArrayList<>();
                        Question question = new Question(questionText.getText(), type, null);
                        System.out.println(questionText.getText() + getLineNumber());
                        questions.add(question);
                        q.setQuestions(questions);
                        createQuestionScreen.dispose();
                        createChoicesScreen(question, q, quest - 1);
                    } else {
                        if (quest != 1) {
                            Question question = new Question(questionText.getText(), type);
                            System.out.println(questionText.getText() + getLineNumber());
                            questions.add(question);
                            q.setQuestions(questions);
                            createQuestionScreen.dispose();
                            createQuestionScreen(q, quest - 1);
                        } else {
                            Question question = new Question(questionText.getText(), type);
                            System.out.println(questionText.getText() + getLineNumber());
                            questions.add(question);
                            q.setQuestions(questions);
                            createQuestionScreen.dispose();
                            q.setQuestions(questions);
                            quizRandomSelectScreen(q);
                        }
                    }
                } catch (IOException ex) {
                    //ex.printStackTrace();
                    simpleGUI("Please choose only one option");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createQuestionScreen.dispose();
                try {
                    createQuizScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // set up frame for creating question Answers
    ArrayList<String> optionList = new ArrayList<>();

    public void createChoicesScreen(Question question1, Quiz q, int quest) throws IOException {
        JFrame createChoicesScreen = new JFrame("Add choices to a question");
        createChoicesScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = createChoicesScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel heading = new JLabel(q.getQuizName());
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel question = new JLabel("Question: " + question1.getName());
        question.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel answerLabel = new JLabel("Answer choice:");
        answerLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.answerText = new JTextField("", 20);
        this.addAnswerButton = new JButton("Add Answer");
        this.addAnswerButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.addAnswerButton.addActionListener(this.actionListener);
        this.nextQuestionButton = new JButton("Add Next Question");
        this.nextQuestionButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.nextQuestionButton.addActionListener(this.actionListener);
        JLabel aInstructions = new JLabel("Make sure to click Add Answer!");
        aInstructions.setFont(new Font("Serif", Font.PLAIN, 10));
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);

        createChoicesScreen.setSize(1280, 720);
        createChoicesScreen.setLocationRelativeTo((Component) null);
        createChoicesScreen.setDefaultCloseOperation(3);
        createChoicesScreen.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        panel.add(heading, gbc);
        gbc.gridy = 2;
        panel.add(question, gbc);
        gbc.gridy = 3;
        panel.add(answerLabel, gbc);
        panel.add(answerText, gbc);
        gbc.gridy++;
        panel.add(aInstructions, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(addAnswerButton);
        if (quest == 0) {
            buttons.add(continueButton);
        } else {
            buttons.add(nextQuestionButton);
        }
        content.add(buttons, "South");
        // Action Listener

        addAnswerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!answerText.getText().equals("")) {
                    optionList.add(answerText.getText());
                    String[] options = new String[optionList.size()];
                    options = optionList.toArray(options);
                    question1.setOptions(options);
                    answerText.setText("");
                } else {
                    simpleGUI("Please input a choice!");
                }
            }
        });

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createChoicesScreen.dispose();
                try {
                    //q.editQuestion(quest, question1);
                    quizRandomSelectScreen(q);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

                nextQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createChoicesScreen.dispose();
                try {
                    //q.editQuestion(quest, question1);
                    createQuestionScreen(q, quest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    // set up frame for creating quiz as randomized/pool questions
    public void quizRandomSelectScreen(Quiz q) throws IOException {
        JFrame quizRandomSelectScreen = new JFrame("Quiz Randomization");
        quizRandomSelectScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = quizRandomSelectScreen.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel heading = new JLabel(courseGlobal.getCourseName() + ": " + q.getQuizName());
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        this.randomized = new JCheckBox("Would you like questions in a random order?");
        JLabel pool = new JLabel("Would you like to have questions selected from a pool of questions?");
        pool.setFont(new Font("Serif", Font.PLAIN, 15));
        this.poolNum = new JTextField("", 5);
        JLabel poolInfo = new JLabel("If no, enter 0 in the text box.");
        poolInfo.setFont(new Font("Serif", Font.PLAIN, 15));
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);

        quizRandomSelectScreen.setSize(1280, 720);
        quizRandomSelectScreen.setLocationRelativeTo((Component) null);
        quizRandomSelectScreen.setDefaultCloseOperation(3);
        quizRandomSelectScreen.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        panel.add(heading, gbc);
        gbc.gridy = 2;
        panel.add(randomized, gbc);
        gbc.gridy = 3;
        panel.add(pool, gbc);
        panel.add(poolNum, gbc);
        gbc.gridy = 4;
        panel.add(poolInfo, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(continueButton);
        content.add(buttons, "South");

        // Action Listener
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    q.setRandomization(randomized.isSelected());
                    if (Integer.parseInt(poolNum.getText()) > (q.getQuestions().size() - 1)) {
                        throw new NumberFormatException();
                    }
                    if (!Objects.equals(poolNum.getText(), "0")) {
                        q.setRandomQuestionsCount(Integer.parseInt(poolNum.getText()));

                    } else {
                        q.setRandomQuestionsCount(0);
                    }
                    //q.setQuestions(questions);
                    //System.out.println(q);
                    Quiz quiz = new Quiz(q.getQuizName(), questions, q.isRandomization(), q.getRandomQuestionsCount());
                    writeToServer("addQuiz<" + courseGlobal.getCourseName() + ">");
                    writeToServer(quiz);
                    quizRandomSelectScreen.dispose();
                    quizCreated(q);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException z) {
                    simpleGUI("Please enter a valid integer less than " +
                            (q.getQuestions().size()) + " or enter 0!");
                }
            }
        });
    }

    String studentNametoBeGraded;

    // set up frame for viewing quiz grades and submissions
    public void viewQuizInfo(Course course, Quiz quizQ) throws IOException, ClassNotFoundException {
        JFrame viewQuizInfo = new JFrame("Quiz Information");
        viewQuizInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = viewQuizInfo.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel heading = new JLabel(course.getCourseName() + ": " + quizQ.getQuizName());
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel stuNumLabel = new JLabel("Enter the student number you would like to view.");
        stuNumLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        this.stuNum = new JTextField("", 5);
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);
        this.studentsInfo = new JLabel("");

        viewQuizInfo.setSize(1280, 720);
        viewQuizInfo.setLocationRelativeTo((Component) null);
        viewQuizInfo.setDefaultCloseOperation(3);
        viewQuizInfo.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        panel.add(heading, gbc);
        gbc.gridy = 2;
        String s = "";
        int i = course.getQuizzes().indexOf(quizQ);
        ArrayList<String> studentNames = new ArrayList<>();
        JPanel buttons = new JPanel();
        try {
            if (course.getQuizAnswers().get(i).getStudents().size() == 0) {
                throw new IndexOutOfBoundsException();
            }
            for (int j = 0; j < course.getQuizAnswers().get(i)
                    .getStudents().size();
                 j++) {
                writeToServer("getBooleanOfStudentQuiz<" + courseGlobal.getCourseName()
                        + "~" + quizQ.getQuizName() + "~" + course.getQuizAnswers().get(i).
                        getStudents().get(j).getUserName() + ">");
                boolean graded = (boolean) ois.readObject();
                s = (j + 1) + ". " + course.getQuizAnswers().get(i).
                        getStudents().get(j).getStudentName()
                        + " | Graded: " + graded;
                studentNames.add(course.getQuizAnswers().get(i).
                        getStudents().get(j).getStudentName());
                this.studentsInfo = new JLabel(s);
                this.studentsInfo.setFont(new Font("Serif", Font.PLAIN, 15));
                panel.add(studentsInfo, gbc);
                gbc.gridy++;
            }
            panel.add(stuNumLabel, gbc);
            panel.add(stuNum, gbc);
            buttons.add(continueButton);
        } catch (IndexOutOfBoundsException f) {
            this.studentsInfo = new JLabel("No students have attempted this quiz yet.");
            this.studentsInfo.setFont(new Font("Serif", Font.PLAIN, 15));
            panel.add(studentsInfo, gbc);
            gbc.gridy++;
        }

        content.add(panel);

        // adds buttons panel

        buttons.add(exitButton);
        content.add(buttons, "South");
        // Action Listener
        this.continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean invalid = false;
                int choice = 0;
                try {
                    choice = Integer.parseInt(stuNum.getText());
                    if (choice < 1 || choice > studentNames.size()) {
                        throw new NumberFormatException();
                    }
                    invalid = true;
                } catch (NumberFormatException p) {
                    invalid = false;
                }
                try {
                    if (!invalid) {
                        simpleGUI("Please enter a valid number!");
                    } else {
                        viewQuizInfo.dispose();
                        studentNametoBeGraded = course.getQuizAnswers().get(i).
                                getStudents().get(choice - 1).getUserName();
                        gradeStudentQuiz(studentNames.get(choice - 1), courseGlobal, quizQ, 0, choice);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewQuizInfo.dispose();
                try {
                    teacherQuizScreen(2);
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    ArrayList<Double> grades = new ArrayList<>();

    // set up frame for grading a student quiz
    public void gradeStudentQuiz(String stu, Course course, Quiz quizQ, int qNum, int s) throws IOException {
        JFrame gradeStudentQuiz = new JFrame("Grading Quiz");
        gradeStudentQuiz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = gradeStudentQuiz.getContentPane();
        content.setLayout(new BorderLayout());
        this.main = new GuiTester();
        content.add(this.main, "Center");

        JLabel infoText = new JLabel(userName);
        infoText.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel heading = new JLabel(course.getCourseName() + ": " + quizQ.getQuizName());
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel student = new JLabel("Student: " + stu);
        student.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel questionsAndAnswers = new JLabel();
        questionsAndAnswers.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel grade = new JLabel("Enter Grade:");
        grade.setFont(new Font("Serif", Font.PLAIN, 15));
        this.gradeNum = new JTextField("", 5);
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.exitButton.addActionListener(this.actionListener);
        this.continueButton = new JButton("Continue");
        this.continueButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.continueButton.addActionListener(this.actionListener);

        gradeStudentQuiz.setSize(1280, 720);
        gradeStudentQuiz.setLocationRelativeTo((Component) null);
        gradeStudentQuiz.setDefaultCloseOperation(3);
        gradeStudentQuiz.setVisible(true);

        // adds info of teacher panel
        JPanel info = new JPanel();
        info.add(infoText);
        content.add(info, "North");

        // adds content to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 10;  //make this component tall
        gbc.weightx = 0.0;
        gbc.gridwidth = 5;
        gbc.gridy = 1;
        panel.add(heading, gbc);
        gbc.gridy = 2;
        panel.add(student, gbc);
        gbc.gridy = 3;

        ArrayList<Question> q = quizQ.getQuestions();
        int f = course.getQuizzes().indexOf(quizQ);
        //Gotta show all the answers!
        //Gotta show all the answers!
        //Gotta show all the answers!
        //Gotta show all the answers!
        //Gotta show all the answers!

        questionsAndAnswers = new JLabel("Question: " + course.getQuizAnswers().get(f).getStudents()
                .get(s - 1).
                getAnswers().getQuestion().get(qNum));
        panel.add(questionsAndAnswers, gbc);
        questionsAndAnswers = new JLabel("Answer: " + course.getQuizAnswers().get(f).getStudents()
                .get(s - 1).
                getAnswers().getAnswer().get(qNum));
        gbc.gridy++;
        panel.add(questionsAndAnswers, gbc);
        content.add(panel);

        // adds buttons panel
        JPanel buttons = new JPanel();
        buttons.add(grade);
        buttons.add(gradeNum);
        buttons.add(continueButton);
        buttons.add(exitButton);
        content.add(buttons, "South");
        // Action Listener

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (qNum != (quizQ.getQuestions().size() - 1)) {
                        if (!Objects.equals(gradeNum.getText(), "")) {
                            grades.add(Double.valueOf(gradeNum.getText()));
                        }

                        gradeStudentQuiz.dispose();
                        System.out.println("One time");
                        gradeStudentQuiz(stu, course, quizQ, qNum + 1, s);
                    } else {
//                        gradeStudentQuiz(stu, course, quizQ, qNum + 1, s);
                        if (!Objects.equals(gradeNum.getText(), "")) {
                            grades.add(Double.valueOf(gradeNum.getText()));
                        }
                        simpleGUI("Finished grading!");
                        gradeStudentQuiz.dispose();
                        writeToServer("addGrades<" + courseGlobal.getCourseName() + "~" +
                                quizQ.getQuizName() + "~" + studentNametoBeGraded + ">");
                        writeToServer(grades);
                        System.out.println("Opening View Course Info");
                        viewQuizInfo(course, quizQ);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException p) {
                    simpleGUI("Please enter a valid number!");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gradeStudentQuiz.dispose();
                try {
                    viewQuizInfo(courseGlobal, quizQ);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void run() {

        // gets info from server
        try {
//            socket = new Socket("localhost", 1427);
//            oos = new ObjectOutputStream(socket.getOutputStream());
//            ois = new ObjectInputStream(socket.getInputStream());
//            Socket socket = new Socket("localhost", 1447);
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject("getCourseNames");
            oos.flush();
            Object object = ois.readObject();
            c = (ArrayList<String>) object;
//            oos.writeObject("findCourse0");
//            oos.flush();
//            Object obj = ois.readObject();
//            coursecourseGlobal = (Course) obj;

            welcomeFrame();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
