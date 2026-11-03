package com.example.andrawd
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput

class Stroke(val color: Color,
             val width: Float)
{
    private val _points = mutableListOf<Offset>();
    val points: List<Offset> get() = _points;

    fun add_point(p: Offset)
    {
        _points.add(p);
    }
}

class Drawing()
{
    private val _strokes = mutableListOf<Stroke>();
    val strokes: List<Stroke> get() = _strokes;

    fun add_stroke(s: Stroke)
    {
        _strokes.add(s);
    }

    @Composable
    fun record_strokes()
    {
        Canvas(modifier = Modifier.fillMaxSize()
                                  .pointerInput(Unit)
                                  {
                                      detectTapGestures(
                                          onTap = { pos: Offset -> Log.d("input", "tap $pos");  },
                                          onLongPress = { pos: Offset -> Log.d("input", "long press $pos"); }
                                      )
                                      detectDragGestures(
                                          onDragStart = { pos: Offset -> Log.d("input", "start $pos"); },
                                          onDrag = { change, _ -> Log.d("input", "drag ${change.position}")},
                                          onDragEnd    = { Log.d("input", "end"); },
                                          onDragCancel = { Log.d("input", "cancel"); });

                                  }
        ) {}
    }
}

class Renderer(drawing: Drawing)
{
    private val _drawing: Drawing = drawing;

    @Composable
    fun fill_screen(c: Color)
    {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(color = c,
                     size = size);
        }
    }

    @Composable
    fun draw_strokes()
    {
        val strokes = _drawing.strokes;
        for (s in strokes) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawPoints(s.points, PointMode.Lines, Color.Black, 1f, StrokeCap.Butt,
                           null, 100f, null,  BlendMode.Color);
            }
        }
    }
}