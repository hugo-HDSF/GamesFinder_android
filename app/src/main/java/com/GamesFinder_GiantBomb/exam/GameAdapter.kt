package com.GamesFinder_GiantBomb.exam

import android.annotation.SuppressLint
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.GamesFinder_GiantBomb.exam.R

class GameAdapter(private var games: ArrayList<Game>) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.game_list_item, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(getItem(position))
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.onItemLongClick(getItem(position)) ?: false
        }
    }

    override fun getItemCount() = games.size

    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(game: Game) {
            gameName.text = game.name
            gameReleaseDate.text = game.original_release_date ?: "Date de sortie inconnue"
            gameDescription.text = game.deck

            // Charger l'image de couverture du jeu
            Glide.with(itemView.context)
                .load(game.image?.medium_url)
                .placeholder(R.drawable.ic_launcher_loading_game)
                .error(R.drawable.ic_launcher_foreground)
                .into(gameCover)
        }

        private var game: Game? = null
        private val gameCover: ImageView = view.findViewById(R.id.game_cover)
        private val gameName: TextView = view.findViewById(R.id.game_name)
        private val gameReleaseDate: TextView = view.findViewById(R.id.game_release_date)
        private val gameDescription: TextView = view.findViewById(R.id.game_description)

    }
    fun getItem(p0: Int): Game {
        return games[p0]
    }

    fun getGames(): ArrayList<Game> {
        return games
    }

    fun setGames(games: ArrayList<Game>) {
        this.games = games
    }
    fun clearGames() {
        games.clear()
        notifyDataSetChanged()
    }

    fun removeGame(game: Game) {
        games.remove(game)
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun onItemClick(game: Game)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(game: Game): Boolean
    }

}

