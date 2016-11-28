import java.util.Scanner;

/**
 * Created by zin ni choi on 11/16/2016.
 */
public class Test {
    public static void main(String[] args) {
        MyDBApp app = new MyDBApp();
        StudentsManager studentsManager = new StudentsManager(app);
        CoursesManager coursesManager = new CoursesManager(app);
        EnrolmentManager enrolmentManager = new EnrolmentManager(app);
        Report report = new Report(app);
        Scanner input = new Scanner(System.in);

        // connect to database.
        app.connect("courseman","postgres","123456");
        System.out.println(studentsManager.validateStudentId(2));
        System.out.println(studentsManager.validateStudentId(65));
        System.out.println(enrolmentManager.isEligibleToEnrol(1,"PPL"));


    }
}
