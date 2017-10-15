package sheridan.project_hamza_timan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import helper.SQLiteHandler;
import helper.SessionManager;

/**
 * Created by 1 on 12.12.2016.
 */

public class Activity_update extends AppCompatActivity {

    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputComment;
    private TextView id;
    private SeekBar inputScore;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler  userDB, adminDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        inputComment = (EditText) findViewById(R.id.comment);
        inputScore =(SeekBar) findViewById(R.id.seekBar_grades);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        id = (TextView) findViewById(R.id.studID);

        //-------------------------------------------------
        Intent intent = getIntent();
        final String userid = intent.getStringExtra("username");
        id.setText("STUDENT ID: "+userid);
        //---------------------------------------------------


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
                                               String comment = inputComment.getText().toString().trim();
                                               String score = Integer.toString(inputScore.getProgress());

                                               SQLiteHandler db = new SQLiteHandler(getApplicationContext());

                                               try {
                                                   db.UpdateUser(Integer.parseInt(userid), comment, score);
                                                   Toast.makeText(getApplicationContext(),
                                                           "Student updated", Toast.LENGTH_LONG)
                                                           .show();
                                                  // db.delete();
                                                   Intent intent = new Intent(Activity_update.this,MainActivity.class);
                                                   startActivity(intent);

                                               }catch(Exception ex){
                                                   Toast.makeText(getApplicationContext(),
                                                           "Error occured: "+ex.getMessage(), Toast.LENGTH_LONG)
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
