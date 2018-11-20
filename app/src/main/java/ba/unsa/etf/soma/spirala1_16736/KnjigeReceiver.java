package ba.unsa.etf.soma.spirala1_16736;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;

/**
 * Created by Kenan Somun on 21/05/2018.
 */

public class KnjigeReceiver extends ResultReceiver {
    private Receiver receiver;

    public KnjigeReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver=receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(receiver != null) {
            receiver.onReceiveResult(resultCode,resultData);
        }
    }
}
