package com.gary.spiders.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Gary on 11/11/2017.
 */

public class WordSearchGridAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] letters;

    public WordSearchGridAdapter(Context mContext, String[] letters) {
        this.mContext = mContext;
        this.letters = letters;
    }

    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView textView = new TextView(mContext);
        textView.setText(letters[position]);
        textView.setTextSize(20);
        textView.setIncludeFontPadding(false);
        return textView;
    }
}
