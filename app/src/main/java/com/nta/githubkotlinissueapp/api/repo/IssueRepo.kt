package com.nta.githubkotlinissueapp.api.repo

import com.nta.githubkotlinissueapp.api.service.IssueAPI

class IssueRepo(private val api: IssueAPI) : IssueAPI by api