package com.example.barchart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.barchart.ui.theme.BarChartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarChartTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val dataList = mutableListOf(0,10)
                    val floatValue = mutableListOf<Float>(0.5f,1f,1.5f,2f,4.4f,7.5f,8f,9f,10f,3f)
                    val stringSituations=mutableListOf("hazmat drill",
                        "easy drill",
                        "great Drill",
                        "great Drill",
                        "great Drill",
                        "great Drill",
                        "great Drill",
                        "great Drill",
                        "great Drill",
                        "other drill")
                    /*dataList.forEachIndexed{index,value->
                        floatValue.add(index=index, element =value.toFloat()/dataList.max().toFloat())

                    }*/
                    
                    BarChart(
                        chartData = floatValue,
                        xAxisScaleData = stringSituations,
                        barData_ = dataList,
                        height =300.dp ,
                        roundType =BarType.TOP_CURVED ,
                        barWidth =20.dp ,
                        barColor = Color.Blue,
                        barArrangement = Arrangement.SpaceEvenly
                    )

                }
            }
        }
    }
}

