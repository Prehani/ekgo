package ekgo;

import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ekgo.patient.PatientData;
import ekgo.patient.PatientInterfaceService;
import ekgo.patient.PatientNotFoundException;

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
    private PatientInterfaceService pService;
    private PatientInterfaceService.LocalBinder pBinder;
    private String username;
    private Boolean pBound;
    private Thread t;

    // fields required for patientList fragment
    private ListView patientList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> patientNames = new ArrayList<String>();

    private String selectedPatientName = "";
    private PatientDataFragment patientDataFragment = new PatientDataFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");

        // Load up PatientDataFragment
        loadFragment(patientDataFragment);
    }

    @Override
    public void onStart() {
        super.onStart();

        patientList = (ListView) findViewById(R.id.patient_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patientNames);
        patientList.setAdapter(adapter);
        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPatientName = patientNames.get(position);
                for(PatientData patient : pBinder.getPatients()) {
                    if(patient.getName() == selectedPatientName) {
                        patientDataFragment.changePatient(patient);
                        break;
                    }
                }
            }
        });

        // Bind PatientInterfaceService to this activity in new thread
        t = new Thread() {
            public void run() {
                Intent intent = new Intent(MainScreen.this, PatientInterfaceService.class);
                intent.putExtra("username", username);

                bindService(intent, connection, getApplicationContext().BIND_AUTO_CREATE);
            }
        };
        t.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.patient_data_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO: logout
        switch(item.getItemId()) {
            case R.id.createPatient:
                createPatientInfoWindow(new PatientData());
                return true;
            case R.id.editPatient:
                if(selectedPatientName != "") {
                    for(PatientData patient : pBinder.getPatients()) {
                        if(patient.getName() == selectedPatientName) {
                            createPatientInfoWindow(patient);
                            break;
                        }
                    }
                }
                return true;
            case R.id.deletePatient:
                if(selectedPatientName != "")
                    try {
                        pBinder.deletePatient(selectedPatientName);
                        updatePatientNames();
                        patientDataFragment.changePatient(null);
                        adapter.notifyDataSetChanged();
                    } catch (PatientNotFoundException e) {
                        Toast.makeText(this, "No patient selected: " + selectedPatientName, Toast.LENGTH_SHORT).show();
                    }
                return true;
            case R.id.logout:
                finish();
                return true;
            case R.id.settings:
                goToSettings();
                return true;
            default:
                return true;
        }
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
    }

    public void createPatientInfoWindow(final PatientData patient) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainScreen.this,
                R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(R.id.content);




        final View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.edit_patient_dialog, viewGroup, false);

        final TextView nameForm = (TextView) dialogView.findViewById(R.id.name);
        final TextView dateForm = (TextView) dialogView.findViewById(R.id.dob);
        final TextView heightForm = (TextView) dialogView.findViewById(R.id.height);
        final TextView weightForm = (TextView) dialogView.findViewById(R.id.weight);
        final TextView medicationsForm = (TextView) dialogView.findViewById(R.id.medications);
        final TextView conditionsForm = (TextView) dialogView.findViewById(R.id.conditions);
        final TextView notesForm = (TextView) dialogView.findViewById(R.id.notes);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dateForm, myCalendar);
            }

        };

        dateForm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainScreen.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // If the patient is nonempty (i.e. we are editing patient data), populate the forms
        if (patient.isSet) {
            nameForm.setText(patient.getName());
            dateForm.setText(patient.getDob().toString()); // DOB
            heightForm.setText(Integer.valueOf(patient.getHeight()).toString()); // Height
            weightForm.setText(Integer.valueOf(patient.getWeight()).toString()); // Weight
            medicationsForm.setText(patient.getMedications()); // Medications
            conditionsForm.setText(patient.getConditions()); // Conditions
            notesForm.setText(patient.getNotes()); // Notes
        }

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        Button buttonOk = dialogView.findViewById(R.id.saveButton);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( nameForm.getText() == "" ||
                    dateForm.getText().toString() == "" ||
                    heightForm.getText().toString() == "" ||
                    weightForm.getText().toString() == "")
                    return;
                try {
                    patient.setDob(new SimpleDateFormat("mm/dd/yyyy").parse((dateForm.getText().toString())));
                } catch (ParseException e){}
                patient.setName(nameForm.getText().toString());
                patient.setHeight(Integer.parseInt(heightForm.getText().toString()));
                patient.setWeight(Integer.parseInt(weightForm.getText().toString()));
                patient.setMedications(medicationsForm.getText().toString());
                patient.setConditions(conditionsForm.getText().toString());
                patient.setNotes(notesForm.getText().toString());
                patient.isSet = true;

                try {
                    pBinder.updatePatient(patient);
                } catch (PatientNotFoundException e) {
                    pBinder.addPatient(patient);
                }
                selectedPatientName = patient.getName();

                // Update the PatientDataFragment
                patientDataFragment.changePatient(patient);

                alertDialog.dismiss();
                updatePatientNames();
            }
        });

        alertDialog.show();
    }

    private void updateLabel(TextView dateForm, Calendar myCalendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateForm.setText(sdf.format(myCalendar.getTime()));
    }

    private void updatePatientNames() {
        patientNames = new ArrayList<String>();
        for(PatientData patient : pBinder.getPatients()) {
            patientNames.add(patient.getName());
        }
        Log.i("patients", patientNames.toString());
        adapter.clear();
        adapter.addAll(patientNames);
    }

    public PatientInterfaceService.LocalBinder getpBinder() {
        if(pBound) return pBinder;
        return null;
    }
    public void goToSettings(){
        Intent settings = new Intent(this, Settings.class);
        startActivity(settings);

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

            updatePatientNames();


        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            pBound = false;
        }
    };
}
