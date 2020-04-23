package ekgo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

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

        // Bind PatientInterfaceService to this activity
        Intent intent = new Intent(this, PatientInterfaceService.class);
        intent.putExtra("username", username);
        bindService(intent, connection, getApplicationContext().BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        pBound = false;
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
