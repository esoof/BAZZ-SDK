package bazz.sdk.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.esoof.android.bazz_sdk.BazzLib;

public class Main extends Activity {

    //==================================================================

    private void UpdateBazzStateDisplay()
    {
        String newState = "Unknown";

        if (MyApplication.mBazzLib!=null) {
            int bazzMode = MyApplication.mBazzLib.getBazzMode();
            switch (bazzMode)
            {
                case BazzLib.BAZZ_MODE_OFF:
                    newState = "Off";
                    break;
                case BazzLib.BAZZ_MODE_ALWAYS_ON:
                    newState = "ON (always)";
                    break;
                case BazzLib.BAZZ_MODE_ON_WHILE_DRIVING:
                    if (MyApplication.mBazzLib.getBazzActive())
                    {
                        newState = "ON (driving)";
                    } else {
                        newState = "Off (driving)";
                    }
                    break;
            }

            RadioButton rb;
            rb = (RadioButton)findViewById(R.id.radioButtonOff);
            rb.setChecked(bazzMode==BazzLib.BAZZ_MODE_OFF);
            rb = (RadioButton)findViewById(R.id.radioButtonAlways);
            rb.setChecked(bazzMode==BazzLib.BAZZ_MODE_ALWAYS_ON);
            rb = (RadioButton)findViewById(R.id.radioButtonDriving);
            rb.setChecked(bazzMode==BazzLib.BAZZ_MODE_ON_WHILE_DRIVING);
        }

        ((TextView)findViewById(R.id.lblBazzStateValue)).setText(newState);


        CheckBox cb = (CheckBox)findViewById(R.id.btnShowInNotificationArea);
        boolean bState = ((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getShowStateInNotificationsArea()));
        cb.setChecked(bState);
    }

    //==================================================================

