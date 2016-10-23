import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
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
        String sql = "SELECT* FROM Enrolment WHERE student = "+studentId+";" ;
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("my_enrols.html"))){
            bufferedWriter.write(app.selectToHtml(sql));
            return true;
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @effect : write to a html file represent all student of a course.
     */
    public boolean courseEnrol(String courseId){
        String sql = "SELECT* FROM Enrolment WHERE course = '"+courseId+"';";
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("course_enrols.html"))){
            bufferedWriter.write(app.selectToHtml(sql));
            return true;
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     *@effect : write to a html file present all student who failed at least one course.
     */
    public boolean fails(){
        String sql = "SELECT* FROM Enrolment WHERE finalgrade = 'F' ;";
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("fails.html"))){
            bufferedWriter.write(app.selectToHtml(sql));
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
