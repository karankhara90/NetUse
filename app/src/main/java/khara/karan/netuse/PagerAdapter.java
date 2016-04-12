package khara.karan.netuse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Locale;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
               InboxFragment tab1 = new InboxFragment();
                //return new InboxFragment();
                return tab1;
            case 1:
                ProfileFragment tab2 = new ProfileFragment();
                return tab2;
//                return profFragmentView;
            case 2:
                FriendsFragment tab3 = new FriendsFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
