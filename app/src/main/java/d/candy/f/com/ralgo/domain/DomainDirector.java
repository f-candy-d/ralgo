package d.candy.f.com.ralgo.domain;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.EnumMap;

import d.candy.f.com.ralgo.domain.service.Service;
import d.candy.f.com.ralgo.infra.LocalSqlAndSharedPrefRepository;
import d.candy.f.com.ralgo.infra.Repository;

/**
 * Created by daichi on 17/08/14.
 */

public class DomainDirector<E extends Enum<E>> {

    @NonNull final private EnumMap<E, Service> mEServiceMap;
    @NonNull final private Repository mRepository;

    public DomainDirector(@NonNull Context context, @NonNull Class<E> keyClass) {
        mEServiceMap = new EnumMap<>(keyClass);
        mRepository = new LocalSqlAndSharedPrefRepository();
    }
}
