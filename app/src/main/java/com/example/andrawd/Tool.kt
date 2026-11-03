package com.example.andrawd

import androidx.compose.ui.graphics.Color

/*
 * Tagged union sort of thing.
 * ToolGroup is the top level enum, it describes a toolbar button.
 * Tool is the active tool within, and .tool_group refers back to its tool_group
 * tool -> tool group
 *      drawTool.PENCIL.group  = ToolGroup.DRAW
 *      shapeTool.SQUARE.group = ToolGroup.SHAPE
 *      ...
 *
 * tool group -> tool
 *      ToolGroup.DRAW.tools = (Tool){PENCIL, ERASER, MARKER}
 *      ...
 */

enum class ToolGroup {
    CURSOR,
    DRAW,
    SHAPE,
    LINE,
    TEXT;

    val tools: List<Tool> get() = this.get_tools();
    val label: String get() = this.get_label();

    private fun get_tools(): List<Tool> = when (this)
    {
        CURSOR -> listOf(CursorTool)
        DRAW   -> DrawTool.entries
        SHAPE  -> ShapeTool.entries
        LINE   -> LineTool.entries
        TEXT   -> TextTool.entries
    }

    private fun get_label(): String = when (this)
    {
        CURSOR -> ">"
        DRAW   -> "D"
        SHAPE  -> "S"
        LINE   -> "L"
        TEXT   -> "T"
   }
}

sealed interface Tool {
    val group : ToolGroup;
}

data object CursorTool : Tool {
    override val group = ToolGroup.CURSOR;
}

enum class DrawTool : Tool {
    PENCIL,
    ERASER,
    MARKER;

    override val group = ToolGroup.DRAW;
}

enum class ShapeTool : Tool {
    SQUARE,
    CIRCLE,
    TRIANGLE;

    override val group = ToolGroup.SHAPE;
}

enum class LineTool : Tool {
    LINE,
    JOIN,
    BEZIER;

    override val group = ToolGroup.LINE;
}

enum class TextTool : Tool{
    TEXTBOX,
    HEADER,
    PARAGRAPH;

    override val group = ToolGroup.TEXT;
}