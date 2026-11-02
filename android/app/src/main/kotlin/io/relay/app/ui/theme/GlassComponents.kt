package io.relay.app.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The glass material: a translucent layered surface with a light-from-above
 * gradient fill and a specular top edge. Elevation is communicated by
 * translucency, not drop shadows (see docs/design). The layered-translucency
 * fallback used here must never appear broken; backdrop blur is layered on where
 * the platform supports it.
 */
fun Modifier.glassPanel(radius: Dp = 24.dp, raised: Boolean = false): Modifier = composed {
    val glass = LocalGlass.current
    val shape = RoundedCornerShape(radius)
    val fill = if (raised) glass.fillRaised else glass.fill
    // One flat fill. The diagonal fill gradient that used to sit here added a
    // second highlight on top of the specular edge below; depth comes from the
    // two elevations (fill, fillRaised) and that edge, nothing else.
    this
        .clip(shape)
        .background(fill)
        .border(
            width = 1.dp,
            brush = Brush.verticalGradient(
                listOf(glass.strokeHighlight, glass.stroke, glass.stroke),
            ),
            shape = shape,
        )
}

/**
 * The quiet backdrop the glass refracts: a deep neutral vertical gradient, a
 * tone shift inside one neutral and nothing else. It used to carry a 5% teal
 * radial bloom at the top; the accent now lives only on the thing to do.
 */
@Composable
fun RelayBackground(content: @Composable () -> Unit) {
    val glass = LocalGlass.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        glass.backgroundGradientTop,
                        glass.backgroundBase,
                        glass.backgroundGradientBottom,
                    )
                )
            ),
    ) {
        content()
    }
}
