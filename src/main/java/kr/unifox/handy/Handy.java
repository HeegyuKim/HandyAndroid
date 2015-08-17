package kr.unifox.handy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by ����� on 2015-07-30.
 */
public class Handy
{
    Context context;
    View view;

    /*************************************
        Constructors
     */
    public Handy(Context context)
    {
        this.context = context;
    }

    public Handy(View view)
    {
        this.view = view;
        context = view.getContext();
    }

    public Handy(Activity activity)
    {
        view = activity.findViewById(android.R.id.content);
        context = activity;
    }

    public Handy(Fragment fragment)
    {
        view = fragment.getView();
        context = fragment.getActivity();
    }


    /*********************************
     *
     * Getter & Setters
     *
     *
     */
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
    public void setView(Activity activity) {
        this.view = activity.findViewById(android.R.id.content);
    }


    /***********************************
     * Utility Methods
     */

    //
    //
    // View Getter
    public <T extends View> T get(int id)
    {
        View view = this.view.findViewById(id);

        return (T)view;
    }
    public TextView _tv(int id)
    {
        return get(id);
    }
    public Button _b(int id)
    {
        return get(id);
    }
    public EditText _et(int id)
    {
        return get(id);
    }

    //
    public void initializeResources() throws IllegalAccessException
    {
        Class cls = getClass();
        Field[] fields = cls.getDeclaredFields();

        for(Field f : fields)
        {
            if(!f.isAnnotationPresent(IDResource.class))
                continue;

            IDResource ann = f.getAnnotation(IDResource.class);
            int id = ann.value();

            if(!f.isAccessible())
                f.setAccessible(true);

            f.set(this, get(id));
        }
    }

    //
    //
    // View Setter
    public void onClick(View.OnClickListener lis, int ...ids)
    {
        for(int id : ids)
            get(id).setOnClickListener(lis);
    }
    public void _tv(int id, String text)
    {
        _tv(id).setText(text);
    }
    public void _tvf(int id, String format, Object ...args)
    {
        _tv(id).setText(String.format(format, args));
    }
    public void _tv(int id, int stringId)
    {
        _tv(id).setText(context.getString(stringId));
    }
    public void _tv(int id, int stringId, Object ...args)
    {
        _tv(id).setText(context.getString(stringId, args));
    }

    //
    //
    //
    public void alert(String title, String message, int okResId)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(okResId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }
    public void alert2(
            String title, String message,
            int positiveResId, int negativeResId,
            final Alert2Listener lis
    )
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(positiveResId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                lis.handle(true);
            }
        });
        alert.setNegativeButton(negativeResId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                lis.handle(false);
            }
        });
        alert.show();
    }
    public void toast(String message, boolean longDuration)
    {
        int duration = longDuration? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast.makeText(context, message, duration).show();
    }


    /*
        Start to another activity
     */
    public void start(Class cls)
    {
        Intent it = new Intent(context, cls);
        context.startActivity(it);
    }
}
