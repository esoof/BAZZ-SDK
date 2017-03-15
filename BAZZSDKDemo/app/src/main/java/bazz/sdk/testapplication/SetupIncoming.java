package bazz.sdk.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.esoof.android.bazz_sdk.BazzLib;

/**
 * Created by TAKEphONE on 05/03/2017.
 */

public class SetupIncoming extends Activity {

    //==================================================================

    private void UpdateIncomingMessagesPrefsDisplay()
    {
        CheckBox cb;
        RadioButton rb;

        cb = (CheckBox)findViewById(R.id.btnWorkWithSMSorMMS);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingWorkWithSMSorMMS()));

        cb = (CheckBox)findViewById(R.id.btnWorkWithWhatsapp);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingWorkWithWhatsapp()));

        cb = (CheckBox)findViewById(R.id.btnWorkWithMessenger);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingWorkWithMessenger()));

        cb = (CheckBox)findViewById(R.id.btnWorkWithLine);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingWorkWithLine()));

        cb = (CheckBox)findViewById(R.id.btnWorkWithGmail);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingWorkWithGmail()));

        cb = (CheckBox)findViewById(R.id.btnWorkWithWeChat);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingWorkWithWeChat()));

        //--------------------------------------

        cb = (CheckBox)findViewById(R.id.btnMuteOthers);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingMuteOthers()));

        cb = (CheckBox)findViewById(R.id.btnHandleIncomingCalls);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingHandleIncomingCalls()));

        cb = (CheckBox)findViewById(R.id.btnReadUnknown);
        cb.setChecked((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getIncomingReadBlockedOrUnknownSenders()));

        //--------------------------------------

        int bazzState = MyApplication.mBazzLib.getWhatsappGroupTreatmentMode();

        rb = (RadioButton)findViewById(R.id.radioButtonDoNotPlay);
        rb.setChecked(bazzState== BazzLib.WHATSAPP_GROUP_TREATMENT_DO_NOT_PLAY);

        rb = (RadioButton)findViewById(R.id.radioButtonOnlyGroupName);
        rb.setChecked(bazzState==BazzLib.WHATSAPP_GROUP_TREATMENT_PLAY_NAME_ONLY);

        rb = (RadioButton)findViewById(R.id.radioButtonPlayAll);
        rb.setChecked(bazzState==BazzLib.WHATSAPP_GROUP_TREATMENT_PLAY_ALL);
    }

    //==================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        CheckBox cb;
        RadioButton rb;
        Button btn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_incoming);

        //--------------------------------------------------

        cb = (CheckBox)findViewById(R.id.btnWorkWithSMSorMMS);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingWorkWithSMSorMMS(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        cb = (CheckBox)findViewById(R.id.btnWorkWithWhatsapp);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingWorkWithWhatsapp(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        cb = (CheckBox)findViewById(R.id.btnWorkWithMessenger);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingWorkWithMessenger(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        cb = (CheckBox)findViewById(R.id.btnWorkWithLine);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingWorkWithLine(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        cb = (CheckBox)findViewById(R.id.btnWorkWithGmail);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingWorkWithGmail(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        cb = (CheckBox)findViewById(R.id.btnWorkWithWeChat);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingWorkWithWeChat(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        //---------------------------------------------------------------------------

        cb = (CheckBox)findViewById(R.id.btnMuteOthers);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingMuteOthers(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        cb = (CheckBox)findViewById(R.id.btnHandleIncomingCalls);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingHandleIncomingCalls(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        cb = (CheckBox)findViewById(R.id.btnReadUnknown);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setIncomingReadBlockedOrUnknownSenders(bChecked);
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        //---------------------------------------------------------------------------

        rb = (RadioButton)findViewById(R.id.radioButtonDoNotPlay);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setWhatsappGroupTreatmentMode(BazzLib.WHATSAPP_GROUP_TREATMENT_DO_NOT_PLAY);
                        UpdateIncomingMessagesPrefsDisplay();
                    }
                }
            }
        });

        rb = (RadioButton)findViewById(R.id.radioButtonOnlyGroupName);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setWhatsappGroupTreatmentMode(BazzLib.WHATSAPP_GROUP_TREATMENT_PLAY_NAME_ONLY);
                        UpdateIncomingMessagesPrefsDisplay();
                    }
                }
            }
        });

        rb = (RadioButton)findViewById(R.id.radioButtonPlayAll);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setWhatsappGroupTreatmentMode(BazzLib.WHATSAPP_GROUP_TREATMENT_PLAY_ALL);
                        UpdateIncomingMessagesPrefsDisplay();
                    }
                }
            }
        });

        //---------------------------------------------------------------------------

        btn = (Button)findViewById(R.id.btnConfigWhatsappGroupsBlocking);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.popupWhatsappGroupsIgnoreSettingsUI();
                    UpdateIncomingMessagesPrefsDisplay();
                }
            }
        });

        UpdateIncomingMessagesPrefsDisplay();
    }

    //==================================================================
}
