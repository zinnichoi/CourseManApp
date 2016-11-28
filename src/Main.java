/**
 * author : Nguyễn Mạnh Tiến 3c14 hanu.
 */


public class Main {
    public static void main(String[] args) {

        MyDBApp app = new MyDBApp();
        StudentsManager studentsManager = new StudentsManager(app);
        CoursesManager coursesManager = new CoursesManager(app);
        EnrolmentManager enrolmentManager = new EnrolmentManager(app);
        Report report = new Report(app);

        // connect to database.
        while (true){
            TextIO.put("enter name of database (courseman): ");
            String dbName = TextIO.getln();
            TextIO.put("enter user name(postgres) : ");
            String userName = TextIO.getln();
            TextIO.put("enter password(123456) : ");
            String password = TextIO.getln();
            if (app.connect(dbName,userName,password)){
                break;
            }else {
                TextIO.putln("invalid input rewrite, please ! ");
            }
        }



        int choice;
        int select;
        do {
            TextIO.putln("*-------------------------COURSEMAN APP-------------------------*");
            TextIO.putln("* Enter your select                                             *");
            TextIO.putln("*1. Manage student                                              *");
            TextIO.putln("*2. Manage course                                               *");
            TextIO.putln("*3. Manage enrolment                                            *");
            TextIO.putln("*4. Report                                                      *");
            TextIO.putln("*0. Exit                                                        *");
            TextIO.putln("*---------------------------------------------------------------*");

            choice = TextIO.getlnInt();
            switch (choice) {
                // Manage student.
                case 1: {
                    do {
                        TextIO.putln(studentsManager.menu());
                        select = TextIO.getlnInt();
                        switch (select) {
                            // Add a new student
                            case 1: {
                                TextIO.put("Enter student id : ");
                                int studentID = TextIO.getlnInt();
                                TextIO.put("Enter first name : ");
                                String firstName = TextIO.getln();
                                TextIO.put("Enter last name  :");
                                String lastName = TextIO.getln();
                                TextIO.put("Enter address    :");
                                String address = TextIO.getln();
                                TextIO.put("Enter date of birth ");
                                String dob = TextIO.getln();

                                if (studentsManager.addStudent(studentID, firstName, lastName, address, dob)) {
                                    TextIO.putln("Add complete !");
                                } else {
                                    TextIO.putln("Can not add !");
                                }

                                break;
                            }

                            // Edit information of an existing student
                            case 2: {
                                TextIO.put("Enter student id : ");
                                int studentID = TextIO.getlnInt();
                                if (studentsManager.isExistStudent(studentID,app)) {
                                    TextIO.putln("Student information :");
                                    TextIO.putln(studentsManager.studentToString(studentID));
                                    TextIO.putln("Enter information want to edit, rewrite info dont want edit");
                                    TextIO.put("Enter first name : ");
                                    String firstName = TextIO.getln();
                                    TextIO.put("Enter last name  :");
                                    String lastName = TextIO.getln();
                                    TextIO.put("Enter address    :");
                                    String address = TextIO.getln();
                                    TextIO.put("Enter date of birth ");
                                    String dob = TextIO.getln();

                                    if (studentsManager.editStudent(studentID, firstName, lastName, address, dob)) {
                                        TextIO.putln("Edit complete");
                                    } else {
                                        TextIO.putln("Infomation input is not valid : ");
                                    }
                                } else {
                                    TextIO.putln("Student with studentId :" + studentID +  " does not exist !");
                                    TextIO.putln("Can not edit !");
                                }

                                break;
                            }

                            // Delete a student
                            case 3: {
                                TextIO.put("Enter student id :");
                                int studentID = TextIO.getlnInt();
                                if (studentsManager.deleteStudent(studentID)) {
                                    TextIO.putln("Delete complete !");
                                } else {
                                    TextIO.putln("Can not delete ! ");
                                }

                                break;
                            }

                            // Display a table of all students
                            case 4: {
                                if (studentsManager.studentToHTML()) {
                                    TextIO.putln("Write to students.html complete !");
                                } else {
                                    TextIO.putln("Can not write to students.html !");
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
                        TextIO.putln(coursesManager.menu());
                        select = TextIO.getlnInt();
                        switch (select) {
                            // Add a new course
                            case 1: {
                                TextIO.put("Enter course id : ");
                                String courseId = TextIO.getln();
                                TextIO.put("Enter course name : ");
                                String name = TextIO.getln();
                                TextIO.put("Enter prerequisites : ");
                                String prerequisites = TextIO.getln();

                                if (coursesManager.addCourse(courseId, name, prerequisites)) {
                                    TextIO.putln("Add complete !");
                                } else {
                                    TextIO.putln("Can not add !");
                                }
                                break;
                            }

                            // Edit information of an existing course
                            case 2: {
                                TextIO.put("Enter course id :");
                                String courseId = TextIO.getln();
                                if (coursesManager.isExistCourse(courseId,app)) {
                                    TextIO.putln("Course information");
                                    TextIO.putln(coursesManager.courseToString(courseId));
                                    TextIO.putln("enter information want to edit, rewrite info dont want edit");
                                    TextIO.put("Enter course name : ");
                                    String name = TextIO.getln();
                                    TextIO.put("Enter prerequisites : ");
                                    String prerequisites = TextIO.getln();

                                    if (coursesManager.editCourse(courseId, name, prerequisites)) {
                                        TextIO.putln("edit infomation conplete !");
                                    } else {
                                        TextIO.putln("input is not valid : ");
                                    }
                                } else {
                                    TextIO.putln("Course with  courseID : " + courseId + " does not exist !");
                                    TextIO.putln("Can not edit ! ");
                                }

                                break;
                            }

                            // Delete a course
                            case 3: {
                                TextIO.put("Enter course id : ");
                                String courseId = TextIO.getln();

                                if (coursesManager.deleteCourse(courseId)) {
                                    TextIO.putln("Delete course complete !");
                                } else {
                                    TextIO.putln("can not delete  ");

                                }

                                break;
                            }

                            // Display a list of all courses.
                            case 4: {
                                if (coursesManager.courseToHTML()) {
                                    TextIO.putln("Write to courses.html complete !");
                                } else {
                                    TextIO.putln("Can not write to courses.html !");
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
                        TextIO.putln(enrolmentManager.menu());
                        select = TextIO.getlnInt();
                        switch (select) {
                            // Add an enrolment.
                            case 1: {
                                TextIO.put("enter student id :");
                                int studentID = TextIO.getlnInt();
                                TextIO.put("enter course id : ");
                                String courseID = TextIO.getln();
                                TextIO.put("enter semester : ");
                                int semester = TextIO.getlnInt();
                                TextIO.put("enter final grade : ");
                                String finalGrade = TextIO.getln();
                                if (enrolmentManager.addEnrolment(studentID, courseID, semester, finalGrade)) {
                                    TextIO.putln("add enrolment complete ! ");
                                } else {
                                    TextIO.putln("can not add !");
                                }

                                break;
                            }

                            // Enter grade for a student's enrolment.
                            case 2: {
                                TextIO.put("enter student id :");
                                int studentID = TextIO.getlnInt();
                                TextIO.put("enter course id : ");
                                String courseID = TextIO.getln();

                                if (enrolmentManager.isExistEnrolment(studentID, courseID)) {
                                    TextIO.putln("Enrolment infomation : ");
                                    TextIO.putln(enrolmentManager.enrolmentToString(studentID, courseID));
                                    TextIO.putln("enter information want to edit, rewrite info dont want edit");
                                    TextIO.put("enter final grade : ");
                                    String finalGrade = TextIO.getln();
                                    if (enrolmentManager.updateFinalGrade(studentID, courseID, finalGrade)) {
                                        TextIO.putln("Update final grade complete !");
                                    } else {
                                        TextIO.putln("Infomation input is not valid :");
                                        TextIO.putln("Can not edit ! ");
                                    }
                                } else {
                                    TextIO.putln("Can not edit ! ");
                                }

                                break;
                            }

                            // Display a list of all enrolments.
                            case 3: {
                                if (enrolmentManager.enrolmentToHTML()) {
                                    TextIO.putln("Write to enrols.html complete !");
                                } else {
                                    TextIO.putln("Can not write to enrols.html !");
                                }
                                break;
                            }

                            // Display a list of all enrolments sorted in descending order of grade.
                            case 4: {
                                if (enrolmentManager.enrolmentSortedToHTML()) {
                                    TextIO.putln("Write to enrols_sorted.html complete !");
                                } else {
                                    TextIO.putln("Can not write to enrols_sorted.html !");
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
                        TextIO.putln(report.menu());
                        select = TextIO.getlnInt();
                        switch (select) {
                            // Display a list of all courses of a given student.
                            case 1: {
                                TextIO.put("enter student id : ");
                                int studentID = TextIO.getlnInt();
                                if (report.myEnrols(studentID)) {
                                    TextIO.putln("Write to my_enrols.html complete !");
                                } else {
                                    TextIO.putln("Can not write to my_enrols.html !");
                                }
                            break;
                            }

                            // Display all students of a given course.
                            case 2: {
                                TextIO.put("enter course id : ");
                                String courseID = TextIO.getln();
                                    if (report.courseEnrol(courseID)) {
                                        TextIO.putln("Write to course_enrols.html complete !");
                                    } else {
                                        TextIO.putln("Can not write to course_enrols.html !");
                                    }
                                break;
                            }

                            // Display a list of all students who have failed at least one course.
                            case 3: {
                                if (report.fails()) {
                                    TextIO.putln("Write to fails.html complete !");
                                } else {
                                    TextIO.putln("Can not write to fails.html !");
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
