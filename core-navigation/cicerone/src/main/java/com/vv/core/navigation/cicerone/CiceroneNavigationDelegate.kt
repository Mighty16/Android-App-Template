package com.vv.core.navigation.cicerone

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.vv.core.navigation.api.InterruptibleNavigationRoute
import com.vv.core.navigation.api.NavigationTransition

class CiceroneNavigationDelegate(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager? = null,
    lifecycle: Lifecycle
) {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    private val navigatorHolder get() = cicerone.getNavigatorHolder()

    private val navigator = CustomCiceroneAppNavigator(activity, fragmentManager, containerId)

    init {
        observeLifeCycle(lifecycle)
    }

    private fun observeLifeCycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                navigatorHolder.setNavigator(navigator)
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                navigatorHolder.removeNavigator()
            }
        })
    }

    suspend fun allowCurrentRouteInterruption(): Boolean {
        val navigationRoute = navigator.getInterruptibleNavigationRoute()
        return navigationRoute?.checkNavigationInterruptAllowed() ?: true
    }

    private class CustomCiceroneAppNavigator(
        activity: FragmentActivity,
        fragmentManager: FragmentManager? = null,
        containerId: Int
    ) :
        AppNavigator(
            activity = activity,
            containerId = containerId,
            fragmentManager = fragmentManager ?: activity.supportFragmentManager
        ) {

        fun getInterruptibleNavigationRoute(): InterruptibleNavigationRoute? {
            val fragment = fragmentManager.findFragmentById(containerId)
            return fragment as? InterruptibleNavigationRoute
        }

        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment
        ) {
            (screen as? CiceroneNavigationRoute)?.transition?.let { anim ->
                fragmentTransaction.setCustomAnimations(
                    anim.enterAnimRes,
                    anim.exitAnimRes,
                    anim.popEnterAnimRes,
                    anim.popExitAnimRes
                )
            }
        }
    }
}