package com.example.barchart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.barchart.ui.theme.BarChartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarChartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Chart(
                        data = mapOf(

                            Pair(0.5f,"A"),
                            Pair(0.5f,"B"),
                            Pair(0.3f,"C"),
                            Pair(0.2f,"D"),
                            Pair(0.1f,"E"),

                            ), max_value = 10
                    )
                }
                /*Column(
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
                    *//*dataList.forEachIndexed{index,value->
                        floatValue.add(index=index, element =value.toFloat()/dataList.max().toFloat())

                    }*//*
                    
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

                }*/
            }
        }
    }
}

