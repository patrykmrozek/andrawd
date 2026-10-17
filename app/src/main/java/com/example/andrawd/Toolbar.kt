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

@Composable
fun toolbar(ad: Andrawd)
{
    var open_tool_group by remember { mutableStateOf<tool_group_e?>(null) } ;

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        Box(modifier = Modifier.fillMaxWidth(0.85F)
                               .offset(0.dp, -15.dp),
            contentAlignment = Alignment.BottomCenter) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                for (tg in tool_group_e.entries) {
                    toolbar_button(tool_group = tg,
                                   selected   = (ad.tool.group == tg),
                                   show_panel = (open_tool_group == tg),
                                   curr_tool  = ad.tool,
                                   on_click   = {
                                       val has_panel = tg.tools.size > 1;
                                       if (ad.tool.group != tg) {
                                           ad.select_tool_group(tg);
                                       }
                                       open_tool_group = if (has_panel && open_tool_group != tg) tg
                                                         else null;
                                   },
                                   on_panel_click   = {ad.set_tool(it); open_tool_group = null},
                                   on_panel_dismiss = {open_tool_group = null});
                }
            }
        }
    }
}

@Composable
private fun toolbar_button(tool_group:       tool_group_e,
                           curr_tool:        tool_e,
                           selected:         Boolean,
                           show_panel:       Boolean,
                           on_click:         () -> Unit,
                           on_panel_click:   (tool_e) -> Unit,
                           on_panel_dismiss: () -> Unit)
{
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Button(onClick = on_click,
                   colors = if (selected) ButtonDefaults.buttonColors()
                            else ButtonDefaults.filledTonalButtonColors()) {
                /* TODO: change these to icons or something */
                Text(tool_group.toString());
            }
            if (show_panel) {
                Popup(alignment = Alignment.Center,
                      offset = IntOffset(0, -150),
                      onDismissRequest = on_panel_dismiss) {
                    panel(tool_group.tools, curr_tool, on_panel_click);
                }
            }
        }
    }
}

@Composable
private fun panel(tools:     List<tool_e>,
                  curr_tool: tool_e,
                  on_click:  (tool_e) -> Unit)
{
    Box(modifier = Modifier.fillMaxHeight(0.8F)
                           .offset(0.dp, -50.dp),
        contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxHeight(),
               verticalArrangement = Arrangement.Bottom,
               horizontalAlignment = Alignment.CenterHorizontally) {
            for (tool in tools) {
                panel_button(tool,
                             tool == curr_tool,
                             on_click);
            }
        }
    }
}

@Composable
private fun panel_button(tool:     tool_e,
                         selected: Boolean,
                         on_click: (tool_e) -> Unit)
{
    Button(onClick = { on_click(tool) },
           colors  = if (selected) ButtonDefaults.buttonColors()
                     else ButtonDefaults.filledTonalButtonColors()) {
        /* TODO: change tthese to icons also */
        Text(tool.toString());
    }
}
