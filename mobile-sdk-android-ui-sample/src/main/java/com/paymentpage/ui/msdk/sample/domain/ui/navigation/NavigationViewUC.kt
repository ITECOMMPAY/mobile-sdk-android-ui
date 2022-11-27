package com.paymentpage.ui.msdk.sample.domain.ui.navigation

import android.util.Log
import com.paymentpage.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.paymentpage.ui.msdk.sample.domain.ui.base.MessageUI
import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewUseCasesStore
import com.paymentpage.ui.msdk.sample.domain.ui.sample.SampleViewIntents
import kotlinx.coroutines.delay
import java.util.*
import kotlin.math.max

class NavigationViewUC(
    startRoute: NavRoutes,
) : BaseViewUC<NavigationViewIntents, NavigationViewState>(NavigationViewState(startRoute)) {


    private var isCanExit = false
    private var mBackStack = LinkedList<NavRoutes>().also {
        it.add(NavRoutes.Exit)
    }

    init {
        mBackStack.addLast(startRoute)
    }

    override suspend fun reduce(viewIntent: NavigationViewIntents) {
        when (viewIntent) {
            is NavigationViewIntents.Navigate -> navigate(viewIntent)
            is NavigationViewIntents.Back -> back(viewIntent)
        }
    }

    private fun clearUseCases() {
        ViewUseCasesStore.deleteExcept(mBackStack.map { it.toString() } + "Navigation" + "Sample")
    }

    override suspend fun onUpdateState(newState: NavigationViewState) {
        clearUseCases()
        printBackStack()
    }

    suspend fun back(viewIntent: NavigationViewIntents.Back = NavigationViewIntents.Back()) {
        val popBackStack = viewIntent.to
        val inclusive = viewIntent.inclusive
        val backStack = mBackStack

        var count = popBackStack?.let {
            backStack.count { screen ->
                screen::class.simpleName == popBackStack
            }
        }

        when (count) {
            //Простая навигация на один шаг назад
            null -> {
                if (backStack[max(
                        backStack.size - 2,
                        0
                    )] != NavRoutes.Exit || isCanExit
                ) backStack.removeLastOrNull()
                else {
                    pushExternalIntent(
                        SampleViewIntents.ShowMessage(
                            MessageUI.Toast(
                                message = "Нажмите еще раз, чтобы выйти."
                            )
                        )
                    )
                    isCanExit = true
                    delay(1500)
                    isCanExit = false
                }
                updateState(
                    viewState.value.copy(
                        currentRoute = backStack.lastOrNull() ?: NavRoutes.Exit
                    )
                )
            }
            //Экран, до которого нужно отчистить бэкстак не найден
            0 -> throw Exception("все плохо, навигируйся нормально $viewIntent")
            //все ок
            else -> {
                //очистка
                while (count > 0) {
                    if (backStack.last::class.simpleName == popBackStack) count--
                    if ((count > 0 || inclusive) && backStack[max(
                            backStack.size - 2,
                            0
                        )] != NavRoutes.Exit
                    ) backStack.removeLastOrNull()
                }

                updateState(
                    viewState.value.copy(
                        currentRoute = backStack.lastOrNull() ?: NavRoutes.Exit
                    )
                )
            }
        }
    }

    private fun navigate(viewIntent: NavigationViewIntents.Navigate) {
        val route = viewIntent.to
        val popBackStack = viewIntent.back
        val backStack = mBackStack

        var count = popBackStack?.let {
            backStack.count { screen ->
                screen::class == popBackStack::class
            }
        }

        when (count) {
            null -> Unit
            //Экран, до которого нужно очистить бэкстэк не найден
            0 -> backStack.addLast(popBackStack)
            //все ок
            else -> {
                //очистка
                while (count > 0) {
                    if (backStack.last::class == popBackStack!!::class) count--
                    if (count > 0) backStack.removeLastOrNull()
                }
                //навигация
            }
        }
        if (popBackStack != route)
            backStack.addLast(route)
        updateState(viewState.value.copy(currentRoute = backStack.last))
    }

    private fun printBackStack() {
        Log.e("APP_TAG", "Graph start")
        mBackStack.forEach {
            val offet = "--"
            Log.e("APP_TAG", "$offet${it.name}")
        }
        Log.e("APP_TAG", "Graph end")
    }
}