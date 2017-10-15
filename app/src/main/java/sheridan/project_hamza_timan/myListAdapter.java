package sheridan.project_hamza_timan;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import helper.SQLiteHandler;
import helper.Students;

/**
 * Created by 1 on 11.12.2016.
 */

public class myListAdapter extends BaseAdapter{


    LayoutInflater myInflator;
    private Context context1;
    private List<Students> starray;

    //constructor
    public myListAdapter(Context myContext, List<Students> students){
        context1 = myContext;
        starray = students;
    }


    @Override
    public int getCount() {
        return starray.size();
    }

    @Override
    public Object getItem(int position) {
        return starray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context1, R.layout.list_item, null);
        TextView tvID = (TextView) v.findViewById(R.id.item);
        TextView tvComment = (TextView) v.findViewById(R.id.textView2);
        TextView tvGrade = (TextView) v.findViewById(R.id.textView1);

        tvID.setText("Student ID: "+String.valueOf(starray.get(position).getID()));
        tvGrade.setText("Comment: "+starray.get(position).getScore());
        tvComment.setText("Score:"+ starray.get(position).getComment());

        v.setTag(String.valueOf(starray.get(position).getID()));

        return v;
    }
}
