import kotlinx.datetime.DayOfWeek

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )
    return emailRegex.matches(email)
}

fun isPasswordValid(password: String): Boolean {
    val minLength = 8
    val hasUppercase = password.any { it.isUpperCase() }
    val hasLowercase = password.any { it.isLowerCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSpecialChar = password.any { "!@#$%^&*()_+-=[]|,./?><".contains(it) }

    return password.length >= minLength && hasUppercase && hasLowercase && hasDigit && hasSpecialChar
}

fun DayOfWeek.localizeToUkrainian(): String {
    val dayNamesInUkrainian = listOf(
        "ПН",
        "ВТ",
        "СР",
        "ЧТ",
        "ПТ",
        "СБ",
        "НД"
    )
    return dayNamesInUkrainian[this.ordinal]
}