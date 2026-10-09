package com.rukia.phone.domain.model

/** The home screen: a [COLUMNS] x [ROWS] grid where each app has a cell (0 until COLUMNS * ROWS, row by row). */
object HomeLayout {
    const val COLUMNS = 4
    const val ROWS = 6
    private const val CELLS = COLUMNS * ROWS

    /** Each app's cell: its [saved] cell if it has one, else the first free cell. Apps that don't fit are left out. */
    fun arrange(appIds: List<String>, saved: Map<String, Int>): Map<String, Int> {
        val cells = saved.filter { (id, cell) -> id in appIds && cell in 0 until CELLS }.toMutableMap()
        for (id in appIds) if (id !in cells) (0 until CELLS).firstOrNull { it !in cells.values }?.let { cells[id] = it }
        return cells
    }

    /** [app] moved to cell [to]; the app that was there, if any, takes [app]'s old cell. */
    fun move(cells: Map<String, Int>, app: String, to: Int): Map<String, Int> {
        val from = cells.getValue(app)
        val other = cells.entries.firstOrNull { it.value == to }?.key
        return cells + (app to to) + listOfNotNull(other?.let { it to from })
    }
}
