package ekgo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import ekgo.patient.PatientInterfaceService;

public class MainScreen extends AppCompatActivity {

    private PatientInterfaceService pService;
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

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            PatientInterfaceService.LocalBinder binder = (PatientInterfaceService.LocalBinder) service;
            pService = binder.getService();
            pBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            pBound = false;
        }
    };

    //TODO: add interface for database stuff

}
