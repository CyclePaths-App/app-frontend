@file:OptIn(InternalResourceApi::class)

package cyclepaths.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/cyclepaths.composeapp.generated.resources/"

internal val Res.font.josefin_sans_bold: FontResource by lazy {
      FontResource("font:josefin_sans_bold", setOf(
        ResourceItem(setOf(), "${MD}font/josefin_sans_bold.ttf", -1, -1),
      ))
    }

internal val Res.font.josefin_sans_italic: FontResource by lazy {
      FontResource("font:josefin_sans_italic", setOf(
        ResourceItem(setOf(), "${MD}font/josefin_sans_italic.ttf", -1, -1),
      ))
    }

internal val Res.font.josefin_sans_regular: FontResource by lazy {
      FontResource("font:josefin_sans_regular", setOf(
        ResourceItem(setOf(), "${MD}font/josefin_sans_regular.ttf", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainFont0Resources(map: MutableMap<String, FontResource>) {
  map.put("josefin_sans_bold", Res.font.josefin_sans_bold)
  map.put("josefin_sans_italic", Res.font.josefin_sans_italic)
  map.put("josefin_sans_regular", Res.font.josefin_sans_regular)
}
