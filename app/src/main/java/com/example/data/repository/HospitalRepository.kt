package com.example.data.repository

import com.example.data.local.AppointmentDao
import com.example.data.local.AppointmentEntity
import com.example.data.models.BloodStock
import com.example.data.models.Department
import com.example.data.models.Doctor
import com.example.data.models.HospitalData
import com.example.data.models.Testimonial
import kotlinx.coroutines.flow.Flow
import java.util.Random

class HospitalRepository(private val appointmentDao: AppointmentDao) {

    fun getDepartments(): List<Department> = HospitalData.departments

    fun getDepartmentById(id: String): Department? =
        HospitalData.departments.find { it.id.equals(id, ignoreCase = true) }

    fun getDoctors(): List<Doctor> = HospitalData.doctors

    fun getDoctorsByDepartment(deptId: String): List<Doctor> {
        if (deptId.isBlank() || deptId.equals("all", ignoreCase = true)) {
            return HospitalData.doctors
        }
        return HospitalData.doctors.filter { it.departmentId.equals(deptId, ignoreCase = true) }
    }

    fun getDoctorById(id: String): Doctor? =
        HospitalData.doctors.find { it.id.equals(id, ignoreCase = true) }

    fun getBloodStocks(): List<BloodStock> = HospitalData.bloodStocks

    fun getTestimonials(): List<Testimonial> = HospitalData.testimonials

    val allAppointments: Flow<List<AppointmentEntity>> = appointmentDao.getAllAppointments()

    suspend fun bookAppointment(
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
    ): AppointmentEntity {
        val randomNum = 100 + Random().nextInt(900)
        val token = "RMH-SWAT-$randomNum"
        val entity = AppointmentEntity(
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
            status = "Confirmed"
        )
        val insertedId = appointmentDao.insertAppointment(entity)
        return entity.copy(id = insertedId)
    }

    suspend fun cancelAppointment(id: Long) {
        appointmentDao.updateAppointmentStatus(id, "Cancelled")
    }

    suspend fun deleteAppointment(id: Long) {
        appointmentDao.deleteAppointment(id)
    }
}
