package khara.karan.netuse;

import android.app.Activity;

/**
 * Created by karan on 11/10/15.
 */
public class SuggestUnivFromDatabase extends Activity{
//    public static final String TAG=SuggestUnivFromDatabase.class.getSimpleName();
//
//    void suggestDB(String grescore, String Percentage,ListView listview){
////        this.listview = listview;
//        float greScore = Float.valueOf(grescore);
//        float percentage = Float.valueOf(Percentage);
//        ParseUser mCurrentUser3 = ParseUser.getCurrentUser();
//        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UserInfo");
//
//        query3.whereLessThanOrEqualTo("greScore", greScore+7);
//        query3.whereGreaterThanOrEqualTo("greScore", greScore-15);
//        query3.whereLessThanOrEqualTo("undergradPercent", percentage + 15);
//        query3.whereGreaterThanOrEqualTo("undergradPercent", percentage-15);
//
//        query3.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
//                    //ListView listview = (ListView) findViewById(R.id.listViewSuggest);
//                    //  String[] usernames = new String[obs.size()];
//                    ArrayList<String> univnames = new ArrayList<String>();
//                    int size = list.size();
//                    String[] arr = new String[size];
//                    int i = 0, repeat;
//                    for (ParseObject b : list) {
//                        //usernames[i] = a.get("fullName").toString();
//                        String abc = b.get("newUnivName").toString();
//                        if (i <= 30) {
//                            arr[i] = abc;
//                        }
//                        repeat = 0;
//                        for (int j = 0; j <= i; j++) {
//                            if (abc.equals(arr[j])) {
//                                repeat++;
//                                if (repeat > 1) {
//                                    break;
//                                }
//                            }
//                        }
//                        if (!abc.equals("blank") && repeat == 1) {
//                            univnames.add(abc);
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuggestUnivFromDatabase.this, R.layout.list_view_row, R.id.listText, univnames);
//                            listview.setAdapter(adapter);
//                        }
//                        i++;
//                    }
//                } else {
//                    Log.e(TAG, e.getMessage());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SuggestUnivFromDatabase.this);
//                    builder.setMessage(e.getMessage());
//                    builder.setTitle(getString(R.string.error_title));
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//        });
//    }
}
