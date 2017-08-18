package d.candy.f.com.ralgo.presentation;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import d.candy.f.com.ralgo.R;

/**
 * Created by daichi on 8/18/17.
 */

public class DayEventsViewer<E extends DayEventsViewer.EventViewLayoutHolder> extends RelativeLayout {

    abstract public static class EventViewLayoutHolder {

        public EventViewLayoutHolder() {}

        abstract protected void onEventViewCreated(View layoutRoot);
        abstract protected int getLayoutXml();
    }

    public static final int EXAMPLE_SHOWN_EVENTS_LIMIT = 4;

    // UI
    private TextView mDateLabelView;
    private TextView mNoEventsMessageView;
    private LinearLayout mCardViewContainer;
    private Button mShowMoreButton;
    private ArrayList<CardView> mCardViews;

    // Attributes
    private String mDateLabelText;
    private int mDateLabelTextColor;
    private String mNoEventMessage;
    private int mShownEventsLimit;
    private int mCardElevationPx;
    private int mCardCornerRadiusPx;
    private int mSpaceSizeBetweenCardsPx;
    private int mCardMarginLeftPx;
    private int mCardMarginRightPx;
    private int mDateLabelMarginLeftPx;
    private int mDateLabelMarginRightPx;
    private int mDateLabelMarginTopPx;
    private int mDateLabelMarginBottomPx;
    private int mNoEventsMessageMarginTopPx;
    private int mNoEventsMessageMarginBottomPx;
    private int mNoEventsMessageMarginLeftPx;
    private int mNoEventsMessageMarginRightPx;
    private int mNoEventsMessageTextColor;
    private int mNoeventsMessageTextSizePx;
    private int mDateLabelTextSizePx;

    // Misc
    private Context mContext;
    // Margins for shadow of a card view
    private final int mCardShadowMarginBottom;
    private final int mCardShadowMarginSide;

    public DayEventsViewer(Context context) {
        this(context, null);
    }

    public DayEventsViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

