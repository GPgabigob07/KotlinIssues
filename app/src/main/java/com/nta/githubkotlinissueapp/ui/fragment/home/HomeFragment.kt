package com.nta.githubkotlinissueapp.ui.fragment.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialElevationScale
import com.nta.githubkotlinissueapp.R
import com.nta.githubkotlinissueapp.api.RxHandler.RxResult
import com.nta.githubkotlinissueapp.api.RxHandler.RxResult.Error
import com.nta.githubkotlinissueapp.api.RxHandler.RxResult.Success
import com.nta.githubkotlinissueapp.api.models.response.IssueModel
import com.nta.githubkotlinissueapp.databinding.FragmentHomeBinding
import com.nta.githubkotlinissueapp.ui.adapter.IssuesAdapter
import com.nta.githubkotlinissueapp.ui.viewmodel.IssuesViewModel
import com.nta.githubkotlinissueapp.utils.dpToPX
import com.nta.githubkotlinissueapp.utils.toast
import org.koin.android.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private lateinit var binder: FragmentHomeBinding
    private val vm: IssuesViewModel by sharedViewModel()

    private val adapter = IssuesAdapter(::openDetails)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false).apply {
            duration = 500
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 500
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(inflater, container, false).also { binder = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        with(binder) {

            issues.adapter = adapter

            issues.itemAnimator?.apply {
                moveDuration = 500
            }

            issues.addItemDecoration(object : ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    if (parent.getChildAdapterPosition(view) < (parent.adapter?.itemCount ?: 0) - 1)
                        outRect.bottom += dpToPX(16f)
                }
            })

            vm.issueList.observe(viewLifecycleOwner, ::handleIssues)

            binder.refreshLayout.setOnRefreshListener(vm::refreshListIssues)

        }

        vm.firstCatch()
    }

    private fun handleIssues(rxResult: RxResult<List<IssueModel>>?) {
        binder.refreshLayout.isRefreshing = rxResult is RxResult.Loading
        when (rxResult) {
            is Success -> {
                adapter.submitList(rxResult.data)
            }
            is Error -> {
                rxResult.throwable.printStackTrace()
                toast(getString(R.string.default_error))
            }
        }
    }

    private fun openDetails(card: MaterialCardView, issueModel: IssueModel) {
        vm.inDetails = issueModel
        val extras = FragmentNavigatorExtras(card to getString(R.string.issue_detail_transition))
        findNavController().navigate(
            R.id.action_navigation_home_to_issueDetailsFragment,
            null,
            null,
            extras
        )
    }

}