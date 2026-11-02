package com.example.andrawd

/*
 * Tagged union sort of thing.
 * tool_group_e is the top level enum, it describes a toolbar button.
 * tool_e is the active tool within, and .tool_group refers back to its tool_group
 * tool -> tool group
 *      draw_tool_e.PENCIL.group  = tool_group_e.DRAW
 *      shape_tool_e.SQUARE.group = tool_group_e.SHAPE
 *      ...
 *
 * tool group -> tool
 *      tool_group_e.DRAW.tools = (tool_e){PENCIL, ERASER, MARKER}
 *      ...
 */

enum class tool_group_e {
    CURSOR,
    DRAW,
    SHAPE,
    LINE,
    TEXT,
    COLOR;

    val tools: List<tool_e> get() = this.get_tools();
    val label: String get() = this.get_label();

    private fun get_tools(): List<tool_e> = when (this)
    {
        CURSOR -> listOf(cursor_tool)
        DRAW   -> draw_tool_e.entries
        SHAPE  -> shape_tool_e.entries
        LINE   -> line_tool_e.entries
        TEXT   -> text_tool_e.entries
        COLOR  -> color_tool_e.entries
    }

    private fun get_label(): String = when (this)
    {
        CURSOR -> ">"
        DRAW   -> "D"
        SHAPE  -> "S"
        LINE   -> "L"
        TEXT   -> "T"
        COLOR  -> "C"
   }
}

sealed interface tool_e {
    val group : tool_group_e;
}

data object cursor_tool : tool_e {
    override val group = tool_group_e.CURSOR;
}

enum class draw_tool_e : tool_e {
    PENCIL,
    ERASER,
    MARKER;

    override val group = tool_group_e.DRAW;
}

enum class shape_tool_e : tool_e {
    SQUARE,
    CIRCLE,
    TRIANGLE;

    override val group = tool_group_e.SHAPE;
}

enum class line_tool_e : tool_e {
    LINE,
    JOIN,
    BEZIER;

    override val group = tool_group_e.LINE;
}

enum class text_tool_e : tool_e{
    TEXTBOX,
    HEADER,
    PARAGRAPH;

    override val group = tool_group_e.TEXT;
}

enum class color_tool_e : tool_e {
    COLOR_1,
    COLOR_2,
    COLOR_3;

    override val group = tool_group_e.COLOR;
}
