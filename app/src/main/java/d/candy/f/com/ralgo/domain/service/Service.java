package d.candy.f.com.ralgo.domain.service;

/**
 * Created by daichi on 17/08/14.
 */

abstract public class Service {

    abstract boolean isReady();

    protected void onServiceStart() {
        if (!isReady()) {
            throw new IllegalStateException("This service is not ready");
        }
    }
}
