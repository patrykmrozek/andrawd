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
    var state by mutableStateOf<ui_state_e>(cursor_state)
        private set;

    private val last_state = mutableStateMapOf<ui_kind_e, ui_state_e>();

    fun set_state(new_state: ui_state_e)
    {
        state = new_state;
        last_state[new_state.kind] = state;
    }

    /*
     * searches the last_state map for last state for the kind, if found set it,
     * otherwise set the first item in the kinds' states as the current state;
     */
    fun select_kind(kind: ui_kind_e)
    {
        set_state(last_state[kind] ?: states_of_k(kind).first());
    }
}

@Composable
fun andrawd()
{
    val app = remember { Andrawd() };
    toolbar(app);
}
