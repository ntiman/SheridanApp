package sheridan.project_hamza_timan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import helper.SessionManager;

public class Login_Activity extends AppCompatActivity {

    private static final String TAG = Login_Activity.class.getSimpleName();
    private Button btnLogin;
    private EditText login;
    private EditText password;
    private ProgressDialog pDialog;
    private SessionManager session;
    private RequestQueue requestQ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        requestQ = Volley.newRequestQueue(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final String user_login = login.getText().toString().trim();
                final String user_password = password.getText().toString().trim();

                // Check if data fields are emtpty
                if (!user_login.isEmpty() && !user_password.isEmpty()) {
                    // login attempt
                    StringRequest strReq = new StringRequest(Method.POST,
                            AppConfig.URL_LOGIN+"name="+user_login+"&password="+user_password, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                JSONObject jObj = new JSONObject(response);

                                String value = jObj.getString("login");


                                if(value.equals("valid")){

                                    //toast if successful
                                    Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();

                                    // Launch main activity
                                    Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                                    intent.putExtra("username", user_login);
                                    startActivity(intent);
                                    finish();
                                }
                                else {

                                    Toast.makeText(getApplicationContext(), "Login was not successful. ", Toast.LENGTH_LONG).show();
                                }
                            }catch(JSONException e){

                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Login Error: " + error.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", user_login);
                            params.put("password", user_password);
                            return params;
                        }

                    };
                    requestQ.add(strReq);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Invalid input. Please provide your login and password.", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
    }




}