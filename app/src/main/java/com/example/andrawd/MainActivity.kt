package com.example.andrawd

     /* andoid */
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.mutableFloatStateOf
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

    val DEFAULT_COLOR: Color = Color.Black;
    val DEFAULT_BG_COLOR: Color = Color.White;
    val DEFAULT_WIDTH: Float = 10f;

    var tool by mutableStateOf<Tool>(CursorTool); private set;
    var curr_color = mutableStateOf<Color>(DEFAULT_COLOR); /* expand this into some color selector */
    var curr_bg_color = mutableStateOf<Color>(DEFAULT_BG_COLOR);
    var curr_width = mutableFloatStateOf(DEFAULT_WIDTH); /* make this some draggable bar thing */

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
    fun render()
    {
        renderer.fill_screen(curr_bg_color.value);
        if (tool.group == ToolGroup.DRAW) {
            Log.d("tool:", "${tool.toString()}");
            if (tool == DrawTool.ERASER) {
                drawing.record_strokes(Color.White, curr_width.value);
            } else if (tool == DrawTool.PENCIL) {
                drawing.record_strokes(Color.Gray, curr_width.value * 0.3f);
            } else if (tool == DrawTool.MARKER) {
                drawing.record_strokes(curr_color.value, curr_width.value);
            }
        }
        renderer.draw_strokes();
    }
}

@Composable
fun andrawd()
{
    val app = remember { Andrawd() };
    app.render();
    toolbar(app);
}
