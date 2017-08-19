package d.candy.f.com.ralgo.presentation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import d.candy.f.com.ralgo.R;

/**
 * Created by daichi on 8/19/17.
 */

public class MinimumEventCardView extends RelativeLayout {

    // UI
    private CardView mCardView;
    private TextView mInitialCharLabel;

    // Misc
    private Context mContext;

    public MinimumEventCardView(Context context) {
        super(context);
        init(context);
    }

    public MinimumEventCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context) {
        mContext = context;
        final View root = inflate(context, R.layout.minimum_event_card_view, this);
        mCardView = root.findViewById(R.id.minimum_event_card_view_card_view);
        mInitialCharLabel = mCardView.findViewById(R.id.minimum_event_card_view_initial_char_label);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
    }

    public void setInitialCharcterLabelText(String string) {
        mInitialCharLabel.setText(getFirstCharacter(string));
    }

    /**
     * @param color The resource ID of a theme color of this view
     */
    public void setThemeColor(int color) {
        final int themeColor = ContextCompat.getColor(mContext, color);
        mCardView.setBackgroundColor(themeColor);
        mInitialCharLabel.setTextColor(themeColor);
    }

    private String getFirstCharacter(String string) {
        return (string == null || string.length() == 0)
                ? null
                : string.substring(0, 1);
    }
}
