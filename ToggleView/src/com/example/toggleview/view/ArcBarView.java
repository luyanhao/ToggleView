package com.example.toggleview.view;

import com.example.toggleview.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ArcBarView extends View {

	/**
	 * 矩形框，此进度条控件就画在这个矩形框中
	 */
	private RectF mOval;
	/**
	 * 前景色画笔
	 */
	private Paint mArcPaint;
	/**
	 * 背景色画笔
	 */
	private Paint mArcBGPaint;
	/**
	 * 父控件宽
	 */
	private float screen_width;
	/**
	 * 父控件高
	 */
	private float screen_height;
	/**
	 * 度数
	 */
	private float degree;
	/**
	 * 直径
	 */
	private float R2;
	/**
	 * 线宽
	 */
	private float line_width;
	/**
	 * 控件中心点X坐标
	 */
	private float center_x;
	/**
	 * 控件中心点Y坐标
	 */
	private float center_y;
	/**
	 * 半径
	 */
	private float r2;
	/**
	 * 用来标志是否点击了下载，画不同的颜色
	 */
	private int flag = 0;

	public ArcBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mArcPaint.setStyle(Paint.Style.STROKE);
		mArcPaint.setColor(getResources().getColor(R.color.green));
		BlurMaskFilter mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.INNER);
		mArcPaint.setMaskFilter(mBlur);

		mArcBGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mArcBGPaint.setStyle(Paint.Style.STROKE);
		mArcBGPaint.setColor(getResources().getColor(R.color.hui));

		BlurMaskFilter mBGBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.INNER);
		mArcBGPaint.setMaskFilter(mBGBlur);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 父控件的宽高
		final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		final int min = Math.min(width, height);
		setMeasuredDimension(width, height);
		screen_height = height;
		screen_width = width;
		center_x = screen_width / 2;
		center_y = screen_height / 2;
		R2 = min;
		r2 = R2 / 2;
		line_width = (float) (R2 * 0.05);
		float left = screen_width / 2 - r2 + line_width / 2;
		float top = screen_height / 2 - r2 + line_width / 2;
		float right = left + R2 - line_width;
		float bottom = top + R2 - line_width;
		mOval = new RectF(left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawSpeed(canvas);
	}

	private void drawSpeed(Canvas canvas) {
		mArcPaint.setStrokeWidth(line_width);
		// 设置背景宽度稍微窄一些
		mArcBGPaint.setStrokeWidth((float) (line_width - 0.001));
		// 画进度背景
		canvas.drawArc(mOval, -90, 360, false, mArcBGPaint);
		// 根据度数画进度
		canvas.drawArc(mOval, -90, degree, false, mArcPaint);
		mArcPaint.setStrokeWidth((float) (line_width * 2.05));
		if (flag == 0) {// 根据标志设置颜色
			mArcPaint.setColor(getResources().getColor(R.color.hui));
		} else {
			mArcPaint.setColor(getResources().getColor(R.color.green));
		}
		// 画竖线
		canvas.drawLine(center_x, (float) (center_y - r2 * 0.5), center_x, (float) (center_y + r2 * 0.25), mArcPaint);
		// 画左斜线
		canvas.drawLine((float) (center_x - r2 * 0.3), (float) (center_y), (float) (center_x + r2 * 0.06), (float) (center_y + r2 * 0.3), mArcPaint);
		// 画右斜线
		canvas.drawLine((float) (center_x + r2 * 0.3), (float) (center_y), (float) (center_x - r2 * 0.06), (float) (center_y + r2 * 0.3), mArcPaint);
		mArcPaint.setStrokeWidth((float) (line_width * 0.7));
		// 画横线
		canvas.drawLine((float) (center_x - r2 * 0.25), (float) (center_y + r2 * 0.4), (float) (center_x + r2 * 0.25), (float) (center_y + r2 * 0.4), mArcPaint);

	}

	public void redraw(int degree) {
		this.degree = degree;
		this.flag = 1;
		// 更新画面
		postInvalidate();
	}

}
