package com.app.berlinclock

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.app.berlinclock.ui.theme.BerlinClockTheme
import com.app.berlinclock.utils.LampColor
import com.app.berlinclock.viewmodel.BerlinClockViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: BerlinClockViewModel by viewModel()
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateBerlinClockInitialState()
        viewModel.startBerlinClock()
        setContent {
            BerlinClockTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background) {
                    BerlinClockUI(viewModel)
                }
            }
        }
    }
}
@Composable
fun BerlinClockUI(viewModel: BerlinClockViewModel) {
    val currentTime = viewModel.berlinCurrentTime.observeAsState()
    val berlinClockTimeState by viewModel.berlinClockTime.collectAsState()
    var minutesOnBottomLamps = berlinClockTimeState.minutesOnLamps?.bottomColors
    var minutesOnTopLamps: List<LampColor> = berlinClockTimeState.minutesOnLamps?.topColors!!
    var hoursOnLampsTop: List<LampColor> = berlinClockTimeState.hoursOnLamps?.topColors!!
    var hoursOnLampBottom: List<LampColor> = berlinClockTimeState.hoursOnLamps?.bottomColors!!

    ConstraintLayout(
            modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
    ) {

        val (button_second,button_hors_top, button_hors_bottom, button_minutes_top, button_minutes_bottom, text_current_time, button_change) = createRefs()

        Row(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button_second) {
                    top.linkTo(parent.top,
                               margin = 40.dp)
                }, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {},
                   shape = RoundedCornerShape(80.dp),
                   modifier = Modifier.border( 2.dp, color = Color.Gray, shape = RoundedCornerShape(80.dp))
                           .width(80.dp)
                           .height(80.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = if (berlinClockTimeState.secondsOnLamp != LampColor.OFF) Color.Yellow else Color.White)) {

            }
        }

        LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button_hors_top) {
                    top.linkTo(button_second.bottom,
                               margin = 20.dp)
                }, horizontalAlignment = Alignment.CenterHorizontally) {
            items(1) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.SpaceBetween) {
                    items(4) {
                        Button(onClick = {},

                               shape = RoundedCornerShape(0.dp),
                               modifier = Modifier.border( 2.dp, color = Color.Gray)
                                       .fillMaxWidth()
                                       .height(25.dp), colors = ButtonDefaults.buttonColors(
                                containerColor = if (hoursOnLampsTop.get(it) != LampColor.OFF) Color.Red else Color.White)) {

                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button_hors_bottom) {
                    top.linkTo(button_hors_top.bottom,
                               margin = 20.dp)
                }, horizontalAlignment = Alignment.CenterHorizontally) {
            items(1) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.SpaceBetween) {
                    items(4) {
                        Button(onClick = {},

                               shape = RoundedCornerShape(0.dp),
                               modifier = Modifier.border( 2.dp, color = Color.Gray)
                                       .fillMaxWidth()
                                       .height(25.dp), colors = ButtonDefaults.buttonColors(
                                containerColor = if (hoursOnLampBottom.get(it) != LampColor.OFF) Color.Red else Color.White)) {

                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button_minutes_top) {
                    top.linkTo(button_hors_bottom.bottom,
                               margin = 5.dp)
                }, horizontalAlignment = Alignment.CenterHorizontally) {
            items(1) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.SpaceBetween) {
                    items(11) {
                        Button(onClick = {},

                               shape = RoundedCornerShape(0.dp),
                               modifier = Modifier.border( 2.dp, color = Color.Gray)
                                       .width(16.dp)
                                       .height(40.dp), colors = ButtonDefaults.buttonColors(
                                containerColor = if(minutesOnTopLamps.get(it) != LampColor.OFF) { if(it == 2 || it == 5 || it == 8) Color.Red else Color.Yellow} else Color.White)) {

                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button_minutes_bottom) {
                    top.linkTo(button_minutes_top.bottom,
                               margin = 5.dp)
                }, horizontalAlignment = Alignment.CenterHorizontally) {
            items(1) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.SpaceBetween) {
                    items(4) {
                        Button(onClick = {},

                               shape = RoundedCornerShape(0.dp),
                               modifier = Modifier.border( 2.dp, color = Color.Gray)
                                       .fillMaxWidth()
                                       .height(25.dp), colors = ButtonDefaults.buttonColors(
                                containerColor =  if (minutesOnBottomLamps?.get(it)  != LampColor.OFF) Color.Yellow else Color.White)) {

                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Row(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(text_current_time) {
                    top.linkTo(button_minutes_bottom.bottom,
                               margin = 40.dp)
                }, horizontalArrangement = Arrangement.Center) {
            Text(text = currentTime.value.toString(), color = Color.Black)
        }

        Row(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button_change) {
                    top.linkTo(text_current_time.bottom,
                               margin = 40.dp)
                }, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {},
                   shape = RoundedCornerShape(10.dp),
                   modifier = Modifier
                           .width(250.dp)
                           .height(40.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green)) {
                Text(text = "Convert Time to Berlin Clock", color = Color.Black)
            }
        }
    }

}