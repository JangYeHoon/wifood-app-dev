package com.yogo.wifood.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.presentation.util.Route
import com.yogo.wifood.presentation.util.createParcelableNavType
import com.yogo.wifood.presentation.view.ModifyUserProfileView
import com.yogo.wifood.presentation.view.MySettingView
import com.yogo.wifood.presentation.view.login.*
import com.yogo.wifood.presentation.view.login.join.GetUserFavorView
import com.yogo.wifood.presentation.view.login.new_compose_views.*
import com.yogo.wifood.presentation.view.main.MainView
import com.yogo.wifood.presentation.view.map.MapView
import com.yogo.wifood.presentation.view.mypage.*
import com.yogo.wifood.presentation.view.mypage.contents.AppInfoView
import com.yogo.wifood.presentation.view.placeList.EditPlaceView
import com.yogo.wifood.presentation.view.placeList.PlaceInfoView
import com.yogo.wifood.presentation.view.placeList.group.GroupDescInputView
import com.yogo.wifood.presentation.view.placeList.group.GroupEditView
import com.yogo.wifood.presentation.view.placeList.group.GroupNameInputView
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInputMenuEvaluation
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInputNameAndVisited
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInputReviewAndImages
import com.yogo.wifood.presentation.view.placeList.placeinfowrite.PlaceInputStarAndEvaluation
import com.yogo.wifood.presentation.view.search.AddNewPlaceCompleteView
import com.yogo.wifood.presentation.view.search.MapSearchAddressView
import com.yogo.wifood.presentation.view.search.SearchPlaceView
import com.yogo.wifood.presentation.view.splash.SplashView
import com.yogo.wifood.presentation.view.splash.StartView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@RequiresApi(Build.VERSION_CODES.O)
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
            route = "${Route.Main.route}?placeLat={placeLat}&placeLng={placeLng}",
            arguments = listOf(
                navArgument("placeLat") {
                    defaultValue = 10000f
                    type = NavType.FloatType
                }, navArgument("placeLng") {
                    defaultValue = 10000f
                    type = NavType.FloatType
                }),
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            MainView(navController, it)
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
            MapView(navController, it)
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
            MySettingView(navController)
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
            StartView(navController = (navController))
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
            SplashView(navController = (navController))
        }
        composable(
            route = "${Route.EditPlace.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            })
        ) {
            EditPlaceView(navController)
        }
        composable(
            route = "${Route.PlaceInputNameAndVisited.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            })
        ) {
            PlaceInputNameAndVisited(navController)
        }
        composable(
            route = "${Route.PlaceInputReviewAndImages.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            })
        ) {
            PlaceInputReviewAndImages(navController)
        }
        composable(
            route = "${Route.PlaceInputStarAndEvaluation.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            })
        ) {
            PlaceInputStarAndEvaluation(navController)
        }
        composable(
            route = "${Route.PlaceInputMenuEvaluation.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            })
        ) {
            PlaceInputMenuEvaluation(navController)
        }
        composable(
            route = "${Route.Search.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            }),
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            SearchPlaceView(navController)
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
            ModifyUserProfileView(navController)
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
            ModifyMyInfoView(navController)
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
            route = Route.ServiceUsingAgreement.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            ServiceUsingDocumentView(navController)
        }
        composable(
            route = Route.GetPhoneNumber.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            GetUserPhoneNumberView(navController)
        }
        composable(
            route = Route.GetAuthNumber.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            GetAuthenticationNumberView(navController)
        }
        composable(
            route = Route.Agreement.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            GetUserAgreementView(navController)
        }
        composable(
            route = "${Route.FindLocation.route}/{viewModel}",
            arguments = listOf(
                navArgument("viewModel") {
                    nullable = true
                    defaultValue = ""
                    type = NavType.StringType
                }),
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            SearchUserAddressView(navController, it)
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
            GetUserAddressView(navController)
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
        composable(
            route = Route.SignUp4.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            GetUserBirthView(navController)
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
            GetUserGenderView(navController)
        }
        composable(
            route = Route.GetUserFavor.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            GetUserFavorView(navController)
        }
        composable(
            route = Route.Complete.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            //JoininCompleteView(navController)
            JoininCompleteView(navController)
        }
        composable(
            route = "${Route.MapSearchAddress.route}/{place}",
            arguments = listOf(navArgument("place") {
                type = createParcelableNavType<Place>()
            }),
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            MapSearchAddressView(navController)
        }
        composable(
            route = Route.AddNewPlaceComplete.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            AddNewPlaceCompleteView(navController)
        }
        composable(
            route = Route.ModifyPhoneNumber.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            ModifyUserPhoneNumberView(navController)
        }
        composable(
            route = Route.ModifyAddress.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            ModifyUserLocationView(navController)
        }
        composable(
            route = Route.ModifyFavor.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            ModifyTasteFavorView(navController)
        }

        composable(
            route = Route.DeveloperInfo.route,
            enterTransition = {
                fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
            },
            exitTransition = {
                fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })
            }
        ) {
            DeveloperInfoView(navController)
        }
    }
}