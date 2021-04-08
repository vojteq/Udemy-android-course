package vojteq.android.courselistfragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.List;

import vojteq.android.courselistfragment.data.Course;
import vojteq.android.courselistfragment.data.CourseArrayAdapter;
import vojteq.android.courselistfragment.data.CourseData;
import vojteq.android.courselistfragment.util.ScreenUtil;

public class MyFragment extends ListFragment {

    private List<Course> courses = new CourseData().courseList();


    public MyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtil screenUtil = new ScreenUtil(getActivity());
        Log.d("WIDTH", String.valueOf(screenUtil.getDpWidth()));
        Log.d("HEIGHT", String.valueOf(screenUtil.getDpHeight()));
        CourseArrayAdapter adapter = new CourseArrayAdapter(getActivity(), R.layout.course_list_item, courses);
        setListAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false);
    }
}
