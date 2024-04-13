package me.lab.happyprimo.di

import me.lab.happyprimo.ui.home.HomeViewModel
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import me.lab.happyprimo.ui.detail.DetailsViewModel
import org.koin.dsl.module

val appModule = module {

    single { (context: Context, lifecycleScope: LifecycleCoroutineScope) ->
        DetailsViewModel(context, lifecycleScope)
    }

    single { (context: Context, lifecycleScope: LifecycleCoroutineScope) ->
        HomeViewModel(context, lifecycleScope)
    }
}

