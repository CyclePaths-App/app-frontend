@file:OptIn(InternalResourceApi::class)

package cyclepaths.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/cyclepaths.composeapp.generated.resources/"

internal val Res.drawable.WelcomeBackground: DrawableResource by lazy {
      DrawableResource("drawable:WelcomeBackground", setOf(
        ResourceItem(setOf(), "${MD}drawable/WelcomeBackground.jpg", -1, -1),
      ))
    }

internal val Res.drawable.badge: DrawableResource by lazy {
      DrawableResource("drawable:badge", setOf(
        ResourceItem(setOf(), "${MD}drawable/badge.png", -1, -1),
      ))
    }

internal val Res.drawable.compose_multiplatform: DrawableResource by lazy {
      DrawableResource("drawable:compose_multiplatform", setOf(
        ResourceItem(setOf(), "${MD}drawable/compose-multiplatform.xml", -1, -1),
      ))
    }

internal val Res.drawable.cycleoption: DrawableResource by lazy {
      DrawableResource("drawable:cycleoption", setOf(
        ResourceItem(setOf(), "${MD}drawable/cycleoption.png", -1, -1),
      ))
    }

internal val Res.drawable.eye: DrawableResource by lazy {
      DrawableResource("drawable:eye", setOf(
        ResourceItem(setOf(), "${MD}drawable/eye.png", -1, -1),
      ))
    }

internal val Res.drawable.gear: DrawableResource by lazy {
      DrawableResource("drawable:gear", setOf(
        ResourceItem(setOf(), "${MD}drawable/gear.png", -1, -1),
      ))
    }

internal val Res.drawable.loginBKG: DrawableResource by lazy {
      DrawableResource("drawable:loginBKG", setOf(
        ResourceItem(setOf(), "${MD}drawable/loginBKG.png", -1, -1),
      ))
    }

internal val Res.drawable.mapoption: DrawableResource by lazy {
      DrawableResource("drawable:mapoption", setOf(
        ResourceItem(setOf(), "${MD}drawable/mapoption.jpg", -1, -1),
      ))
    }

internal val Res.drawable.viewheatmapoption: DrawableResource by lazy {
      DrawableResource("drawable:viewheatmapoption", setOf(
        ResourceItem(setOf(), "${MD}drawable/viewheatmapoption.png", -1, -1),
      ))
    }

internal val Res.drawable.visible: DrawableResource by lazy {
      DrawableResource("drawable:visible", setOf(
        ResourceItem(setOf(), "${MD}drawable/visible.png", -1, -1),
      ))
    }

internal val Res.drawable.walkoption: DrawableResource by lazy {
      DrawableResource("drawable:walkoption", setOf(
        ResourceItem(setOf(), "${MD}drawable/walkoption.jpg", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("WelcomeBackground", Res.drawable.WelcomeBackground)
  map.put("badge", Res.drawable.badge)
  map.put("compose_multiplatform", Res.drawable.compose_multiplatform)
  map.put("cycleoption", Res.drawable.cycleoption)
  map.put("eye", Res.drawable.eye)
  map.put("gear", Res.drawable.gear)
  map.put("loginBKG", Res.drawable.loginBKG)
  map.put("mapoption", Res.drawable.mapoption)
  map.put("viewheatmapoption", Res.drawable.viewheatmapoption)
  map.put("visible", Res.drawable.visible)
  map.put("walkoption", Res.drawable.walkoption)
}
