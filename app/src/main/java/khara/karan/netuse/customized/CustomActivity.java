package khara.karan.netuse.customized;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import khara.karan.netuse.utilities.TouchEffect;

/**
 * This is a common activity that all other activities of the app can extend to
 * inherit the common behaviors like implementing a common interface that can be
 * used in all child activities.
 */
public class CustomActivity /*extends FragmentActivity*/ extends Activity implements OnClickListener
{

	/**
	 * Apply this Constant as touch listener for views to provide alpha touch
	 * effect. The view must have a Non-Transparent background.
	 */
	public static final TouchEffect TOUCH = new TouchEffect();

	@Override
	public void setContentView(int layoutResID)
	{
		super.setContentView(layoutResID);
//		setupActionBar();   // -- 1
	}

	/**
	 * This method will setup the top title bar (Action bar) content and display
	 * values. It will also setup the custom background theme for ActionBar. You
	 * can override this method to change the behavior of ActionBar for
	 * particular Activity
	 */
//	protected void setupActionBar()   // -- 1
//	{
//		final ActionBar actionBar = getActionBar();
//		if (actionBar == null)
//			return;
//		actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		actionBar.setDisplayUseLogoEnabled(true);
//		actionBar.setLogo(R.drawable.icon);
//		actionBar.setBackgroundDrawable(getResources().getDrawable(
//				R.drawable.actionbar_bg));
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setHomeButtonEnabled(true);
//	}

	/**
	 * Sets the touch and click listener for a view with given id.
	 * return the view on which listeners applied
	 */
	public View setTouchNClick(int id)
	{

		View v = setClick(id);  // --2
		if (v != null)
			v.setOnTouchListener(TOUCH);
		return v;
	}

	/**
	 * Sets the click listener for a view with given id.
	 * returns the view on which listener is applied
	 */
	public View setClick(int id)  // --2
	{

		View v = findViewById(id);
		if (v != null)
			v.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v)
	{

	}
}
