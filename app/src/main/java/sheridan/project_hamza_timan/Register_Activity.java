package sheridan.project_hamza_timan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import sheridan.project_hamza_timan.MainActivity;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sheridan.project_hamza_timan.R;
import app.AppConfig;
import app.AppController;
import helper.SQLiteHandler;
import helper.SessionManager;

public class Register_Activity extends AppCompatActivity {

    private static final String TAG = Register_Activity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputComment;
    private SeekBar inputScore;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler  userDB, adminDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputComment = (EditText) findViewById(R.id.comment);
        inputScore =(SeekBar) findViewById(R.id.seekBar_grades);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handlers

        adminDB = new SQLiteHandler(getApplicationContext());

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String comment = inputComment.getText().toString().trim();
                String score = Integer.toString(inputScore.getProgress());

                Intent intent = getIntent();
                String usernme = intent.getStringExtra("username");

                int studentid = 0;

               try {
                    studentid = Integer.parseInt(name);
               }catch(Exception x){

                   Toast.makeText(getApplicationContext(),"Invalid ID.",Toast.LENGTH_SHORT).show();

               }

                if (!name.isEmpty() && !comment.isEmpty()) {
                    // Check if user is already logged in or not
                        //ADDING USER-------------------------------------------------------------------------------------------
                        try {

                            adminDB.addUser(studentid, comment, score);
                           Toast.makeText(getApplicationContext(),"Student successfully added.",Toast.LENGTH_SHORT).show();


                        }catch(Exception ex) {
                           Toast.makeText(getApplicationContext(),"Failed to add student: " + ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }



                    //END OF ADD USER---------------------------------------------------------------------------------------------------------
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter student details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }


}