package com.xdsjs.save.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.xdsjs.save.R;

/**
 * 自定义实现四位密码框
 * Created by xdsjs on 2015/10/19.
 */
public class PasswordEditText extends EditText {

    private int passwordLength;//密码的长度
    private int passwordColor; //密码的颜色
    private float passwordRadius;//密码半径

    private int textlength;

    private Paint passwordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        passwordLength = 4;
        passwordColor = getResources().getColor(R.color.whitesmoke);
        passwordRadius = 15.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        //中心点坐标
        float px;
        float py = height / 2;
        float half = width / passwordLength / 2;
        passwordPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < passwordLength; i++) {
            if (i + 1 <= passwordLength) {
                passwordPaint.setStyle(Paint.Style.STROKE);
            } else {
                passwordPaint.setStyle(Paint.Style.FILL);
            }
            px = width * i / passwordLength + half;
            canvas.drawCircle(px, py, passwordRadius, passwordPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textlength = text.toString().length();
        invalidate();
    }
}
