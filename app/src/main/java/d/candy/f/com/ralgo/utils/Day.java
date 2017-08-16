package d.candy.f.com.ralgo.utils;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by daichi on 8/16/17.
 */

public class Day {

    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private int mDayOfWeek;

    public Day(@NonNull Calendar calendar) {
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public Day(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public Day() {
        this(Calendar.getInstance());
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public int getDayOfWeek() {
        return mDayOfWeek;
    }

    public Calendar getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDayOfMonth, 0, 0, 0);
        return calendar;
    }

    public Calendar getStartOfNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDayOfMonth, 0, 0, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    public boolean isOnSameDay(long timeInMillis) {
        final long startOfDay = getStartOfDay().getTimeInMillis();
        final long startOfNextDay = getStartOfNextDay().getTimeInMillis();

        return (startOfDay <= timeInMillis && timeInMillis < startOfNextDay);
    }

    public boolean isOnSameDay(@NonNull Calendar calendar) {
        return isOnSameDay(calendar.getTimeInMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        return  (mYear == day.mYear &&
                mMonth == day.mMonth &&
                mDayOfMonth == day.mDayOfMonth &&
                mDayOfWeek == day.mDayOfWeek);

    }

    @Override
    public int hashCode() {
        int result = mYear;
        result = 31 * result + mMonth;
        result = 31 * result + mDayOfMonth;
        result = 31 * result + mDayOfWeek;
        return result;
    }
}
