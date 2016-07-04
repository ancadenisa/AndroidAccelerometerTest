package org.flowerplatform.acceslerometertest;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class AccelerometerActivity extends ActionBarActivity {
	WebView myBrowser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);
		myBrowser = (WebView)findViewById(R.id.mybrowser);
	    final JavascriptBridge jsBridge
	        = new JavascriptBridge(this);
       myBrowser.addJavascriptInterface(jsBridge, "AndroidFunction");    
       myBrowser.getSettings().setJavaScriptEnabled(true); 
       myBrowser.loadUrl("file:///android_asset/mypage.html"); 
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    	   WebView.setWebContentsDebuggingEnabled(true);
    	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accelerometer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public class JavascriptBridge {
		Context mContext;

		JavascriptBridge(Context c) {
			mContext = c;
		}
		@JavascriptInterface 
		public void showToast(String toast) {
			Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
		}

	}
}
