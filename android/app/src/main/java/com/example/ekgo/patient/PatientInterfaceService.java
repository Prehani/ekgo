package ekgo.patient;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Date;

public class PatientInterfaceService extends IntentService {

    private final IBinder binder = new LocalBinder();


    private ArrayList<PatientData> patients;
    private String username;

    public PatientInterfaceService() {
        super("PatientInterfaceService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {}


    @Override
    public IBinder onBind(Intent intent) {
        this.username = intent.getStringExtra("username");

        PatientDBHelper patientDBHelper = getPatientDBHelper();

        this.patients = patientDBHelper.readPatients(this.username);

        return binder;
    }




    private ArrayList<PatientData> getPatients() { return this.patients; }

    private void addPatient(Date dob, int weight, int height, String medications,
                           String conditions, String notes) {
        PatientData patient = new PatientData(patients.size()+1, dob, weight, height, medications,
                conditions, notes);
        this.patients.add(patient);

        // Saves new patient to DB
        PatientDBHelper patientDBHelper = getPatientDBHelper();
        patientDBHelper.savePatient(this.username, patient);
    }

    private void updatePatient(PatientData patient) throws PatientNotFoundException {
        for(PatientData p : patients) {
            if(p.getId() == patient.getId()) {
                p.update(patient);
                updateDB();
                return;
            }
        }
        throw new PatientNotFoundException("Patient with ID" + patient.getId()
                + " not found in database");
    }

    private void deletePatient(int id) throws PatientNotFoundException {
        for(int i = 0; i < patients.size(); i++) {
            if(patients.get(i).getId() == id) {
                patients.remove(i);
                updateDB();
                return;
            }
        }
        throw new PatientNotFoundException("Patient with ID" + id + " not fonud in database");
    }

    private void updateDB() {
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


    // The IBinder defining the interface to actually interact with the database
    public class LocalBinder extends Binder {
        public PatientInterfaceService getService() {
            return PatientInterfaceService.this;
        }

        public void addPatient(Date dob, int weight, int height, String medications,
                               String conditions, String notes) {
            PatientInterfaceService.this.addPatient(dob, weight, height, medications,
                    conditions, notes);
        }

        public void updatePatient(PatientData patient) throws PatientNotFoundException {
            PatientInterfaceService.this.updatePatient(patient);
        }

        public void deletePatient(int id) throws PatientNotFoundException {
            PatientInterfaceService.this.deletePatient(id);
        }

        public ArrayList<PatientData> getPatients() {
            return PatientInterfaceService.this.getPatients();
        }
    }
}