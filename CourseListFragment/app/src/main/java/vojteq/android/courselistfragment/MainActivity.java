package vojteq.android.courselistfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import vojteq.android.courselistfragment.data.Course;

public class MainActivity extends AppCompatActivity implements CourseListFragment.Callbacks {

    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detailContainer) != null) {
            isTwoPane = true;
        }
    }

    @Override
    public void onItemSelected(Course course, int position) {
        if (isTwoPane) {
            Bundle bundle = new Bundle();
            bundle.putInt("course_id", position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            CourseDetailFragment courseDetailFragment = new CourseDetailFragment();
            courseDetailFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.detailContainer, courseDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
            intent.putExtra("course_id", position);
            startActivity(intent);
        }
    }
}