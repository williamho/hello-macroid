package com.example.hello

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view._
import android.widget.{ListView, LinearLayout, TextView, Button, EditText}
import com.fortysevendeg.macroid.extras.RecyclerViewTweaks._
import com.fortysevendeg.macroid.extras.ViewTweaks._
import macroid._
import macroid.contrib._
import macroid.FullDsl._

class ExampleFragment extends Fragment with Contexts[Fragment] {
  private def randString(): String =
    scala.util.Random.alphanumeric.take(10).mkString

  val songs = (10000 to 10100).map { i =>
    Song(i.toString, randString(), randString())
  }.toVector

  lazy val layoutManager = new LinearLayoutManager(fragmentContextWrapper.getOriginal)

  var songList = slot[RecyclerView]

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ): View = Ui.get {
    layout[LinearLayout](
      widget[RecyclerView]
        <~ LpTweaks.matchParent
        <~ rvNoFixedSize
        <~ rvLayoutManager(layoutManager)
        <~ wire(songList)
    )
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle): Unit = {
    super.onViewCreated(view, savedInstanceState)
    val page = Option(savedInstanceState).map(_.getInt("page")).getOrElse(0)

    Ui.run {
      songList <~ rvAdapter(new SongAdapter(songs))
    }
  }
}

