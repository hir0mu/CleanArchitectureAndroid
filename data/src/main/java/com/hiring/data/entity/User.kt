package com.hiring.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("description")
    val description: String?,
    @SerialName("facebook_id")
    val facebookId: String?,
    @SerialName("followees_count")
    val followeesCount: Int?,
    @SerialName("followers_count")
    val followersCount: Int?,
    @SerialName("github_login_name")
    val githubLoginName: String?,
    @SerialName("id")
    val id: String,
    @SerialName("items_count")
    val itemsCount: Int,
    @SerialName("linkedin_id")
    val linkedinId: String?,
    @SerialName("location")
    val location: String?,
    @SerialName("name")
    val name: String,
    @SerialName("organization")
    val organization: String?,
    @SerialName("permanent_id")
    val permanentId: Int?,
    @SerialName("profile_image_url")
    val profileImageUrl: String?,
    @SerialName("team_only")
    val teamOnly: Boolean?,
    @SerialName("twitter_screen_name")
    val twitterScreenName: String?,
    @SerialName("website_url")
    val websiteUrl: String?
)
