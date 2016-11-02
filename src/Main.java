/**
 * author : Nguyễn Mạnh Tiến 3c14 hanu.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        MyDBApp app = new MyDBApp();
        StudentsManager studentsManager = new StudentsManager(app);
        CoursesManager coursesManager = new CoursesManager(app);
        EnrolmentManager enrolmentManager = new EnrolmentManager(app);
        Report report = new Report(app);
        Scanner input = new Scanner(System.in);

        // connect to database.
        while (true){
            System.out.println("enter name of database (courseman): ");
            String dbName = input.nextLine();
            System.out.println("enter user name(postgres) : ");
            String userName = input.nextLine();
            System.out.println("enter password(123456) : ");
            String password = input.nextLine();
            if (app.connect(dbName,userName,password)){
                break;
            }else {
                System.out.println("invalid input rewrite, please ! ");
            }
        }



        int choice;
        int select;
        do {
            System.out.println("*-------------------------COURSEMAN APP-------------------------*");
            System.out.println("* Enter your select                                             *");
            System.out.println("*1. Manage student                                              *");
            System.out.println("*2. Manage course                                               *");
            System.out.println("*3. Manage enrolment                                            *");
            System.out.println("*4. Report                                                      *");
            System.out.println("*0. Exit                                                        *");
            System.out.println("*---------------------------------------------------------------*");

            choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                // Manage student.
                case 1: {
                    do {
                        System.out.println(studentsManager.menu());
                        select = input.nextInt();
                        input.nextLine();
                        switch (select) {
                            // Add a new student
                            case 1: {
                                System.out.println("Enter student id : ");
                                int studentID = input.nextInt();
                                input.nextLine();
                                System.out.println("Enter first name : ");
                                String firstName = input.nextLine();
                                System.out.println("Enter last name  :");
                                String lastName = input.nextLine();
                                System.out.println("Enter address    :");
                                String address = input.nextLine();
                                System.out.println("Enter date of birth ");
                                String dob = input.nextLine();

                                if (studentsManager.addStudent(studentID, firstName, lastName, address, dob)) {
                                    System.out.println("Add complete !");
                                } else {
                                    System.out.println("Can not add !");
                                }

                                break;
                            }

                            // Edit information of an existing student
                            case 2: {
                                System.out.println("Enter student id : ");
                                int studentID = input.nextInt();
                                input.nextLine();
                                if (studentsManager.isExistStudent(studentID,app)) {
                                    System.out.println("Student information :");
                                    System.out.println(studentsManager.studentToString(studentID));
                                    System.out.println("enter information want to edit, rewrite info dont want edit");
                                    System.out.println("Enter first name : ");
                                    String firstName = input.nextLine();
                                    System.out.println("Enter last name  :");
                                    String lastName = input.nextLine();
                                    System.out.println("Enter address    :");
                                    String address = input.nextLine();
                                    System.out.println("Enter date of birth ");
                                    String dob = input.nextLine();

                                    if (studentsManager.editStudent(studentID, firstName, lastName, address, dob)) {
                                        System.out.println("Edit complete");
                                    } else {
                                        System.out.println("Infomation input is not valid : ");
                                    }
                                } else {
                                    System.out.println("Can not edit !");
                                }

                                break;
                            }

                            // Delete a student
                            case 3: {
                                System.out.println("Enter student id :");
                                int studentID = input.nextInt();

                                if (studentsManager.deleteStudent(studentID)) {
                                    System.out.println("Delete complete !");
                                } else {
                                    System.out.println("Can not delete ! ");
                                }

                                break;
                            }

                            // Display a table of all students
                            case 4: {
                                if (studentsManager.studentToHTML()) {
                                    System.out.println("Write to students.html complete !");
                                } else {
                                    System.out.println("Can not write to students.html !");
                                }

                                break;
                            }

                            // Exit program
                            case 0:
                                System.exit(0);
                        }
                    } while (select != 5);
                    break;
                    // Back to main menu5
                }

                // Manage course.
                case 2: {
                    do {
                        System.out.println(coursesManager.menu());
                        select = input.nextInt();
                        input.nextLine();
                        switch (select) {
                            // Add a new course
                            case 1: {
                                System.out.println("Enter course id : ");
                                String courseId = input.nextLine();
                                System.out.println("Enter course name : ");
                                String name = input.nextLine();
                                System.out.println("Enter prerequisites : ");
                                String prerequisites = input.nextLine();

                                if (coursesManager.addCourse(courseId, name, prerequisites)) {
                                    System.out.println("Add complete !");
                                } else {
                                    System.out.println("Can not add !");
                                }
                                break;
                            }

                            // Edit information of an existing course
                            case 2: {
                                System.out.println("Enter course id :");
                                String courseId = input.nextLine();

                                if (coursesManager.isExistCourse(courseId,app)) {
                                    System.out.println("Course information");
                                    System.out.println(coursesManager.courseToString(courseId));
                                    System.out.println("enter information want to edit, rewrite info dont want edit");
                                    System.out.println("Enter course name : ");
                                    String name = input.nextLine();
                                    System.out.println("Enter prerequisites : ");
                                    String prerequisites = input.nextLine();

                                    if (coursesManager.editCourse(courseId, name, prerequisites)) {
                                        System.out.println("edit infomation conplete !");
                                    } else {
                                        System.out.println("input is not valid : ");
                                    }
                                } else {
                                    System.out.println("Can not edit ! ");
                                }

                                break;
                            }

                            // Delete a course
                            case 3: {
                                System.out.println("Enter course id : ");
                                String courseId = input.nextLine();

                                if (coursesManager.deleteCourse(courseId)) {
                                    System.out.println("Delete course complete !");
                                } else {
                                    System.out.println("can not delete : ");
                                }

                                break;
                            }

                            // Display a list of all courses.
                            case 4: {
                                if (coursesManager.courseToHTML()) {
                                    System.out.println("Write to courses.html complete !");
                                } else {
                                    System.out.println("Can not write to courses.html !");
                                }
                                break;
                            }

                            // exit program.
                            case 0: {
                                System.exit(0);
                            }
                        }
                    } while (select != 5);
                    // back to main menu
                    break;
                }

                // Manage enrolment
                case 3: {
                    do {
                        System.out.println(enrolmentManager.menu());
                        select = input.nextInt();
                        input.nextLine();
                        switch (select) {
                            // Add an enrolment.
                            case 1: {
                                System.out.println("enter student id :");
                                int studentID = input.nextInt();
                                input.nextLine();
                                System.out.println("enter course id : ");
                                String courseID = input.nextLine();
                                System.out.println("enter semester : ");
                                int semester = input.nextInt();
                                input.nextLine();
                                System.out.println("enter final grade : ");
                                String finalGrade = input.nextLine();

                                if (enrolmentManager.addEnrolment(studentID, courseID, semester, finalGrade)) {
                                    System.out.println("add enrolment complete ! ");
                                } else {
                                    System.out.println("can not add !");
                                }

                                break;
                            }

                            // Enter grade for a student's enrolment.
                            case 2: {
                                System.out.println("enter student id :");
                                int studentID = input.nextInt();
                                input.nextLine();
                                System.out.println("enter course id : ");
                                String courseID = input.nextLine();

                                if (enrolmentManager.isExistEnrolment(studentID, courseID)) {
                                    System.out.println("Enrolment infomation : ");
                                    System.out.println(enrolmentManager.enrolmentToString(studentID, courseID));
                                    System.out.println("enter information want to edit, rewrite info dont want edit");
                                    System.out.println("enter final grade : ");
                                    String finalGrade = input.nextLine();
                                    if (enrolmentManager.updateFinalGrade(studentID, courseID, finalGrade)) {
                                        System.out.println("Update final grade complete !");
                                    } else {
                                        System.out.println("Infomation input is not valid :");
                                        System.out.println("Can not edit ! ");
                                    }
                                } else {
                                    System.out.println("Can not edit ! ");
                                }

                                break;
                            }

                            // Display a list of all enrolments.
                            case 3: {
                                if (enrolmentManager.enrolmentToHTML()) {
                                    System.out.println("Write to enrols.html complete !");
                                } else {
                                    System.out.println("Can not write to enrols.html !");
                                }
                                break;
                            }

                            // Display a list of all enrolments sorted in descending order of grade.
                            case 4: {
                                if (enrolmentManager.enrolmentSortedToHTML()) {
                                    System.out.println("Write to enrols_sorted.html complete !");
                                } else {
                                    System.out.println("Can not write to enrols_sorted.html !");
                                }
                                break;
                            }

                            // Exit program.
                            case 0:
                                System.exit(0);
                        }
                    } while (select != 5);
                    // Back to main menu.
                    break;
                }

                // Report.
                case 4: {
                    do {
                        System.out.println(report.menu());
                        select = input.nextInt();
                        input.nextLine();
                        switch (select) {
                            // Display a list of all courses of a given student.
                            case 1: {
                                System.out.println("enter student id : ");
                                int studentID = input.nextInt();
                                input.nextLine();
                                if (report.myEnrols(studentID)) {
                                    System.out.println("Write to my_enrols.html complete !");
                                } else {
                                    System.out.println("Can not write to my_enrols.html !");
                                }
                            break;
                            }

                            // Display all students of a given course.
                            case 2: {
                                System.out.println("enter course id : ");
                                String courseID = input.nextLine();
                                    if (report.courseEnrol(courseID)) {
                                        System.out.println("Write to course_enrols.html complete !");
                                    } else {
                                        System.out.println("Can not write to course_enrols.html !");
                                    }
                                break;
                            }

                            // Display a list of all students who have failed at least one course.
                            case 3: {
                                if (report.fails()) {
                                    System.out.println("Write to fails.html complete !");
                                } else {
                                    System.out.println("Can not write to fails.html !");
                                }

                                break;
                            }

                            // Exit program.
                            case 0:
                                System.exit(0);
                        }
                    } while (select != 4);
                    // Back to main menu.
                    break;
                }
            }
        } while (choice != 0);
        // Close MyDBApp.
        app.close();
    }
}
