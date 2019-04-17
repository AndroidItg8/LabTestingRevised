package itg8.com.labtestingapp.common.htmlparser;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;



public class URLImageParser implements Html.ImageGetter {
    Context c;
    View container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public URLImageParser(View t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();


        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }


}