package com.example.andrawd

     /* andoid */
import android.os.Bundle
             /* activity */
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
             /* compose */
                     /* foundation */
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.offset
                     /* material */
import androidx.compose.material3.Button
import androidx.compose.material3.Text
                     /* ui */
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Popup
import androidx.compose.ui.unit.IntOffset
                     /* runtime */
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import com.example.andrawd.ui.theme.andrawd_theme

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            andrawd_theme {
                toolbar();
            }
        }
    }
}

enum class ui_state_e {
    CURSOR,
    DRAW,
    SHAPE,
    LINE,
    TEXT,
}

@Composable
fun toolbar_button(label: String,
                   state: ui_state_e,
                   handle_state_change: (ui_state_e) -> Unit) {

    var show_panel by remember { mutableStateOf(false) };

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Button(onClick = { show_panel = !show_panel; handle_state_change(state) }) {
                Text(label);
            }
            if (show_panel) {
                Popup(alignment = Alignment.Center,
                      offset = IntOffset(0, -150),
                      onDismissRequest = { show_panel = false }) {
                    panel(state);
                }
            }
        }
    }
}
@Preview
@Composable
fun toolbar()
{
    var curr_ui_state     by remember { mutableStateOf(ui_state_e.CURSOR) };

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Box(modifier = Modifier.fillMaxWidth(0.85F)
                               .offset(0.dp, -15.dp),
            contentAlignment = Alignment.BottomCenter) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                toolbar_button("CURSOR", ui_state_e.CURSOR)
                    {curr_ui_state = ui_state_e.CURSOR };
                toolbar_button("DRAW",   ui_state_e.DRAW)
                    {curr_ui_state = ui_state_e.DRAW };
                toolbar_button("SHAPE",  ui_state_e.SHAPE)
                    {curr_ui_state = ui_state_e.SHAPE };
                toolbar_button("LINE",   ui_state_e.LINE)
                    {curr_ui_state = ui_state_e.LINE };
                toolbar_button("TEXT",   ui_state_e.TEXT)
                    {curr_ui_state = ui_state_e.TEXT };
            }
        }
    }
}

@Composable
fun panel(state: ui_state_e) {
    Box(modifier = Modifier.fillMaxHeight(0.8F)
                           .offset(0.dp, -50.dp)
                           .background(Color.Red),
        contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxHeight(),
               verticalArrangement = Arrangement.SpaceBetween,
               horizontalAlignment = Alignment.CenterHorizontally) {
            when (state) {
                ui_state_e.CURSOR -> {}
                ui_state_e.DRAW -> {
                    Button(onClick = {}) {
                        Text("PENCIL");
                    }
                    Button(onClick = {}) {
                        Text("RUBBER");
                    }
                    Button(onClick = {}) {
                        Text("MARKER");
                    }
                    Button(onClick = {}) {
                        Text("COLOR");
                    }
                }

                ui_state_e.SHAPE -> {
                    Button(onClick = {}) {
                        Text("SQUARE");
                    }
                    Button(onClick = {}) {
                        Text("CIRCLE");
                    }
                    Button(onClick = {}) {
                        Text("TRIANGLE");
                    }
                    Button(onClick = {}) {
                        Text("COLOR");
                    }
                }

                ui_state_e.LINE -> {
                    Button(onClick = {}) {
                        Text("LINE");
                    }
                    Button(onClick = {}) {
                        Text("JOIN");
                    }
                    Button(onClick = {}) {
                        Text("BEZIER");
                    }
                    Button(onClick = {}) {
                        Text("COLOR");
                    }
                }

                ui_state_e.TEXT -> {
                    Button(onClick = {}) {
                        Text("TEXTBOX");
                    }
                    Button(onClick = {}) {
                        Text("HEADER");
                    }
                    Button(onClick = {}) {
                        Text("PARAGRAPH");
                    }
                    Button(onClick = {}) {
                        Text("COLOR");
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun panel_pv()
{
    panel(ui_state_e.DRAW);
}

/*
data class Pixel(
    val x: Float,
    val y: Float,
    val col: Color,
)

@Composable
fun draw_pixel(pix: Pixel)
{
    Canvas(modifier=Modifier.fillMaxWidth()) {
        drawRect(color=pix.col,
                 topLeft=Offset(pix.x, pix.y),
                 size=Size(1F, 1F));
    }
}

val cols = listOf(Color.Red, Color.Blue, Color.Green);
@Preview
@Composable
fun draw_pixel_lines()
{
    val pix_y = 300F;
    val pix_x_offset = 300F;

    for (j in 1..50) {
        for (i in 1..50) {
            draw_pixel(Pixel(pix_x_offset + i + j, pix_y + j, cols[i % cols.size]));
        }
    }
}
 */