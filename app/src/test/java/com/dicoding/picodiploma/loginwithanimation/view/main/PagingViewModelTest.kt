package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.picodiploma.loginwithanimation.MainDispatcherRule
import com.dicoding.picodiploma.loginwithanimation.data.pref.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.pref.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PagingViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var storiesViewModel: PagingViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(storyRepository.getListStories()).thenReturn(
            MutableLiveData(PagingData.from(listOf()))
        )
        storiesViewModel = PagingViewModel(storyRepository)
    }

    @Test
    fun `The test should confirm that the story data is not null and is returned correctly`() = runTest {
        val dummyStory = generateDummyStories()
        val data: PagingData<ListStoryItem> = StoriesPagingSource.snapshot(dummyStory.listStory)
        val expectedQuote = MutableLiveData<PagingData<ListStoryItem>>()
        expectedQuote.value = data
        `when`(storyRepository.getListStories()).thenReturn(expectedQuote)

        val storiesViewModel = PagingViewModel(storyRepository)
        val actualData: PagingData<ListStoryItem> = storiesViewModel.getListStory.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = AdapterStory.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualData)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory.listStory.size, differ.snapshot().size)
        assertEquals(dummyStory.listStory[0], differ.snapshot()[0])
    }

    @Test
    fun `The test should verify that when there are no stories, no data is returned`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedQuote = MutableLiveData<PagingData<ListStoryItem>>()
        expectedQuote.value = data
        Mockito.`when`(storyRepository.getListStories()).thenReturn(expectedQuote)
        val storiesViewModel = PagingViewModel(storyRepository)
        val actualQuote: PagingData<ListStoryItem> = storiesViewModel.getListStory.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = AdapterStory.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)
        assertEquals(0, differ.snapshot().size)
    }
}

class StoriesPagingSource : PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

fun generateDummyStories(): StoryResponse {
    return StoryResponse(
        error = false,
        message = "success",
        listStory = arrayListOf(
            ListStoryItem(
                id = "id",
                name = "name",
                description = "description",
                photoUrl = "photoUrl",
                createdAt = "createdAt",
                lat = 0.01,
                lon = 0.01
            )
        )
    )
}