package com.example.andrawd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

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
private fun toolbar_button(k:                ui_kind_e,
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

@Composable
private fun panel(states: List<ui_state_e>,
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

@Composable
private fun panel_button(state: ui_state_e, selected: Boolean, on_click: (ui_state_e) -> Unit)
{
    Button(onClick = { on_click(state) },
        colors  = if (selected) ButtonDefaults.buttonColors()
        else ButtonDefaults.filledTonalButtonColors()) {
        /* TODO: change tthese to icons also */
        Text(state.toString());
    }
}
