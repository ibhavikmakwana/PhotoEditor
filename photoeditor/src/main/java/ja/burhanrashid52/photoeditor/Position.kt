package ja.burhanrashid52.photoeditor

import androidx.annotation.Px

/**
 * Initial position for an overlay inside the [PhotoEditorView].
 *
 * Coordinates are expressed in pixels from the top-left corner of the editor.
 */
data class Position(
    @Px val x: Int,
    @Px val y: Int
)
