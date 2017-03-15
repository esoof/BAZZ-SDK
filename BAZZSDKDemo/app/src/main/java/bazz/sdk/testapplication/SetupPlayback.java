package bazz.sdk.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * Created by TAKEphONE on 05/03/2017.
 */

public class SetupPlayback extends Activity {

    //==================================================================

    private void UpdateDisplay()
    {
        if (MyApplication.mBazzLib!=null)
        {
            SeekBar sb;

            sb = (SeekBar)findViewById(R.id.seekBarVolume);
            sb.setProgress(MyApplication.mBazzLib.getPlaybackVolume());

            sb = (SeekBar)findViewById(R.id.seekBarHeadsetVolume);
            sb.setProgress(MyApplication.mBazzLib.getHeadsetVolume());

            sb = (SeekBar)findViewById(R.id.seekBarSpeed);
            sb.setProgress(MyApplication.mBazzLib.getPlaybackSpeed()+2);
        }
    }

    //==================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SeekBar sb;
        Button btn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_playback);

        //--------------------------------------------------------

        sb = (SeekBar)findViewById(R.id.seekBarVolume);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setPlaybackVolume(progress);
                        UpdateDisplay();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb = (SeekBar)findViewById(R.id.seekBarHeadsetVolume);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setHeadsetVolume(progress);
                        UpdateDisplay();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb = (SeekBar)findViewById(R.id.seekBarSpeed);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setPlaybackSpeed(progress-2);
                        UpdateDisplay();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //--------------------------------------------------------

        btn = (Button)findViewById(R.id.btnConfigBT);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.popupBluetoothDeviceSettingsUI();
                }
            }
        });

        btn = (Button)findViewById(R.id.btnConfigTTS);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.mBazzLib!=null)
                {
                    MyApplication.mBazzLib.popupTextToSpeechSettingsUI();
                }
            }
        });

        UpdateDisplay();
    }

    //==================================================================
}
