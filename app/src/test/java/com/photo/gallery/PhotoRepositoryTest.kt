package com.photo.gallery

import androidx.paging.PagingSource
import androidx.paging.map
import com.photo.gallery.data.model.SearchPhotoResponseModel
import com.photo.gallery.data.pagination.BasePagingSource
import com.photo.gallery.data.pagination.PagingDataModel
import com.photo.gallery.data.repository.PhotoRepoImpl
import com.photo.gallery.data.repository.PhotoRepository
import com.photo.gallery.network.ApiService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class PhotoRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val apiService = mockk<ApiService>()
    private lateinit var repository: PhotoRepository

    @Before
    fun setup() {
        hiltRule.inject()
        repository = PhotoRepoImpl(apiService)
    }

    @Test
    fun getRandomPhoto() {
        coEvery {
            apiService.getPhotos(1)
        } coAnswers {
            Response.success(listOf(generatePhotoResponseModel()))
        }
        runBlocking {
            val response = repository.getRandomPhoto()
            response.first().map { photoUIModel ->
                assertEquals(photoUIModel.id, generatePhotoResponseModel().id)
            }
        }
    }

    @Test
    fun searchPhoto() {
        coEvery {
            apiService.searchPhotos(page = 1, query = "")
        } coAnswers {
            Response.success(SearchPhotoResponseModel(results = listOf(generatePhotoResponseModel())))
        }
        runBlocking {
            val response = repository.searchPhoto("")
            response.first().map { photoUIModel ->
                assertEquals(photoUIModel.id, generatePhotoResponseModel().id)
            }
        }
    }

    @Test
    fun testPagingData() = runBlocking {
        val expectedResult = listOf(generatePhotoResponseModel())

        val pagingSource = BasePagingSource {
            PagingDataModel(
                responseCode = 1,
                data = expectedResult,
                hasNext = false,
                isSuccess = true
            )
        }

        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 5,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = listOf(generatePhotoResponseModel()),
            prevKey = null,
            nextKey = null,
        )
        assertEquals(expected, actual)
    }
}