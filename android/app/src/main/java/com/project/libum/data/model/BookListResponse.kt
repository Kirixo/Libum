package com.project.libum.data.model
import com.google.gson.annotations.SerializedName
import com.project.libum.data.dto.Book
import java.net.URL

data class BookResponse(
    @SerializedName("books")
    val responseBooks: List<ResponseBook>,
    @SerializedName("total_count")
    val totalCount: Int
)

data class ResponseBook(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("mean_score")
    val meanScore: Double,
    @SerializedName("page_count")
    val pageCount: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("language")
    val language: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("author")
    val author: String
)

fun ResponseBook.toParcelableBook(): Book {
    return Book(
        id = this.id,
        title = this.title,
        author = this.author,
        isFavorite = false,
        coverURL = URL(this.cover),
        percentRead = null,
        localPercentRead = null,
        lastReadPage = null
    )
}

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
