package ekgo;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.graphics.Rect;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import ekgo.patient.PatientData;

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

    //TODO: build out options menu stuff
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.createPatient:
                createPatientInfoWindow();
                return true;

            case R.id.editPatient:
                createPatientInfoWindow();
                return true;

            case R.id.deletePatient:
                //TODO: delete patient from database then update patientlist
                return true;

            default:
                return true;
        }
    }

    private void createPatientInfoWindow(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),
                R.style.CustomAlertDialog);
        ViewGroup viewGroup = view.findViewById(R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.edit_patient_dialog, viewGroup, false);
        builder.setView(dialogView);

        //TODO: fill in patient data
        final AlertDialog alertDialog = builder.create();

        Button buttonOk=dialogView.findViewById(R.id.saveButton);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: save/update patient data
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
