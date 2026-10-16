package com.example.andrawd

enum class ui_kind_e {
    CURSOR,
    DRAW,
    SHAPE,
    LINE,
    TEXT,
    COLOR,
}

/*
 * Tagged union sort of thing.
 * ui_kind_e is the top level enum, it describes a toolbar button.
 * ui_state_e is the active tool within, and .kind refers back to its kind
 * For example, draw_state_e has states {PENCIL, ERASER, MARKER}, and has kind ui_kind_e.DRAW
 */
sealed interface ui_state_e { val kind : ui_kind_e; }

data object cursor_state : ui_state_e {
    override val kind = ui_kind_e.CURSOR;
}

enum class draw_state_e : ui_state_e {
    PENCIL,
    ERASER,
    MARKER;
    override val kind = ui_kind_e.DRAW;
}

enum class shape_state_e : ui_state_e {
    SQUARE,
    CIRCLE,
    TRIANGLE;
    override val kind = ui_kind_e.SHAPE;
}

enum class line_state_e : ui_state_e {
    LINE,
    JOIN,
    BEZIER;
    override val kind = ui_kind_e.LINE;
}

enum class text_state_e : ui_state_e{
    TEXTBOX,
    HEADER,
    PARAGRAPH;
    override val kind = ui_kind_e.TEXT;
}

enum class color_state_e : ui_state_e {
    COLOR_1,
    COLOR_2,
    COLOR_3;
    override val kind = ui_kind_e.COLOR;
}

fun states_of_k(k: ui_kind_e): List<ui_state_e> = when (k) {
    ui_kind_e.CURSOR -> listOf(cursor_state)
    ui_kind_e.DRAW   -> draw_state_e.entries
    ui_kind_e.SHAPE  -> shape_state_e.entries
    ui_kind_e.LINE   -> line_state_e.entries
    ui_kind_e.TEXT   -> text_state_e.entries
    ui_kind_e.COLOR  -> color_state_e.entries
}
