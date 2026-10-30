package com.example.data.models

data class Department(
    val id: String,
    val name: String,
    val shortDescription: String,
    val fullDescription: String,
    val iconName: String,
    val headDoctor: String,
    val services: List<String>,
    val opdTimings: String,
    val floorLocation: String,
    val activeBeds: Int,
    val totalDoctors: Int
)

data class Doctor(
    val id: String,
    val name: String,
    val title: String,
    val departmentId: String,
    val departmentName: String,
    val degrees: String,
    val experienceYears: Int,
    val consultationFee: Int,
    val availableDays: String,
    val availableSlots: List<String>,
    val rating: Float,
    val reviewsCount: Int,
    val bio: String,
    val roomNumber: String,
    val isAvailableToday: Boolean = true
)

data class BloodStock(
    val bloodGroup: String,
    val unitsAvailable: Int,
    val status: String // "Available", "Low Stock", "Critical"
)

data class Testimonial(
    val id: String,
    val patientName: String,
    val location: String,
    val rating: Int,
    val department: String,
    val comment: String,
    val date: String
)
