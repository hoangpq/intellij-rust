/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.core.resolve.ref

import org.rust.lang.core.psi.RsPath
import org.rust.lang.core.psi.ext.RsElement
import org.rust.lang.core.types.BoundElement

interface RsPathReference : RsReference {
    override fun getElement(): RsPath

    fun resolvePublicOnly(): RsElement? = multiResolvePublicOnly().singleOrNull()

    fun multiResolvePublicOnly(): List<RsElement> = multiResolve()

    fun advancedResolve(): BoundElement<RsElement>? =
        resolve()?.let { BoundElement(it) }
}
