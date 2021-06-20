package com.nta.githubkotlinissueapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nta.githubkotlinissueapp.api.RxHandler
import com.nta.githubkotlinissueapp.api.RxHandler.RxResult.Success
import com.nta.githubkotlinissueapp.api.models.response.IssueModel
import com.nta.githubkotlinissueapp.api.repo.IssueRepo


class IssuesViewModel(
    private val repo: IssueRepo
) : ViewModel() {

    private val _currentInDetails = MutableLiveData<IssueModel>();

    val currentInDetails : LiveData<IssueModel>
    get() = _currentInDetails

    var inDetails: IssueModel?
    set(v) = _currentInDetails.postValue(v)
    get() = _currentInDetails.value;

    private val _issueList = MutableLiveData<RxHandler.RxResult<List<IssueModel>>>()
    val issueList: MutableLiveData<RxHandler.RxResult<List<IssueModel>>>
        get() = _issueList

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun refreshListIssues() {
        RxHandler.emitOn(_issueList, repo::getIssues)
    }

    fun firstCatch() {
        if (_issueList.value != null && _issueList.value is Success && !(_issueList.value as Success<List<IssueModel>>).data.isNullOrEmpty()) return
        else refreshListIssues()
    }
}