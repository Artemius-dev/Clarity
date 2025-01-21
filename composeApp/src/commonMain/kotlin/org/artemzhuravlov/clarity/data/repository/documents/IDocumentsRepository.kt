package org.artemzhuravlov.clarity.data.repository.documents

interface IDocumentsRepository {
    suspend fun getPdfDocumentUrl(policyId: String, onSuccess: (String) -> Unit, onError: (Exception) -> Unit)
}