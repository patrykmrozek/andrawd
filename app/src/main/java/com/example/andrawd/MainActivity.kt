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
    var tool by mutableStateOf<tool_e>(cursor_tool)
        private set;

    private val last_tool = mutableStateMapOf<tool_group_e, tool_e>();

    fun set_tool(new_tool: tool_e)
    {
        tool = new_tool;
        last_tool[new_tool.group] = tool;
    }

    /*
     * searches the last_tool map for last tool for the tool_group, if found set it,
     * otherwise set the first item in the tool_groups' tools as the current tool;
     */
    fun select_tool_group(tool_group: tool_group_e)
    {
        set_tool(last_tool[tool_group] ?: tool_group.tools.first());
    }
}

@Composable
fun andrawd()
{
    val app = remember { Andrawd() };
    toolbar(app);
}
