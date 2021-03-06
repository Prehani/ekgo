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

    private void addPatient(String name, Date dob, int weight, int height, String medications,
                           String conditions, String notes) {
        PatientData patient = new PatientData(patients.size()+1, name, dob, weight, height, medications,
                conditions, notes);
        patient.setId(patients.size() + 1);
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

    private void deletePatient(String name) throws PatientNotFoundException {
        for(int i = 0; i < patients.size(); i++) {
            if(patients.get(i).getName() == name) {
                patients.remove(i);

                PatientDBHelper patientDBHelper = getPatientDBHelper();
                patientDBHelper.deletePatient(username, name);
                return;
            }
        }
        throw new PatientNotFoundException("Patient with Name " + name + " not found in database");
    }

    private void updateDB() {
        PatientDBHelper patientDBHelper = getPatientDBHelper();

        for(PatientData patient : this.patients) {
            patientDBHelper.updatePatient(username, patient);
        }
    }

    private PatientDBHelper getPatientDBHelper() {
        Context context = getApplicationContext();
        SQLiteDatabase patientDB = context.openOrCreateDatabase("patients", Context.MODE_PRIVATE, null);
        PatientDBHelper patientDBHelper = new PatientDBHelper(patientDB);
        patientDBHelper.createTable();
        return patientDBHelper;
    }


    // The IBinder defining the interface to actually interact with the database
    public class LocalBinder extends Binder {
        public PatientInterfaceService getService() {
            return PatientInterfaceService.this;
        }

        public void addPatient(String name, Date dob, int weight, int height, String medications,
                               String conditions, String notes) {
            PatientInterfaceService.this.addPatient(name, dob, weight, height, medications,
                    conditions, notes);
        }

        public void addPatient(PatientData patient) {
            PatientInterfaceService.this.addPatient(patient.getName(), patient.getDob(), patient.getWeight(),
                    patient.getHeight(), patient.getMedications(), patient.getConditions(),
                    patient.getNotes());
        }

        public void updatePatient(PatientData patient) throws PatientNotFoundException {
            PatientInterfaceService.this.updatePatient(patient);
        }

        public void deletePatient(String name) throws PatientNotFoundException {
            PatientInterfaceService.this.deletePatient(name);
        }

        public ArrayList<PatientData> getPatients() {
            System.out.println("getting patients");
            return PatientInterfaceService.this.getPatients();
        }
    }
}