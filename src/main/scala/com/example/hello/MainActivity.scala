package com.example.hello

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{AppCompatSpinner, Toolbar}
import android.view._
import android.view.ViewGroup.LayoutParams._
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
      R.style.AppTheme_Toolbar
    )
    val _toolbar = new Toolbar(contextTheme)
    _toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light)

    setSupportActionBar(_toolbar)

    Ui(_toolbar) <~
      LpTweaks.matchWidth <~
      vBackground(R.color.primary) <~
      vElevation(4.dp)
  }

  // in fragments, this has inflater as a second arg
  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    val context: ContextWrapper = ContextWrapper(
      getSupportActionBar().getThemedContext()
    )

    val item = menu.add(0, Id.menuItemSearch, 0, "Search") // TODO: use R string
    MenuItemCompat.setShowAsAction(
      item,
      MenuItemCompat.SHOW_AS_ACTION_ALWAYS |
      MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
    )

    var searchField = slot[SearchView]

    def sChoices(choices: String*): Tweak[Spinner] = sAdapter {
      val adapter = new ArrayAdapter[String](
        context.getOriginal, // TODO: accept ContextWrapper implicit
        android.R.layout.simple_spinner_item,
        choices.toArray
      )
      adapter.setDropDownViewResource(R.layout.toolbar_dropdown_item)
      adapter
    }

    // ugh this is a mess
    val searchView = Ui.get {
      layout[TableLayout](
        layout[TableRow](
          widget[AppCompatSpinner](context)
            <~ sChoices("Title", "Artist")
            <~ lp[TableRow](0, WRAP_CONTENT, 1),
          widget[AppCompatSpinner](context)
            <~ sChoices("starts with", "contains")
            <~ lp[TableRow](0, WRAP_CONTENT, 1)
        ),
        layout[TableRow](
          widget[SearchView](context) <~ wire(searchField)
        )
      ) <~ vertical <~ padding(top = 38.dp, bottom = -4.dp)
    }

    searchField.foreach(_.setIconifiedByDefault(false))

    MenuItemCompat.setActionView(item, searchView)


    super.onCreateOptionsMenu(menu)
    /*
    getMenuInflater().inflate(R.menu.search_menu, menu)

    Ui.run {
      Ui(findViewById(R.id.title_or_artist_spinner).asInstanceOf[Spinner])<~ sChoices(Array("Artist", "Title"))
      this.find[Spinner](R.id.match_type_spinner) <~ sChoices(Array("starts with", "contains"))
    }

    true
    */
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = item.getItemId match {
    case R.id.action_search =>
      Ui.run { textView <~ text("Hey!") }
      true
    case _ => super.onOptionsItemSelected(item)
  }
}

