package com.judahben149.paging3trial

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.hilt.android.scopes.ActivityScoped
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class Repository @Inject constructor(private val service: MovieService) {

    suspend fun fetchDiscoverMovies(pageNumber: Int): Response<DiscoverMoviesResponse> {
        return service.fetchDiscoverMoviesList(pageNumber)
    }

}

class MoviesPagingDataSource(private val repository: Repository): PagingSource<Int, DiscoverMoviesResponse.DiscoverMoviesResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMoviesResponse.DiscoverMoviesResults> {

        return try {
            val currentPage = params.key ?: 1
            val response = repository.fetchDiscoverMovies(currentPage)
            val data = response.body()!!.results

            val responseData = mutableListOf<DiscoverMoviesResponse.DiscoverMoviesResults>()
            responseData.addAll(data)

            LoadResult.Page (
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
                    )

        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }  catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DiscoverMoviesResponse.DiscoverMoviesResults>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}