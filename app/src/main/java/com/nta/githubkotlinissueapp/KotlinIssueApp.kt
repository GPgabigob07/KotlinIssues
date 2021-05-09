package com.nta.githubkotlinissueapp

import android.app.Application
import com.nta.githubkotlinissueapp.di.modules.IssuesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KotlinIssueApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@KotlinIssueApp)

            modules(IssuesModule.moduleInfo)
        }
    }
}