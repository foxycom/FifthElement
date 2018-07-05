package fifthelement.theelement.presentation.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.support.v7.widget.Toolbar;
import fifthelement.theelement.R;

public class SettingFragment extends Fragment {

    private View view;
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // setContentView(R.layout.main);

        // Find the ListView resource.
        view = inflater.inflate(R.layout.setting_fragment, container, false);
        mainListView = view.findViewById(R.id.library_view);


        // Create and populate a List of for the library.
        String[] options = new String[]{"Theme1", "Theme2", "Theme3", "Delete Songs", "Hide album art notification "};
        ArrayList<String> libraryList = new ArrayList<String>();
        libraryList.addAll(Arrays.asList(options));

        // Create ArrayAdapter using the library list.
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, libraryList);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Called: " + position);
                switch(position) {
                    case 0:
                        getActivity().setTheme(R.style.AppTheme5);
                        break;
                    case 1:
                        getActivity().setTheme(R.style.AppTheme);
                        break;
                    case 2:
                        getActivity().setTheme(R.style.AppTheme3);
                }

            }
        });

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);
        return view;
    }
}