package com.example.andrawd

     /* andoid */
import android.os.Bundle
             /* activity */
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
/*compose*/
                     /* runtime */
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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

class Andrawd()
{
    var tool by mutableStateOf<Tool>(CursorTool)
        private set;
    private val last_tool = mutableStateMapOf<ToolGroup, Tool>();
    private val canvas = Drawing();

    fun set_tool(new_tool: Tool)
    {
        tool = new_tool;
        last_tool[new_tool.group] = tool;
    }

    /*
     * searches the last_tool map for last tool for the tool_group, if found set it,
     * otherwise set the first item in the tool_groups' tools as the current tool;
     */
    fun select_tool_group(tool_group: ToolGroup)
    {
        set_tool(last_tool[tool_group] ?: tool_group.tools.first());
    }

    @Composable
    fun fill_screen(c: Color)
    {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(color = c,
                     size = size);
        }
    }
}

@Composable
fun andrawd()
{
    val app = remember { Andrawd() };
    app.fill_screen(Color.Red);
    toolbar(app);
}
