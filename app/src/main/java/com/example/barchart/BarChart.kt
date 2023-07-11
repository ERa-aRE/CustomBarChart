package com.example.barchart

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round

@Composable
fun BarChart(
    chartData: List<Float>,
    xAxisScaleData: List<String>,
    barData_: List<Int>,
    height: Dp,
    roundType: BarType,
    barWidth: Dp,
    barColor: Color,
    barArrangement: Arrangement.Horizontal
) {
    val barData by remember { mutableStateOf(barData_ + 0) }

    ///
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp
    val xAxisScaleHeight = 30.dp
    val yAxisScalePadding by remember {
        mutableStateOf(93f)
    }
    val yAxisTextWidth by remember {
        mutableStateOf(100.dp)
    }
    val barShape =
        when (roundType) {
            BarType.CIRCULAR_TYPE -> CircleShape
            BarType.TOP_CURVED -> RoundedCornerShape(topEnd = 3.dp, topStart = 3.dp)
        }
    val density = LocalDensity.current
    // y-axis scale text paint
    val textPaint = remember(density) {
        Paint().apply {
            color = Color.Black.hashCode()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 10.sp.toPx() }

        }
    }
    // horizontal dots
    val yCoordinates = mutableListOf<Float>()
    //dotted line effect
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    //height of vertical line over x-axis scale connection x-axis horizontal line
    val lineHeightXAxis = 10.dp
    // height of horizontal line over x-axis scale
    val horizontalLineHeight = 2.dp

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        //y-axis scale and horizontal dotted lines on chart indication y-axis scale
        Column(
            modifier = Modifier
                .padding(top = xAxisScaleHeight+10.dp, end = 3.dp)
                .height(height)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val yAxisScaleText = (barData.max()) / 10f
                (0..10).forEach { i ->
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            (barData.min() + yAxisScaleText * i).toString(),
                            20f,
                            size.height - yAxisScalePadding - i * size.height / 10f,
                            textPaint
                        )
                    }
                    yCoordinates.add(size.height - yAxisScalePadding - i * size.height / 10f)
                }
                //horizontal dotted lines on chart indication y-axis scale
                (1..10).forEach {
                    drawLine(
                        start = Offset(x = yAxisScalePadding , y = yCoordinates[it]-5.dp.toPx()),
                        end = Offset(x = size.width, y = yCoordinates[it]-5.dp.toPx()),
                        color = Color.Gray,
                        strokeWidth = 3f,
                        pathEffect = pathEffect

                    )

                }


            }


        }


        //Chart with Bar chart and x-axis scale
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .width(width - yAxisTextWidth)
                .height(height + xAxisScaleHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier.width(width - yAxisTextWidth),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = barArrangement
            ) {
                //Chart
                val chartData_ = chartData.map{
                    it/barData_.max().toFloat()
                }
                chartData_.forEachIndexed { index, value ->
                    var animationTriggered by remember {
                        mutableStateOf(false)
                    }
                    val chartBarHeight by animateFloatAsState(
                        targetValue = if (animationTriggered) value else 0f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            delayMillis = 500,
                        )
                    )
                    LaunchedEffect(key1 = true) {
                        animationTriggered = true
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Each Chart
                        Box(
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .clip(barShape)
                                .width(barWidth)
                                .height(height )
                                .background(Color.Transparent),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(barShape)
                                    .fillMaxWidth()
                                    .fillMaxHeight(chartBarHeight)
                                    .background(barColor)
                            )

                        }
                        //scale x-axis and bottom part of graph
                        Column(
                            modifier = Modifier.height(xAxisScaleHeight),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // small vertical line joining the horizontal x-axis line
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 2.dp,
                                            bottomEnd = 2.dp
                                        )
                                    )
                                    .width(horizontalLineHeight)
                                    .height(lineHeightXAxis)
                                    .background(color = Color.Gray)
                            )
                            //Scale x-axis
                            Text(
                                text =
                                xAxisScaleData[index]+" ${chartData[index]} ",
                                modifier = Modifier,
                                fontSize = 6.sp,
                                textAlign = TextAlign.Center,
                            )

                        }

                    }

                }

            }


            //horizontal line on x-axis below the chart
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = xAxisScaleHeight )
                        .clip(
                            RoundedCornerShape(2.dp)
                        )
                        .fillMaxWidth()
                        .height(horizontalLineHeight)
                        .background(Color.Gray)
                )
            }


        }

    }


}