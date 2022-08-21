package com.example.wifood.presentation

import androidx.compose.animation.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.presentation.util.Route
import com.example.wifood.presentation.util.createParcelableNavType
import com.example.wifood.presentation.view.*
import com.example.wifood.presentation.view.login.*
import com.example.wifood.presentation.view.login.join.JoininView
import com.example.wifood.presentation.view.main.MainView
import com.example.wifood.presentation.view.map.MapView
import com.example.wifood.presentation.view.mypage.AppInfoView
import com.example.wifood.presentation.view.placeList.PlaceInfoView
import com.example.wifood.presentation.view.placeList.PlaceInfoWriteView
import com.example.wifood.presentation.view.placeList.group.GroupDescInputView
import com.example.wifood.presentation.view.placeList.group.GroupEditView
import com.example.wifood.presentation.view.placeList.group.GroupNameInputView
import com.example.wifood.presentation.view.search.SearchPlaceComposeView
import com.example.wifood.presentation.view.splash.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalSerializationApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(
            route = Route.Main.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            MainView(navController)
        }
        composable(route = Route.Login.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            LoginView(navController)
        }
        composable(
            route = Route.Map.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            MapView(navController)
        }
        composable(
            route = Route.MyPage.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            MyPageComposeView(navController)
        }
        composable(
            route = "${Route.PlaceInfo.route}/{place}/{group}",
            arguments = listOf(
                navArgument("place") { type = createParcelableNavType<Place>() },
                navArgument("group") { type = createParcelableNavType<Group>() },
            )
        ) {
            PlaceInfoView(navController)
        }
        composable(
            route = Route.Splash.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            SplashView(navController = (navController))
        }
        composable(
            route = Route.Onboarding.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            OnboardingView(navController = (navController))
        }
        composable(
            route = Route.MobileAuthentication.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            MobileAuthenticationView(navController = (navController))
        }
        composable(
            route = "${Route.Joinin.route}?email={email}",
            arguments = listOf(
                navArgument("email") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
            )
        ) {
            JoininView(navController, it)
        }
        composable(
            route = "${Route.EditPlace.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            })
        ) {
            PlaceInfoWriteView(navController)
        }
        composable(
            route = Route.Search.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            composable(Route.Search.route) {
                SearchPlaceComposeView(navController)
            }
        }
        composable(
            route = Route.EditProfile.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            EditProfileComposeView(navController)
        }
        composable(
            route = Route.FindPwd.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            FindPwdView(navController)
        }
        composable(
            route = Route.EditMyInfo.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            EditMyInfoComposeView(navController)
        }
        composable(
            route = Route.AppInfo.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            AppInfoView(navController)
        }
        composable(
            route = Route.SignUp1.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            SignUpView1(navController)
        }
        composable(
            route = Route.SignUp2.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            SignUpView2(navController)
        }
        composable(
            route = Route.SignUp3.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            SignUpView3(navController)
        }
        composable(
            route = Route.SignUp4.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            SignUpView4(navController)
        }
        composable(
            route = Route.SignUp5.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            SignUpView5(navController)
        }
        composable(
            route = "${Route.GroupNameInput.route}/{group}",
            arguments = listOf(navArgument("group") { type = createParcelableNavType<Group>() })
        ) {
            GroupNameInputView(navController)
        }
        composable(
            route = "${Route.GroupDescInput.route}/{group}",
            arguments = listOf(navArgument("group") { type = createParcelableNavType<Group>() })
        ) {
            GroupDescInputView(navController)
        }
        composable(
            route = "${Route.GroupEdit.route}/{group}",
            arguments = listOf(navArgument("group") { type = createParcelableNavType<Group>() })
        ) {
            GroupEditView(navController)
        }
        composable(Route.FindPwd.route) {
            FindPwdView(navController)
        }
        composable(Route.EditMyInfo.route) {
            EditMyInfoComposeView(navController)
        }
        composable(Route.AppInfo.route) {
            AppInfoView(navController)
        }
    }
}