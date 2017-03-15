package bazz.sdk.testapplication;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.esoof.android.bazz_sdk.BazzLib;

/**
 * Created by TAKEphONE on 05/03/2017.
 */

public class SetupVoiceCommands extends Activity {

    //==================================================================

    private void UpdateDisplay()
    {
        RadioButton btn;
        int         nState;

        if (MyApplication.mBazzLib!=null)
        {
            nState = MyApplication.mBazzLib.getVoiceCommandsAfterSenderDoWhat();

            btn    = (RadioButton)findViewById(R.id.radioButtonAfterSenderWaitCommand);
            btn.setChecked(nState== BazzLib.VOICE_COMMANDS_AFTER_SENDER_WAIT_COMMAND);

            btn    = (RadioButton)findViewById(R.id.radioButtonAfterSenderPlayContent);
            btn.setChecked(nState== BazzLib.VOICE_COMMANDS_AFTER_SENDER_READ_CONTENT);

            btn    = (RadioButton)findViewById(R.id.radioButtonAfterSenderStop);
            btn.setChecked(nState== BazzLib.VOICE_COMMANDS_AFTER_SENDER_DO_NOTHING);

            //----------------------

            nState = MyApplication.mBazzLib.getVoiceCommandsAfterContentDoWhat();

            btn    = (RadioButton)findViewById(R.id.radioButtonAfterContentWaitCommand);
            btn.setChecked(nState== BazzLib.VOICE_COMMANDS_AFTER_CONTENT_WAIT_COMMAND);

            btn    = (RadioButton)findViewById(R.id.radioButtonAfterContentSendText);
            btn.setChecked(nState== BazzLib.VOICE_COMMANDS_AFTER_CONTENT_SEND_TEXT);

            btn    = (RadioButton)findViewById(R.id.radioButtonAfterContentDoNothing);
            btn.setChecked(nState== BazzLib.VOICE_COMMANDS_AFTER_CONTENT_DO_NOTHING);
        }
    }

    //==================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        RadioButton rb;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_voice_commands);

        rb = (RadioButton)findViewById(R.id.radioButtonAfterSenderWaitCommand);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    if (MyApplication.mBazzLib != null) {
                        MyApplication.mBazzLib.setVoiceCommandsAfterSenderDoWhat(BazzLib.VOICE_COMMANDS_AFTER_SENDER_WAIT_COMMAND);
                        UpdateDisplay();
                    }
                }
            }
        });

        rb = (RadioButton)findViewById(R.id.radioButtonAfterSenderPlayContent);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    if (MyApplication.mBazzLib != null) {
                        MyApplication.mBazzLib.setVoiceCommandsAfterSenderDoWhat(BazzLib.VOICE_COMMANDS_AFTER_SENDER_READ_CONTENT);
                        UpdateDisplay();
                    }
                }
            }
        });

        rb = (RadioButton)findViewById(R.id.radioButtonAfterSenderStop);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    if (MyApplication.mBazzLib != null) {
                        MyApplication.mBazzLib.setVoiceCommandsAfterSenderDoWhat(BazzLib.VOICE_COMMANDS_AFTER_SENDER_DO_NOTHING);
                        UpdateDisplay();
                    }
                }
            }
        });

        //--------------------------------------------------------------

        rb = (RadioButton)findViewById(R.id.radioButtonAfterContentWaitCommand);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setVoiceCommandsAfterContentDoWhat(BazzLib.VOICE_COMMANDS_AFTER_CONTENT_WAIT_COMMAND);
                    UpdateDisplay();
                }
            }
        });

        rb = (RadioButton)findViewById(R.id.radioButtonAfterContentSendText);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setVoiceCommandsAfterContentDoWhat(BazzLib.VOICE_COMMANDS_AFTER_CONTENT_SEND_TEXT);
                    UpdateDisplay();
                }
            }
        });

        rb = (RadioButton)findViewById(R.id.radioButtonAfterContentDoNothing);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setVoiceCommandsAfterContentDoWhat(BazzLib.VOICE_COMMANDS_AFTER_CONTENT_DO_NOTHING);
                    UpdateDisplay();
                }
            }
        });

        UpdateDisplay();
    }

    //==================================================================
}
