# ShadowView

## 圆角矩形阴影(Rectangle shape shadow)

1. 普通阴影(shadow with no offset)

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

        <com.randalldev.shadowview.ShadowView
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:layout_constraintBottom_toBottomOf="@id/btn_target"
            app:layout_constraintEnd_toEndOf="@id/btn_target"
            app:layout_constraintStart_toStartOf="@id/btn_target"
            app:layout_constraintTop_toTopOf="@id/btn_target"
            app:shadowBottomHeight="16dp"
            app:shadowCardColor="#FF7043"
            app:shadowColor="#FFEE58"
            app:shadowLeftHeight="16dp"
            app:shadowRadius="16dp"
            app:shadowRightHeight="16dp"
            app:shadowRound="8dp"
            app:shadowTopHeight="16dp" />

        <Button
            android:id="@+id/btn_target"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@android:color/transparent"
            android:paddingStart="40dp"
            android:paddingEnd="40dp"
            android:paddingTop="20dp"
            android:paddingBottom="20dp"
            android:text="target button"
            android:textColor="@color/purple_700"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>
    ```

   ![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/a31943931c6b4b2fadcf106fdf47aa84~tplv-k3u1fbpfcp-watermark.image?)

   抛开配色不谈，这个效果还可以吧

2. 普通阴影 + 偏移(shadow with offset)

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
        ···
            app:shadowLeftHeight="16dp"
            app:shadowOffsetX="8dp"
            app:shadowOffsetY="4dp"
            app:shadowRadius="16dp"
        ···
    </androidx.constraintlayout.widget.ConstraintLayout>
    ```

   ![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/54eca85d94364c09bd58422fb5006f69~tplv-k3u1fbpfcp-watermark.image?)

## 圆形阴影(Circle shape shadow)

圆形阴影也可以认为是一种特殊的圆角矩形阴影，可以继续沿用圆角矩形的方式，或者添加 `shadowShape` 属性。

如果要使用圆角矩形的方式，需要事先确定目标控件的尺寸，这可能会遇到屏幕适配问题，所以我这里就直接演示使用 `shadowShape` 属性的方式

1. 普通阴影(shadow with no offset)
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
        ···
            app:shadowCardColor="#FF7043"
            app:shadowColor="#FFEE58"
            app:shadowRadius="16dp"
            app:shadowShape="1" />

        <Button
            android:id="@+id/btn_target"
            android:layout_width="wrap_content"
            android:layout_height="0dp"
            android:background="@android:color/transparent"
            android:padding="20dp"
            android:text="target button"
            android:textColor="@color/purple_700"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintDimensionRatio="1:1"
        ···

    </androidx.constraintlayout.widget.ConstraintLayout>
    ```

   ![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/62c575e46b8b40859ac5ab06aa010387~tplv-k3u1fbpfcp-watermark.image?)

   很简单吧，相比圆角矩形的配置，多了一个 `shadowShape` 但是少了很多尺寸的设置，只需要设置一个 `shadowRaduis` 即可。

2. 普通阴影 + 偏移(shadow with no offset)

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
        ···
            app:shadowCardColor="#FF7043"
            app:shadowColor="#FFEE58"
            app:shadowRadius="16dp"
            app:shadowOffsetX="4dp"
            app:shadowOffsetY="4dp"
            app:shadowShape="1" />

        ···
    </androidx.constraintlayout.widget.ConstraintLayout>
    ```

   ![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5f8775156a4f4a11ac35bcc7496d30e2~tplv-k3u1fbpfcp-watermark.image?)

> 这个使用起来还是比较方便的吧，只需要目标控件设置 `padding` 留出足够的空间绘制阴影效果即可。
>
>并且不需要再写 `drawable` 文件设置控件的背景了。
>
>当然也不是没有缺陷，目前还是只能兼容圆角矩形和圆形。异形的暂时没用到，可能也不会去做支持。

## 参考文章

[Android进阶：快速实现自定义阴影效果](https://zhuanlan.zhihu.com/p/136440080)
