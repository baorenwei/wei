package com.example.base.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 滑块工具
 *
 * */
public class LineIndicatier extends LinearLayout {
	private View indicatier_line = null;
	private Scroller scroller = null;
	
	private int index = 0;
	private int secontCount = 3;

	public LineIndicatier(Context context) {
		super(context);
		init();
	}
	
	public LineIndicatier(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		indicatier_line = new View(getContext());
		LayoutParams lp = new LayoutParams(1, 1);
		indicatier_line.setLayoutParams(lp);
		
		this.addView(indicatier_line);
		indicatier_line.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
		scroller = new Scroller(getContext());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		//if (changed) {
			int width = (r-l);
			int height = b-t;
			
			layout_indicatier(width, height);
		//}
	}
	
	public int getSectionCount() {
		return secontCount;
	}
	
	public void setSectionCount(int count) {
		secontCount = count;
		
		layout_indicatier(getWidth(), getHeight());
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int _index) {
		int tmp_index = index;
		index = _index%secontCount;
		
		int width = indicatier_line.getWidth();
		int dx = (index-tmp_index)*width;
		
		scroller.startScroll(tmp_index*width, 0, dx, 0, 1000);
		postInvalidate();
	}
	
	public void setIndex1(int _index) {
		final int tmp_index = index;
		index = _index%secontCount;
		
		int width = indicatier_line.getWidth();

		Animation animation = new TranslateAnimation(tmp_index*width, index*width, 0, 0);
		animation.setFillAfter(true);
		animation.setDuration(600);
		indicatier_line.startAnimation(animation);
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		
		if (scroller.computeScrollOffset()) {
            int width = indicatier_line.getWidth();
            int height = indicatier_line.getHeight();
            int left = scroller.getCurrX();
            int right = left+width;
            indicatier_line.layout(left, 0, right, height);
            
            postInvalidate();
        }  
	}
	
	private void layout_indicatier(int width, int height) {
		int w = width/secontCount;
		
		int left = index*w;
		int right = left+w;
		indicatier_line.layout(left, 0, right, height);
		
		//Matrix matrix = new Matrix();   
		//matrix.postTranslate(0, 0);   
		//indicatier_line.setImageMatrix(matrix);
	}
}
