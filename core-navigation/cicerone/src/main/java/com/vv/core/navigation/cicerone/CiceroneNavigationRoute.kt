package com.vv.core.navigation.cicerone

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.vv.core.navigation.api.NavigationRoute
import com.vv.core.navigation.api.NavigationTransition

interface CiceroneNavigationRoute : FragmentScreen, NavigationRoute {

    val transition: NavigationTransition?

    companion object {
        operator fun invoke(
            key: String? = null,
            clearContainer: Boolean = true,
            fragmentCreator: Creator<FragmentFactory, Fragment>
        ) = FragmentScreen(key, clearContainer, fragmentCreator)
    }
}