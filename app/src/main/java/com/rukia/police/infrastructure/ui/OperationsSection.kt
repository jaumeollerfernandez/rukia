package com.rukia.police.infrastructure.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import com.rukia.phone.Kit
import com.rukia.phone.RukiaIcons
import com.rukia.police.domain.model.Operation
import com.rukia.police.domain.model.OperationsBoard
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private fun clock(millis: Long) = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HH:mm"))

/**
 * Field operations: the squad available right now and where it can go (tapping one asks [onPick] to confirm),
 * then every squad already sent. Says so when no squad is free.
 */
@Composable
internal fun OperationsSection(board: OperationsBoard, labels: Map<String, String>, onPick: (Operation) -> Unit) {
    val squad = board.squad
    val footer = if (squad == null) "No officers are free right now. Squads become available at set times during the case."
        else "${squad.label}, available until ${board.squadUntil?.let(::clock) ?: "—"}. One squad, one place: choose carefully."
    Section("Operations", footer = footer) {
        if (squad == null && board.dispatched.isEmpty()) NoSquadRow()
        board.operations.forEachIndexed { i, op ->
            if (i > 0) RowDivider()
            val patrol = op.type == "patrol"
            ActionRow(
                if (patrol) RukiaIcons.Shield else RukiaIcons.Search, if (patrol) PoliceBlue else Kit.Tint,
                op.label, if (patrol) "Send a patrol" else "Send officers to inspect",
            ) { onPick(op) }
        }
        board.dispatched.forEachIndexed { i, d ->
            if (i > 0 || board.operations.isNotEmpty()) RowDivider()
            ActionRow(RukiaIcons.Check, Kit.Accept, labels[d.operation] ?: d.operation, "Squad sent at ${clock(d.at)}", trailing = {}) {}
        }
    }
}

@Composable
private fun ColumnScope.NoSquadRow() =
    ActionRow(RukiaIcons.Shield, LocalPolice.current.chevron, "No squad available", "Wait for the next shift", trailing = {}) {}
