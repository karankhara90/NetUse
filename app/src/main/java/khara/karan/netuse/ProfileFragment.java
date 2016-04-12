package khara.karan.netuse;

//public class ProfileFragment extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
//
//}

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.FileInputStream;

public class ProfileFragment extends Fragment implements OnClickListener{
    View profFragmentView;
    public TextView tvStudUni;
    SharedPreferences shared_univ_name;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private String file = "mydata";
    ArrayAdapter<String> mStudAdapter;


    Context context ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profFragmentView = inflater.inflate(R.layout.activity_profile, container, false);
        tvStudUni = (TextView)profFragmentView.findViewById(R.id.textViewStudUni);
       // fff();

        try {
            fff();
//            tvStudUni.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //ListView studList = (ListView) profFragmentView.findViewById(R.id.lvStudFromUniv);
//                    ListView studList = (ListView) profFragmentView.findViewById(android.R.id.list);
//                    studList.setVisibility(View.VISIBLE);
//
//                    String[] str = {"a", "b", "cccc", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "u", "v"};
//                    mStudAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, str);
//                    studList.setAdapter(mStudAdapter);
//                }
//            });

        }
        catch (Exception e) {
            Log.e("TAG","{}{}{}{}{}{}{}{}{}{}{}: "+e);
        }


//        shared_univ_name = PreferenceManager.getDefaultSharedPreferences(this.getActivity()); //1
//        //if(shared_univ_name.contains("shared_newUniv")){
//            new_univ_name = shared_univ_name.getString("shared_newUniv", "error getting val"); //2

        return profFragmentView;
    }

    void fff() {
        try {
            FileInputStream fin = getActivity().openFileInput(file);
            int c;
            String new_univ_name = "";
            while ((c = fin.read()) != -1) {
                new_univ_name = new_univ_name + Character.toString((char) c);
            }
            tvStudUni.setText(new_univ_name);
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {

    }



}

