package com.example.andrawd

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset

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
}