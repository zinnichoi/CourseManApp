import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  * author : Nguyễn Mạnh Tiến 3c14 hanu.
 * @overview a class report in sql database.
 * @attribute :
 * app : MyDBApp.
 */
public class Report {
    MyDBApp app ;

    /**
     * @effect : create a object report with MyDBApp.
     */
    public Report(MyDBApp app) {
        this.app = app;
    }

    /**
     * @effect : write to a html file present all enrol of a student.
     */
    public boolean myEnrols(int studentId){
        File file = new File("my_enrols.html");
        if (StudentsManager.isExistStudent(studentId,app)){
            String sql = "SELECT course.courseid, course.name , finalgrade FROM student JOIN enrolment ON student.studentid = enrolment.student JOIN course ON course.courseid = enrolment.course WHERE student.studentid = "+studentId+";";

            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
                bufferedWriter.write(app.selectToHtml(sql));
                TextIO.putln("writed to : "+file.getAbsolutePath());
                return true;
            }catch (IOException ex){
                ex.printStackTrace();
                return false;
            }
        }else {
            TextIO.putln("Student with id = "+studentId+" does not exist.");

        }
        return false;

    }

    /**
     * @effect : write to a html file represent all student of a course.
     */
    public boolean courseEnrol(String courseId){
        File file = new File("course_enrols.html");
        if (CoursesManager.isExistCourse(courseId,app)){
            String sql = "SELECT student.studentid, student.firstname, student.lastname, finalgrade FROM student JOIN enrolment ON student.studentid = enrolment.student JOIN course ON course.courseid = enrolment.course WHERE course.courseid ='" + courseId + "';";
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
                bufferedWriter.write(app.selectToHtml(sql));
                TextIO.putln("Writed to "+file.getAbsolutePath());
                return true;
            }catch (IOException ex){
                ex.printStackTrace();
                return false;
            }
        }else {
            TextIO.putln("Course with id = "+courseId+" does not exist.");
        }
        return false;
    }

    /**
     *@effect : write to a html file present all student who failed at least one course.
     */
    public boolean fails(){
        File file = new File("fails.html");
        String sql = "SELECT student.studentid, student.firstname, student.lastname, course.courseid, course.name , finalgrade FROM student JOIN enrolment ON student.studentid = enrolment.student JOIN course ON course.courseid = enrolment.course WHERE enrolment.finalgrade = 'F'";
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            bufferedWriter.write(app.selectToHtml(sql));
            TextIO.putln("Writed to"+file.getAbsolutePath());
            return true;
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @return : a String present report menu.
     */
    public String menu(){
        return  "*---------------------------REPORT------------------------------*\n" +
                "* Enter your choice                                             *\n" +
                "*1. Display a list of all courses of a given student            *\n" +
                "*2. Display all students of a given course                      *\n" +
                "*3. Display a list of all students failed at least one course   *\n" +
                "*4. Back to menu                                                *\n" +
                "*0. Exit                                                        *\n" +
                "*---------------------------------------------------------------*\n";
    }
}
