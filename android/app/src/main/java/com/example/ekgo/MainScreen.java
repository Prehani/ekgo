package ekgo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ekgo.patient.PatientData;
import ekgo.patient.PatientInterfaceService;

public class MainScreen extends AppCompatActivity {

    /**
     * To use PatientInterfaceService:
     *      Make calls to pBinder. Here's the API
     *
     *      void pBinder.addPatient(Date dob, int weight, int height, String medications,
     *                          String conditions, String notes);
     *             Adds a patient to the patient list
     *
     *      void pBinder.updatePatient(PatientData patient) throws PatientNotFoundException;
     *             Updates the patient within the database. If the patientId is not found,
     *             will throw a PatientNotFound error
     *
     *      void pBinder.deletePatient(int id) throws PatientNotFoundException;
     *             Deletes patient with given id from the database
     *
     *      ArrayList<PatientData> pBinder.getPatients();
     *             Gets the list of patients
     */
    //TODO: everytime there is an update to patient data, we will need to update the
    //      display. We can accomplish this by setting a flag in the pBinder to be checked
    //      for updates. Alternatively, we can somehow ping all fragments to actually update
    private PatientInterfaceService pService;
    private PatientInterfaceService.LocalBinder pBinder;
    private String username;
    private Boolean pBound;
    private Thread t;

    // fields required for patientList fragment
    private ListView patientListFragment;
    private ArrayAdapter<String> adapter;
    private ArrayList<PatientData> pData = pBinder.getPatients();
    private String[] patientNames = new String[pData.size()];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: actually make main_screen lmao
        setContentView(R.layout.main_screen);

        //TODO: set username based on whatever was passed from login activity
    }

    @Override
    public void onStart() {
        super.onStart();

        // Bind PatientInterfaceService to this activity in new thread
        t = new Thread() {
            public void run() {
                Intent intent = new Intent(MainScreen.this, PatientInterfaceService.class);
                intent.putExtra("username", username);

                bindService(intent, connection, getApplicationContext().BIND_AUTO_CREATE);
            }
        };

        // adds the patient list fragment to this activity the activity
        //patientListFragment = (ListView) findViewById(R.id.list_view);
        for (int i = 0; i < pData.size(); i++) {
            patientNames[i] = pData.get(i).toString();
        }

        patientListFragment = (ListView) findViewById(R.id.display);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patientNames);
        patientListFragment.setAdapter(adapter);
        patientListFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " will be sent to next fragment", Toast.LENGTH_LONG).show();
            }
        });
        loadFragment(new PatientListFragment());
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        pBound = false;
        t.stop();
    }

    public PatientInterfaceService.LocalBinder getpBinder() {
        if(pBound) return pBinder;
        return null;
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            pBinder = (PatientInterfaceService.LocalBinder) service;
            pService = pBinder.getService();
            pBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            pBound = false;
        }
    };
}
