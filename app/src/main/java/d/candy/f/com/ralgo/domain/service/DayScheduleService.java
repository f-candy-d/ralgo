package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;

import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.domain.structure.DaySchedule;
import d.candy.f.com.ralgo.infra.Repository;

/**
 * Created by daichi on 8/16/17.
 */

public class DayScheduleService extends Service implements RepositoryUser {

    private Repository mRepository = null;
    @NonNull private DaySchedule mDaySchedule;

    public DayScheduleService(long date) {
        mDaySchedule = new DaySchedule(date);
        initDaySchedule(date);
    }

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }

    private void initDaySchedule(long date) {
        mDaySchedule.changeDate(date);

    }
}
