package com.example.data.firestore

import android.content.Context
import com.example.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.Random
import kotlin.coroutines.cancellation.CancellationException

class FirestoreHospitalRepository(private val db: FirebaseFirestore) {

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    fun observeAppointments(userId: String): Flow<List<FirestoreAppointment>> {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        return db.collection("users").document(userId)
            .collection("appointments")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(
                    FirestoreAppointment::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
            }
    }

    suspend fun bookAppointment(
        userId: String,
        patientName: String,
        patientPhone: String,
        patientAge: String,
        patientGender: String,
        departmentId: String,
        departmentName: String,
        doctorId: String,
        doctorName: String,
        appointmentDate: String,
        timeSlot: String,
        symptoms: String
    ): FirestoreAppointment {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        val randomNum = 100 + Random().nextInt(900)
        val token = "RMH-SWAT-$randomNum"

        val collectionRef = db.collection("users").document(userId).collection("appointments")
        val docRef = collectionRef.document()

        val appointment = FirestoreAppointment(
            id = docRef.id,
            userId = userId,
            tokenNumber = token,
            patientName = patientName.trim(),
            patientPhone = patientPhone.trim(),
            patientAge = patientAge.trim(),
            patientGender = patientGender,
            departmentId = departmentId,
            departmentName = departmentName,
            doctorId = doctorId,
            doctorName = doctorName,
            appointmentDate = appointmentDate,
            appointmentTimeSlot = timeSlot,
            symptoms = symptoms.trim(),
            status = "Confirmed",
            createdAt = null
        )

        try {
            docRef.set(appointment).await()
            return appointment
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            throw e
        }
    }

    suspend fun cancelAppointment(userId: String, appointmentId: String) {
        require(userId.isNotBlank() && appointmentId.isNotBlank()) { "Invalid IDs" }
        val docRef = db.collection("users").document(userId)
            .collection("appointments").document(appointmentId)
        try {
            docRef.update("status", "Cancelled").await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            throw e
        }
    }

    suspend fun deleteAppointment(userId: String, appointmentId: String) {
        require(userId.isNotBlank() && appointmentId.isNotBlank()) { "Invalid IDs" }
        val docRef = db.collection("users").document(userId)
            .collection("appointments").document(appointmentId)
        try {
            docRef.delete().await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            throw e
        }
    }

    suspend fun submitInquiry(userId: String, name: String, contact: String, message: String) {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        val docRef = db.collection("users").document(userId)
            .collection("inquiries").document()
        val inquiry = FirestoreInquiry(
            id = docRef.id,
            userId = userId,
            name = name.trim(),
            contact = contact.trim(),
            message = message.trim(),
            createdAt = null
        )
        try {
            docRef.set(inquiry).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            throw e
        }
    }

    suspend fun submitBloodRequest(userId: String, bloodGroup: String) {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        val docRef = db.collection("users").document(userId)
            .collection("bloodRequests").document()
        val request = FirestoreBloodRequest(
            id = docRef.id,
            userId = userId,
            bloodGroup = bloodGroup,
            status = "Pending",
            createdAt = null
        )
        try {
            docRef.set(request).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            throw e
        }
    }
}
