package com.vv.core.navigation.api

interface NavigationRoute

interface InterruptibleNavigationRoute:NavigationRoute {
    suspend fun checkNavigationInterruptAllowed(): Boolean
}

interface IAppNavigator {
    fun navigateTo(route: NavigationRoute)
    suspend fun allowRouteInterruption(): Boolean
}

interface IBaseNavigator {
    fun navigateBack(result: NavigationResult<Any>? = null)
}

sealed class NavigationResult<out T> {
    object Cancel : NavigationResult<Nothing>()
    data class Data<R>(val data: R) : NavigationResult<R>()
}

open class NavigationTransition(
    val enterAnimRes: Int,
    val exitAnimRes: Int,
    val popEnterAnimRes: Int,
    val popExitAnimRes: Int,
) {

    object EMPTY : NavigationTransition(0, 0, 0, 0)

}