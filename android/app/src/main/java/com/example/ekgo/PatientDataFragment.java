package ekgo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.io.Console;
import java.util.Date;

import ekgo.patient.PatientData;
import ekgo.patient.PatientInterfaceService;
import ekgo.patient.PatientNotFoundException;

public class PatientDataFragment extends Fragment {

    private View view;
    private TextView patientId;
    private TextView dob;
    private TextView height;
    private TextView weight;
    private TextView heartRate;
    private TextView medications;
    private TextView conditions;
    private TextView notes;
    private PatientData patient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.patient_data, container, false);

        updateDisplay();

        return view;
    }

    private void updateDisplay() {
        patientId = (TextView) view.findViewById(R.id.patientID);
        dob = (TextView) view.findViewById(R.id.patientDOB);
        height = (TextView) view.findViewById(R.id.patientHeight);
        weight = (TextView) view.findViewById(R.id.patientWeight);
        heartRate = (TextView) view.findViewById(R.id.patientHeartRate);
        medications = (TextView) view.findViewById(R.id.patientMedications);
        conditions = (TextView) view.findViewById(R.id.patientConditions);
        notes = (TextView) view.findViewById(R.id.patientNotes);

        setHasOptionsMenu(true);

        try {

            patient = (PatientData) getArguments().get("patientData");

        } catch (NullPointerException e) {}

        if(patient != null) {
            patientId.setText("Id: " + patient.getId());
            dob.setText("DOB: " + patient.getDob().toString());
            height.setText("Height: " + patient.getHeight());
            weight.setText("Weight: " + patient.getWeight());
            medications.setText("Medications: " + patient.getMedications());
            conditions.setText("Conditions: " + patient.getConditions());
            notes.setText("Additional Notes: " + patient.getNotes());
        } else {
            patientId.setText("Id:");
            dob.setText("DOB:");
            height.setText("Height:");
            weight.setText("Weight:");
            medications.setText("Medications:");
            conditions.setText("Conditions:");
            notes.setText("Additional Notes:");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.patient_data_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.createPatient:
            case R.id.editPatient:
                ((MainScreen) getActivity()).createPatientInfoWindow(patient);
                return true;

            case R.id.deletePatient:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to delete this patient?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PatientInterfaceService.LocalBinder pBinder = ((MainScreen) getActivity()).getpBinder();
                                try {
                                    pBinder.deletePatient(patient.getId());
                                } catch(PatientNotFoundException e){
                                    Toast toast = Toast.makeText(getContext(), "shit be broke yo", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                patient = null;
                                updateDisplay();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.create().show();


                return true;

            default:
                return true;
        }
    }
}
