package org.artemzhuravlov.clarity.data.repository.policies

import org.artemzhuravlov.clarity.data.model.policies.Policy

interface IPoliciesRepository {
    suspend fun getPolicies(onSuccess: ((List<Policy>) -> Unit)?,
                            onError: ((Exception) -> Unit)?)
}