        // Init semi-constant variables
        mCardShadowMarginBottom = getResources().getDimensionPixelSize(R.dimen.dayEventsViewerCardShadowMarginBtoom);
        mCardShadowMarginSide = getResources().getDimensionPixelSize(R.dimen.dayEventsViewerCardShadowMarginSide);
    }

    private void init(@NonNull Context context) {
        mCardViews = new ArrayList<>();
        mContext = context;
        View rootView = inflate(context, R.layout.day_events_viewer, this);
        mDateLabelView = rootView.findViewById(R.id.day_events_viewer_date_label);
        mNoEventsMessageView = rootView.findViewById(R.id.day_events_viewer_no_event_message);
        mCardViewContainer = rootView.findViewById(R.id.day_events_viewer_event_view_container);
        mShowMoreButton = rootView.findViewById(R.id.day_events_viewer_show_more_button);

        showMoreButton(false);
        showNoEventMessage(true);
    }

    private void init(@NonNull Context context, AttributeSet attrs) {
        init(context);
        if (attrs != null) {
            TypedArray array = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.DayEventsViewer, 0, 0);

            // DateLabel
            mDateLabelText = array.getString(R.styleable.DayEventsViewer_dateLabelText);
            mDateLabelTextColor = array.getColor(R.styleable.DayEventsViewer_dateLabelTextColor,
                    ContextCompat.getColor(mContext, R.color.dayEventsViewerDateLabelTextColor));
            mDateLabelTextSizePx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_dateLabelTextSize,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerDateLabelTextSize));
            mDateLabelMarginTopPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_dateLabelMarginTop,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerDateLabelMarginTop));
            mDateLabelMarginBottomPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_dateLabelMarginBottom,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerDateLabelMarginBottom));
            mDateLabelMarginLeftPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_dateLabelMarginLeft,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerDateLabelMarginLeft));
            mDateLabelMarginRightPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_dateLabelMarginRight,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerDateLabelMarginRight));

            // NoEventsMessage
            mNoEventMessage = array.getString(R.styleable.DayEventsViewer_noEventsMessage);
            mNoEventsMessageTextColor = array.getColor(R.styleable.DayEventsViewer_noeventsMessageTextColor,
                    ContextCompat.getColor(context, R.color.textDarkSecondary));
            mNoeventsMessageTextSizePx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_noEventsMessageTextSize,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerNoEventsMessageTextSize));
            mNoEventsMessageMarginTopPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_noEventsMessageMarginTop,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerNoEventsMessageMarginTop));
            mNoEventsMessageMarginBottomPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_noEventsMessageMarginBottom,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerNoEventsMessageMarginBottom));
            mNoEventsMessageMarginLeftPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_noEventsMessageMarginLeft,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerNoEventsMessageMarginLeft));
            mNoEventsMessageMarginRightPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_noEventsMessageMarginRight,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerNoEventsMessageMarginRight));

            // EventView
            mCardElevationPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_eventViewElevation,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerCardElevation));
            mCardCornerRadiusPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_eventViewElevation,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerCardCornerRadius));
            mSpaceSizeBetweenCardsPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_spaceSizeBetweenEventViews,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerSpaceSizeBetweenCards));
            mCardMarginRightPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_eventViewMarginRight,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerCardMarginRight));
            mCardMarginLeftPx = array.getDimensionPixelSize(R.styleable.DayEventsViewer_eventViewMarginLeft,
                    getResources().getDimensionPixelSize(R.dimen.dayEventsViewerCardMarginLeft));

            // Misc
            mShownEventsLimit = array.getInt(R.styleable.DayEventsViewer_shownEventsLimit,
                    EXAMPLE_SHOWN_EVENTS_LIMIT);


            array.recycle();
        }

        invalidateDateLabelTextViewForAttrs();
        invalidateDateLabelTextViewForLayout();
        invalidateNoEventMessageViewForAttrs();
        invalidateNoEventMessageViewForLayout();
    }

    private void invalidateDateLabelTextViewForLayout() {
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(mDateLabelView.getLayoutParams());
        layoutParams.setMargins(mDateLabelMarginLeftPx, mDateLabelMarginTopPx,
                mDateLabelMarginRightPx, mDateLabelMarginBottomPx);
        mDateLabelView.setLayoutParams(layoutParams);

        mDateLabelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDateLabelTextSizePx);
    }

    private void invalidateDateLabelTextViewForAttrs() {
        if (mDateLabelText != null) {
            mDateLabelView.setText(mDateLabelText);
        } else {
            mDateLabelView.setText(R.string.day_events_viewer_example_date_label_text);
        }

        mDateLabelView.setTextColor(mDateLabelTextColor);
    }

    private void invalidateNoEventMessageViewForAttrs() {
        if (mNoEventMessage != null) {
            mNoEventsMessageView.setText(mNoEventMessage);
        } else {
            mNoEventsMessageView.setText(R.string.day_events_viewer_example_no_events_message);
        }
        mNoEventsMessageView.setTextColor(mNoEventsMessageTextColor);
    }

    private void invalidateNoEventMessageViewForLayout() {
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(mNoEventsMessageView.getLayoutParams());
        layoutParams.setMargins(
                mNoEventsMessageMarginLeftPx, mNoEventsMessageMarginTopPx,
                mNoEventsMessageMarginRightPx, mNoEventsMessageMarginBottomPx);
        mNoEventsMessageView.setLayoutParams(layoutParams);

        mNoEventsMessageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNoeventsMessageTextSizePx);
    }

    public String getDateLabelText() {
        return mDateLabelText;
    }

    public void setDateLabelText(String dateLabelText) {
        mDateLabelText = dateLabelText;
        invalidateDateLabelTextViewForAttrs();
    }

    public int getDateLabelTextColor() {
        return mDateLabelTextColor;
    }

    public void setDateLabelTextColor(int dateLabelTextColor) {
        mDateLabelTextColor = dateLabelTextColor;
        invalidateDateLabelTextViewForAttrs();
    }

    public String getNoEventMessage() {
        return mNoEventMessage;
    }

    public void setNoEventMessage(String noEventMessage) {
        mNoEventMessage = noEventMessage;
        invalidateNoEventMessageViewForAttrs();
    }

    public int getShownEventsLimit() {
        return mShownEventsLimit;
    }

    public void setShownEventsLimit(int shownEventsLimit) {
        mShownEventsLimit = shownEventsLimit;
    }

    public int countEvents() {
        return mCardViews.size();
    }

    public int getNoEventsMessageTextColor() {
        return mNoEventsMessageTextColor;
    }

    public void setNoEventsMessageTextColor(int noEventsMessageTextColor) {
        mNoEventsMessageTextColor = noEventsMessageTextColor;
        invalidateNoEventMessageViewForAttrs();
    }

    public boolean hasNoEvents() {
        return (mCardViews.size() == 0);
    }

    public int addEventView(@NonNull EventViewLayoutHolder layoutHolder) {
        CardView cardView = (hasNoEvents()) ? createBaseCardView(false) : createBaseCardView(true);
        View layout = inflate(mContext, layoutHolder.getLayoutXml(), null);
        cardView.addView(layout);
        // The CardView contains a LayoutHolder object as a tag
        cardView.setTag(layoutHolder);

        if (hasNoEvents()) {
            showNoEventMessage(false);
        }

        if (countEvents() < mShownEventsLimit) {
            // Add the new CardView to this view
            mCardViewContainer.addView(cardView);

        } else if (countEvents() + 1 == mShownEventsLimit) {
            showMoreButton(true);
        }

        mCardViews.add(cardView);

        return mCardViews.size() - 1;
    }

    private void showMoreButton(boolean show) {
        if (show) {
            mShowMoreButton.setVisibility(View.VISIBLE);
        } else {
            mShowMoreButton.setVisibility(View.GONE);
        }
    }

    private CardView createBaseCardView(boolean addSpace) {
        CardView cardView = new CardView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (addSpace) {
            layoutParams.setMargins(
                    mCardMarginLeftPx + mCardShadowMarginSide,
                    mSpaceSizeBetweenCardsPx,
                    mCardMarginRightPx + mCardShadowMarginSide,
                    mCardShadowMarginSide);
        } else {
            layoutParams.setMargins(
                    mCardMarginLeftPx + mCardShadowMarginSide,
                    0,
                    mCardMarginRightPx + mCardShadowMarginSide,
                    mCardShadowMarginBottom);
        }
        cardView.setLayoutParams(layoutParams);
        cardView.setCardElevation(mCardElevationPx);
        cardView.setRadius(mCardCornerRadiusPx);

        return cardView;
    }

    @SuppressWarnings("unchecked")
    public E getEventViewLayoutHolder(int index) {
        return (E) mCardViews.get(index).getTag();
    }

    private void showNoEventMessage(boolean show) {
        if (show) {
            mNoEventsMessageView.setVisibility(View.VISIBLE);
        } else {
            mNoEventsMessageView.setVisibility(View.GONE);
        }
    }
}
