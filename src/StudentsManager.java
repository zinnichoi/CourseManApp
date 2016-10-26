import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @overview a class manage student in sql database.
 * @attribute :
 * app : MyDBApp.
 */
public class StudentsManager {
    private MyDBApp app;

    /**
     * @effect :
     * create a object manageStudent with MyDBApp.
     */
    public StudentsManager(MyDBApp app) {
        this.app = app;
    }


    /**
     * @effect : add a student into database.
     * if firstname, lastname, address, dateofbirth is valid
     * if studentid is not exist in database
     * using method insert in MyDBApp
     * return true
     * else
     * return false
     */
    public boolean addStudent(int studentID, String firstName, String lastName, String address, String dob) {
        if (isValidStudentId(studentID) && isValidFirstName(firstName) && isValidLastName(lastName) && isValidAddress(address) && isValidDateOfBirth(dob)) {
            if (!isExistStudent(studentID,app)) {
                String sql = String.format("INSERT INTO Student VALUES (%d,'%s','%s','%s','%s');", studentID, firstName, lastName, address, dob);
                return app.insert(sql);
            } else {
                System.err.println("Student id existed !");
                return false;
            }
        }
        return false;
    }

    /**
     * @effect : edit a student information from database
     * if firstname, lastname,address, date od birth is valid
     * using method update in MyDBApp
     * return true
     * else
     * return false
     */
    public boolean editStudent(int studentId, String firstName, String lastname, String address, String dob) {
        if (isValidFirstName(firstName) && isValidLastName(lastname) && isValidAddress(address) && isValidDateOfBirth(dob)) {
            String sql = String.format("UPDATE Student \n" +
                    "SET firstname = '%s',lastname = '%s',address = '%s',dateofbirth = '%s' \n" +
                    "WHERE studentid = %d;", firstName, lastname, address, dob, studentId);
            return app.update(sql);
        }
        return false;
    }

    /**
     * @effect :delete a student information from database
     * if studentid is exist
     * using method delete in MyDBApp
     * return true
     * else
     * return false
     */
    public boolean deleteStudent(int studentId) {
        if (isExistStudent(studentId,app)) {
            String sql = String.format("DELETE FROM Student WHERE studentid = %d;", studentId);
            return app.delete(sql);
        }
        return false;
    }

    /**
     * @effect : write to a HTML file present information of all student.
     */
    public boolean studentToHTML() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("students.html"))) {
            bufferedWriter.write(app.selectToHtml("SELECT* fROM Student"));
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @effect : return a string present information of all student.
     */
    public String studentToString(int studentID) {
        return app.selectToString("SELECT* fROM Student WHERE studentid = "+studentID+";");
    }



    /**
     * @effect : check wheare a student is exist in database by studentid
     * using method selectToString in MyDBApp
     * if selectToString return 'empty'
     * return false
     * else
     * return true
     */
    public static boolean isExistStudent(int studentId,MyDBApp app) {
        String sql = "SELECT * from Student WHERE studentid = '" + studentId + "';";
        if (!app.selectToString(sql).equals("empty")){
            return true;
        }
        System.err.println("student id : "+studentId+" does not exist ! ");
        return false;
    }

    /**
     *@effect :
     *      return a string present manage student menu
     */
    public String menu(){
        return  "*--------------------------MANAGE STUDENT-----------------------*\n"+
                "* Enter your choice                                             *\n" +
                "*1. Add a new student                                           *\n" +
                "*2. Edit student's information                                  *\n" +
                "*3. Delete a student                                            *\n" +
                "*4. Display a table of all students                             *\n" +
                "*5. Back to menu                                                *\n" +
                "*0. Exit                                                        *\n" +
                "*---------------------------------------------------------------*\n";
    }

    /**
     * @effect :  check firstName is valid
     * if firstName != null /\ 0 < firstName.length <= 50
     * return true
     * else
     * print error message
     * return false
     */
    private boolean isValidFirstName(String firstName) {
        if (firstName != null && firstName.length() > 0 && firstName.length() <= 50) {
            return true;
        }
        System.err.println("invalid first name!");
        return false;
    }

    /**
     * @effect :  check lastName is valid
     * if lastName != null /\ 0 < lastName.length <= 50
     * return true
     * else
     * print error message
     * return false
     */
    private boolean isValidLastName(String lastName) {
        if (lastName != null && lastName.length() > 0 && lastName.length() <= 50) {
            return true;
        }
        System.err.println("invalid last name!");
        return false;
    }

    /**
     * @effect :  check address is valid
     * if address != null /\ 0 < address.length <= 250
     * return true
     * else
     * print error message
     * return false
     */
    private boolean isValidAddress(String address) {
        if (address != null && address.length() > 0 && address.length() <= 250) {
            return true;
        }
        System.err.println("invalid address !");
        return false;
    }

    /**
     * @effect :  check dateOfBirth is valid
     * if dateOfBirth != null /\ 0 < dateOfBirth.length <= 30
     * return true
     * else
     * print error message
     * return false
     */
    private boolean isValidDateOfBirth(String dob) {
        if (dob != null && dob.length() > 0 && dob.length() < 30) {
            return true;
        }
        System.err.println("invalid date of birth !");
        return false;
    }

    public boolean isValidStudentId(int studentId) {
        if (studentId >= 0) {
            return true;
        }
        System.err.println("studentid can not smaller than 0");
        return false;
    }
}

