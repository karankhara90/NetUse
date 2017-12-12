package khara.karan.netuse;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class AddPlace extends ActionBarActivity {
    protected EditText mPlaceName,mPlaceNumber,mPlaceAddress;
    Spinner mSpPlaceCategory, mSpPlaceType, mSpPlaceCity;
    protected Button mBtnAddPlace;
    //String placeName, placeNumber, placeAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPlaceName = (EditText)findViewById(R.id.placeNameField);
        mPlaceNumber = (EditText)findViewById(R.id.editPlaceNumber);
        mPlaceAddress = (EditText)findViewById(R.id.editPlaceAddress);


        //for place category
        mSpPlaceCategory = (Spinner) findViewById(R.id.spPlaceCategory);
        List<String> list4 = new ArrayList<String>();
        list4.add("Restaurant");
        list4.add("Cinema");
        list4.add("Apartment");
        list4.add("Hotel");

        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list4);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter5.notifyDataSetChanged();
        mSpPlaceCategory.setAdapter(dataAdapter5);

        //for place type
        mSpPlaceType = (Spinner) findViewById(R.id.spPlaceType);
        List<String> list5 = new ArrayList<String>();
        list5.add(" -- ");
        list5.add("American");
        list5.add("Indian");
        list5.add("Chinese");
        list5.add("Italian");
        list5.add("Mexican");
        list5.add("Japanese");


        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list5);
        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter6.notifyDataSetChanged();
        mSpPlaceType.setAdapter(dataAdapter6);

        //for place City
        mSpPlaceCity = (Spinner) findViewById(R.id.spPlaceCity);
        List<String> list6 = new ArrayList<String>();
        list6.add("Fresno");
        list6.add("Clovis");
        list6.add("San Jose");
        list6.add("Madera");
        list6.add("Fremont");
        list6.add("Los Angeles");


        ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list6);
        dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter7.notifyDataSetChanged();
        mSpPlaceCity.setAdapter(dataAdapter7);

        // Button
        mBtnAddPlace = (Button)findViewById(R.id.btnSubmitPlace);

        mBtnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (placeName.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder((AddPlace.this));

                    builder.setMessage("Please make sure you enter a Place Name");
                    builder.setTitle(getString(R.string.signup_error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else { */
                    //setProgressBarIndeterminateVisibility(true);
                    ParseObject object = new ParseObject("PlaceNearby");
                    //ParseQuery<ParseObject> query = ParseQuery.getQuery("PlaceNearby");

                    //query.getFirstInBackground(new GetCallback<ParseObject>() {

                      //  public void done(ParseObject object, ParseException e) {
                        //    if (e == null) {
                                String placeName = mPlaceName.getText().toString();

                                String placeAddress = mPlaceAddress.getText().toString();
                                String placeCategory = mSpPlaceCategory.getSelectedItem().toString();
                                String placeType = mSpPlaceType.getSelectedItem().toString();
                                String placeCity = mSpPlaceCity.getSelectedItem().toString();


                                try{
                                    object.put("Name", placeName);
                                    object.put("PlaceAddress", placeAddress);
                                    object.put("Category", placeCategory);
                                    object.put("Type", placeType);
                                    object.put("City", placeCity);
                                    long placeNumber = Long.parseLong(mPlaceNumber.getText().toString());
                                    object.put("PlaceNumber", placeNumber);
                                }
                                catch(Exception e1){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPlace.this);

                                    builder.setMessage(e1.getMessage());
                                    builder.setTitle("Error hai ethe ji");
                                    builder.setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }



                               // }


                                object.saveInBackground();

                                Intent intent = new Intent(AddPlace.this, NearbyPlaces.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                                startActivity(intent);


                                //mFullName = (TextView) findViewById(R.id.name);
                                //mFullName.setText(FullName);

                            } /*else {
                                // error
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddPlace.this);

                                builder.setMessage(e.getMessage());
                                builder.setTitle("Error hai ethe ji");
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } */

                       // }
                    //});

                //}
           // }
        });


    }

}
