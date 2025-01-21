package org.artemzhuravlov.clarity.data.repository.documents

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.storage.storage

class DocumentsRepository : IDocumentsRepository {
    override suspend fun getPdfDocumentUrl(
        policyId: String,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            val storageRef = Firebase.storage.reference
            val fileRef = storageRef.child("files/$policyId.pdf")

            onSuccess.invoke(fileRef.getDownloadUrl())
        } catch (e: Exception) {
            onError.invoke(e)
        }
    }
}