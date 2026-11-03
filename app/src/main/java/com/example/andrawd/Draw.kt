package com.example.andrawd
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

class Stroke(val color: Color,
             val width: Float)
{
    private val _points = mutableStateListOf<Offset>();
    val points: List<Offset> get() = _points;

    fun add_point(p: Offset)
    {
        _points.add(p);
    }
}

class Drawing() {
    private val _strokes =  mutableStateListOf<Stroke>();
    val strokes: List<Stroke> get() = _strokes;

    private fun add_stroke(s: Stroke)
    {
        _strokes.add(s);
    }

    @Composable
    fun record_strokes(curr_color: Color)
    {
        val width = 5f; /* need to store this in Andrawd w color */
        Canvas(modifier = Modifier.fillMaxSize()
                                  .pointerInput(Unit)
                                  {
                                      awaitPointerEventScope {
                                          var stroke = Stroke(curr_color, width);
                                          while(true) {
                                              val event = awaitPointerEvent();
                                              val p = event.changes.first().position;
                                              Log.d("position", "$p");
                                              when (event.type) {
                                                  PointerEventType.Press   -> {
                                                      stroke = Stroke(curr_color, width);
                                                      stroke.add_point(p);
                                                      add_stroke(stroke);
                                                  }
                                                  PointerEventType.Move    -> stroke.add_point(p);
                                              }
                                          }
                                      }
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
        val strokes =  _drawing.strokes;
        Log.d("strokes", "$strokes");
        Log.d("strokes size", "${strokes.size}");
        for (s in strokes) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val point_mode: PointMode = if (s.points.size > 1) PointMode.Polygon
                                            else                   PointMode.Points;
                Log.d("stroke points: ", "${s.points}");
                Log.d("stroke color: ",  "${s.color}");
                Log.d("stroke width: ",  "${s.width}");
                Log.d("stroke_size: ",   "${s.points.size}");
                drawPoints(s.points, point_mode, s.color, s.width, StrokeCap.Round);
            }
        }
    }
}