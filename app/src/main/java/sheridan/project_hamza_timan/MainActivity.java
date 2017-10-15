package sheridan.project_hamza_timan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import helper.SQLiteHandler;
import helper.Students;


public class MainActivity extends AppCompatActivity {

    SQLiteHandler db;
    private ListView lv;
    private myListAdapter adapter;
    private List<Students> student_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lvStudents);
        student_list = new ArrayList<>();



        //get data from db
        db = new SQLiteHandler(this);
        student_list = db.getListContents();


        adapter = new myListAdapter(getApplicationContext(), student_list);
        lv.setAdapter(adapter);

     lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id){


             String str = view.getTag().toString();

             Intent intent12 = new Intent(MainActivity.this,Activity_update.class);
             intent12.putExtra("username", str);
             startActivity(intent12);

         }
     });

    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:

                // Launch add student activity
                Intent intent = new Intent(MainActivity.this,Register_Activity.class);
               // String user = getIntent().getStringExtra("username").toLowerCase();
                //intent.putExtra("username", user);
                startActivity(intent);
                return true;

            case R.id.logout_item:
                Intent intent2 = new Intent(MainActivity.this,Login_Activity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}