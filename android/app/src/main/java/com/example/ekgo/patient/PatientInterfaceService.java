package ekgo.patient;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Date;

public class PatientInterfaceService extends Service {

    private ArrayList<PatientData> patients;
    private String username;

    @Override
    public IBinder onBind(Intent intent) {
        //TODO: figure out how username is being passed around the application
        this.username = intent.getStringExtra("username");

        PatientDBHelper patientDBHelper = getPatientDBHelper();

        //TODO: some kind of verification
        this.patients = patientDBHelper.readPatients(this.username);

        return null;
    }

    public ArrayList<PatientData> getPatients() { return this.patients; }

    public void addPatient(Date dob, int weight, int height, String medications,
                           String conditions, String notes) {
        // TODO: better patient ID assignment
        PatientData patient = new PatientData(patients.size()+1, dob, weight, height, medications,
                conditions, notes);
        this.patients.add(patient);

        // Saves new patient to DB
        PatientDBHelper patientDBHelper = getPatientDBHelper();
        patientDBHelper.savePatient(this.username, patient);
    }

    public void updateDB() {
        PatientDBHelper patientDBHelper = getPatientDBHelper();

        for(PatientData patient : this.patients) {
            patientDBHelper.updatePatient(patient);
        }
    }


    private PatientDBHelper getPatientDBHelper() {
        Context context = getApplicationContext();
        SQLiteDatabase patientDB = context.openOrCreateDatabase("patients", Context.MODE_PRIVATE, null);
        return new PatientDBHelper(patientDB);
    }

}