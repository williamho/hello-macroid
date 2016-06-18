package com.example.hello

import android.app.Activity
import android.os.Bundle
import android.widget.{ListView, LinearLayout, TextView, Button}
import macroid._
import macroid.FullDsl._

class MainActivity extends Activity with Contexts[Activity] {
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    var textView = slot[TextView]

    setContentView {
      getUi {
        layout[LinearLayout](
          widget[Button]
            <~ text("This is a button")
            <~ On.click {
              textView <~ text("Button has been pressed")
            },
          widget[TextView]
            <~ wire(textView)
            <~ text("Initial text")
        ) <~ vertical
      }
    }
  }
}

