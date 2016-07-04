package com.example.hello

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
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
    val contextTheme = new ContextThemeWrapper(
      activityContextWrapper.getOriginal,
      R.style.ThemeOverlay_AppCompat_Dark_ActionBar
    )
    val _toolbar = new Toolbar(contextTheme)
    _toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light)

    setSupportActionBar(_toolbar)

    Ui(_toolbar) <~ LpTweaks.matchWidth <~ vBackground(R.color.primary)
  }

  // in fragments, this has inflater as a second arg
  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    val item = menu.add(0, Id.menuItemSearch, 0, "Search") // TODO: use R string
    MenuItemCompat.setShowAsAction(
      item,
      MenuItemCompat.SHOW_AS_ACTION_ALWAYS |
      MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
    )

    def weight(w: Float) = Tweak[View] { v =>
      val params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, w)
      v.setLayoutParams(params)
    }

    var searchField = slot[SearchView]

    // ugh this is a mess
    val searchView = Ui.get {
      layout[TableLayout](
        layout[TableRow](
          widget[Spinner] <~ sAdapter {
            val adapter = new ArrayAdapter[String](
              activityContextWrapper.getOriginal,
              android.R.layout.simple_spinner_item,
              Array("Title", "Artist")
            )
            adapter.setDropDownViewResource(
              android.R.layout.simple_spinner_dropdown_item
            )
            adapter
          } <~ weight(1),
          widget[Spinner] <~ sAdapter {
            val adapter = new ArrayAdapter[String](
              activityContextWrapper.getOriginal,
              android.R.layout.simple_spinner_item,
              Array("starts with", "contains")
            )
            adapter.setDropDownViewResource(
              android.R.layout.simple_spinner_dropdown_item
            )
            adapter
          } <~ weight(1)
        ),
        layout[TableRow](
          widget[SearchView] <~ wire(searchField)
        )
      ) <~ vertical
    }

    searchField.foreach { s =>
      s.setIconifiedByDefault(false)
      s.setSubmitButtonEnabled(true)
    }

    MenuItemCompat.setActionView(item, searchView)

    super.onCreateOptionsMenu(menu)
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = item.getItemId match {
    case R.id.action_search =>
      Ui.run { textView <~ text("Hey!") }
      true
    case _ => super.onOptionsItemSelected(item)
  }
}

