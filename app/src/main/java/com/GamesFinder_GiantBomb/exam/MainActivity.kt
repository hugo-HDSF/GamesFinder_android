package com.GamesFinder_GiantBomb.exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.GamesFinder_GiantBomb.exam.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search_game = findViewById<EditText>(R.id.search_game)
        val search_button = findViewById<Button>(R.id.search_button)
        val empty_button = findViewById<Button>(R.id.empty_button)

        val game_recycler_view = findViewById<RecyclerView>(R.id.game_recycler_view)
        gameAdapter = GameAdapter(ArrayList<Game>())
        game_recycler_view.layoutManager = LinearLayoutManager(this)
        game_recycler_view.adapter = gameAdapter

        search_button.setOnClickListener {
            val query = search_game.text.toString().trim()
            if (query.isNotEmpty()) {
                searchGames(query)
            }
        }

        empty_button.setOnClickListener {
            search_game.text.clear()
            gameAdapter.clearGames()
        }

        gameAdapter.onItemClickListener = object : GameAdapter.OnItemClickListener {
            override fun onItemClick(game: Game) {
                val intent = Intent(this@MainActivity, GameDetailActivity::class.java)
                intent.putExtra("game", game)
                startActivity(intent)
            }
        }

        gameAdapter.onItemLongClickListener = object : GameAdapter.OnItemLongClickListener {
            override fun onItemLongClick(game: Game): Boolean {
                gameAdapter.removeGame(game)
                Toast.makeText(this@MainActivity, "Jeu ${game.name} supprimé.", Toast.LENGTH_LONG).show()
                return true
            }
        }

    }

    private fun searchGames(query: String) {
        val apiClient = ApiClient.create()
        val apiKey = "a2a40921632bfbaf09daba0734f6f102da8a89b2"
        val filter = "name:$query"

        apiClient.searchGames(apiKey, filter).enqueue(object : Callback<ApiResult> {
            override fun onResponse(call: Call<ApiResult>, response: Response<ApiResult>) {
                if (response.isSuccessful) {
                    val games = response.body()?.results ?: emptyList()
                    gameAdapter.setGames(games as ArrayList<Game>)
                    gameAdapter.notifyDataSetChanged()
                } else {
                    // Gérer les erreurs
                }
            }

            override fun onFailure(call: Call<ApiResult>, t: Throwable) {
                // Gérer les erreurs
            }
        })
    }
}