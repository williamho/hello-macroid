package com.example.hello

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view._
import android.widget.{ListView, LinearLayout, TextView, Button, EditText}
import com.fortysevendeg.macroid.extras.ToolbarTweaks._
import com.fortysevendeg.macroid.extras.ViewTweaks._
import macroid._
import macroid.contrib._
import macroid.FullDsl._

class ExampleFragment extends Fragment with Contexts[Fragment] {
  var textView = slot[TextView]

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View = Ui.get {
    layout[LinearLayout](
      widget[TextView] <~ wire(textView)
    )
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle): Unit = {
    super.onViewCreated(view, savedInstanceState)
    Ui.run {
      textView <~ text("this is some text in a fragment")
    }
  }
}

