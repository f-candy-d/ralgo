package d.candy.f.com.ralgo.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import d.candy.f.com.ralgo.R;

/**
 * Created by daichi on 8/19/17.
 */

public class EventCardView extends RelativeLayout {

    abstract public static class LayoutHolder {

        public LayoutHolder() {}
        abstract public int getLayoutXml();
        abstract protected void onLayoutCreated(View view);
    }

    // UI
    private CardView mCardView;
    private TextView mHeaderLabel;
    private TextView mInitialCharLabel;
    private LinearLayout mContentLayoutContainer;
    private View mContentLayout;

    // Misc
    private Context mContext;

    public EventCardView(Context context) {
        super(context);
        init(context);
    }

    public EventCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context) {
        mContext = context;
        final View root = inflate(context, R.layout.event_card_view, this);
        mCardView = root.findViewById(R.id.event_card_view_card_view);
        mHeaderLabel = root.findViewById(R.id.event_card_view_header);
        mInitialCharLabel = root.findViewById(R.id.event_card_view_initial_char_label);
        mContentLayoutContainer = root.findViewById(R.id.event_card_view_content_layout_container);
        mContentLayout = null;
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
    }

    public void setContentLayout(@NonNull LayoutHolder layoutHolder) {
        if (mContentLayout != null) {
            mContentLayoutContainer.removeView(mContentLayout);
        }
        mContentLayout = inflateContentLayout(layoutHolder);
        mContentLayoutContainer.addView(mContentLayout);
    }

    private View inflateContentLayout(@NonNull LayoutHolder layoutHolder) {
        View layout = inflateContentLayout(layoutHolder.getLayoutXml());
        layoutHolder.onLayoutCreated(layout);
        return layout;
    }

    private View inflateContentLayout(int layoutXml) {
        return inflate(mContext, layoutXml, null);
    }

    public void setInitialCharacterLabelText(String text) {
        mInitialCharLabel.setText(getFirstCharacter(text));
    }

    public void setHeaderText(String text) {
        mHeaderLabel.setText(text);
    }

    /**
     * @param color The resource ID of a theme color of this view
     */
    public void setThemeColor(int color) {
        final int themeColor = ContextCompat.getColor(mContext, color);
        mCardView.setCardBackgroundColor(themeColor);
        mInitialCharLabel.setTextColor(themeColor);
        mHeaderLabel.setTextColor(themeColor);
    }

    private String getFirstCharacter(String string) {
        return (string == null || string.length() == 0)
                ? null
                : string.substring(0, 1);
    }

}
