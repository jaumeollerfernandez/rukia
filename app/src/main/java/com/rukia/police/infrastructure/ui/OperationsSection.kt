package com.rukia.police.infrastructure.ui

import androidx.compose.ui.res.stringResource
import com.rukia.R
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
    val footer = if (squad == null) stringResource(R.string.ops_none_free)
        else stringResource(R.string.ops_squad_until, squad.label, board.squadUntil?.let(::clock) ?: "—")
    Section(stringResource(R.string.operations), footer = footer) {
        if (squad == null && board.dispatched.isEmpty()) NoSquadRow()
        board.operations.forEachIndexed { i, op ->
            if (i > 0) RowDivider()
            val patrol = op.type == "patrol"
            ActionRow(
                if (patrol) RukiaIcons.Shield else RukiaIcons.Search, if (patrol) PoliceBlue else Kit.Tint,
                op.label, stringResource(if (patrol) R.string.send_patrol else R.string.send_inspect),
            ) { onPick(op) }
        }
        board.dispatched.forEachIndexed { i, d ->
            if (i > 0 || board.operations.isNotEmpty()) RowDivider()
            ActionRow(RukiaIcons.Check, Kit.Accept, labels[d.operation] ?: d.operation, stringResource(R.string.squad_sent_at, clock(d.at)), trailing = {}) {}
        }
    }
}

@Composable
private fun ColumnScope.NoSquadRow() =
    ActionRow(RukiaIcons.Shield, LocalPolice.current.chevron, stringResource(R.string.no_squad), stringResource(R.string.next_shift), trailing = {}) {}