    private void UpdateLogList()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_log, R.id.text1, MyApplication.mLogLines);

        ((ListView)findViewById(R.id.lstLog)).setAdapter(adapter);
    }

    //==================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        RadioButton rb;
        CheckBox cb;
        Button btn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------------------------------------------------------------
        //--- Operation mode buttons (Off / Always / When driving) ---
        //------------------------------------------------------------

        if (MyApplication.mBazzLib!=null)
        {
            int bazzState = MyApplication.mBazzLib.getBazzMode();

            rb = (RadioButton)findViewById(R.id.radioButtonOff);
            rb.setChecked(bazzState==BazzLib.BAZZ_MODE_OFF);
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    if (bChecked)
                    {
                        if (MyApplication.mBazzLib!=null)
                        {
                            MyApplication.mBazzLib.setBazzMode(BazzLib.BAZZ_MODE_OFF);
                            UpdateBazzStateDisplay();
                        }
                    }
                }
            });

            rb = (RadioButton)findViewById(R.id.radioButtonAlways);
            rb.setChecked(bazzState==BazzLib.BAZZ_MODE_ALWAYS_ON);
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    if (bChecked)
                    {
                        if (MyApplication.mBazzLib!=null)
                        {
                            MyApplication.mBazzLib.setBazzMode(BazzLib.BAZZ_MODE_ALWAYS_ON);
                            UpdateBazzStateDisplay();
                        }
                    }
                }
            });

            rb = (RadioButton)findViewById(R.id.radioButtonDriving);
            rb.setChecked(bazzState==BazzLib.BAZZ_MODE_ON_WHILE_DRIVING);
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    if (bChecked)
                    {
                        if (MyApplication.mBazzLib!=null)
                        {
                            MyApplication.mBazzLib.setBazzMode(BazzLib.BAZZ_MODE_ON_WHILE_DRIVING);
                            UpdateBazzStateDisplay();
                        }
                    }
                }
            });

            //--------------------------------------------------

            cb    = (CheckBox)findViewById(R.id.btnShowInNotificationArea);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setShowStateInNotificationsArea(bChecked);
                        UpdateBazzStateDisplay();
                    }
                }
            });

            //--------------------------------------------------

            btn = (Button)findViewById(R.id.btnSendMessage);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.requestAddIncomingMessage("0545633956","Shimon Shniter","This is a test of BAZZ demo");
                    }
                }
            });
        }

        //------------------------------------------------------------
        //--- Callbacks from BAZZ SDK:                             ---
        //------------------------------------------------------------

        if (MyApplication.mBazzLib!=null)
        {
            //--- Main BAZZ events (BAZZ state - Off / Always / Driving) ---

            MyApplication.mBazzLib.setOnBazzModeChangedListener(new BazzLib.BazzModeChangedListener() {
                @Override
                public void onBazzModeChanged() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            UpdateBazzStateDisplay();
                        }
                    });
                }
            });

            //--- BAZZ driving detection events ----------------------------

            MyApplication.mBazzLib.setOnDrivingEventListener(new BazzLib.BazzDrivingEventsListener() {
                @Override
                public void onDrivingSensorEvent(String source, boolean newState) {

                    HandleCallbackFromLib("Driving sensor '"+source+"': "+(newState?"On":"Off"));
                }

                @Override
                public void onDrivingStarted() {
                    HandleCallbackFromLib("Driving started");
                }

                @Override
                public int onPlayDriveStartedPrompt() {
                    HandleCallbackFromLib("Play 'driving started' prompt");
                    return 0;
                }

                @Override
                public void onDrivingEnded() {
                    HandleCallbackFromLib("Driving ended");
                }

                @Override
                public int onPlayDriveEndedPrompt() {
                    HandleCallbackFromLib("Play 'driving ended' prompt");
                    return 0;
                }
            });

            //--- BAZZ incoming messages: -----------------------------------

            MyApplication.mBazzLib.setOnIncomingMessagesListener(new BazzLib.BazzIncomingMessagesListener() {
                @Override
                public boolean onIncomingMessagesListener(String type,
                                                          String phone,
                                                          String name,
                                                          String text)
                {
                    HandleCallbackFromLib(type+" from "+name+" ("+phone+"): "+text);
                    return false;
                }
            });

            //--- BAZZ requests result: -------------------------------------

            MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
                @Override
                public boolean onRequestResult(String requestId, String requestText, String requestResult)
                {
                    HandleCallbackFromLib("Message reply was "+requestResult);
                    return false;
                }
            });
        }

        //------------------------------------------------------------
        //--- Misc screen buttons                                  ---
        //------------------------------------------------------------

        btn = (Button)findViewById(R.id.btnClear);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.mLogLines.clear();
                UpdateLogList();
            }
        });

        btn = (Button)findViewById(R.id.btnConfigDriving);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main.this, SetupDriving.class);
                startActivityForResult(myIntent,Main.APP_REQUEST_SETUP_DRIVING);
            }
        });

        btn = (Button)findViewById(R.id.btnConfigIncoming);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main.this, SetupIncoming.class);
                startActivityForResult(myIntent,Main.APP_REQUEST_SETUP_INCOMING);
            }
        });

        btn = (Button)findViewById(R.id.btnConfigVoiceCommands);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main.this, SetupVoiceCommands.class);
                startActivityForResult(myIntent,Main.APP_REQUEST_SETUP_VOICE_COMMANDS);
            }
        });

        btn = (Button)findViewById(R.id.btnConfigPlayback);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main.this, SetupPlayback.class);
                startActivityForResult(myIntent,Main.APP_REQUEST_SETUP_PLAYBACK);
            }
        });

        btn = (Button)findViewById(R.id.btnConfigMisc);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main.this, SetupMisc.class);
                startActivityForResult(myIntent,Main.APP_REQUEST_SETUP_MISC);
            }
        });

        UpdateBazzStateDisplay();
        UpdateLogList();

        //--------------------------------------------------
    }

    private void HandleCallbackFromLib(String txt)
    {
        //Toast.makeText(Main.this,txt,Toast.LENGTH_SHORT).show();

        MyApplication.mLogLines.add(0,txt);

        runOnUiThread(new Runnable() {
            public void run() {
                UpdateLogList();
            }
        });
    }

    // ====================================================================================

    private static final int APP_REQUEST_SETUP_DRIVING        = 0;
    private static final int APP_REQUEST_SETUP_INCOMING       = 1;
    private static final int APP_REQUEST_SETUP_VOICE_COMMANDS = 2;
    private static final int APP_REQUEST_SETUP_PLAYBACK       = 3;
    private static final int APP_REQUEST_SETUP_MISC           = 4;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Decide what to do based on the original request code
        switch (requestCode)
        {
            case Main.APP_REQUEST_SETUP_DRIVING:
                UpdateBazzStateDisplay();
                break;
            case Main.APP_REQUEST_SETUP_INCOMING:
                UpdateBazzStateDisplay();
                break;
            case Main.APP_REQUEST_SETUP_VOICE_COMMANDS:
                UpdateBazzStateDisplay();
                break;
            case Main.APP_REQUEST_SETUP_PLAYBACK:
                UpdateBazzStateDisplay();
                break;
            case Main.APP_REQUEST_SETUP_MISC:
                UpdateBazzStateDisplay();
                break;
            default:
                // NOP
                break;
        }
    }

}
