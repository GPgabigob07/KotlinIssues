package com.nta.githubkotlinissueapp.api.service

import com.nta.githubkotlinissueapp.api.models.response.IssueModel
import io.reactivex.Observable
import retrofit2.http.GET

interface IssueAPI {
    @GET("issues")
    fun getIssues(): Observable<List<IssueModel>>
}