package com.example.hello

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
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

class MainActivity extends AppCompatActivity with Contexts[FragmentActivity] {

  var toolbar = slot[Toolbar]
  var textView = slot[TextView]

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    setContentView {
      Ui.get {
        layout[LinearLayout](
          widget[Toolbar]
            <~ tbTitle("Hello")
            <~ tbBackgroundColor(R.color.primary)
            <~ vElevation(4.0f)
            <~ LpTweaks.matchWidth
            <~ wire(toolbar)
            <~ Tweak[Toolbar]{ t => 
              setSupportActionBar(t)
            },
          fragment[ExampleFragment].framed(Id.exampleFragment, Tag.exampleFragment)
        ) <~ vertical
      }
    }
  }

  // in fragments, this has inflater as a second arg
  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(R.menu.search_menu, menu)
    super.onCreateOptionsMenu(menu)
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = item.getItemId match {
    case R.id.action_search =>
      Ui.run { textView <~ text("Hey!") }
      true
    case _ => super.onOptionsItemSelected(item)
  }
}

