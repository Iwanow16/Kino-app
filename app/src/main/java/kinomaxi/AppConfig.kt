package kinomaxi

object AppConfig {

    private const val BASE_URL = "https://api.themoviedb.org"
    const val API_BASE_URL = "$BASE_URL/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhODY3ZDkwNWY1OTNjMTU2MGRlMDkyOWQ0ZTExZTZlNyIsInN1YiI6IjY0YTI5ZWQyMTEzODZjMDEzOWFlMmQ1MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bzH9FNNjkSmPAx5QCfCF5NqUgEhwIz4OwsQzVfpUlxo"

    const val POSTER_PREVIEW_SIZE = "w342"
    const val BACKDROP_PREVIEW_SIZE = "w300"
}
