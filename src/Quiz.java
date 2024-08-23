import java.io.Serializable;
import java.util.*;

/**
 * Quiz class for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class Quiz implements Serializable {
    private String quizName;
    private String date; //I guess we might not need it on teacher's end
    //private Question[] questions;
    private ArrayList<Question> questions;
    private int quizID;
    private boolean randomization = false;
    private int randomQuestionsCount = 0;
    //private ArrayList<String> answers;
    private Answers answers;

    /**
     *
     * Leave this for now, dont worry about quiz ID and Date
     */
//    public Quiz(String quizName, int quizID, String date, Quiz[] quizzes) {
//        this.quizName = quizName;
//        this.quizID = quizID;
//        this.date = date;
//        this.quizzes = quizzes;
//    }

    /**
     * If you have a question array...If you want to randomize you can do it later by the setter for
     * radmization boolean and how many random questions
     */
    public Quiz(String quizName, ArrayList<Question> questions) {
        this.quizName = quizName;
        this.questions = questions;
    }

    /**
     * IF you have a question array and want to randomize...
     */
    public Quiz(String quizName, ArrayList<Question> questions, boolean randomization, int randomQuestionsCount) {
        this.quizName = quizName;
        this.questions = questions;
        this.randomization = randomization;
        this.randomQuestionsCount = randomQuestionsCount;
    }

    /*public Quiz(String quizName, ArrayList<Question> questions, boolean randomization, int randomQuestionsCount,
                ArrayList<String> answers) {
        this.quizName = quizName;
        this.questions = questions;
        this.randomization = randomization;
        this.randomQuestionsCount = randomQuestionsCount;
        this.answers = answers;
    }*/

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getDate() {
        return date;
    }

    public boolean isRandomization() {
        return randomization;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setRandomization(boolean randomization) {
        this.randomization = randomization;
    }

    public void setRandomQuestionsCount(int randomQuestionsCount) {
        this.randomQuestionsCount = randomQuestionsCount;
    }

    public int getRandomQuestionsCount() {
        return randomQuestionsCount;
    }
    //    public ArrayList<String> getAnswers() {
//        return answers;
//    }
//    public void setAnswers(ArrayList<String> answers) {
//        this.answers = answers;
//    }

    /**
     * answers
     * Add a question by passing a Question object
     */

    public void addQuestion(Question question) {
        /*Question[] arr = new Question[questions.length + 1];
        for (int i = 0; i < questions.length; i++) {
            arr[i] = questions[i];
        }
        arr[questions.length] = question;
        this.questions = arr;*/
        questions.add(question);
    }

    /**
     * editing any question by the index and new Question
     */

    public void editQuestion(int index, Question question) {
        this.questions.set(index, question);
    }

    /**
     * Deleting any question by index value...
     */

    public void deleteQuestion(int index, Course course) {
        this.questions.remove(index);
        course.courseWriter();
    }

    /**
     * This method randomizes the question order and option order and returns
     * a randomized Question Array List
     */
    public ArrayList<Question> getRandomOrderQuestions() {
        ArrayList<Question> randomized = this.getQuestions();
        if (randomization) {
            Collections.shuffle(randomized);
        }
        for (Question question : randomized) {
            String[] options = question.getOptions();
            List<String> optionList = Arrays.asList(options);
            Collections.shuffle(optionList);
            String[] shuffled = optionList.toArray(options);
            question.setOptions(shuffled);
        }

        return randomized;
    }

    /**
     * Gets a set number of questions randomly from a larger pool of questions
     *
     * @param count        number of desired questions from pool
     * @param questionList original pool of question
     * @return newQuestions list of questions randomly selected from pool of questions
     */
    public ArrayList<Question> getQuestionsFromPool(int count, ArrayList<Question> questionList) {
        ArrayList<Question> newQuestions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Random random = new Random();
            //adding new question randomly picked from original list
            Question question = questionList.get(random.nextInt(questionList.size()));
            //add to list if not already in it
            if (!newQuestions.contains(question)) {
                newQuestions.add(question);
            } else {
                //decrement if no question is added to list so we can match final count
                i--;
            }
        }

        return newQuestions;
    }

    /**
     * This toString method is only for *FILE I/O*
     */
    @Override
    public String toString() {
        String toString = "";
        toString += "<" + quizName + ',' +
                //", date='" + date + '\'' +  //feels unnecessary on the teacher's end
                randomization +
                "," + randomQuestionsCount +
                ",[";

        for (int i = 0; i < questions.size(); i++) {
            if (i == questions.size() - 1) {
                toString += questions.get(i).toString();
            } else {
                toString += questions.get(i).toString() + "#";
            }
        }
        toString += "]>";
        return toString;
    }
}
