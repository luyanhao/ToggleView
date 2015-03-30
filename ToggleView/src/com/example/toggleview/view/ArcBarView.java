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
	 * ���ο򣬴˽������ؼ��ͻ���������ο���
	 */
	private RectF mOval;
	/**
	 * ǰ��ɫ����
	 */
	private Paint mArcPaint;
	/**
	 * ����ɫ����
	 */
	private Paint mArcBGPaint;
	/**
	 * ���ؼ���
	 */
	private float screen_width;
	/**
	 * ���ؼ���
	 */
	private float screen_height;
	/**
	 * ����
	 */
	private float degree;
	/**
	 * ֱ��
	 */
	private float R2;
	/**
	 * �߿�
	 */
	private float line_width;
	/**
	 * �ؼ����ĵ�X����
	 */
	private float center_x;
	/**
	 * �ؼ����ĵ�Y����
	 */
	private float center_y;
	/**
	 * �뾶
	 */
	private float r2;
	/**
	 * ������־�Ƿ��������أ�����ͬ����ɫ
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
		// ���ؼ��Ŀ��
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
		// ���ñ��������΢խһЩ
		mArcBGPaint.setStrokeWidth((float) (line_width - 0.001));
		// �����ȱ���
		canvas.drawArc(mOval, -90, 360, false, mArcBGPaint);
		// ���ݶ���������
		canvas.drawArc(mOval, -90, degree, false, mArcPaint);
		mArcPaint.setStrokeWidth((float) (line_width * 2.05));
		if (flag == 0) {// ���ݱ�־������ɫ
			mArcPaint.setColor(getResources().getColor(R.color.hui));
		} else {
			mArcPaint.setColor(getResources().getColor(R.color.green));
		}
		// ������
		canvas.drawLine(center_x, (float) (center_y - r2 * 0.5), center_x, (float) (center_y + r2 * 0.25), mArcPaint);
		// ����б��
		canvas.drawLine((float) (center_x - r2 * 0.3), (float) (center_y), (float) (center_x + r2 * 0.06), (float) (center_y + r2 * 0.3), mArcPaint);
		// ����б��
		canvas.drawLine((float) (center_x + r2 * 0.3), (float) (center_y), (float) (center_x - r2 * 0.06), (float) (center_y + r2 * 0.3), mArcPaint);
		mArcPaint.setStrokeWidth((float) (line_width * 0.7));
		// ������
		canvas.drawLine((float) (center_x - r2 * 0.25), (float) (center_y + r2 * 0.4), (float) (center_x + r2 * 0.25), (float) (center_y + r2 * 0.4), mArcPaint);

	}

	public void redraw(int degree) {
		this.degree = degree;
		this.flag = 1;
		// ���»���
		postInvalidate();
	}

}
