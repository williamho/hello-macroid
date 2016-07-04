package com.example.hello

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view._
import android.widget._
import com.fortysevendeg.macroid.extras.SpinnerTweaks._
import com.fortysevendeg.macroid.extras.ToolbarTweaks._
import com.fortysevendeg.macroid.extras.ViewTweaks._
import macroid._
import macroid.contrib._
import macroid.FullDsl._

class MainActivity extends AppCompatActivity with Contexts[FragmentActivity] {

  var toolbar = slot[Toolbar]
  var textView = slot[TextView]
  var toolbarSecondRow = slot[Toolbar]

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    setContentView {
      Ui.get {
        layout[LinearLayout](
          uiToolbar
            <~ tbTitle("Hello")
            <~ wire(toolbar),
          fragment[ExampleFragment]
            .pass(bundle("page" -> 1))
            .framed(Id.exampleFragment, Tag.exampleFragment)
            <~ LpTweaks.matchParent
        ) <~ vertical
      }
    }
  }

  lazy val uiToolbar: Ui[Toolbar] = {
    val context = implicitly[ActivityContextWrapper].getOriginal

    val contextTheme = new ContextThemeWrapper(
      context,
      R.style.ThemeOverlay_AppCompat_Dark_ActionBar
    )
    val _toolbar = new Toolbar(contextTheme)
    _toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light)

    setSupportActionBar(_toolbar)

    Ui(_toolbar) <~ 
      LpTweaks.matchWidth <~ 
      vBackground(R.color.primary) <~
      addViews(
        List(
          widget[Toolbar] <~ addViews(
            List(
              widget[Spinner] <~ sAdapter {
                val adapter = new ArrayAdapter[String](
                  context,
                  android.R.layout.simple_spinner_item,
                  Array("Title", "Artist")
                )
                adapter.setDropDownViewResource(
                  android.R.layout.simple_spinner_dropdown_item
                )
                adapter
              }
              ,
              widget[Spinner] <~ sAdapter {
                val adapter = new ArrayAdapter[String](
                  context,
                  android.R.layout.simple_spinner_item,
                  Array("Starts with", "Contains")
                )
                adapter.setDropDownViewResource(
                  android.R.layout.simple_spinner_dropdown_item
                )
                adapter
              }
            )
          ) <~ wire(toolbarSecondRow)
        )
      )
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

