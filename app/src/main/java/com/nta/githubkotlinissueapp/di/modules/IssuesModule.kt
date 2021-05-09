package com.nta.githubkotlinissueapp.di.modules

import com.nta.githubkotlinissueapp.api.client.Clients.client
import com.nta.githubkotlinissueapp.api.client.Clients.provideRetrofit
import com.nta.githubkotlinissueapp.api.client.Clients.provideRetrofitAPI
import com.nta.githubkotlinissueapp.api.repo.IssueRepo
import com.nta.githubkotlinissueapp.api.service.IssueAPI
import com.nta.githubkotlinissueapp.ui.viewmodel.IssuesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object IssuesModule {
    val moduleInfo = module {
        //Provides OkHttp client
        factory { client() }

        //Provides Retrofit instance
        single { provideRetrofit(get()) }

        //Provides IssueApi
        single { provideRetrofitAPI(get(), IssueAPI::class.java) }

        //Provides IssueRepo
        single { IssueRepo(get()) }

        viewModel { IssuesViewModel(get()) }
    }
}