package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tokenNumber: String,
    val patientName: String,
    val patientPhone: String,
    val patientAge: String,
    val patientGender: String,
    val departmentId: String,
    val departmentName: String,
    val doctorId: String,
    val doctorName: String,
    val appointmentDate: String,
    val appointmentTimeSlot: String,
    val symptoms: String,
    val status: String, // "Confirmed", "Completed", "Cancelled"
    val timestamp: Long = System.currentTimeMillis()
)
