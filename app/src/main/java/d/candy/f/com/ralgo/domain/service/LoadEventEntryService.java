package d.candy.f.com.ralgo.domain.service;

import android.support.annotation.NonNull;

import d.candy.f.com.ralgo.domain.RepositoryUser;
import d.candy.f.com.ralgo.domain.structure.Event;
import d.candy.f.com.ralgo.infra.Repository;

/**
 * Created by daichi on 17/08/16.
 */

public class LoadEventEntryService extends Service implements RepositoryUser {

    private Repository mRepository = null;

    public LoadEventEntryService() {}

    @Override
    boolean isReady() {
        return (mRepository != null);
    }

    @Override
    public void setRepository(@NonNull Repository repository) {
        mRepository = repository;
    }
}
