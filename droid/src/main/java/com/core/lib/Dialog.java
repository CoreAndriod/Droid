package com.core.lib;

import android.content.DialogInterface;

/**
 * Created by l on 18/03/2018.
 */

public interface Dialog {


    void OnPositiveClick(DialogInterface dialog, int which);
    void OnNegativeClick(DialogInterface dialog, int which);

}
