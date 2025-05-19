package com.vsulimov.memos.reducer

import com.vsulimov.memos.action.ConfigureServerAction
import com.vsulimov.memos.state.ScreenState
import com.vsulimov.redux.TypedReducer

/**
 * A reducer responsible for handling [ConfigureServerAction] and updating the [ScreenState.ConfigureServer].
 * This object implements the [TypedReducer] interface, processing actions of type [ConfigureServerAction]
 * to manage server configuration state, including URL updates and error handling.
 */
object ConfigureServerReducer : TypedReducer<ConfigureServerAction, ScreenState.ConfigureServer>(
    ConfigureServerAction::class.java
) {

    /**
     * Reduces the given [action] and current [state] to produce a new [ScreenState.ConfigureServer].
     * Handles URL changes, error setting, error clearing, and ignores unrecognized actions.
     *
     * @param action The [ConfigureServerAction] to process.
     * @param state The current [ScreenState.ConfigureServer] before reduction.
     * @return The updated [ScreenState.ConfigureServer] after applying the action.
     */
    override fun reduceTyped(
        action: ConfigureServerAction,
        state: ScreenState.ConfigureServer
    ): ScreenState.ConfigureServer = when (action) {
        is ConfigureServerAction.ServerUrlChanged -> {
            state.copy(serverUrl = action.newUrl)
        }

        is ConfigureServerAction.SetServerUrlError -> {
            state.copy(serverUrlErrorResId = action.serverUrlErrorResId)
        }

        ConfigureServerAction.ClearServerUrlError -> {
            state.copy(serverUrlErrorResId = null)
        }

        ConfigureServerAction.Next -> {
            state // No state change for Next action
        }
    }
}
