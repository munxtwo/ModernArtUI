package com.ktay.ModernArtUI;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private SeekBar seekBar;
    private int[] textViewIds = { R.id.one, R.id.two, R.id.four, R.id.five, R.id.six};
    private Map<TextView, float[]> textViewColorMap = new HashMap<TextView, float[]>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initTextViews();
        initSeekBar();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.more_info:
                MoreInfoDialogFragment moreInfoDialogFragment = MoreInfoDialogFragment.getInstance();
                moreInfoDialogFragment.show(getFragmentManager(), "MoreInfoDialogFragment");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Initialize the seekbar
     */
    private void initSeekBar() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                for (TextView textView : textViewColorMap.keySet()) {

                    float[] hsvColor = textViewColorMap.get(textView).clone();
                    hsvColor[0] = (hsvColor[0] + (270f * progress / seekBar.getMax())) % 360;

                    textView.setBackgroundColor(Color.HSVToColor(hsvColor));
                }
            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

            }
        });
    }

    private void initTextViews() {

        for (int id : textViewIds) {
            TextView textView = (TextView) findViewById(id);
            int startColor = ((ColorDrawable) textView.getBackground()).getColor();

            float[] startHsvColor = new float[3];
            Color.colorToHSV(startColor, startHsvColor);

            textViewColorMap.put(textView, startHsvColor);
        }
    }

}
