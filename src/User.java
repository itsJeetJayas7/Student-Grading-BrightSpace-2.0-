import java.io.*;
import java.util.ArrayList;

/**
 * User method for Project 4
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Santiago Lopez, Jeet Jayas, Abhi Vandalore, Surya Perla, Patric Wang
 * @version April 11, 2022
 */
public class User {
    private String fullName;
    private String userName;
    private String password;
    private String type;

    public User() {
    }

    public User(String fullName, String userName, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.type = userName.substring(userName.indexOf("@") + 1);
        /*if (type.equals("student")) {
            Student student = new Student(fullName, userName, password);
        }*/
        try {
            File f = new File("UserLibrary.txt");
            PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));

            pw.println(fullName + " " + type);
            pw.println(userName);
            pw.println(password);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        try {
            File f = new File("UserLibrary.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            ArrayList<String> arrayList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                arrayList.add(line);
            }
            br.close();
            for (int i = 0; i < arrayList.size(); i += 3) {
                if (arrayList.get(i + 1).equals(userName) && arrayList.get(i + 2).equals(password)) {
                    this.fullName = arrayList.get(i).substring(0, arrayList.get(i).length() - 8);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser(String userName, String password) {
        try {
            File f = new File("UserLibrary.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            for (int i = 0; i < lines.size(); i += 3) {
                String checkType = lines.get(i).substring(lines.get(i).length() - 7);
                String checkUserName = lines.get(i + 1);
                String checkPW = lines.get(i + 2);
                if (checkUserName.equals(userName) && checkPW.equals(password)) {
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
}
