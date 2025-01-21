package org.artemzhuravlov.clarity.data.repository.config

interface IConfigRepository {
    suspend fun getPrivacyPolicy(onSuccess: ((String) -> Unit)?,
                                 onError: ((Exception) -> Unit)?)

    suspend fun getTermsAndConditions(onSuccess: ((String) -> Unit)?,
                                 onError: ((Exception) -> Unit)?)
}
