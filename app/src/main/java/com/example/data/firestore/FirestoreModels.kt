package com.example.data.firestore

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FirestoreAppointment(
    @DocumentId val id: String = "",
    val userId: String = "",
    val tokenNumber: String = "",
    val patientName: String = "",
    val patientPhone: String = "",
    val patientAge: String = "",
    val patientGender: String = "",
    val departmentId: String = "",
    val departmentName: String = "",
    val doctorId: String = "",
    val doctorName: String = "",
    val appointmentDate: String = "",
    val appointmentTimeSlot: String = "",
    val symptoms: String = "",
    val status: String = "Confirmed",
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreInquiry(
    @DocumentId val id: String = "",
    val userId: String = "",
    val name: String = "",
    val contact: String = "",
    val message: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreBloodRequest(
    @DocumentId val id: String = "",
    val userId: String = "",
    val bloodGroup: String = "",
    val status: String = "Pending",
    @ServerTimestamp val createdAt: Timestamp? = null
)
