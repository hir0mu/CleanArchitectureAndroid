package com.hiring.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    @SerialName("description")
    val description: String? = null,
    @SerialName("facebook_id")
    val facebookId: String? = null,
    @SerialName("followees_count")
    val followeesCount: Int? = null,
    @SerialName("followers_count")
    val followersCount: Int? = null,
    @SerialName("github_login_name")
    val githubLoginName: String? = null,
    @SerialName("id")
    val id: String,
    @SerialName("items_count")
    val itemsCount: Int = 0,
    @SerialName("linkedin_id")
    val linkedinId: String? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("organization")
    val organization: String? = null,
    @SerialName("permanent_id")
    val permanentId: Int? = null,
    @SerialName("profile_image_url")
    val profileImageUrl: String? = null,
    @SerialName("team_only")
    val teamOnly: Boolean? = null,
    @SerialName("twitter_screen_name")
    val twitterScreenName: String? = null,
    @SerialName("website_url")
    val websiteUrl: String? = null
)
