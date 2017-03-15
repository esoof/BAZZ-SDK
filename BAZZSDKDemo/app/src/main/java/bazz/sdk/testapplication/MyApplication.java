package bazz.sdk.testapplication;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.esoof.android.bazz_sdk.BazzLib;

import java.util.ArrayList;

/**
 * Created by TAKEphONE on 09/03/2017.
 */

public class MyApplication extends Application {

    public static BazzLib mBazzLib = null;

    public static ArrayList<String> mLogLines = new ArrayList<String>();

    private static final String APP_ID = "<Your BAZZ SDK app id here>";


    @Override
    public void onCreate() {
        super.onCreate();

        mBazzLib = new BazzLib();

        if (mBazzLib!=null)
        {
            // APP_ID is a string you wil§l receive from us when we register your app
            mBazzLib.init(this,APP_ID);

            // You must call these functions, too to initialize the app properly
            mBazzLib.setAppName("BAZZ SDK Demo");
            mBazzLib.setMainActivity("Main");

            mBazzLib.setIncomingWorkWithWhatsapp(true);
            mBazzLib.setIncomingWorkWithMessenger(true);
            mBazzLib.setIncomingWorkWithLine(true);
            mBazzLib.setIncomingWorkWithGmail(true);
            mBazzLib.setIncomingWorkWithLine(true);

            // *** You must add this to enable access to IM messaging apps ***
            bindService(new Intent(getApplicationContext(), BazzNotificationServiceLink.class), new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName className, IBinder service) {
                }
                @Override
                public void onServiceDisconnected(ComponentName arg0) {
                }
            }, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onTerminate()
    {
        if (mBazzLib!=null)
        {
            mBazzLib.shutdown(this);
            mBazzLib = null;
        }

        super.onTerminate();
    }
}
