package bazz.sdk.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

/**
 * Created by TAKEphONE on 05/03/2017.
 */

public class SetupMisc extends Activity {

    //==================================================================

    private void UpdateDisplay()
    {
        RadioButton rb;

        if (MyApplication.mBazzLib!=null)
        {
            String text = MyApplication.mBazzLib.getTextReply();

            rb = (RadioButton)findViewById(R.id.radioButtonUseDefaultText);
            rb.setChecked(text==null);

            rb = (RadioButton)findViewById(R.id.radioButtonUseCustomText);
            rb.setChecked(text!=null);

            boolean state = MyApplication.mBazzLib.getUseFloatingBubble();

            rb = (RadioButton)findViewById(R.id.radioButtonFullScreen);
            rb.setChecked(!state);

            rb = (RadioButton)findViewById(R.id.radioButtonFloatingBubble);
            rb.setChecked(state);
        }
    }

    //==================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        RadioButton rb;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_misc);

        rb    = (RadioButton)findViewById(R.id.radioButtonUseDefaultText);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setTextReply(null);
                        UpdateDisplay();
                    }
                }
            }
        });

        rb    = (RadioButton)findViewById(R.id.radioButtonUseCustomText);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setTextReply("Hi there, this is a BAZZ demo");
                        UpdateDisplay();
                    }
                }
            }
        });

        //---------------------------------------------------------------------------

        rb    = (RadioButton)findViewById(R.id.radioButtonFullScreen);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setUseFloatingBubble(false);
                        UpdateDisplay();
                    }
                }
            }
        });

        rb    = (RadioButton)findViewById(R.id.radioButtonFloatingBubble);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked)
                {
                    if (MyApplication.mBazzLib!=null)
                    {
                        MyApplication.mBazzLib.setUseFloatingBubble(true);
                        UpdateDisplay();
                    }
                }
            }
        });

        UpdateDisplay();
    }

    //==================================================================
}
