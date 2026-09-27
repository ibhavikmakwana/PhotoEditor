package ja.burhanrashid52.photoeditor

import android.content.Context
import android.widget.RelativeLayout
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import org.junit.Test

/**
 * Created by Burhanuddin Rashid on 15/05/21.
 *
 * @author <https:></https:>//github.com/burhanrashid52>
 */
@RunWith(RobolectricTestRunner::class)
class GraphicManagerTest {

    private var mContext = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testGraphicMangerAddViews() {
        val id = R.layout.view_photo_editor_text
        val childId = R.id.frmBorder
        val photoEditorView = PhotoEditorView(mContext)
        val graphicManager = GraphicManager(photoEditorView, PhotoEditorViewState())
        val graphic: Graphic = object : Graphic(
            context = mContext,
            layoutId = id,
            viewType = ViewType.TEXT,
            graphicManager = graphicManager
        ) {

        }
        graphicManager.addView(graphic)

        // NOTE(lucianocheng): Expect 4 views: Image, Filter, Brush,
        //                     and the Graphic we just added.
        assertEquals(4, photoEditorView.childCount.toLong())
        assertNotNull(photoEditorView.findViewById(childId))
    }

    @Test
    fun testGraphicManagerAddViewsUsesCenterByDefault() {
        val photoEditorView = PhotoEditorView(mContext)
        val graphicManager = GraphicManager(photoEditorView, PhotoEditorViewState())
        val graphic = newTextGraphic(graphicManager)

        graphicManager.addView(graphic)

        val params = graphic.rootView.layoutParams as RelativeLayout.LayoutParams
        assertEquals(RelativeLayout.TRUE, params.getRule(RelativeLayout.CENTER_IN_PARENT))
    }

    @Test
    fun testGraphicManagerAddViewsUsesExplicitPositionWhenProvided() {
        val photoEditorView = PhotoEditorView(mContext)
        val graphicManager = GraphicManager(photoEditorView, PhotoEditorViewState())
        val graphic = newTextGraphic(graphicManager)
        val position = Position(x = 42, y = 84)

        graphicManager.addView(graphic, position)

        val params = graphic.rootView.layoutParams as RelativeLayout.LayoutParams
        assertEquals(42, params.leftMargin)
        assertEquals(84, params.topMargin)
        assertEquals(RelativeLayout.TRUE, params.getRule(RelativeLayout.ALIGN_PARENT_TOP))
        assertFalse(params.getRule(RelativeLayout.CENTER_IN_PARENT) == RelativeLayout.TRUE)
    }

    private fun newTextGraphic(graphicManager: GraphicManager): Graphic {
        return object : Graphic(
            context = mContext,
            layoutId = R.layout.view_photo_editor_text,
            viewType = ViewType.TEXT,
            graphicManager = graphicManager
        ) {}
    }
}
