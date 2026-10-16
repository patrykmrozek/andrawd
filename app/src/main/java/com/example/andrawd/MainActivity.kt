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
import androidx.compose.material3.ButtonDefaults
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
                andrawd();
            }
        }
    }
}

enum class ui_kind_e {
    CURSOR,
    DRAW,
    SHAPE,
    LINE,
    TEXT,
    COLOR,
}

/*
 * Tagged union sort of thing.
 * ui_kind_e is the top level enum, it describes a toolbar button.
 * ui_state_e is the active tool within, and .kind refers back to its kind
 * For example, draw_state_e has states {PENCIL, ERASER, MARKER}, and has kind ui_kind_e.DRAW
 */
sealed interface ui_state_e { val kind : ui_kind_e; }

data object cursor_state : ui_state_e {
    override val kind = ui_kind_e.CURSOR;
}

enum class draw_state_e : ui_state_e {
    PENCIL,
    ERASER,
    MARKER;
    override val kind = ui_kind_e.DRAW;
}

enum class shape_state_e : ui_state_e {
    SQUARE,
    CIRCLE,
    TRIANGLE;
    override val kind = ui_kind_e.SHAPE;
}

enum class line_state_e : ui_state_e {
    LINE,
    JOIN,
    BEZIER;
    override val kind = ui_kind_e.LINE;
}

enum class text_state_e : ui_state_e{
    TEXTBOX,
    HEADER,
    PARAGRAPH;
    override val kind = ui_kind_e.TEXT;
}

enum class color_state_e : ui_state_e {
    COLOR_1,
    COLOR_2,
    COLOR_3;
    override val kind = ui_kind_e.COLOR;
}

fun states_of_k(k: ui_kind_e): List<ui_state_e> = when (k) {
    ui_kind_e.CURSOR -> listOf(cursor_state)
    ui_kind_e.DRAW   -> draw_state_e.entries
    ui_kind_e.SHAPE  -> shape_state_e.entries
    ui_kind_e.LINE   -> line_state_e.entries
    ui_kind_e.TEXT   -> text_state_e.entries
    ui_kind_e.COLOR  -> color_state_e.entries
}

class Andrawd()
{
    var state by mutableStateOf<ui_state_e>(cursor_state);

    fun set_state(new_state: ui_state_e)
    {
        state = new_state;
    }
}

@Composable
fun andrawd()
{
    val app = remember { Andrawd() };
    toolbar(app.state, app::set_state);
}

@Composable
fun toolbar_button(k:                ui_kind_e,
                   curr_state:       ui_state_e,
                   selected:         Boolean,
                   show_panel:       Boolean,
                   on_click:         () -> Unit,
                   on_panel_click:   (ui_state_e) -> Unit,
                   on_panel_dismiss: () -> Unit)
{
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Button(onClick = on_click,
                   colors = if (selected) ButtonDefaults.buttonColors()
                            else ButtonDefaults.filledTonalButtonColors()) {
                /* TODO: change these to icons or something */
                Text(k.toString());
            }
            if (show_panel) {
                Popup(alignment = Alignment.Center,
                      offset = IntOffset(0, -150),
                      onDismissRequest = on_panel_dismiss) {
                    panel(states_of_k(k), curr_state, on_panel_click);
                }
            }
        }
    }
}

/* too much state in toolbar, need some higher level andrawd_run() function or somethign */
@Composable
fun toolbar(curr_state: ui_state_e, set_state: (ui_state_e) -> Unit)
{
    var open_kind  by remember { mutableStateOf<ui_kind_e?>(null) } ;

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Box(modifier = Modifier.fillMaxWidth(0.85F)
                               .offset(0.dp, -15.dp),
            contentAlignment = Alignment.BottomCenter) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                for (k in ui_kind_e.entries) {
                    toolbar_button(k = k,
                                   selected   = curr_state.kind == k,
                                   show_panel = open_kind == k,
                                   curr_state = curr_state,
                                   on_click   = {
                                       val has_panel = states_of_k(k).size > 1;
                                       if (curr_state.kind != k) {
                                           set_state(states_of_k(k).first());
                                       }
                                       open_kind = if (has_panel && open_kind != k) k else null;
                                   },
                                   on_panel_click   = {set_state(it); open_kind = null},
                                   on_panel_dismiss = {open_kind = null});
                }
            }
        }
    }
}

@Composable
fun panel_button(state: ui_state_e, selected: Boolean, on_click: (ui_state_e) -> Unit)
{
    Button(onClick = { on_click(state) },
           colors  = if (selected) ButtonDefaults.buttonColors()
                     else ButtonDefaults.filledTonalButtonColors()) {
        /* TODO: change tthese to icons also */
       Text(state.toString());
    }
}

@Composable
fun panel(states: List<ui_state_e>,
          curr_state: ui_state_e,
          on_click: (ui_state_e) -> Unit) {
    Box(modifier = Modifier.fillMaxHeight(0.8F)
                           .offset(0.dp, -50.dp),
        contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxHeight(),
               verticalArrangement = Arrangement.Bottom,
               horizontalAlignment = Alignment.CenterHorizontally) {
            for (state in states) {
                panel_button(state,
                             state == curr_state,
                             on_click);
            }
        }
    }
}