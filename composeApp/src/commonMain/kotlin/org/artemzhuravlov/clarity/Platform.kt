package org.artemzhuravlov.clarity

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform