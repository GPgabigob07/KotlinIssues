package com.nta.githubkotlinissueapp.ui.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nta.githubkotlinissueapp.api.RxHandler.RxResult.Loading
import com.nta.githubkotlinissueapp.api.RxHandler.RxResult.Success
import com.nta.githubkotlinissueapp.utils.requireOrThenAwait
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class IssuesViewModelTest : KoinTest {

    private val tag = this::class.simpleName

    private val viewModel: IssuesViewModel by inject()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setupMyTripViewModel() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun loadIssueList() {
        with(viewModel) {
            Log.d(tag, "Calling API")
            firstCatch()

            Log.d(tag, "Validate load")
            assert(issueList.requireOrThenAwait() is Loading)

            Log.d(tag, "Validate success")
            assert(issueList.requireOrThenAwait() is Success)
        }
    }

    @Test
    fun testDetailingAnItem() {
        with(viewModel) {

            firstCatch()
            assert(issueList.requireOrThenAwait() is Loading)

            assert(issueList.requireOrThenAwait() is Success)

            val list = (issueList.value!! as Success).data

            assert(list.isNullOrEmpty().not())

            val item = list.random()

            inDetails = item

            assert(inDetails != null)
        }
    }
}