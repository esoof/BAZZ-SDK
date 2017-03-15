package bazz.sdk.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by TAKEphONE on 27/02/2017.
 */

public class SetupDriving extends Activity {

    //==================================================================

    private void UpdateDisplay()
    {
        CheckBox btn;
        boolean  bState;

        btn    = (CheckBox)findViewById(R.id.btnDriving);
        bState = ((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getDriveDetectByDriving()));
        btn.setChecked(bState);

        btn    = (CheckBox)findViewById(R.id.btnBluetooth);
        bState = ((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getDriveDetectByBluetooth()));
        btn.setChecked(bState);

        btn    = (CheckBox)findViewById(R.id.btnWiredHeadset);
        bState = ((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getDriveDetectByWiredHeadset()));
        btn.setChecked(bState);

        btn    = (CheckBox)findViewById(R.id.btnCharger);
        bState = ((MyApplication.mBazzLib!=null) && (MyApplication.mBazzLib.getDriveDetectByCharger()));
        btn.setChecked(bState);
    }

    //==================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        CheckBox cb;
        Button btn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_driving);

        cb    = (CheckBox)findViewById(R.id.btnDriving);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setDriveDetectByDriving(bChecked);
                    UpdateDisplay();
                }
            }
        });

        cb    = (CheckBox)findViewById(R.id.btnBluetooth);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setDriveDetectByBluetooth(bChecked);
                    UpdateDisplay();
                }
            }
        });

        cb    = (CheckBox)findViewById(R.id.btnWiredHeadset);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setDriveDetectByWiredHeadset(bChecked);
                    UpdateDisplay();
                }
            }
        });

        cb    = (CheckBox)findViewById(R.id.btnCharger);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.setDriveDetectByCharger(bChecked);
                    UpdateDisplay();
                }
            }
        });

        //---------------------------------------------------------------------------

        btn = (Button)findViewById(R.id.btnConfigBluetooth);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.popupBluetoothIgnoreSettingsUI();
                    UpdateDisplay();
                }
            }
        });

        UpdateDisplay();
    }

    //==================================================================
}