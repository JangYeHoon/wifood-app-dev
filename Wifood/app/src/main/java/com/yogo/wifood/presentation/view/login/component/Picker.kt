package com.yogo.wifood.presentation.view.login.component

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.ItemTouchHelper.UP
import com.yogo.wifood.ui.theme.mainFont
import com.yogo.wifood.view.ui.theme.Gray01Color
import com.yogo.wifood.view.ui.theme.MainColor
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun Picker(
    state: MutableState<Int>,
    modifier: Modifier = Modifier,
    range: IntRange? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    onStateChanged: (Int) -> Unit = {},
    additional: String
) {
    val coroutineScope = rememberCoroutineScope()
    val numbersColumnHeight = 36.dp
    val halvedNumbersColumnHeight = numbersColumnHeight / 2
    val halvedNumbersColumnHeightPx =
        with(LocalDensity.current) { halvedNumbersColumnHeight.toPx() }

    fun animatedStateValue(offset: Float): Int =
        state.value - (offset / halvedNumbersColumnHeightPx).toInt()

    val animatedOffset = remember { Animatable(0f) }.apply {
        if (range != null) {
            val offsetRange = remember(state.value, range) {
                val value = state.value
                val first = -(range.last - value) * halvedNumbersColumnHeightPx
                val last = -(range.first - value) * halvedNumbersColumnHeightPx
                first..last
            }
            updateBounds(offsetRange.start, offsetRange.endInclusive)
        }
    }
    val coercedAnimatedOffset = animatedOffset.value % halvedNumbersColumnHeightPx
    val animatedStateValue = animatedStateValue(animatedOffset.value)

    Column(
        modifier = modifier
            .wrapContentSize()
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { deltaY ->
                    coroutineScope.launch {
                        animatedOffset.snapTo(animatedOffset.value + deltaY)
                    }
                },
                onDragStopped = { velocity ->
                    coroutineScope.launch {
                        val endValue = animatedOffset.fling(
                            initialVelocity = velocity,
                            animationSpec = exponentialDecay(frictionMultiplier = 20f),
                            adjustTarget = { target ->
                                val coercedTarget = target % halvedNumbersColumnHeightPx
                                val coercedAnchors = listOf(
                                    -halvedNumbersColumnHeightPx,
                                    0f,
                                    halvedNumbersColumnHeightPx
                                )
                                val coercedPoint =
                                    coercedAnchors.minByOrNull { abs(it - coercedTarget) }!!
                                val base =
                                    halvedNumbersColumnHeightPx * (target / halvedNumbersColumnHeightPx).toInt()
                                coercedPoint + base
                            }
                        ).endState.value

                        state.value = animatedStateValue(endValue)
                        onStateChanged(state.value)
                        animatedOffset.snapTo(0f)
                    }
                }
            )
    ) {
        val spacing = 4.dp

        val arrowColor = MaterialTheme.colors.onSecondary.copy(alpha = ContentAlpha.disabled)

        /*
        Arrow(direction = DrawerArrowDrawable.ArrowDirection.UP, tint = arrowColor)
         */

        Spacer(modifier = Modifier.height(spacing))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset { IntOffset(x = 0, y = coercedAnimatedOffset.roundToInt()) }
        ) {
            val baseLabelModifier = Modifier.align(Alignment.Center)
            val labelFirstColor = Gray01Color
            val labelSecondColor = Color(0xFF9A99A2)
            val labelThirdColor = Color(0x809A99A2)

            ProvideTextStyle(textStyle) {
                Label(
                    text = customString((animatedStateValue - 2).toString(), additional),
                    modifier = baseLabelModifier
                        .offset(y = -halvedNumbersColumnHeight - halvedNumbersColumnHeight - 18.dp),
                    color = if (animatedStateValue != 1) labelThirdColor else Color.Transparent,
                    fontSize = 16
                )
                Label(
                    text = customString((animatedStateValue - 1).toString(), additional),
                    modifier = baseLabelModifier
                        .offset(y = -halvedNumbersColumnHeight - 12.dp),
                    color = if (animatedStateValue != 1) labelSecondColor else Color.Transparent,
                    fontSize = 18
                )
                Label(
                    text = customString(animatedStateValue.toString(), additional),
                    modifier = baseLabelModifier
                        .alpha(1 - abs(coercedAnimatedOffset) / halvedNumbersColumnHeightPx),
                    color = labelFirstColor,
                    fontSize = 20
                )
                if (range != null) {
                    Label(
                        text = customString((animatedStateValue + 1).toString(), additional),
                        modifier = baseLabelModifier
                            .offset(y = halvedNumbersColumnHeight + 12.dp),
                        color = if (animatedStateValue != range.last) labelSecondColor else Color.Transparent,
                        fontSize = 18
                    )
                }
                if (range != null) {
                    Label(
                        text = customString((animatedStateValue + 2).toString(), additional),
                        modifier = baseLabelModifier
                            .offset(y = halvedNumbersColumnHeight + halvedNumbersColumnHeight + 18.dp),
                        color = if (animatedStateValue != range.last) labelThirdColor else Color.Transparent,
                        fontSize = 16
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing))

        /*
        Arrow(direction = ArrowDirection.DOWN, tint = arrowColor)
         */
    }
}

@Composable
private fun Label(
    text: String,
    modifier: Modifier,
    color: Color,
    fontSize: Int
) {
    Text(
        text = text,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    // FIXME: Empty to disable text selection
                })
            }
            .drawBehind {
                drawLine(
                    color = Color.Companion.Transparent, //MainColor,
                    start = Offset(x = 0f, y = this.size.height),
                    end = Offset(x = this.size.width, y = this.size.height),
                    strokeWidth = 5F
                )
            }
            .width(100.dp),
        textAlign = TextAlign.Center,
        fontFamily = mainFont,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Normal,
        color = color
    )
}

private fun customString(text: String, additional: String): String {
    return buildString {
        append(text)
        append(additional)
    }
}

private suspend fun Animatable<Float, AnimationVector1D>.fling(
    initialVelocity: Float,
    animationSpec: DecayAnimationSpec<Float>,
    adjustTarget: ((Float) -> Float)?,
    block: (Animatable<Float, AnimationVector1D>.() -> Unit)? = null,
): AnimationResult<Float, AnimationVector1D> {
    val targetValue = animationSpec.calculateTargetValue(value, initialVelocity)
    val adjustedTarget = adjustTarget?.invoke(targetValue)

    return if (adjustedTarget != null) {
        animateTo(
            targetValue = adjustedTarget,
            initialVelocity = initialVelocity,
            block = block
        )
    } else {
        animateDecay(
            initialVelocity = initialVelocity,
            animationSpec = animationSpec,
            block = block,
        )
    }
}