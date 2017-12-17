//package khara.karan.netuse;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.parse.FindCallback;
//import com.parse.GetCallback;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class NearbyPlaces extends Activity {
//    protected Button mBtnNewPlace, mBtnBackPlaces, mBtnToRec;
//    ListView listview1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_nearby_places);
//
//        mBtnBackPlaces = (Button) findViewById(R.id.btnBackPlaces);
//        mBtnBackPlaces.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseUser currUser = ParseUser.getCurrentUser();
//                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
//                query.whereEqualTo("userId", currUser);
//                query.getFirstInBackground(new GetCallback<ParseObject>() {
//                    @Override
//                    public void done(ParseObject parseObject, ParseException e) {
//                        String univName = parseObject.get("newUnivName").toString();
//                        if (univName.equals("blank")) {
//                            Intent intent3 = new Intent(NearbyPlaces.this, FutureStudent.class);
//                            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                            startActivity(intent3);
//                        } else if (!univName.equals("blank")) {
//                            Intent intent3 = new Intent(NearbyPlaces.this, MainActivity.class);
//                            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                            startActivity(intent3);
//                        }
//                    }
//                });
//            }
//        });
//
//        mBtnNewPlace = (Button) findViewById(R.id.btnAddPlace);
//        mBtnNewPlace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(NearbyPlaces.this, AddPlace.class);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                startActivity(intent2);
//            }
//        });
//
//        mBtnToRec = (Button) findViewById(R.id.btnToRec);
//        mBtnToRec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(NearbyPlaces.this, ReccPlaces.class);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                startActivity(intent2);
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlaceNearby");
//        //query.orderByAscending(ParseConstants.KEY_USERNAME);
//        query.orderByAscending("createdAt");
//        // we limit by 1000. because returning a large no of users will take too long or will crash our app
//        //query.setLimit(1000);
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> obs, ParseException e) {
//                setProgressBarIndeterminateVisibility(false);
//
//                if (e == null) {
//                    //  String[] usernames = new String[obs.size()];
//                    listview1 = (ListView) findViewById(R.id.listView1);
//                    ArrayList<String> placesList = new ArrayList<String>();
//
//                    for (ParseObject a : obs) {
//                        //usernames[i] = a.get("fullName").toString();
//                        String place = a.get("Name").toString();
//
//                        placesList.add(place);
//                        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(NearbyPlaces.this,
//                        //      android.R.layout.simple_list_item_1, placesList);
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NearbyPlaces.this, R.layout.list_view_row, R.id.listText, placesList);
//                        listview1.setAdapter(adapter);
//                        listview1.setOnItemClickListener(new ListClickHandler());
//                    }
//                } else {
//                    //Log.e(TAG, e.getMessage());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(NearbyPlaces.this);
//                    builder.setMessage(e.getMessage());
//                    builder.setTitle(getString(R.string.error_title));
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//
//            }
//        });
//
//    }
//
//    public class ListClickHandler implements OnItemClickListener{
//
//        String categ_selected;
//        String place_selected;
//        @Override
//        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
//            // TODO Auto-generated method stub
//            TextView listText = (TextView) view.findViewById(R.id.listText);
//            place_selected = listText.getText().toString();
//            ParseQuery<ParseObject> queryPlace = ParseQuery.getQuery("PlaceNearby");
//            queryPlace.whereEqualTo("Name", place_selected);
//            queryPlace.getFirstInBackground(new GetCallback<ParseObject>() {
//                @Override
//                public void done(ParseObject parseObject, ParseException e1) {
//                    if (e1 == null) {
//                        categ_selected = parseObject.get("Category").toString();
//                        Log.e("TAG", "CATEGory -------------" + categ_selected);
//                        Intent intent = new Intent(NearbyPlaces.this, ReccPlaces.class);
//                        Log.e("TAG", "PLACE -------------" + place_selected);
//                        Log.e("TAG", "CATEG -------------" + categ_selected);
//                        intent.putExtra("selected-place", place_selected);
//                        intent.putExtra("selected-category", categ_selected);
//                        startActivity(intent);
//                    } else {
//                        Log.e("TAG", "ERRORRRRR");
//                    }
//                }
//            });
//        }
//    }
//}
