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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
                ui();
            }
        }
    }
}

data class Vec2(
    val x: Float,
    val y: Float,
);

enum class state {
    CURSOR,
    DRAW,
    SHAPE,
    LINE,
    TEXT,
}

var curr_state by mutableStateOf(state.CURSOR);
@Preview
@Composable
fun ui()
{
    var can_display_panel by remember {mutableStateOf(false)};

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Box(modifier = Modifier.fillMaxWidth(fraction = 0.85F)
                               .offset(0.dp, -15.dp),
            contentAlignment = Alignment.BottomCenter) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Button(modifier = Modifier.weight(1F),
                       onClick = { curr_state = state.CURSOR; can_display_panel = false; }) {
                    Text("CURSOR");
                }
                Button(modifier = Modifier.weight(1F),
                       onClick = { curr_state = state.DRAW; can_display_panel = true; }) {
                    Text("TOOLS");
                }
                Button(modifier = Modifier.weight(1F),
                       onClick = { curr_state = state.SHAPE; can_display_panel = true; }) {
                    Text("SHAPES");
                }
                Button(modifier = Modifier.weight(1F),
                       onClick = { curr_state = state.LINE; can_display_panel = true; }) {
                    Text("LINES");
                }
                Button(modifier = Modifier.weight(1F),
                       onClick = { curr_state = state.TEXT; can_display_panel = true; }) {
                    Text("TEXT");
                }
            }
            if (can_display_panel) {
                panel();
            }
        }
    }
}

@Composable
fun panel() {
    Box(modifier = Modifier.fillMaxWidth()
                           .offset(0.dp, -50.dp)
                           .background(Color.Red),
        contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            when (curr_state) {
                state.CURSOR -> {}

                state.DRAW -> {
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

                state.SHAPE -> {
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

                state.LINE -> {
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

                state.TEXT -> {
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