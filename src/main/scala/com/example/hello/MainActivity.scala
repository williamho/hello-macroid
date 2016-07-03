package com.example.hello

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextThemeWrapper
import android.support.v7.widget.Toolbar
import android.widget.{ListView, LinearLayout, TextView, Button, EditText}
import macroid._
import macroid.contrib._
import macroid.FullDsl._
import com.fortysevendeg.macroid.extras.ToolbarTweaks._

class MainActivity extends AppCompatActivity with Contexts[Activity] {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    var toolbar = slot[Toolbar]
    var textView = slot[TextView]

    setContentView {
      Ui.get {
        layout[LinearLayout](
          widget[Toolbar]
            <~ tbTitle("Hello")
            <~ tbTextColor(Color.parseColor("#ffffff"))
            <~ tbBackgroundColor(Color.parseColor("#5d56b0"))
            <~ LpTweaks.matchWidth
            <~ wire(toolbar),
          widget[TextView]
            <~ wire(textView)
            <~ text("Initial text")
        ) <~ vertical
      }
    }
  }
}

