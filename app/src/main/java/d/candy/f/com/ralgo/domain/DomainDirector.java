package d.candy.f.com.ralgo.domain;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.EnumMap;

import d.candy.f.com.ralgo.data_store.sql_database.DbOpenHelper;
import d.candy.f.com.ralgo.domain.service.Service;
import d.candy.f.com.ralgo.infra.SqliteAndSharedPrefRepository;
import d.candy.f.com.ralgo.infra.Repository;

/**
 * Created by daichi on 17/08/14.
 */

public class DomainDirector<E extends Enum<E>> {

    @NonNull final private EnumMap<E, Service> mServiceMap;
    @NonNull final private Repository mRepository;

    public DomainDirector(@NonNull Context context, @NonNull Class<E> keyClass) {
        mServiceMap = new EnumMap<>(keyClass);
        mRepository = new SqliteAndSharedPrefRepository(context, new DbOpenHelper(context));
    }

    public void addService(@NonNull E key, Service service) {
        mServiceMap.put(key, service);
        if (service instanceof RepositoryUser) {
            ((RepositoryUser) service).setRepository(mRepository);
        }
    }

    public Service getService(@NonNull E key) {
        if (mServiceMap.containsKey(key)) {
            return mServiceMap.get(key);
        }
        throw new IllegalStateException("The required service does not exist");
    }

    public <U extends Service> U getAndCastService(@NonNull E key, @NonNull Class<U> serviceClass) {
        return serviceClass.cast(getService(key));
    }
}
