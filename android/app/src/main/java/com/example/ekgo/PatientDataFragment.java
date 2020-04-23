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
    PatientData patient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.patient_data, container, false);

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
        }

        return view;
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
                createPatientInfoWindow(patient);
                return true;

            case R.id.deletePatient:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to delete this patient?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PatientInterfaceService.LocalBinder pBinder = ((MainScreen) getActivity()).getpBinder();
                                try {
                                    pBinder.deletePatient(Integer.parseInt(patientId.getText().toString()));
                                } catch(PatientNotFoundException e){
                                    Toast toast = Toast.makeText(getContext(), "shit be broke yo", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
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

    private void createPatientInfoWindow(final PatientData patient){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),
                R.style.CustomAlertDialog);
        ViewGroup viewGroup = view.findViewById(R.id.content);
        final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.edit_patient_dialog, viewGroup, false);

        final TextView dateForm = (TextView) dialogView.findViewById(R.id.dob);
        final TextView heightForm = (TextView) dialogView.findViewById(R.id.height);
        final TextView weightForm = (TextView) dialogView.findViewById(R.id.weight);
        final TextView medicationsForm = (TextView) dialogView.findViewById(R.id.medications);
        final TextView conditionsForm = (TextView) dialogView.findViewById(R.id.conditions);
        final TextView notesForm = (TextView) dialogView.findViewById(R.id.notes);

        // If the patient is nonempty (i.e. we are editing patient data), populate the forms
        if(patient != null) {
            dateForm.setText(patient.getDob().toString()); // DOB
            heightForm.setText(patient.getHeight()); // Height
            weightForm.setText(patient.getWeight()); // Weight
            medicationsForm.setText(patient.getMedications()); // Medications
            conditionsForm.setText(patient.getConditions()); // Conditions
            notesForm.setText(patient.getNotes()); // Notes
        }

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();

        Button buttonOk=dialogView.findViewById(R.id.saveButton);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patient.setDob(new Date(dateForm.getText().toString()));
                patient.setHeight(Integer.parseInt(heightForm.getText().toString()));
                patient.setWeight(Integer.parseInt(weightForm.getText().toString()));
                patient.setMedications(medicationsForm.getText().toString());
                patient.setConditions(conditionsForm.getText().toString());
                patient.setNotes(notesForm.getText().toString());

                // TODO: send signal to actually save patient data

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private class DeletePatientDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to delete this patient?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

}
