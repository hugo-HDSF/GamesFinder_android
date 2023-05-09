package com.GamesFinder_GiantBomb.exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.GamesFinder_GiantBomb.exam.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject
import kotlin.math.log


class GameDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        // Récupérer le jeu à partir de l'intent
        val game = intent.getParcelableExtra<Game>("game")

        // Trouver les vues et les remplir avec les données du jeu
        val gameImage = findViewById<ImageView>(R.id.game_image)
        val gameName = findViewById<TextView>(R.id.game_name)
        val gameReleaseDate = findViewById<TextView>(R.id.game_release_date)
        val gameDescription = findViewById<TextView>(R.id.game_description)
        val gameComments = findViewById<TextView>(R.id.game_comments)

        // Charger l'image avec Glide
        Glide.with(this)
            .load(game?.image?.medium_url)
            .into(gameImage)

        // Remplir les autres vues avec les données du jeu
        gameName.text = game?.name
        gameReleaseDate.text = game?.original_release_date ?: "Date de sortie inconnue"
        gameDescription.text = game?.description?.replace(Regex("<.*?>"), "") ?: "Description non disponible"


        val gameId = game?.id
        Log.e("GameDetailActivity", "Game ID: $gameId")
        if (gameId != null) {
            fetchGameComments(gameId)
        }
    }

    private fun fetchGameComments(gameId: Int) {
        val apiClient = ApiClient.create()
        val call = apiClient.getGameComments(
            apiKey = "90f011305c53c1ec3340214868ba42eaf9ce6fe8",
            format = "json",
            fieldList = "score,reviewer,deck,description",
            gameId = gameId
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        val jsonString = responseBody.string()
                        val jsonObject = JSONObject(jsonString)
                        val results = jsonObject.getJSONArray("results")
                        val stringBuilder = StringBuilder()

                        for (i in 0 until results.length()) {
                            val comment = results.getJSONObject(i)
                            val score = comment.getDouble("score")
                            val reviewer = comment.getString("reviewer")
                            val deck = comment.getString("deck")
                            val description = comment.getString("description")

                            stringBuilder.append("Reviewer: $reviewer\n")
                            stringBuilder.append("Score: $score\n")
                            stringBuilder.append("Deck: $deck\n")
                            val plainTextDescription = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                            stringBuilder.append("Description: $plainTextDescription\n\n")
                            stringBuilder.append("====================================\n\n")
                        }

                        val gameCommentsTextView = findViewById<TextView>(R.id.game_comments)
                        gameCommentsTextView.text = stringBuilder.toString()
                    }
                } else {
                    Log.e("GameDetailActivity", "Failed to fetch game comments: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("GameDetailActivity", "Failed to fetch game comments", t)
            }
        })
    }

}