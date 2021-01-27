/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.toml.crates.local

import org.jetbrains.annotations.TestOnly

class TestCratesLocalIndexServiceImpl : CratesLocalIndexService {
    var testCrates: Map<String, CargoRegistryCrate> = emptyMap()

    override fun isReady(): Boolean = true

    override fun getCrate(crateName: String): CargoRegistryCrate? = testCrates[crateName]
    override fun getAllCrateNames(): List<String> = testCrates.keys.toList()

    companion object {
        private val REAL_INSTANCE: CratesLocalIndexService by lazy {
            CratesLocalIndexServiceImpl().apply {
                updateCrates()
            }
        }

        /**
         * Simulates the behaviour of real service.
         * Returns the service object in local [REAL_INSTANCE], where it is being properly initialized on first access.
         */
        @TestOnly
        fun getRealInstance(): CratesLocalIndexService = REAL_INSTANCE
    }
}

@TestOnly
fun withMockedCrates(crates: Map<String, CargoRegistryCrate>, action: () -> Unit) {
    val resolver = CratesLocalIndexService.getInstance() as TestCratesLocalIndexServiceImpl
    val orgCrates = resolver.testCrates
    try {
        resolver.testCrates = crates
        action()
    } finally {
        resolver.testCrates = orgCrates
    }
}
