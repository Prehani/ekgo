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
import android.app.Fragment;

import java.io.Console;
import java.util.Date;

import ekgo.patient.PatientData;
import ekgo.patient.PatientInterfaceService;
import ekgo.patient.PatientNotFoundException;

public class PatientDataFragment extends Fragment {

    private View view;
    private TextView patientId;
    private TextView patientName;
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

    public void changePatient(PatientData patient) {
        this.patient = patient;
        updateDisplay();
    }

    private void updateDisplay() {
        patientId = (TextView) view.findViewById(R.id.patientID);
        patientName = (TextView) view.findViewById(R.id.patientName);
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
            patientName.setText("Name: " + patient.getName());
            patientId.setText("Id: " + patient.getId());
            dob.setText("DOB: " + patient.getDob().toString());
            height.setText("Height: " + patient.getHeight());
            weight.setText("Weight: " + patient.getWeight());
            medications.setText("Medications: " + patient.getMedications());
            conditions.setText("Conditions: " + patient.getConditions());
            notes.setText("Additional Notes: " + patient.getNotes());
        } else {
            patientId.setText("Id:");
            patientName.setText("Name: ");
            dob.setText("DOB:");
            height.setText("Height:");
            weight.setText("Weight:");
            medications.setText("Medications:");
            conditions.setText("Conditions:");
            notes.setText("Additional Notes:");
        }
    }
}
