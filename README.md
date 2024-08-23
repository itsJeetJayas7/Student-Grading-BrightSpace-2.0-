# Project-4-5-C-18000-SP-22
Project 4 and 5 Option 2 (Quizzes) 

Common Features
While the specifics of each implementation will be related to the option you select, there are two common features for every project. 

Roles

There will be two defined roles in the application: Teacher and Student.
Teachers will be able to create courses and edit content within them. 
Students will be able to access the available courses and submit responses. 
Users will select their role  while creating an account, with permissions associated with their actions accordingly. 
Permissions details will be noted in the option requirements. 

Courses

Each application must support multiple courses.
Teachers will be able to create as many courses as they like.
Students can access any course that has been created. 

===================================================================================================================================================================

The second option is to implement a learning management system quiz tool. Online quizzes allow teachers to present information in a variety of formats to evaluate student progress. 

Looking for an example? Navigate to the Brightspace quizzes!

Reminder: You can assume that only one user is accessing the application at a time. A teacher might log in, create a quiz, then log out. A student could then log in and complete the quiz. 

Your implementation must have the following: 
Core

Data must persist regardless of whether or not a user is connected. If a user disconnects and reconnects, their data should still be present. 

Descriptive errors should appear as appropriate. For example, if someone tries to log in with an invalid account. The application should not crash under any circumstances. 

Users can create, edit, and delete accounts for themselves.

The attributes you collect as part of account creation are up to you. 

Users should be required to either create an account or sign in before gaining access to the application. 

Whichever identifier you maintain for the user must be unique. 

Quizzes

Any number of quizzes can be added to a course. 

Quizzes will have one or more multiple choice questions. 

Quiz questions will appear on the same page in the order they are added to the quiz. 

Teachers

Teachers can create, edit, and delete quizzes. 

Teachers can view student submissions for the quiz. 

Students

Students can take any created quiz. Students can select their responses to each question. 

After completing a quiz, students can submit it. Each submission must be timestamped. 

Selections

File imports.

All file imports must occur as a prompt to enter the file path.  

Teachers can import a file with the quiz title and quiz questions to create a new quiz. 

Students can attach a file to any question as a response. 

Randomization

Teachers can choose to randomize the order of questions and the order of potential options for a question.

Students will receive a different order with every attempt. 

Grading

Teachers can view the quiz submissions for individual students and assign point values to each answer. 

Students can view their graded quizzes, with the points for each individual question and their total score listed. 

Optional Features: 

Teachers can create questions with different formats than multiple choice. Select two from the following list (fill in the blank, dropdown, matching, true / false). Students can respond to the question. 

Teachers can create a question pool wherein the questions select for the quiz are a random subset of the larger pool. Students will receive a different subset with every attempt. 

Teachers can grant specific students alternate forms of access to the quiz in the form of different deadlines and/or extended time. Both features must be present. The individual student who receives those changes will have them present in the quiz when they take it. 
