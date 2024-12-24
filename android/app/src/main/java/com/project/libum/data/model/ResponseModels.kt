package com.project.libum.data.model
import com.google.gson.annotations.SerializedName
import com.project.libum.data.dto.Book
import java.net.URL

data class LoginResponse(
    val email: String,
    val id: Int,
    val login: String
)

data class BookListResponse(
    val id: Int,
    val name: String
)

data class BooksResponse(
    @SerializedName("books")
    val books: List<BookDto>,
    @SerializedName("total_count")
    val totalCount: Int
)

data class BookDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("mean_score")
    val meanScore: Double?,
    @SerializedName("price")
    val price: Double,
    @SerializedName("genres")
    val genres: List<GenreDto>
)

fun BookDto.toParcelableBook(): Book {
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

data class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
