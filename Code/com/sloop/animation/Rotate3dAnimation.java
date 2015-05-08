/**
 * @Title: Rotate3dAnimation.java
 * @Package com.sloop.animation
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015��3��10�� ����11:20:10
 * @version V1.0
 */

package com.sloop.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 3D��ת��Ч
 * @ClassName: Rotate3dAnimation
 * @author sloop
 * @date 2015��3��10�� ����11:20:10
 */

public class Rotate3dAnimation extends Animation {

	// ��ʼ�Ƕ�
	private final float mFromDegrees;
	// �����Ƕ�
	private final float mToDegrees;
	// ���ĵ�
	private final float mCenterX;
	private final float mCenterY;
	private final float mDepthZ;	//���
	// �Ƿ���ҪŤ��
	private final boolean mReverse;
	// ����ͷ
	private Camera mCamera;
	ContextThemeWrapper context;
	//����--���ر�����Ĭ��ֵΪ1��
	float scale = 1;

	/**
	 * ����һ���µ�ʵ�� Rotate3dAnimation. 
	 * @param fromDegrees	��ʼ�Ƕ�
	 * @param toDegrees		�����Ƕ�
	 * @param centerX		���ĵ�x����
	 * @param centerY		���ĵ�y����
	 * @param depthZ		���
	 * @param reverse		�Ƿ�Ť��
	 */
	public Rotate3dAnimation(ContextThemeWrapper context, float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
		this.context = context;
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;
		mCenterX = centerX;
		mCenterY = centerY;
		mDepthZ = depthZ;
		mReverse = reverse;
		//��ȡ�ֻ����ر� ����dp��px�ı�����
		scale = context.getResources().getDisplayMetrics().density;
		Log.e("scale",""+scale);
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	// ����Transformation
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float fromDegrees = mFromDegrees;
		// �����м�Ƕ�
		float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

		
		final float centerX = mCenterX;
		final float centerY = mCenterY;
		final Camera camera = mCamera;

		final Matrix matrix = t.getMatrix();

		camera.save();
		if (mReverse) {
			camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
		} else {
			camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
		}
		camera.rotateY(degrees);
		// ȡ�ñ任��ľ���
		camera.getMatrix(matrix);
		camera.restore();

//----------------------------------------------------------------------------
		/** 
		 * �޸���������		(����(#��)��t�r(������///)
		 * ��Ҫ���ܣ�
		 * ԭ����3D��ת��������Ļ�����ܶ����������Ч�����ܴ�
		 * ��������Ļ���ر�Ϊ1,5���ֻ�����ʾЧ������������
		 * �������ر�3,0���ֻ�����о���ת�о�Ҫ������Ļ��Ե��
		 * ����ӭ������ĸо���
		 * 
		 * �������
		 * ������Ļ�����ܶȶԱ任�������У����
		 * ��֤�������������ȵ��ֻ�����ʾ��Ч��������ͬ��
		 *  
		 */
		float[] mValues = {0,0,0,0,0,0,0,0,0};
		matrix.getValues(mValues);			//��ȡ��ֵ
		mValues[6] = mValues[6]/scale;		//��ֵ����
		matrix.setValues(mValues);			//���¸�ֵ
		
//		Log.e("TAG", "mValues["+0+"]="+mValues[0]+"------------\t"+"mValues["+6+"]="+mValues[6]);
//----------------------------------------------------------------------------

		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
	


}
