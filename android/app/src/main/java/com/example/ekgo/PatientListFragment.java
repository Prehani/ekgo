package ekgo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Fragment;

/**
 * Fragment that will populate the listview of patients in the main_screen
 */
public class PatientListFragment extends Fragment {

    View view;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.patient_list, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);

        return view;
    }
}
