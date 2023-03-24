package com.randalldev.shadowview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

/**
 *
 * @author Randall.
 * @Date 2023-03-16.
 * @Time 11:11.
 */
class ShadowView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    /**
     * 阴影的形状
     */
    private var shadowShape = 0

    /**
     * 被绘制阴影的控件的圆角
     */
    private var shadowRound = 0

    /**
     * 阴影的颜色
     */
    private var shadowColor = 0

    /**
     * 被绘制阴影的控件的背景色
     */
    private var shadowCardColor = 0

    /**
     * 阴影的范围，对应设计稿的 Blur 参数
     */
    private var shadowRadius = 0

    /**
     * 阴影的 X 轴位移，对应设计稿 Offset 的 X 参数
     */
    private var shadowOffsetY = 0

    /**
     * 阴影的 Y 轴位移，对应设计稿 Offset 的 Y 参数
     */
    private var shadowOffsetX = 0

    /**
     * 顶部实际可供显示的区域
     */
    private var shadowTopHeight = 0

    /**
     * 左侧实际可供显示的区域
     */
    private var shadowLeftHeight = 0

    /**
     * 右侧实际可供显示的区域
     */
    private var shadowRightHeight = 0

    /**
     * 底部实际可供显示的区域
     */
    private var shadowBottomHeight = 0

    init {
        initView(context, attrs)
        //设置软件渲染类型，跟绘制阴影相关，后边会说
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ShadowView)
        shadowShape = a.getInteger(R.styleable.ShadowView_shadowShape, DEFAULT_VALUE_SHADOW_SHAPE)
        shadowRound = a.getDimensionPixelSize(R.styleable.ShadowView_shadowRound, DEFAULT_VALUE_SHADOW_ROUND)
        shadowColor = a.getColor(
            R.styleable.ShadowView_shadowColor, ContextCompat.getColor(
                context, DEFAULT_VALUE_SHADOW_COLOR
            )
        )
        shadowCardColor = a.getColor(
            R.styleable.ShadowView_shadowCardColor, ContextCompat.getColor(
                context, DEFAULT_VALUE_SHADOW_CARD_COLOR
            )
        )
        shadowTopHeight = a.getDimensionPixelSize(
            R.styleable.ShadowView_shadowTopHeight, dp2px(
                context, DEFAULT_VALUE_SHADOW_TOP_HEIGHT.toFloat()
            )
        )
        shadowRightHeight = a.getDimensionPixelSize(
            R.styleable.ShadowView_shadowRightHeight, dp2px(
                context, DEFAULT_VALUE_SHADOW_RIGHT_HEIGHT.toFloat()
            )
        )
        shadowLeftHeight = a.getDimensionPixelSize(
            R.styleable.ShadowView_shadowLeftHeight, dp2px(
                context, DEFAULT_VALUE_SHADOW_LEFT_HEIGHT.toFloat()
            )
        )
        shadowBottomHeight = a.getDimensionPixelSize(
            R.styleable.ShadowView_shadowBottomHeight, dp2px(
                context, DEFAULT_VALUE_SHADOW_BOTTOM_HEIGHT.toFloat()
            )
        )
        shadowOffsetX = a.getDimensionPixelSize(
            R.styleable.ShadowView_shadowOffsetX, dp2px(
                context, DEFAULT_VALUE_SHADOW_OFFSET_X.toFloat()
            )
        )
        shadowOffsetY = a.getDimensionPixelSize(
            R.styleable.ShadowView_shadowOffsetY, dp2px(
                context, DEFAULT_VALUE_SHADOW_OFFSET_Y.toFloat()
            )
        )
        // 阴影的宽度
        shadowRadius = a.getDimensionPixelSize(
            R.styleable.ShadowView_shadowRadius, dp2px(
                context, DEFAULT_VALUE_SHADOW_RADIUS.toFloat()
            )
        )

        a.recycle()
        setPadding(shadowRadius, shadowRadius, shadowRadius, shadowRadius)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas) {
        val shadowPaint = Paint()
        shadowPaint.color = shadowCardColor
        shadowPaint.style = Paint.Style.FILL
        shadowPaint.isAntiAlias = true
        val left = shadowLeftHeight.toFloat()
        val top = shadowTopHeight.toFloat()
        val right = (width - shadowRightHeight).toFloat()
        val bottom = (height - shadowBottomHeight).toFloat()
        shadowPaint.setShadowLayer(shadowRadius.toFloat(), shadowOffsetX.toFloat(), shadowOffsetY.toFloat(), shadowColor)
        if (shadowShape == 0) {
            // 如果绘制圆角矩形的阴影，用 drawRoundRect
            val rectF = RectF(left, top, right, bottom)
            canvas.drawRoundRect(rectF, shadowRound.toFloat(), shadowRound.toFloat(), shadowPaint)
        } else {
            // 如果绘制圆形的阴影，用 drawCircle
            val radius = measuredHeight.toFloat() / 2 - shadowRadius
            canvas.drawCircle(measuredHeight.toFloat() / 2, measuredHeight.toFloat() / 2, radius, shadowPaint)
        }
        shadowPaint.utilReset()
        canvas.save()
    }

    companion object {
        private val DEFAULT_VALUE_SHADOW_COLOR = R.color.shadow_default_color
        private val DEFAULT_VALUE_SHADOW_CARD_COLOR = R.color.shadow_card_default_color
        private const val DEFAULT_VALUE_SHADOW_SHAPE = 0
        private const val DEFAULT_VALUE_SHADOW_ROUND = 0
        private const val DEFAULT_VALUE_SHADOW_RADIUS = 40
        private const val DEFAULT_VALUE_SHADOW_TOP_HEIGHT = 40
        private const val DEFAULT_VALUE_SHADOW_LEFT_HEIGHT = 40
        private const val DEFAULT_VALUE_SHADOW_RIGHT_HEIGHT = 40
        private const val DEFAULT_VALUE_SHADOW_BOTTOM_HEIGHT = 40
        private const val DEFAULT_VALUE_SHADOW_OFFSET_Y = 0
        private const val DEFAULT_VALUE_SHADOW_OFFSET_X = 0

        fun dp2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }
    }

    /**
     * 自定义画笔重置方法
     */
    @JvmOverloads
    internal fun Paint.utilReset(colorString: String? = null, @ColorInt color: Int? = null) {
        this.reset()
        //这里默认值使用白色，可处理掉系统渲染抗锯齿时，人眼可观察到像素颜色
        this.color = color ?: Color.parseColor(colorString ?: "#FFFFFF")
        this.isAntiAlias = true
        this.style = Paint.Style.FILL
        this.strokeWidth = 0f
    }
}

