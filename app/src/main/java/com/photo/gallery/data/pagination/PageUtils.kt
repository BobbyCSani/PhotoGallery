package com.photo.gallery.data.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource

object PageUtils {
    const val FIRST_PAGE = 1
    private const val NETWORK_PAGE_SIZE = 20
    private const val PREFETCH_DISTANCE = 1

    fun getPrevPage(params: PagingSource.LoadParams<Int>): Int? {
        val currentPage = params.key ?: FIRST_PAGE
        return if (currentPage == FIRST_PAGE) null
        else currentPage.minus(1)
    }

    fun getNextPage(params: PagingSource.LoadParams<Int>, hasNext: Boolean): Int? {
        val currentPage = params.key ?: FIRST_PAGE
        return if (hasNext) currentPage.plus(1) else null
    }

    private fun createPagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            initialLoadSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = PREFETCH_DISTANCE
        )
    }

    fun <T : Any> createPager(pagingSourceFactory: () -> PagingSource<Int, T>) = Pager(
        config = createPagingConfig(),
        pagingSourceFactory = pagingSourceFactory,
    )
}