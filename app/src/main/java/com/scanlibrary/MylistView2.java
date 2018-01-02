package com.scanlibrary;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
 * 
 * ��������listview
 * @author Administrator
 *
 */

public class MylistView2 extends ListView {

    public MylistView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    public MylistView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public MylistView2(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       
        
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);  


    }
}
