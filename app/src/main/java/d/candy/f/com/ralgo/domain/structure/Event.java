package d.candy.f.com.ralgo.domain.structure;

import d.candy.f.com.ralgo.utils.Quantizable;

/**
 * Created by daichi on 17/08/16.
 */

public class Event extends Thing {

    public enum Repetition implements Quantizable {
        EVERYDAY {@Override public int quantize() { return 0; }},
        WEEKLY {@Override public int quantize() { return 1; }},
        ONE_DAY {@Override public int quantize() { return 2; }},
        CUSTOM {@Override public int quantize() { return 3;}};

        public static final Converter<Repetition> CONVERTER =
                new Converter<Repetition>() {
                    @Override
                    public Repetition convertFromQuantity(int quantity) {
                        Repetition[] values = Repetition.values();
                        for (Repetition repetition : values) {
                            if (repetition.quantize() == quantity) {
                                return repetition;
                            }
                        }
                        return null;
                    }
                };
    }


    private long mId;
    private long mContentThingId;
    private long mStartDate;
    private long mEndDate;
    private String mNote;
}
