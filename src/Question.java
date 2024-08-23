import java.io.Serializable;

/**
 * Question method for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class Question implements Serializable {
    private String name;
    private String type;
    private String[] options;

    public Question(String name, String type, String[] options) {//If we want to create with options too
        this.name = name;
        this.type = type;
        this.options = options;
    }

    public Question(String name, String type) {//Without options
        this.name = name;
        this.type = type;
        if (type.equals("TrueFalse")) {
            this.options = new String[]{"true", "false"};
        } else {
            this.options = null;
        }
    }

    public String[] getOptions() {
        return options;
    }

    public void printOptions() {
        for (int i = 0; i < getOptions().length; i++) {
            System.out.println(i + 1 + ". " + getOptions()[i]);
        }
    }

    /**
     * If you would like/have to add a single option for a quiz (only if the type is MCQ)
     */
    public void addSingleOption(String option) {
        if (type.equals("MCQ")) {
            String[] arr = new String[options.length + 1];
            for (int i = 0; i < options.length; i++) {
                arr[i] = this.options[i];
            }
            arr[options.length] = option;
            this.options = arr;
        }
    }

    /**
     * Edit any option : Pass the index and the new Option
     */
    public void editOption(int index, String newOption) {
        this.options[index] = newOption;
    }

    /**
     * Delete an option: Pass the index
     */

    public void deleteOption(int index) {
        if (type.equals("MCQ")) {
            String[] arr = options;
            arr[index] = null;
            String[] newArr = new String[options.length - 1];
            int j = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == null) {
                    continue;
                }
                newArr[j] = arr[i];
                j++;
            }
            this.options = newArr;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    /**
     *
     * I will make a method to print for the Student and Teachers
     */

    /**
     * This toString Function is *JUST FOR FILE I/O*
     */
    @Override
    public String toString() {
        String toString = "";
        toString += "<" + name + "-" + type + "-[";
        try {
            if (this.options != null) {
                for (int i = 0; i < options.length; i++) {
                    if (i == options.length - 1) {
                        toString += options[i];
                    } else {
                        toString += options[i] + "~";
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Options is null!!!");
        }
        toString += "]>";
        return toString; //Have only put "," so that its easier for file IO
    }
}
