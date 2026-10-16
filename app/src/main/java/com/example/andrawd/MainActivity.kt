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
