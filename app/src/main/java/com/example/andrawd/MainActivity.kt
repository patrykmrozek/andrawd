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
    COLOR,
}

enum class draw_state_e {
    PENCIL,
    ERASER,
    MARKER,
}

enum class shape_state_e {
    SQUARE,
    CIRCLE,
    TRIANGLE,
}

enum class line_state_e {
    LINE,
    JOIN,
    BEZIER,
}

enum class text_state_e {
    TEXTBOX,
    HEADER,
    PARAGRAPH,
}

@Composable
fun toolbar_button(state: ui_state_e,
                   handle_state: (ui_state_e) -> Unit) {

    var show_panel by remember { mutableStateOf(false) };

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Button(onClick = { show_panel = !show_panel; handle_state(state) }) {
                Text(state.toString());
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
    var curr_ui_state by remember { mutableStateOf(ui_state_e.CURSOR) };

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Box(modifier = Modifier.fillMaxWidth(0.85F)
                               .offset(0.dp, -15.dp),
            contentAlignment = Alignment.BottomCenter) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                for (state in ui_state_e.entries) {
                    toolbar_button(state) { curr_ui_state = state};
                }
            }
        }
    }
}

@Composable
fun <state_t> panel_button(state: state_t, handle_state: (state_t) -> Unit)
{
    Button(onClick = {handle_state(state)}) {
       Text(state.toString());
    }
}

@Composable
fun panel(state: ui_state_e) {
    Box(modifier = Modifier.fillMaxHeight(0.8F)
                           .offset(0.dp, -50.dp),
        contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxHeight(),
               verticalArrangement = Arrangement.Bottom,
               horizontalAlignment = Alignment.CenterHorizontally) {
            when (state) {
                ui_state_e.CURSOR -> {}
                ui_state_e.DRAW -> {
                    for (draw_state in draw_state_e.entries) {
                        panel_button(draw_state) {};
                    }
                }
                ui_state_e.SHAPE -> {
                    for (shape_state in shape_state_e.entries) {
                        panel_button(shape_state) {};
                    }
                }
                ui_state_e.LINE -> {
                    for (line_state in line_state_e.entries) {
                        panel_button(line_state) {};
                    }
                }
                ui_state_e.TEXT -> {
                    for (text_state in text_state_e.entries) {
                        panel_button(text_state) {};
                    }
                }
                ui_state_e.COLOR -> {}
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
