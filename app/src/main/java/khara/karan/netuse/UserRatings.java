package khara.karan.netuse;

/**
 * Created by karan on 11/10/15.
 */

public class UserRatings {


//    void getCurrentNewUserRating(){
//        ListView listview;
//
//        float UndergradUnivRating1, UndergradStudRating1, GreRating1;
//        float[] UndergradUnivRating2;
//        float[] UndergradStudRating2;
//        float[] GreRating2;
//        String fullname, undergradUniv1,undergradUniv2 ;
////    public static
//
//        String[] seniorStuds;
//        float[] studAvgRating;
//        float avgSeniorUserRating;
//        ParseUser currUser;
//        int i,j;
//        String[] univRating;
//        Object object;
//        int listSize;
//        String rate;
//
//        currUser = ParseUser.getCurrentUser();
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRating");
//        query.whereEqualTo("userId", currUser);
//        query.getFirstInBackground(new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject parseObject, ParseException e) {
//
////                String gre_score = parseObject.get("greScore").toString();
////                String under_percent = parseObject.get("undergradPercent").toString();
////
////                //suggestDB(gre_score, under_percent);
////
//                String undergradUniv1 = parseObject.get("undergradUniv").toString();
////                float GreScore1, UndergradPercent1;
////                GreScore1 = Float.valueOf(parseObject.get("greScore").toString());
////                UndergradPercent1 = Float.valueOf(parseObject.get("undergradPercent").toString());
//
//
//
//                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UnivDetail");
//                query1.whereEqualTo("univName", undergradUniv1);
//                query1.getFirstInBackground(new GetCallback<ParseObject>() {
//                    @Override
//                    public void done(ParseObject parseObject, ParseException e) {
//                        //suggestDB(gre_score, under_percent);
//
//                        float GreScore1, UndergradPercent1;
//                        GreScore1 = Float.valueOf(parseObject.get("greScore").toString());
//                        UndergradPercent1 = Float.valueOf(parseObject.get("undergradPercent").toString());
//                        float GreRating1 = getGreRating(GreScore1);
//                        float UndergradStudRating1 = getUndergradStudRating(UndergradPercent1);
//                        if (parseObject != null) {
//                            String temp = parseObject.get("univRating").toString();
//
//                            float UndergradUnivRating1 = Float.valueOf(temp);
//                            Log.e("TAG", "greRating1: " + GreRating1 + ",   undergradUnivRating1: " + UndergradUnivRating1 + ",  undergradStudRating21: " + UndergradStudRating1);
//                            float getCurrentUserTotalRating = getThisUserAvgRatings(GreRating1, UndergradUnivRating1, UndergradStudRating1);
//                            Log.e("TAG", "Current Student's total rating: " + getCurrentUserTotalRating);
//
//                        } else {
//                            Log.e("TAG", "exception = ^^^^^^^" + e);
//                        }
//                    }
//                });
//            }
//        });
//
//    }
//
//    float getThisUserAvgRatings(float greRating,float undergradUnivRating,float undergradStudRating){
//        float totalUserRating;
//        float sum = greRating+undergradStudRating+undergradUnivRating;
//        float div = sum/3;
//        totalUserRating = div;
//        return totalUserRating;
//    }
//
//    float getGreRating(float greScore){
//        float greRating;
//        float temp = greScore-240;
//        greRating = temp/10;
//        return greRating;
//    }
//    float getUndergradStudRating(float undergradPercent){
//        undergradPercent = undergradPercent/10;
//        return undergradPercent;
//    }

}
