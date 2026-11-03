package com.example.andrawd

     /* andoid */
import android.os.Bundle
             /* activity */
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
              /*compose*/
                     /* runtime */
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateMapOf
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
    val curr_color: Color = Color.Black; /* expand this into some color selector */

    private val last_tool = mutableStateMapOf<ToolGroup, Tool>();
    private val drawing = Drawing();
    private val renderer = Renderer(drawing);

    fun set_tool(new_tool: Tool)
    {
        tool = new_tool;
        last_tool[new_tool.group] = tool;
    }

    /*
     * searches the last_tool map for last tool in the tool_group, if found set it,
     * otherwise set the first item in the tool_groups' tools as the current tool;
     */
    fun select_tool_group(tool_group: ToolGroup)
    {
        set_tool(last_tool[tool_group] ?: tool_group.tools.first());
    }

    @Composable
    fun render(c: Color)
    {
        renderer.fill_screen(c);
        if (tool.group == ToolGroup.DRAW) {
            drawing.record_strokes(curr_color);
        }
        renderer.draw_strokes();
    }
}

@Composable
fun andrawd()
{
    val app = remember { Andrawd() };
    app.render(Color.White);
    toolbar(app);
}
