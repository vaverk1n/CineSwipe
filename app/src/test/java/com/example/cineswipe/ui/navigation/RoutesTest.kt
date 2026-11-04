package com.example.cineswipe.ui.navigation

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class RoutesTest {

    @Test
    fun `when tabs are listed, then routes are unique and order matches design`() {
        val routes = BottomTab.entries.map { it.route }

        assertEquals(routes.distinct(), routes)
        assertEquals(
            listOf(Routes.Swipe, Routes.Collections, Routes.Lists, Routes.More),
            routes,
        )
    }

    @Test
    fun `when details routes are compared, then equality depends on movie id`() {
        assertEquals(Routes.Details(42), Routes.Details(42))
        assertNotEquals(Routes.Details(42), Routes.Details(43))
    }
}