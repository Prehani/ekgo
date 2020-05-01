package ekgo.patient;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatientDBHelper {
    SQLiteDatabase sqLiteDatabase;

    public PatientDBHelper(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS patients" +
                "(username TEXT PRIMARY KEY, id INTEGER, dob TEXT, height INTEGER, weight INTEGER," +
                "medications TEXT, conditions TEXT, notes TEXT)");
    }

    public ArrayList<PatientData> readPatients(String username) {
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from patients where username like '%s'", username), null);

        int idIndex = c.getColumnIndex("id");
        int dobIndex = c.getColumnIndex("dob");
        int heightIndex = c.getColumnIndex("height");
        int weightIndex = c.getColumnIndex("weight");
        int medicationsIndex = c.getColumnIndex("medications");
        int conditionsIndex = c.getColumnIndex("conditions");
        int notesIndex = c.getColumnIndex("notes");

        c.moveToFirst();

        ArrayList<PatientData> patientsList = new ArrayList<>();

        while (!c.isAfterLast()) {
            int id = c.getInt(idIndex);
            Date dob = null;
            try {
                dob = new SimpleDateFormat("MM/dd/YYYY").parse(c.getString(dobIndex));
            } catch (Exception e) {
                Log.e("DateError", "Unable to parse patient DOB");
            }
            int height = c.getInt(heightIndex);
            int weight = c.getInt(weightIndex);
            String medications = c.getString(medicationsIndex);
            String conditions = c.getString(conditionsIndex);
            String notes = c.getString(notesIndex);

            PatientData note = new PatientData(id, dob, height, weight, medications, conditions, notes);
            patientsList.add(note);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return patientsList;
    }

    public void savePatient(String username, PatientData patient) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO patients (username, id, dob, height, weight, medications, conditions, notes) VALUES ('%s', '%s', '%s', '%s','%s','%s','%s','%s')",
                username,
                patient.getId(),
                patient.getDob().toString(),
                patient.getHeight(),
                patient.getWeight(),
                patient.getMedications(),
                patient.getConditions(),
                patient.getNotes()));
    }

    public void updatePatient(PatientData patient) {
        createTable();
        sqLiteDatabase.execSQL(String.format("UPDATE notes set dob = '%s', weight = '%s', height = '%s', medications = '%s', conditions = '%s', notes = '%s' where id ='%s'",
                patient.getDob().toString(), patient.getWeight(), patient.getHeight(),
                patient.getMedications(), patient.getConditions(), patient.getNotes(), patient.getId()));
    }
}
