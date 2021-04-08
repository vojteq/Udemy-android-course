package vojteq.android.courselistfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vojteq.android.courselistfragment.data.Course;
import vojteq.android.courselistfragment.data.CourseData;

public class CourseDetailFragment extends Fragment {

    private Course course;

    public CourseDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("course_id")) {
            int position = bundle.getInt("course_id");
            course = new CourseData().courseList().get(position);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_detail_fragment, container, false);
        if (course != null) {
            TextView courseName = view.findViewById(R.id.detailCourseNameTextView);
            courseName.setText(course.getCourseName());
            ImageView courseImage = view.findViewById(R.id.detailCourseImageView);
            courseImage.setImageResource(course.getImageResourceId(getActivity()));
            TextView courseDescription = view.findViewById(R.id.detailCourseDescriptionTextView);
            courseDescription.setText(course.getCourseName());
        }
        return view;
    }
}
