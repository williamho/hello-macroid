package com.example.hello

import android.support.v7.widget.RecyclerView
import android.view._
import android.widget._
import macroid._
import macroid.contrib._
import macroid.FullDsl._

case class Song(
  id: String,
  title: String,
  artist: String
)

// Why is this so much boilerplate
class SongAdapter(songs: Vector[Song])(
  implicit context: ActivityContextWrapper
) extends RecyclerView.Adapter[SongAdapter.ViewHolder] {
  override def onCreateViewHolder(
    parentViewGroup: ViewGroup,
    viewType: Int
  ): SongAdapter.ViewHolder = {
    SongAdapter.viewHolder
  }

  override def getItemCount(): Int = songs.size

  override def onBindViewHolder(
    holder: SongAdapter.ViewHolder,
    position: Int
  ): Unit = {
    songs.lift(position).map(holder.update)
  }

}

object SongAdapter {
  def viewHolder(
    implicit context: ActivityContextWrapper
  ): SongAdapter.ViewHolder = {
    new SongAdapter.ViewHolder(new SongAdapter.ViewUpdater)
  }

  class ViewHolder(viewUpdater: SongAdapter.ViewUpdater)
    extends RecyclerView.ViewHolder(viewUpdater.content) {
    def update(song: Song): Unit = viewUpdater.update(song)
  }

  class ViewUpdater(implicit context: ActivityContextWrapper) {
    private var id = slot[TextView]
    private var title = slot[TextView]
    private var artist = slot[TextView]

    val content = Ui.get {
      layout[LinearLayout](
        widget[TextView] <~ wire(id) <~ TextTweaks.large,
        layout[LinearLayout](
          widget[TextView] <~ wire(title) <~ TextTweaks.medium,
          widget[TextView] <~ wire(artist)
        ) <~ vertical
      ) <~ horizontal
    }

    def update(song: Song): Unit = Ui.run {
      (id <~ text(song.id)) ~
      (title <~ text(song.title)) ~
      (artist <~ text(song.artist))
    }
  }
}

