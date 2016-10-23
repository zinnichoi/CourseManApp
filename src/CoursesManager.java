import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @overview : a class manage course in database.
 * @attribute :
 *     app  : MyDBApp
 */
public class CoursesManager {
    MyDBApp app;

    /**
     * @effect : create a object CoursesManager with MyDBApp
     */
    public CoursesManager(MyDBApp app) {
        this.app = app;
    }

    /**
     * @effect : add a student into database.
     * if courseId,name, prerequisites is valid
     * if courseId is not exist in database
     *       using method insert in MyDBApp
     *              return true
     * else
     *              return false
     */
    public boolean addCourse(String courseId,String name, String prerequisites){
        if (isValidCourseId(courseId) && isValidname(name) && isValidPrerequisites(prerequisites)){
            if (!isExistCourse(courseId)){
                String sql = String.format("INSERT INTO Course VALUES ('%s','%s','%s');",courseId,name,prerequisites);
                return app.insert(sql);
            }
        }
        return false;
    }

    /**
     * @effect : edit a course information from database
     * if courseID, name, prerequisites is valid
     * using method update in MyDBApp
     *      return true
     * else
     *      return false
     */
    public boolean editCourse(String courseId, String name, String prerequisites){
        if (isValidCourseId(courseId) && isValidname(name) && isValidPrerequisites(prerequisites)){
            String sql = String.format("UPDATE Course \n" +
                    "SET name = '%s',prerequisites = '%s' \n" +
                    "WHERE courseid = '%s';",name,prerequisites,courseId);
            return app.update(sql);
        }
        return false;
    }

    /**
     * @effect :delete a course information from database
     * if courseid is exist
     * using method delete in MyDBApp
     * return true
     * else
     * return false
     */
    public boolean deleteCourse(String courseId){
        if (isValidCourseId(courseId) && isValidCourseId(courseId)){
            String sql = String.format("DELETE FROM Course WHERE courseid ='%s';", courseId);
            return app.delete(sql);
        }
        return false;
    }

    /**
     * @effect : write to HTML file present information of all course.
     */
    public boolean courseToHTML() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("courses.html"))) {
            bufferedWriter.write(app.selectToHtml("SELECT* fROM Course"));
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @effect : return a string present information of all course.
     */
    public String courseToString(String courseId){
        return app.selectToString("SELECT* FROM Course WHERE courseid = '"+courseId+"';");
    }

    /**
     * @effect : check wheare a course is exist in database by courseid
     * using method selectToString in MyDBApp
     * if selectToString return 'empty'
     * return false
     * else
     * return true
     */
    public boolean isExistCourse(String courseId){
        String sql = "SELECT * from Course WHERE courseid = '" + courseId + "';";
        return !app.selectToString(sql).equals("empty");
    }

    /**
     * @effect
     *      return a string present manage course.
     */
    public String menu(){
        return  "*--------------------------MANAGE COURSE------------------------*\n" +
                "* Enter your choice                                             *\n" +
                "*1. Add a new course                                            *\n" +
                "*2. Edit course's information                                   *\n" +
                "*3. Delete a course                                             *\n" +
                "*4. Display a list of all courses                               *\n" +
                "*5. Back to menu                                                *\n" +
                "*0. Exit                                                        *\n" +
                "*---------------------------------------------------------------*\n";
    }

    /**
     * @effect :  check courseid is valid
     * if courseid != null /\ 0 < courseid.length <= 5
     * return true
     * else
     * print error message
     * return false
     */
    private boolean isValidCourseId(String courseId){
        if (courseId != null && courseId.length() > 0 && courseId.length() <= 5){
            return true;
        }
        System.err.println("invalid course id!");
        return false;
    }

    /**
     * @effect :  check name is valid
     * if name != null /\ 0 < name.length <= 200
     * return true
     * else
     * print error message
     * return false
     */
    private boolean isValidname(String name){
        if (name != null && name.length() > 0 && name.length() <= 200){
            return true;
        }
        System.err.println("invalid name!");
        return false;
    }

    /**
     * @effect :  check prerequisites is valid
     * if prerequisites != null /\ 0 < prerequisites.length <= 250
     * return true
     * else
     * print error message
     * return false
     */
    private boolean isValidPrerequisites(String prerequisites){
        if (prerequisites != null && prerequisites.length() > 0 && prerequisites.length() <= 250 || prerequisites.equals("_")){
            return true;
        }
        System.err.println("invalid prerequisites!");
        return false;
    }

    /**
     * @effect :return prequisites of a course
     * if course id is not exist
     * return '_'
     * else
     * using selectToString from MyDBApp to get prerequisites.
     */
    public String getPrerequisites(String courseId){
        if (!isExistCourse(courseId)){
            return "_";
        }
        String sql = "SELECT prerequisites FROM Course WHERE courseid = '"+courseId+"';";
        return app.selectToString(sql);
    }
}