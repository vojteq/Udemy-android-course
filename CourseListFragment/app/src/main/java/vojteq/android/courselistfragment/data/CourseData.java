package vojteq.android.courselistfragment.data;

import java.util.ArrayList;

public class CourseData {
    private String[] courseNames = {"First Course", "Second Course", "Third Course", "Fourth Course", "Fifth Course", "Sixth Course", "Seventh Course"};

    public ArrayList<Course> courseList() {
        ArrayList<Course> list = new ArrayList<>();

        for (String courseName : courseNames) {
            list.add(new Course(
                    courseName,
                    courseName.replace(" ", "").toLowerCase())
            );
        }

        return list;
    }
}
