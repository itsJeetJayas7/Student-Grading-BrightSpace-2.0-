import org.junit.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;

import static org.junit.Assert.*;

/**
 * TestCases class for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */

@FixMethodOrder(MethodSorters.JVM)

public class TestCases {
    //Test cases for project 4

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class TestCase {
        private final InputStream originalInput = System.in;
        private final PrintStream originalOutput = System.out;
        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;
        // switch TA_ERROR_MESSAGE to Room1_PJ4_Team_Message after every thing done

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @Test(timeout = 1000)
        public void testOne() {
            String input = "1\nPuruo Wang\nPWS@student\n123456\n4\n";
            receiveInput(input);
            try {
                NewMainMethod.main(new String[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
                fail("Ensure your program handles file reading and writing correctly and has the " +
                        "correct number of scanner calls!");
            }

            String out = getOutput();
            String expectedFull = "Hi, welcome to BrightSpace: Project Butterfly Effect!\n"
                    + "1. Sign up (do not have a account?)\n"
                    + "2. Login in\n"
                    + "3. Edit Account Info\n"
                    + "4. Exit\n"

                    + "Sign up!\n"
                    + "Enter full Name: \n"

                    + "Teacher EX: username@teacher\n"
                    + "Student EX: username@student\n"
                    + "Enter username: \n"

                    + "Enter password: \n"

                    + "Create the account success!\n"
                    + "1. Sign up (do not have a account?)\n"
                    + "2. Login in\n"
                    + "3. Edit Account Info\n"
                    + "4. Exit\n"

                    + "Thanks for using! See u next time!\n";

            out = out.replaceAll("\r\n", "\n");
            assertEquals("Ensure your project output contains the correct winning information!",
                    expectedFull, out);

            String userLibrary = "";
            try {
                FileReader fr = new FileReader("UserLibrary.txt");
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    userLibrary += line;
                    userLibrary += "\n";
                    line = br.readLine();
                }
                fr.close();
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                Assert.fail("Unexpected Exception!");
            }

            String expectedGameLog = "Puruo Wang student\n"
                    + "PWS@student\n"
                    + "123456\n";

            assertEquals("Ensure your UserLibrary.txt file output is correct",
                    expectedGameLog, userLibrary);
        }


        @Test(timeout = 1000)
        public void testTwo() {
            String input = "3\nPWS@student\n123456\n1\nPWSS@student\n4\n";
            receiveInput(input);
            try {
                NewMainMethod.main(new String[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
                fail("Ensure your program handles file reading and writing correctly and has the correct " +
                        "number of scanner calls!");
            }

            String out = getOutput();
            String expectedFull = "Hi, welcome to BrightSpace: Project Butterfly Effect!\n"
                    + "1. Sign up (do not have a account?)\n"
                    + "2. Login in\n"
                    + "3. Edit Account Info\n"
                    + "4. Exit\n"

                    + "Username EX: username@student/username@teacher\n"
                    + "TO Exit PRESS 1\n"
                    + "Username :\n"
                    + "Password :\n"

                    + "1. Change Username\n"
                    + "2. Change Password\n"
                    + "3. Delete account\n"
                    + "TO Exit PRESS Any Key\n"

                    + "Please Enter your new Username\n"
                    + "Username EX: username@student/username@teacher\n"

                    + "1. Sign up (do not have a account?)\n"
                    + "2. Login in\n"
                    + "3. Edit Account Info\n"
                    + "4. Exit\n"

                    + "Thanks for using! See u next time!\n";

            out = out.replaceAll("\r\n", "\n");
            assertEquals("Ensure your project output contains the correct winning information!",
                    expectedFull, out);

            String userLibrary = "";
            try {
                FileReader fr = new FileReader("UserLibrary.txt");
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    userLibrary += line;
                    userLibrary += "\n";
                    line = br.readLine();
                }
                fr.close();
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                Assert.fail("Unexpected Exception!");
            }

            String expectedGameLog = "Puruo Wang student\n"
                    + "PWSS@student\n"
                    + "123456\n";

            assertEquals("Ensure your UserLibrary.txt file output is correct",
                    expectedGameLog, userLibrary);
        }


        @Test(timeout = 1000)
        public void testThree() {
            String input = "1\nPuruo W\nPWT@teacher\n654321\n4\n";
            receiveInput(input);
            try {
                NewMainMethod.main(new String[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
                fail("Ensure your program handles file reading and writing correctly and has the correct " +
                        "number of scanner calls!");
            }

            String out = getOutput();
            String expectedFull = "Hi, welcome to BrightSpace: Project Butterfly Effect!\n"
                    + "1. Sign up (do not have a account?)\n"
                    + "2. Login in\n"
                    + "3. Edit Account Info\n"
                    + "4. Exit\n"

                    + "Sign up!\n"
                    + "Enter full Name: \n"

                    + "Teacher EX: username@teacher\n"
                    + "Student EX: username@student\n"
                    + "Enter username: \n"

                    + "Enter password: \n"

                    + "Create the account success!\n"
                    + "1. Sign up (do not have a account?)\n"
                    + "2. Login in\n"
                    + "3. Edit Account Info\n"
                    + "4. Exit\n"

                    + "Thanks for using! See u next time!\n";

            out = out.replaceAll("\r\n", "\n");
            assertEquals("Ensure your project output contains the correct winning information!",
                    expectedFull, out);

            String userLibrary = "";
            // do a testcase about UserLibrary if User successfully create an account in our case 1
            try {
                FileReader fr = new FileReader("UserLibrary.txt");
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    userLibrary += line;
                    userLibrary += "\n";
                    line = br.readLine();
                }
                fr.close();
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                Assert.fail("Unexpected Exception!");
            }

            String expectedGameLog = "Puruo Wang student\n"
                    + "PWSS@student\n"
                    + "123456\n"
                    + "Puruo W teacher\n"
                    + "PWT@teacher\n"
                    + "654321\n";

            assertEquals("Ensure your UserLibrary.txt file output is correct",
                    expectedGameLog, userLibrary);
        }


        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}
