package in.gov.cgg.testtoaster;

import android.content.Context;
import android.widget.Toast;

public class ToasterMessage {

    public static void toasterMethod(Context context, String message) {
        Toast.makeText(context, "Toast here..", Toast.LENGTH_SHORT).show();
    }
}
