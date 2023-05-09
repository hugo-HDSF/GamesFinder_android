package com.GamesFinder_GiantBomb.exam
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Game(
    val aliases: String?,
    val api_detail_url: String?,
    val date_added: String?,
    val date_last_updated: String?,
    val deck: String?,
    val description: String?,
    val expected_release_day: Int?,
    val expected_release_month: Int?,
    val expected_release_quarter: Int?,
    val expected_release_year: Int?,
    val guid: String?,
    val id: Int,
    val image: Image?,
    val image_tags:  List<ImageTag>?,
    val name: String?,
    val number_of_user_reviews: Int?,
    val original_game_rating: List<GameRating>?,
    val original_release_date: String?,
    val site_detail_url: String?
) : Parcelable

@Parcelize
data class Image(
    val icon_url: String?,
    val medium_url: String?,
    val screen_url: String?,
    val screen_large_url: String?,
    val small_url: String?,
    val super_url: String?,
    val thumb_url: String?,
    val tiny_url: String?,
    val original_url: String?,
    val image_tags: String?
) : Parcelable

@Parcelize
data class ImageTag(
    val api_detail_url: String?,
    val name: String?,
    val total: Int?
) : Parcelable

@Parcelize
data class GameRating(
    val api_detail_url: String?,
    val id: Int,
    val name: String?
) : Parcelable

data class Platform(
    val api_detail_url: String?,
    val id: Int,
    val name: String?,
    val site_detail_url: String?,
    val abbreviation: String?
)
