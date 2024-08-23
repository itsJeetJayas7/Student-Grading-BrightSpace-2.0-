Project 5 - Team 001
===============================================================
Code Submitted by 
Project Report Submitted by 
===============================================================
For the set up, we have a few user accounts/files and course files and quiz files. These should be used for reference for testing.
===============================================================
How our Program works:
Overall, we organized our code with a main execution method that executes the basic program (GuiTester.java).
This program has everything we need to use. It utilizes various different GUIs for each pane and option to be prompted by the user.
From the moment the program is started to logging in, signing up, taking quizzes, etc., there are different GUIs that handle the different scenarios.
Additionally, the GuiTester.java implements the runnable function, which means it can run threads concurrently.
The ServerClass.java incorporates the code for creating a server. This class must be run initially because it sets up the server and allows for the main functionality to be run.
The ServerCourseManagement.java has the code for running the server and saving the different edits that happen in relation to the course and accounts for Brightspace.
The MultiThreadHandlerClass.java has the code for implementing different edits that are executed in the program and then writes these edits to the server for it to directly change the items in each file.

===============================================================

Now lets look at the flow of the overall program.

The first pane asks the user to log in or sign up.

Signing Up
If the user chooses to sign up, they are prompted to enter their full information. They will enter their full name, username and password. The usernames’ format we chose for this assignment is in the format of “username@type”. The username is the actual name of the username while the type is whether the user is a student or teacher.

Up next, we check if the information already exists for a specific user by traversing an ArrayList and a corresponding file that stores each and every user’s information.

Utilizing this information we create a new User object with a User constructor in the User.java file. 

Upon successful completion, the program takes us back to the main display of the 4 main options.


Logging In
Up next, the user can choose to log in. First we ask the user to input their username (with the ‘username@type’ format) and then their password. If there is no matching profiles, we ask have a popup Joption Pane and reprompt the user.

Once we get a successful login, the program takes the user to their respective homepage for either a student of a teacher.

For a teacher we get the following options
Create New Course
Edit Course
Delete Course
View Quizzes for a Course
Logout. 
If the user selects the first option, we prompt them to create a new course and the name of that course. The backend creates a new course object and updates that into the server so it can be saved in the file.
If the user selects the second option, we prompt them to either change the name for a course, or add/delete quizzes and questions for that specific course. We send a writeToServer() command to change the name of the specific course, quiz or question at hand and it changes it through the Server.
If the user selects the third option, we prompt them the course they want to delete after showing all the courses available. Then we delete the course by sending a writeToServer() command that specifies it to delete the specific course through the server.
Next, we can show the quizzes for a teacher to view by getting the quizzes through the server.
Lastly, we have an option that allows for the student to logout.

For students, there are different options. A student can view and take quizzes as he or she pleases. First we show all the courses available for a student. After entering a valid input, we display the quizzes the student has taken. Afterwards, we prompt the student for their response for each question displayed. Once a student is finished, the responses are saved. 


Editing Account Info
When it comes to editing the account information, we simply ask the user to modify their username, full name or password. The system checks the existing usernames and ensures proper method control.
The username updates through the server and the specific files, so even when the user wants to edit their account information again, it shows the same items as well.


Exit
When the user selects this option, it simply exits the main function and ends the program.

===============================================================
Description of Each Classes:
GuiTester.java - contains ALL the GUIs for each option and step in the process for using Brightspace. THe flow of the program is outlined through each GUI. We map this flow through actionevents. When a specific button is clicked, then the actionevent for that would be to dispose the current GUI Pane and open another GUI Pane and continue the methodologies. For example, after logging in, the GUI automatically closes the logging in GUI Frame and opens the Teacher/Student Main Menu GUI Frame. 
ServerClass.java - class that contains the information for the server with the proper localhost and portnumber for the function to run properly
ServerCourseManagement - the code for running the server and saving the different edits that happen in relation to the course and accounts for Brightspace
MultiThreadHandlerClass.java - the code for implementing different edits that are executed in the program and then writes these edits to the server for it to directly change the items in each file.
[Obsolete] NewMainMethod.java - contains the main method for execution of the code. The functionality of the code is described above.
Asnwers.java - Contains a information for each student's answers to specific quizzes taken. It saves the String response, String question and whether or not it is graded. It also has other stuff as well.
Course.java - Contains the information for each course. This information includes the course name (as a string) and an ArrayList of the Quizzes (as quiz objects) for each course. 
Question.java - Contains the information about each question created. An arraylist of these objects is locataed in the Quiz class. This class saves the name of the question (the question itself) as a string, the type of question as a string (MCQ, True/False, Fill in the Blank). 
Quiz.java - A class that contains the information for a quiz. The quiz stores the name for the quiz as a string and the timestamp for the quiz itself.
Student.java - A class that contains the information for the student. It stores the student's name,  username and password. Additionally, it saves the student's answers to a corresponding quiz arraylist (each having their own arraylists). 
User.java - A class that contains the information for the user itself. It does not distinguish between teacher and student (except for a string value called type). If it is a student, we initialize another student class when a user creates an account in the NewMainMethod. The User class saves the full name, username, password and type of user (student or teacher) as a string.
===============================================================
Compiling
All of the files shown in the vocareum workspace must be included in the src folder to be run properly. The GUITester.java must be executed for the entire program to run. THere must be an empty CourseNames.txt and UserLibrary.txt to create/use files.
===============================================================
If you are entering a quiz file, you must enter it in the following format:
<Hey,false,0,[<hello-MCQ-[a~b]>#<hello-TrueFalse-[true~false]>#<hello-MCQ-[a~b]>#<hello-MCQ-[a~b]>]>
===============================================================
If you are entering a quiz file with student responses, it must be in the following format:
<Hey,false,0,[<hello-MCQ-[a~b]>#<hello-TrueFalse-[true~false]>#<hello-MCQ-[a~b]>#<hello-MCQ-[a~b]>]>
$

===============================================================
If you are entering an answer file, you must enter it in the Following format (the answer choice is a,b,c,d for MCQ; True or False for TrueFalse questions; or the actual string for fill in the blank):
Answer

