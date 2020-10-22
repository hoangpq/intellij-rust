/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.sdk.flavors

import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.io.FileUtil
import com.intellij.util.io.exists
import com.intellij.util.io.isDirectory
import org.rust.stdext.list
import org.rust.stdext.toPath
import java.nio.file.Path

object WinSdkFlavor : RsSdkFlavor {
    override fun isApplicable(): Boolean = SystemInfo.isWindows

    override fun getHomePathCandidates(): List<Path> {
        val programFiles = System.getenv("ProgramFiles")?.toPath() ?: return emptyList()
        if (!programFiles.exists() || !programFiles.isDirectory()) return emptyList()
        return programFiles.list()
            .filter { it.isDirectory() }
            .filter {
                val name = FileUtil.getNameWithoutExtension(it.fileName.toString())
                name.toLowerCase().startsWith("rust")
            }
            .map { it.resolve("bin") }
            .filter { it.isDirectory() }
            .toList()
    }
}