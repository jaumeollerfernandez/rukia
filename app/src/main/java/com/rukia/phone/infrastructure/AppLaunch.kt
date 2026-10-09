package com.rukia.phone.infrastructure

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Requests to open an app on the phone from outside the game, e.g. a tapped notification. The game loads the
 * request's case, that case's phone consumes the [request] and opens its app, passing [argument] on to it
 * (for the chat app: the chat id), which reads and clears it.
 */
object AppLaunch {
    private const val EXTRA_CASE = "launch_case"
    private const val EXTRA_APP = "launch_app"
    private const val EXTRA_ARGUMENT = "launch_argument"

    /** A new instance per tap, so tapping the same notification twice is still noticed. */
    class Request(val caseId: String, val app: String, val argument: String?)

    var request by mutableStateOf<Request?>(null)

    /** For the app the phone just opened. */
    var argument by mutableStateOf<String?>(null)

    /** True while the game is on screen. Notifications use it to stay quiet about what the player is already seeing. */
    @Volatile var foreground = false

    /** Intent that brings the game to the front and opens [app] with [argument] in case [caseId]. */
    fun intent(context: Context, caseId: String, app: String, argument: String?): Intent =
        requireNotNull(context.packageManager.getLaunchIntentForPackage(context.packageName)).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(EXTRA_CASE, caseId)
            putExtra(EXTRA_APP, app)
            putExtra(EXTRA_ARGUMENT, argument)
        }

    fun handle(intent: Intent?) {
        val caseId = intent?.getStringExtra(EXTRA_CASE) ?: return
        val app = intent.getStringExtra(EXTRA_APP) ?: return
        request = Request(caseId, app, intent.getStringExtra(EXTRA_ARGUMENT))
        intent.removeExtra(EXTRA_CASE) // don't reopen it on activity recreation
    }
}
