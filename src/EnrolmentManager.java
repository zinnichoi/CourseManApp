import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  * author : Nguyễn Mạnh Tiến 3c14 hanu.
 * @overview a class manage enrolment in sql database.
 * @attribute :
 * app : MyDBApp.
 */
public class EnrolmentManager {
    MyDBApp app;

    /**
     * @effect :
     * create a object manageStudent with MyDBApp.
     */
    public EnrolmentManager(MyDBApp app) {
        this.app = app;
    }

    /**
     * @effect : add a enrolment into database.
     * if student id, course id, semester, final grade are valid
     * if student id is exist and course id is exist
     * if enrolment is not exist in database
     * if student is eligible to enrol
     * using method insert in MyDBApp
     * return true
     * else
     * return false
     */
    public boolean addEnrolment(int studentId, String courseId, int semester, String finalGrade) {
        if (isValidStudentId(studentId) && isValidCourseId(courseId) && isValidSemester(semester) && isValidFinalGrade(finalGrade)) {
            if (StudentsManager.isExistStudent(studentId, app) && CoursesManager.isExistCourse(courseId, app)) {
                if (!isExistEnrolment(studentId, courseId)) {
                    if (isEligibleToEnrol(studentId, courseId)) {
                        String sql = String.format("INSERT INTO Enrolment VALUES (%d,'%s', %d,'%s');", studentId, courseId, semester, finalGrade);
                        return app.insert(sql);
                    }
                } else {
                    TextIO.putln("Enrolment with studentId :" + studentId + " , courseID : " + courseId + " existed !");
                    return false;
                }
            }else {
                TextIO.putln("Studentid or courseid existed");
            }
        }
        return false;
    }

    /**
     * @effect : update final grade of a enrolment from database
     * if student id, course id, final grade are valid
     * using method update in MyDBApp
     * return true
     * else
     * return false
     */
    public boolean updateFinalGrade(int studentID, String courseID, String finalGrade) {
        if (isValidStudentId(studentID) && isValidCourseId(courseID) && isValidFinalGrade(finalGrade)) {
            String sql = String.format("UPDATE Enrolment \n" +
                    "SET finalgrade = '%s' \n" +
                    "WHERE student = %d AND course = '%s';", finalGrade, studentID, courseID);
            return app.update(sql);
        }
        return false;
    }

    /**
     * @effect : write to HTML file present information of all enrolment.
     */
    public boolean enrolmentToHTML() {
        File file = new File("enrols.html");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(app.selectToHtml("SELECT* fROM Enrolment"));
            TextIO.putln("Writed to : "+file.getAbsolutePath());
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @effect : write to HTML file present information of all enrolment sorted by descending order.
     */
    public boolean enrolmentSortedToHTML() {
        File file = new File("enrols_sorted.html");
        String sql = "SELECT * " +
                "FROM Enrolment " +
                "ORDER BY " +
                " CASE finalgrade " +
                "   WHEN  'E' THEN 1 " +
                "   WHEN  'G' THEN 2 " +
                "   WHEN  'P' THEN 3 " +
                "   WHEN  'F' THEN 4 " +
                "   ELSE 5" +
                "   END";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(app.selectToHtml(sql));
            TextIO.putln("Writed to "+file.getAbsolutePath());
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @return a String represent information of enrolment with studentId and courseId
     */
    public String enrolmentToString(int studentID, String courseID) {
        String sql = String.format("SELECT* FROM Enrolment WHERE student = %d AND course = '%s' ;", studentID, courseID);
        return app.selectToString(sql);
    }

    /**
     * @effect : check exist enrolment with studentId and courseId.
     */
    public boolean isExistEnrolment(int studentID, String courseID) {
        String sql = String.format("SELECT* FROM Enrolment WHERE student = %d AND course = '%s';", studentID, courseID);
        if (!app.selectToString(sql).equals("empty")){
            return true;
        }
        return false;
    }

    /**
     * @return String present menu of manage enrolment.
     */
    public String menu() {
        return "*-----------------------MANAGE ENROLMENT------------------------*\n" +
                "* Enter your choice                                             *\n" +
                "*1. Add a new enrolment                                         *\n" +
                "*2. Enter grade for a student's enrolment                       *\n" +
                "*3. Display a list of all enrolments                            *\n" +
                "*4. Display a list of all enrolments sorted in descending grade *\n" +
                "*5. Back to menu                                                *\n" +
                "*0. Exit                                                        *\n" +
                "*---------------------------------------------------------------*\n";
    }

    /**
     * @effect : validate studentid
     * if studentid > 0
     * return true
     * else
     * print err message
     * return false
     */
    public boolean isValidStudentId(int studentId) {
        if (studentId >= 0) {
            return true;
        }
        TextIO.putln("studentid can not smaller than 0");
        return false;
    }

    /**
     * @effect : validate courseid
     * if courseid != null /\ 0 < courseid,length < 5
     * return true
     * else
     * print err message
     * return false
     */
    public boolean isValidCourseId(String courseID) {
        if (courseID != null && courseID.length() > 0 && courseID.length() <= 5) {
            return true;
        }
        TextIO.putln("invalid last name!");
        return false;
    }

    /**
     * @effect : validate semester
     * if 1 <= semester <= 8
     * return true
     * else
     * print err message
     * return false
     */
    public boolean isValidSemester(int semester) {
        if (semester >= 1 && semester <= 8) {
            return true;
        }
        TextIO.putln("semester must from 1 - 8");
        return false;
    }

    /**
     * @effect : validate finalGrade
     * if finalGrade is one of 'E','G','P','F' and '_'
     * return true
     * else
     * print err message
     * return false
     */
    public boolean isValidFinalGrade(String finalGrade) {
        if (finalGrade.equals("E") || finalGrade.equals("G") || finalGrade.equals("P") || finalGrade.equals("F") || finalGrade.equals("_")) {
            return true;
        }
        TextIO.putln("invalid final grade");
        return false;
    }

    /**
     * @effect : check eligible to enrol
     * if exist courseid
     * get prerequisites
     * if exist enrolment with studentid and course = prequisites
     * return true
     * else print err message
     * return false.
     */
    public boolean isEligibleToEnrol(int studentID, String courseId) {
        // get  prerequisites
        String prerequisites = app.selectToString("SELECT prerequisites FROM Course WHERE courseid = '" + courseId + "';").substring(0,3);
        if (isExistEnrolment(studentID,prerequisites)){
            return true;
        }
        if (prerequisites.equals("-")){
            return true;
        }
        TextIO.putln("not eligible to enrol!");
        return false;
    }
}
